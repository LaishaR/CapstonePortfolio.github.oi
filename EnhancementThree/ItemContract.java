/*
 * Programmer: Laisha R.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */
package com.assignment.inventoryapp;

import android.provider.BaseColumns;

public final class ItemContract {

    // To prevent accidental instantiation.
    private ItemContract() {
    }

    // Inner class that defines the table contents.
    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "items"; // Table name constant
        public static final String COLUMN_NAME_ITEM_NAME = "item_name"; // Column name for item name.
        public static final String COLUMN_NAME_ITEM_DESCRIPTION = "item_description"; // Column name for item description.
        public static final String COLUMN_NAME_QUANTITY = "quantity"; // Column name for quantity.
        public static final String COLUMN_ITEM_IMAGE_RESOURCE_ID = "image_resource_id"; // New column for image resource ID.

    }
}
//END
