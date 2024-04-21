/*
 * Programmer: Laisha Ramos.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllItems extends AppCompatActivity {
    private ImageView back_button;
    private RecyclerView recyclerView;
    private SearchView searchBar;
    private DBHelper dbHelper;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_items_layout);
        // Find and assign the back button view.
        back_button = findViewById(R.id.back);
        // Set onClickListener for the back button to finish the activity.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Find and assign the SearchView.
        searchBar = findViewById(R.id.searchBar);

        // Expand the SearchView by default.
        searchBar.setIconified(false);

        // Initialize the database helper.
        dbHelper = new DBHelper(this);
        // Find and assign the RecyclerView.
        recyclerView = findViewById(R.id.recyclerViewAllItems);
        // Set up RecyclerView with GridLayoutManager and fixed size.
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        // Initialize itemAdapter with an empty list initially.
        itemAdapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(itemAdapter);

// Fetch items from the database.
        List<ItemModel> allItems = dbHelper.getAllItems();

// Update the existing itemAdapter with the fetched items.
        itemAdapter.setItems(allItems);

// Set listener for changes in SearchView text.
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the itemAdapter based on the search query.
                if (itemAdapter != null) {
                    itemAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }
}
//END
