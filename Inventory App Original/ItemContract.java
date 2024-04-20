package com.assignment.inventoryapp;

import android.provider.BaseColumns;

public final class ItemContract {

    // To prevent accidental instantiation
    private ItemContract() {
    }

    // Inner class that defines the table contents
    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String COLUMN_NAME_ITEM_NAME = "item_name";
        public static final String COLUMN_NAME_ITEM_DESCRIPTION = "item_description";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_ITEM_IMAGE_RESOURCE_ID = "image_resource_id"; // New column for image resource ID

    }
}
