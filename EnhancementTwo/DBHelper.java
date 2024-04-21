/*
 * Programmer: Laisha R.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory_db";
    private static final int DATABASE_VERSION = 2;

    // User table.
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Items table.
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_DESCRIPTION = "item_description";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_ITEM_IMAGE_URL = "item_image_url";  // Updated column for image URL.

    // Create table queries.
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT);";

    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + " ("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ITEM_NAME + " TEXT, "
            + COLUMN_ITEM_DESCRIPTION + " TEXT, "
            + COLUMN_ITEM_IMAGE_URL + " TEXT, "
            + COLUMN_QUANTITY + " INTEGER);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Mathod called when the database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ITEMS);
    }
    // Method called when the database is upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Recreate user and item tables if needed.
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    // Method to retrieve all items from the database.
    public List<ItemModel> getAllItems() {
        List<ItemModel> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// Get column indices for item data.
        int columnIndexId = cursor.getColumnIndex(COLUMN_ITEM_ID);
        int columnIndexName = cursor.getColumnIndex(COLUMN_ITEM_NAME);
        int columnIndexDescription = cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION);
        int columnIndexQuantity = cursor.getColumnIndex(COLUMN_QUANTITY);
        int columnIndexImageUrl = cursor.getColumnIndex(COLUMN_ITEM_IMAGE_URL);
// Iterate over the cursor to retrieve items
        while (cursor.moveToNext()) {
            ItemModel item = new ItemModel();

            // Retrieve and set item data if columns exist.
            if (columnIndexName != -1) {
                item.setItemName(cursor.getString(columnIndexName));
            }

            if (columnIndexId != -1) {
                item.setId(cursor.getInt(columnIndexId));
            }

            if (columnIndexDescription != -1) {
                item.setItemDescription(cursor.getString(columnIndexDescription));
            }

            if (columnIndexQuantity != -1) {
                item.setQuantity(cursor.getInt(columnIndexQuantity));
            }

            if (columnIndexImageUrl != -1) {
                item.setItemImageUrl(cursor.getString(columnIndexImageUrl));
            }

            itemList.add(item);
        }

        cursor.close();
        db.close();
        return itemList;
    }
    // Method to insert a new item into the database.
    public long insertItem(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_DESCRIPTION, item.getItemDescription());
        values.put(COLUMN_ITEM_IMAGE_URL, item.getItemImageUrl());  // Updated line to include image URL
        values.put(COLUMN_QUANTITY, item.getQuantity());
        long id = db.insert(TABLE_ITEMS, null, values);
        db.close();
        return id;
    }
    // Method to update item details in the database.
    public void updateItemDetails(int itemId, String itemName, String itemDescription, int quantity, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_ITEM_DESCRIPTION, itemDescription);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_ITEM_IMAGE_URL, imageUrl);
        System.out.println("updating data : "+itemId);

// Update item details using item ID as condition.
        db.update(TABLE_ITEMS, values, COLUMN_ITEM_ID + " = ?", new String[]{""+itemId});
        db.close();
        System.out.println("done : "+quantity);
    }

    //Method to delete an item from the database.
    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = db.delete(TABLE_ITEMS, COLUMN_ITEM_ID + " = ?", new String[]{""+itemId});
        db.close();
// Return true if deletion was successful.
        return affectedRows > 0;
    }
    // Method to retrieve low inventory items from the database.
    public List<ItemModel> getLowInventoryItems() {
        List<ItemModel> lowInventoryItems = new ArrayList<>();

        // Set the low inventory threshold.
        int lowInventoryThreshold = 5;

        SQLiteDatabase db = this.getReadableDatabase();
// Define projection for required item data.
        String[] projection = {
                COLUMN_ITEM_NAME,
                COLUMN_ITEM_DESCRIPTION,
                COLUMN_QUANTITY,
                COLUMN_ITEM_IMAGE_URL
        };
// Define selection criteria for low inventory items.
        String selection = COLUMN_QUANTITY + " < ?";
        String[] selectionArgs = {String.valueOf(lowInventoryThreshold)};
// Query the database for low inventory items.
        Cursor cursor = db.query(
                TABLE_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
// Process cursor data to retrieve low inventory items.
        if (cursor != null) {
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION);
            int quantityColumnIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
            int imageColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_IMAGE_URL);
// Iterate over the cursor to retrieve low inventory items.
            while (cursor.moveToNext()) {
                String itemName = cursor.getString(nameColumnIndex);
                String itemDescription = cursor.getString(descriptionColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                String itemImage = cursor.getString(imageColumnIndex);

                // Create an ItemModel object with retrieved data.
                ItemModel item = new ItemModel(0,itemName, itemDescription, quantity, itemImage);

                // Add the item to the list of low inventory items.
                lowInventoryItems.add(item);
            }

            cursor.close();
        }

        db.close();

        return lowInventoryItems;
    }


}

// END