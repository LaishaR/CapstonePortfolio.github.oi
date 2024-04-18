package com.assignment.inventoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {
    private List<ItemModel> itemList;
    private List<ItemModel> itemListFull;

    public ItemAdapter(List<ItemModel> itemList) {
        this.itemList = new ArrayList<>(itemList);
        this.itemListFull = new ArrayList<>(itemList);
    }

    public void setItems(List<ItemModel> items) {
        itemList.clear();
        itemList.addAll(items);
        itemListFull.clear();
        itemListFull.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = itemList.get(position);

        // Bind data to views in the ViewHolder
        holder.itemNameTextView.setText(item.getItemName());
        holder.itemDescriptionTextView.setText(item.getItemDescription());
        holder.quantityTextView.setText("Quantity: " + String.valueOf(item.getQuantity()));

        // Load image into the ImageView using Glide
        Glide.with(holder.itemView.getContext())
                .load(item.getItemImageUrl())
                .into(holder.itemImageView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView itemDescriptionTextView;
        public TextView quantityTextView;
        public ImageView itemImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemDescriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
        }
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private final Filter itemFilter = new Filter() {

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List<ItemModel>) results.values);
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ItemModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // No filtering, return the full list
                filteredList.addAll(itemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemModel item : itemListFull) {
                    if (item.getItemName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }
    };
}
