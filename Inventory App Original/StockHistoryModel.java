package com.assignment.inventoryapp;

public class StockHistoryModel {
    private String itemName;
    private int quantityChange;
    private String action; // "Added" or "Removed"
    private String timestamp;

    public StockHistoryModel(String itemName, int quantityChange, String action, String timestamp) {
        this.itemName = itemName;
        this.quantityChange = quantityChange;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public String getAction() {
        return action;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

