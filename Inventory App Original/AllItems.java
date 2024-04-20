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
        back_button = findViewById(R.id.back);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        searchBar = findViewById(R.id.searchBar);

        // Expand the SearchView
        searchBar.setIconified(false);

        // Initialize database helper
        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recyclerViewAllItems);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        // Initialize your itemAdapter with an empty list initially
        itemAdapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(itemAdapter);

// Fetch items from the database
        List<ItemModel> allItems = dbHelper.getAllItems();

// Update the existing itemAdapter with the fetched items
        itemAdapter.setItems(allItems);


        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (itemAdapter != null) {
                    itemAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }
}
