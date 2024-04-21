/*
 * Programmer: Laisha R.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

// Class representing a stock history entry
public class StockHistoryModel {
    private String itemName; // Name of the item
    private int quantityChange; // Quantity change (positive for addition, negative for removal)
    private String action; // Action performed ("Added" or "Removed")
    private String timestamp; // Timestamp of the action

    // Constructor to initialize a stock history entry with provided values
    public StockHistoryModel(String itemName, int quantityChange, String action, String timestamp) {
        this.itemName = itemName;
        this.quantityChange = quantityChange;
        this.action = action;
        this.timestamp = timestamp;
    }
    // Getter method to retrieve the item name
    public String getItemName() {
        return itemName;
    }

    // Getter method to retrieve the quantity change
    public int getQuantityChange() {
        return quantityChange;
    }

    // Getter method to retrieve the action
    public String getAction() {
        return action;
    }

    // Getter method to retrieve the timestamp
    public String getTimestamp() {
        return timestamp;
    }
}
//END