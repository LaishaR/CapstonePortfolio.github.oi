/*
 * Programmer: Laisha R.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 2
 * Purpose: This class represents the functionality for displaying all items in the inventory,
 *          allowing users to efficiently search for specific items using a binary search algorithm.
 *
 * Issues: None
 *
 * Course Outcomes:
 * The implementation of the binary search algorithm in the binarySearch method demonstrates optimization and efficiency by significantly reducing the time complexity for searching items within the inventory list. This enhancement aligns with the course objective to optimize algorithms for improved performance.
 * Binary Search Implementation:
 * The binary search algorithm has been integrated into the AllItems class to optimize the search functionality for efficiently locating items within the inventory list. Here's how it works:
 * 1.   Sorting: The list of items fetched from the database is sorted alphabetically by item name to facilitate binary search. This sorting is performed in the onCreate method, demonstrating an understanding of optimization techniques to prepare data for efficient search operations.
 * 2.   Binary Search Logic: The binarySearch method performs the binary search algorithm on the sorted list of items. It efficiently narrows down the search range by comparing the query with the item names, thereby reducing the search time complexity to O(log n). This efficient algorithmic logic ensures quick retrieval of items, crucial for user experience in applications with large datasets.
 * 3.   Integration: Whenever the user types in the search query, the onQueryTextChange method triggers the binary search to find the position of the queried item. If found, it scrolls the RecyclerView to that position, providing a seamless search experience to the user. This integration of binary search into the user interface enhances usability and responsiveness, meeting the objective of creating efficient and user-friendly applications.
 * Optimization and Time Complexity:
 * - Optimization: By employing the binary search algorithm, the search functionality is optimized for large inventories. Instead of iterating through the entire list linearly, binary search efficiently narrows down the search range with each comparison, leading to faster search operations. This optimization ensures that the application maintains responsiveness even with increasing dataset sizes, fulfilling the requirement to optimize algorithms for improved performance.
 * - Time Complexity: Binary search achieves a time complexity of O(log n), where n is the number of items in the inventory list. This logarithmic time complexity ensures efficient search operations even for substantial inventory sizes, making the app responsive and user-friendly. Understanding and implementing algorithms with lower time complexities are essential for developing scalable applications capable of handling growing data volumes while maintaining optimal performance.
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