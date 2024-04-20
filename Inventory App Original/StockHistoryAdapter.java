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

    public StockHistoryAdapter(List<StockHistoryModel> stockHistoryList) {
        this.stockHistoryList = stockHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_history_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StockHistoryModel historyEntry = stockHistoryList.get(position);

        // Bind data to views in the ViewHolder
        holder.itemNameTextView.setText(historyEntry.getItemName());
        holder.quantityChangeTextView.setText(String.valueOf(historyEntry.getQuantityChange()));
        holder.actionTextView.setText(historyEntry.getAction());
        holder.timestampTextView.setText(historyEntry.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return stockHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView quantityChangeTextView;
        public TextView actionTextView;
        public TextView timestampTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            quantityChangeTextView = itemView.findViewById(R.id.quantityChangeTextView);
            actionTextView = itemView.findViewById(R.id.actionTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
