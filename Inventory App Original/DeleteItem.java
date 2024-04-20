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

        deleteItemButton = findViewById(R.id.saveItemButton);
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
