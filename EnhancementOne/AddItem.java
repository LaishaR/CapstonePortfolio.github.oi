/*
 * Programmer: Laisha Ramos.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 2
 *
 * Purpose: This class represents the activity for adding an item to the Inventory App. It includes functionality
 *          for capturing item details such as name, description, quantity, and an image. Additionally, it allows
 *          users to capture images using the device's camera, scan barcodes, and save item data to the database.
 * Issues: None
 *
 * Course Outcomes:
 * The AddItem class exhibits proficiency in Android app development by implementing features for capturing item details, images, and scanning barcodes. The integration of camera functionality, barcode scanning, and database operations aligns with the course objective of developing mobile applications with advanced user interactions and data management capabilities.
 * Functionality and Optimization:
 * The AddItem class provides comprehensive functionality for adding items to the Inventory App. Here's an overview of the implemented functionalities and optimizations:
 * 1.   Item Addition: Users can input item details such as name, description, and quantity via EditText fields.
 * 2.   Image Capture: The app allows users to capture item images using the device's camera, enhancing visual representation and identification of items.
 * 3.   Barcode Scanning: Integration with ZXing library enables barcode scanning functionality, facilitating efficient item identification and data input.
 * 4.   Database Interaction: Upon saving, item data including name, description, quantity, and image URL are stored in the SQLite database using DBHelper class methods, ensuring persistent storage and retrieval of item information.
 * 5.   Input Validation: User inputs are validated to ensure completeness and correctness, enhancing data integrity and user experience.
 * 6.   Permission Handling: Proper permission checks and requests are implemented for camera and storage access, ensuring compliance with Android permissions model and enhancing security.
 * Meeting Course Objectives:
 * - Mobile Application Development: The AddItem class demonstrates proficiency in mobile application development through its implementation of interactive features and user-friendly interfaces for capturing item details and images.
 * - Integration of External Libraries: Integration with ZXing library for barcode scanning showcases the ability to utilize third-party libraries to extend app functionality and user experience.
 * - Database Integration: Integration with DBHelper class for database operations fulfills the course objective of incorporating database management techniques into mobile app development, enabling efficient data storage and retrieval.
 */

package com.assignment.inventoryapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddItem extends AppCompatActivity {
    private DBHelper dbHelper;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 102;
    private Uri imageUri;
    private ImageView itemImageView;
    private Bitmap capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_layout);

        // Initialize the database helper.
        dbHelper = new DBHelper(this);

        // Find the views.
        final EditText itemNameEditText = findViewById(R.id.name);
        final EditText itemDescriptionEditText = findViewById(R.id.description);
        final EditText quantityEditText = findViewById(R.id.quantity);
        itemImageView = findViewById(R.id.itemimgview);
        Button captureImageButton = findViewById(R.id.captureImageButton);
        Button scanBarcodeButton = findViewById(R.id.scanBarcodeButton);
        Button saveItemButton = findViewById(R.id.saveItemButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        // Set click listeners for buttons.
        captureImageButton.setOnClickListener(v -> checkAndRequestPermissions());
        scanBarcodeButton.setOnClickListener(v -> startBarcodeScanner());
        saveItemButton.setOnClickListener(v -> saveItem(itemNameEditText.getText().toString().trim(),
                itemDescriptionEditText.getText().toString().trim(),
                quantityEditText.getText().toString().trim()));
        cancelButton.setOnClickListener(v -> finish());
    }

    // The method to save the item to the database.
    private void saveItem(String itemName, String itemDescription, String quantityStr) {
        if (validateInputs(itemName, itemDescription, quantityStr)) {
            int quantity = Integer.parseInt(quantityStr);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            capturedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String imageUrl = saveItemToDatabase(itemName, itemDescription, quantity, byteArray);

            if (imageUrl != null) {
                Toast.makeText(AddItem.this, "Item saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddItem.this, "Failed to save item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // The Method to validate user inputs.
    private boolean validateInputs(String itemName, String itemDescription, String quantityStr) {
        if (itemName.isEmpty() || itemDescription.isEmpty() || quantityStr.isEmpty() || capturedImage == null) {
            Toast.makeText(this, "Please fill in all fields and capture an image", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                Toast.makeText(this, "Quantity must be a positive integer", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid quantity format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Method to save the item image to the database.
    private String saveItemToDatabase(String itemName, String itemDescription, int quantity, byte[] image) {
        try {
            // Save image to internal storage.
            String imageUrl = ImageUtils.saveImageToInternalStorage(this, itemName, image);
            if (imageUrl != null) {
                // Open writable database.
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_ITEM_NAME, itemName);
                values.put(DBHelper.COLUMN_ITEM_DESCRIPTION, itemDescription);
                values.put(DBHelper.COLUMN_QUANTITY, quantity);
                values.put(DBHelper.COLUMN_ITEM_IMAGE_URL, imageUrl);

                // Insert item data into the database.
                long itemId = db.insert(DBHelper.TABLE_ITEMS, null, values);
                db.close();

                if (itemId != -1) {
                    return imageUrl;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to check and request permissions for camera and storage.
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission granted, start camera intent.
            dispatchTakePictureIntent();
        }
    }
    // Method to initiate barcode scanner.
    private void startBarcodeScanner() {
        new IntentIntegrator(this).initiateScan();
    }

    // Handling the result of the activity, including barcode scanning and image capture.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                // Retrieve the captured image as Bitmap.
                capturedImage = (Bitmap) data.getExtras().get("data");
                // Display the captured image in ImageView.
                itemImageView.setImageBitmap(capturedImage);
            }
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() != null) {
                    // Handle the scanned barcode result.
                    Toast.makeText(this, "Barcode Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                    // Additional processing for the scanned barcode can be implemented here.
                } else {
                    Toast.makeText(this, "Barcode scan canceled", Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    // Handling permission request results.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start camera intent.
                dispatchTakePictureIntent();
            } else {
                // Camera Permission denied, informed the user.
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to start the camera intent for capturing an image.
    private void dispatchTakePictureIntent() {
        // Create an intent to capture an image.
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Start the activity for capturing an image.
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
// END