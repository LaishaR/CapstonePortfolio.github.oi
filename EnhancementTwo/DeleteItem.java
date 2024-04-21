/*
 * Programmer: Laisha R.
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteItem extends AppCompatActivity {
    private ImageView back_arrow;

    private Spinner allItemsSpinner;
    private ImageView itemImageView;
    private Button  deleteItemButton, cancelButton;
    private EditText itemNameEditText, itemDescriptionEditText, itemQuantityEditText;

    private DBHelper dbHelper;
    private Context ctx = this;
    private int selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_item_layout);
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

        deleteItemButton = findViewById(R.id.saveItemButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize DBHelper.
        dbHelper = new DBHelper(this);
// Spinner item selected listener.
        allItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle item selection, populate other fields
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


// Button click listeners.
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
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

        // Extract item names.
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
    // Method to delete item from database.
    private void deleteItem() {
        // Call DBHelper method to delete the item from the database
        boolean isDeleted = dbHelper.deleteItem(selectedItemId);

        if (isDeleted) {
            Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Optionally, you can navigate to another activity or perform other actions
        } else {
            Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show();
        }
    }




}
//END