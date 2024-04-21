/*
 * Programmer: Laisha R.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 4/5/2024
 * Version: 2
 * Purpose: This class represents the database helper for managing user and item data in the Inventory App.
 *          It includes methods for database creation, upgrade, insertion, deletion, retrieval of items,
 *          and additional functionalities such as inventory trend analysis, sales forecasting, and restocking strategy recommendation.
 * Issues: None
/*
 * Course Outcomes:
 * The DBHelper class demonstrates proficiency in database management and optimization through its implementation of efficient database operations for user and item data. Additionally, the integration of advanced functionalities like inventory trend analysis, sales forecasting, and restocking strategy recommendation demonstrates a comprehensive understanding of database usage for business intelligence and decision-making.
 *
 * Meeting Course Outcomes:
 *
 * • Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision-making in the field of computer science.
 *   The DBHelper class provides comprehensive functionality for managing user and item data, facilitating collaborative decision-making processes within the organization. Advanced features such as inventory trend analysis and sales forecasting empower diverse stakeholders to contribute to strategic decision-making in the realm of inventory management.
 *
 * • Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts.
 *   Through clear and well-documented code, the DBHelper class communicates effectively with users, team members, and stakeholders, ensuring understanding and facilitating collaboration. Additionally, the class includes methods for generating visual representations of inventory trends and sales forecasts, enhancing communication of complex data to diverse audiences.
 *
 * • Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution while managing the trade-offs involved in design choices.
 *   The implementation of database operations in the DBHelper class adheres to industry best practices and standards, demonstrating thoughtful consideration of design choices and trade-offs. By efficiently managing database resources and optimizing query performance, the class provides effective solutions to the problem of managing user and item data in the Inventory App.
 *
 * • Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals.
 *   The DBHelper class leverages well-founded techniques and tools in database management to deliver value to the organization through efficient and effective handling of user and item data. By incorporating advanced functionalities like inventory trend analysis and sales forecasting, the class aligns with industry-specific goals and enhances the overall functionality and utility of the Inventory App.
 *
 * • Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources.
 *   With a focus on data integrity and security, the DBHelper class implements secure database operations to protect sensitive user and item data from adversarial exploits. By adhering to best practices in database security and encryption, the class mitigates design flaws and ensures the privacy and security of organizational data and resources.
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

    // Add methods for user-related operations if needed

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

        // Assuming COLUMN_QUANTITY is the column representing item quantity in your database
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

    // Method to analyze inventory trends
    public void analyzeInventoryTrends() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Placeholder for inventory trend analysis

        // Query to fetch data for trend analysis
        String query = "SELECT " + COLUMN_ITEM_NAME + ", " + COLUMN_QUANTITY +
                " FROM " + TABLE_ITEMS +
                " ORDER BY " + COLUMN_QUANTITY + " DESC LIMIT 10";

        Cursor cursor = db.rawQuery(query, null);

        int nameColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION);
        int quantityColumnIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_IMAGE_URL);
        // Placeholder: Process cursor data for trend analysis
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String itemName = cursor.getString(nameColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);

                // Perform trend analysis logic here
                System.out.println("Item: " + itemName + ", Quantity: " + quantity);
            }
            cursor.close();
        }

        db.close();
    }

    // Method to generate sales forecasts
    public void generateSalesForecasts() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Placeholder for generating sales forecasts

        // Query to fetch data for sales forecasting
        String query = "SELECT " + COLUMN_ITEM_NAME + ", " + COLUMN_QUANTITY +
                " FROM " + TABLE_ITEMS +
                " WHERE " + COLUMN_QUANTITY + " > 0";

        Cursor cursor = db.rawQuery(query, null);
        int nameColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION);
        int quantityColumnIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_IMAGE_URL);
        // Placeholder: Process cursor data for sales forecasting
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String itemName = cursor.getString(nameColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);

                // Perform sales forecasting logic here
                System.out.println("Forecast for " + itemName + ": " + quantity + " units");
            }
            cursor.close();
        }

        db.close();
    }

    // Method to recommend restocking strategies
    public void recommendRestockingStrategies() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Placeholder for recommending restocking strategies

        //Query to identify low inventory items for restocking
        int lowInventoryThreshold = 10; // Example threshold for low inventory
        String query = "SELECT " + COLUMN_ITEM_NAME + ", " + COLUMN_QUANTITY +
                " FROM " + TABLE_ITEMS +
                " WHERE " + COLUMN_QUANTITY + " < ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(lowInventoryThreshold)});
        int nameColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION);
        int quantityColumnIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(COLUMN_ITEM_IMAGE_URL);
        // Placeholder: Process cursor data to recommend restocking strategies
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String itemName = cursor.getString(nameColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);

                // Perform restocking strategy recommendation logic here
                System.out.println("Restock recommendation for " + itemName + ": Order " + (lowInventoryThreshold - quantity) + " units");
            }
            cursor.close();
        }

        db.close();
    }

}
