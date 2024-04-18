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

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Initialize UI components
        allItemsSpinner = findViewById(R.id.all_items);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemDescriptionEditText = findViewById(R.id.itemDescriptionEditText);
        itemQuantityEditText = findViewById(R.id.itemQuantityEditText);
        itemImageView = findViewById(R.id.itemimgview);
        captureImageButton = findViewById(R.id.captureImageButton);
        saveItemButton = findViewById(R.id.saveItemButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);
// Spinner Item Selected Listener
        allItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle item selection, populate other fields
                onItemSelectedInSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Get the selected item position from the intent
        int selectedItemPosition = getIntent().getIntExtra("SELECTED_ITEM_POSITION", -1);

        if (selectedItemPosition != -1) {
            // Fetch item details based on the selected item position
            ItemModel selectedItem = dbHelper.getAllItems().get(selectedItemPosition);

            // Populate UI fields with the selected item details
            selectedItemId = selectedItem.getId();
            itemNameEditText.setText(selectedItem.getItemName());
            itemDescriptionEditText.setText(selectedItem.getItemDescription());
            itemQuantityEditText.setText(String.valueOf(selectedItem.getQuantity()));

            // Load image into the ImageView using Glide
            loadImage(selectedItem.getItemImageUrl());
        }

        // Set click listeners
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

        populateSpinner();
    }



    private void populateSpinner() {
        // Fetch items from the database
        List<ItemModel> itemList = dbHelper.getAllItems();

        // Extract item names
        String[] itemNames = new String[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            itemNames[i] = itemList.get(i).getItemName();
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemNames);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        allItemsSpinner.setAdapter(adapter);
    }

    private void onItemSelectedInSpinner(int position) {
        // Fetch item details based on the selected item position
        List<ItemModel> itemList = dbHelper.getAllItems();
        if (position >= 0 && position < itemList.size()) {
            ItemModel selectedItem = itemList.get(position);
            selectedItemId = selectedItem.getId();

            // Populate other fields with the selected item details
            itemNameEditText.setText(selectedItem.getItemName());
            itemDescriptionEditText.setText(selectedItem.getItemDescription());
            itemQuantityEditText.setText(String.valueOf(selectedItem.getQuantity()));
                // Load image into the ImageView using Glide
           loadImage(selectedItem.getItemImageUrl());
        }
    }
    private void loadImage(String imageUrl) {
//        // Use Glide to load the image into the ImageView
       // itemImageView = findViewById(R.id.itemimgview);
        Glide.with(ctx)
                .load(imageUrl)
                .into(itemImageView);
        //Toast.makeText(getApplicationContext(),"---"+imageUrl,Toast.LENGTH_SHORT).show();



    }
    private void updateItemDetails() {
        String itemName = itemNameEditText.getText().toString().trim();
        String itemDescription = itemDescriptionEditText.getText().toString().trim();
        String quantityStr = itemQuantityEditText.getText().toString().trim();

        if (!itemName.isEmpty() && !quantityStr.isEmpty() && capturedImage != null) {
            int quantity = Integer.parseInt(quantityStr);
            // Convert the capturedImage to a byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            capturedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String imageUrl = saveImageToStorage(itemName, byteArray);
            // Save the captured image to internal storage
           // String imageUrl = ImageUtils.saveImageToInternalStorage(capturedImage, "item_image_" + selectedItemId);

            // Update the item details in the database with the image URL
            dbHelper.updateItemDetails(selectedItemId, itemName, itemDescription, quantity, imageUrl);

            // Notify the user
            Toast.makeText(this, "Item details updated successfully", Toast.LENGTH_SHORT).show();

            // Optionally, navigate to the main activity or perform other actions
            finish();
        } else {
            Toast.makeText(this, "Please fill in all fields and capture an image", Toast.LENGTH_SHORT).show();
        }
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
