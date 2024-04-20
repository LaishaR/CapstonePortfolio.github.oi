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

    // User table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Items table
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_DESCRIPTION = "item_description";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_ITEM_IMAGE_URL = "item_image_url";  // Updated column for image URL

    // Create table queries
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    // Add methods for user-related operation

    public List<ItemModel> getAllItems() {
        List<ItemModel> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int columnIndexId = cursor.getColumnIndex(COLUMN_ITEM_ID);
        int columnIndexName = cursor.getColumnIndex(COLUMN_ITEM_NAME);
        int columnIndexDescription = cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION);
        int columnIndexQuantity = cursor.getColumnIndex(COLUMN_QUANTITY);
        int columnIndexImageUrl = cursor.getColumnIndex(COLUMN_ITEM_IMAGE_URL);

        while (cursor.moveToNext()) {
            ItemModel item = new ItemModel();

            // Check if the column exists before retrieving values
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

    public void updateItemDetails(int itemId, String itemName, String itemDescription, int quantity, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_ITEM_DESCRIPTION, itemDescription);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_ITEM_IMAGE_URL, imageUrl);
        System.out.println("updating data : "+itemId);


        db.update(TABLE_ITEMS, values, COLUMN_ITEM_ID + " = ?", new String[]{""+itemId});
        db.close();
        System.out.println("done : "+quantity);
    }

    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = db.delete(TABLE_ITEMS, COLUMN_ITEM_ID + " = ?", new String[]{""+itemId});
        db.close();

        return affectedRows > 0;
    }

    public List<ItemModel> getLowInventoryItems() {
        List<ItemModel> lowInventoryItems = new ArrayList<>();

        // Assuming COLUMN_QUANTITY is the column representing item quantity in database
        int lowInventoryThreshold = 5;

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_ITEM_NAME,
                COLUMN_ITEM_DESCRIPTION,
                COLUMN_QUANTITY,
                COLUMN_ITEM_IMAGE_URL
        };

        String selection = COLUMN_QUANTITY + " < ?";
        String[] selectionArgs = {String.valueOf(lowInventoryThreshold)};

        Cursor cursor = db.query(
                TABLE_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION);
            int quantityColumnIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
            int imageColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_IMAGE_URL);

            while (cursor.moveToNext()) {
                String itemName = cursor.getString(nameColumnIndex);
                String itemDescription = cursor.getString(descriptionColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                String itemImage = cursor.getString(imageColumnIndex);

                // Create an ItemModel with retrieved data
                ItemModel item = new ItemModel(0,itemName, itemDescription, quantity, itemImage);

                // Add the item to the list
                lowInventoryItems.add(item);
            }

            cursor.close();
        }

        db.close();

        return lowInventoryItems;
    }


}
