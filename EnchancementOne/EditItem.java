/*
 * Programmer: Laisha Ramos.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class EditItem extends AppCompatActivity {
    private ImageView back_arrow;
    private Button cancel_btn;
    private Button save_btn;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 102;
    private Spinner allItemsSpinner;
    private ImageView itemImageView;
    private Button captureImageButton, saveItemButton, cancelButton;
    private EditText itemNameEditText, itemDescriptionEditText, itemQuantityEditText;
    private Bitmap capturedImage;
    private DBHelper dbHelper;
private Context ctx = this;
    private int selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_layout);
        back_arrow = findViewById(R.id.back);
// Back button click listener.
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Initialize UI components.
        allItemsSpinner = findViewById(R.id.all_items);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemDescriptionEditText = findViewById(R.id.itemDescriptionEditText);
        itemQuantityEditText = findViewById(R.id.itemQuantityEditText);
        itemImageView = findViewById(R.id.itemimgview);
        captureImageButton = findViewById(R.id.captureImageButton);
        saveItemButton = findViewById(R.id.saveItemButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize DBHelper.
        dbHelper = new DBHelper(this);
// Spinner item selected listener.
        allItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onItemSelectedInSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing.
            }
        });

        // Get selected item position from intent.
        int selectedItemPosition = getIntent().getIntExtra("SELECTED_ITEM_POSITION", -1);
// If item position is valid, fetch item details and populate UI fields.
        if (selectedItemPosition != -1) {
            ItemModel selectedItem = dbHelper.getAllItems().get(selectedItemPosition);
            selectedItemId = selectedItem.getId();
            itemNameEditText.setText(selectedItem.getItemName());
            itemDescriptionEditText.setText(selectedItem.getItemDescription());
            itemQuantityEditText.setText(String.valueOf(selectedItem.getQuantity()));

            loadImage(selectedItem.getItemImageUrl());
        }

        // Set click listeners.
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
            }
        });

        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItemDetails();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
// Populate spinner with item names.
        populateSpinner();
    }

// Method to populate spinner with item names.
    private void populateSpinner() {
        List<ItemModel> itemList = dbHelper.getAllItems();
        String[] itemNames = new String[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            itemNames[i] = itemList.get(i).getItemName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        allItemsSpinner.setAdapter(adapter);
    }

    // Method to handle item selection in spinner.
    private void onItemSelectedInSpinner(int position) {
        List<ItemModel> itemList = dbHelper.getAllItems();
        if (position >= 0 && position < itemList.size()) {
            ItemModel selectedItem = itemList.get(position);
            selectedItemId = selectedItem.getId();

            itemNameEditText.setText(selectedItem.getItemName());
            itemDescriptionEditText.setText(selectedItem.getItemDescription());
            itemQuantityEditText.setText(String.valueOf(selectedItem.getQuantity()));

            loadImage(selectedItem.getItemImageUrl());
        }
    }
    // Method to load image into ImageView using Glide.
    private void loadImage(String imageUrl) {
        Glide.with(ctx)
                .load(imageUrl)
                .into(itemImageView);

    }
     // Method to update item details.
    private void updateItemDetails() {
        String itemName = itemNameEditText.getText().toString().trim();
        String itemDescription = itemDescriptionEditText.getText().toString().trim();
        String quantityStr = itemQuantityEditText.getText().toString().trim();

        if (!itemName.isEmpty() && !quantityStr.isEmpty() && capturedImage != null) {
            int quantity = Integer.parseInt(quantityStr);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            capturedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String imageUrl = saveImageToStorage(itemName, byteArray);

            dbHelper.updateItemDetails(selectedItemId, itemName, itemDescription, quantity, imageUrl);

            // Notify the user
            Toast.makeText(this, "Item details updated successfully", Toast.LENGTH_SHORT).show();

            // Optionally, navigate to the main activity or perform other actions
            finish();
        } else {
            Toast.makeText(this, "Please fill in all fields and capture an image", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to save image to storage.
    private String saveImageToStorage(String itemName, byte[] image) {
        try {
            String imageUrl = ImageUtils.saveImageToInternalStorage(this, itemName, image);
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
// Method to dispatch take picture intent.
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Method to handle activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                // Get the captured image as a Bitmap
                capturedImage = (Bitmap) data.getExtras().get("data");

                // Set the image to your ImageView
                ImageView itemImageView = findViewById(R.id.itemimgview);
                itemImageView.setImageBitmap(capturedImage);
            }
        }
    }

    private void checkAndRequestPermissions() {
        // Check if the app has camera and write external storage permissions.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // If permissions are not granted, request them.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permissons already granted, proceed with the camera capture
            dispatchTakePictureIntent();
        }
    }
// Method to handle permission requests result.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If the camera permission is granted, proceed with the camera capture.
                dispatchTakePictureIntent();
            } else {
                // If the camera permisson is denied, show a toast message.
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
//END