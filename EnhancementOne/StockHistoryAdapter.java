/*
 * Programmer: Laisha Ramos.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StockHistoryAdapter extends RecyclerView.Adapter<StockHistoryAdapter.ViewHolder> {
    private List<StockHistoryModel> stockHistoryList;
    // Constructor to initialize the adapter with a list of stock history items.
    public StockHistoryAdapter(List<StockHistoryModel> stockHistoryList) {
        this.stockHistoryList = stockHistoryList;
    }
    // Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the stock history item view.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_history_item_layout, parent, false);
        return new ViewHolder(view);
    }
    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the stock history entry at the specified position.
        StockHistoryModel historyEntry = stockHistoryList.get(position);

        // Bind data to views in the ViewHolder
        holder.itemNameTextView.setText(historyEntry.getItemName());
        holder.quantityChangeTextView.setText(String.valueOf(historyEntry.getQuantityChange()));
        holder.actionTextView.setText(historyEntry.getAction());
        holder.timestampTextView.setText(historyEntry.getTimestamp());
    }
    // Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return stockHistoryList.size();
    }
    // ViewHolder class to hold references to the views for each item in the RecyclerView.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView quantityChangeTextView;
        public TextView actionTextView;
        public TextView timestampTextView;
        // Constructor to initialize the ViewHolder with the item view.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find and initialize the TextViews in the item view.
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            quantityChangeTextView = itemView.findViewById(R.id.quantityChangeTextView);
            actionTextView = itemView.findViewById(R.id.actionTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
// END