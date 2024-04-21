/*
 * Programmer: Laisha R.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */
package com.assignment.inventoryapp;

public class ItemModel {
    private int id; // Field to store the item ID.
    private String itemName; // Field to store the item name.
    private String itemDescription; // Field to store the item description.
    private int quantity; // Field to store the quantity of the item.
    private int imageResourceId; // This is the resource ID of the image.
    private String itemImageUrl; // This is the URL of the item image,
    private byte[] itemImage; // New field to store the image as a byte array.


    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public  ItemModel(){} // Default constructor
    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public ItemModel(int id, String itemName, String itemDescription, int quantity,  String itemImageUrl) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.itemImageUrl = itemImageUrl;
    }

    // Getters and setters for all fields...

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }
}
//END