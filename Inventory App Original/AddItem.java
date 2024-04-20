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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddItem extends AppCompatActivity {
    private ImageView back_arrow;
    private DBHelper dbHelper;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 102;
    private Uri imageUri;
    private ImageView itemImageView;
    private Bitmap capturedImage; // Store the captured image as a Bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_layout);
        back_arrow = findViewById(R.id.back);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dbHelper = new DBHelper(this);

        final EditText itemNameEditText = findViewById(R.id.name);
        final EditText itemDescriptionEditText = findViewById(R.id.description);
        final EditText quantityEditText = findViewById(R.id.quantity);
        itemImageView = findViewById(R.id.itemimgview);
        Button captureImageButton = findViewById(R.id.captureImageButton);
        Button saveItemButton = findViewById(R.id.saveItemButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
            }
        });

        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemNameEditText.getText().toString().trim();
                String itemDescription = itemDescriptionEditText.getText().toString().trim();
                String quantityStr = quantityEditText.getText().toString().trim();

                if (!itemName.isEmpty() && !quantityStr.isEmpty() && capturedImage != null) {
                    int quantity = Integer.parseInt(quantityStr);

                    // Convert the capturedImage to a byte array
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    capturedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    // Save the item with the image and get the image URL
                    String imageUrl = saveItem(itemName, itemDescription, quantity, byteArray);

                    if (imageUrl != null) {
                        Toast.makeText(AddItem.this, "Item saved successfully", Toast.LENGTH_SHORT).show();
                        // Optionally, navigate to the main activity or perform other actions
                        finish();
                    } else {
                        Toast.makeText(AddItem.this, "Failed to save item", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddItem.this, "Please fill in all fields and capture an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Optionally, handle cancel button action
                finish();
            }
        });
    }

    private String saveItem(String itemName, String itemDescription, int quantity, byte[] image) {
        // Save the image to internal storage and get the image URL
        String imageUrl = saveImageToStorage(itemName, image);

        if (imageUrl != null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_ITEM_NAME, itemName);
            values.put(DBHelper.COLUMN_ITEM_DESCRIPTION, itemDescription);
            values.put(DBHelper.COLUMN_QUANTITY, quantity);
            values.put(DBHelper.COLUMN_ITEM_IMAGE_URL, imageUrl);

            // Inserting Row
            long itemId = db.insert(DBHelper.TABLE_ITEMS, null, values);
            db.close();

            if (itemId != -1) {
                return imageUrl;
            }
        }

        return null;
    }

    private String saveImageToStorage(String itemName, byte[] image) {
        try {
            // Save the image to internal storage
            String imageUrl = ImageUtils.saveImageToInternalStorage(this, itemName, image);
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permissions already granted, proceed with the camera capture
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed with the camera capture
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
