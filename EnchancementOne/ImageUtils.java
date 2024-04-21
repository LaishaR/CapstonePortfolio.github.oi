/*
 * Programmer: Laisha Ramos.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUtils {

    public static String saveImageToInternalStorage(Context context, String itemName, byte[] image) throws IOException {
        // Get the directory for storing item images.
        File directory = context.getDir("item_images", Context.MODE_PRIVATE);
        // Generate a timestamp to append to the filename.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        // Construct the filename using item name and timestamp
        String fileName = "IMG_" + itemName + "_" + timeStamp + ".png";
        // Create a new image file in the directory.
        File imageFile = new File(directory, fileName);
        // Write the image data to the file.
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(image);
        }
// Return the absolute path of the saved image file
        return imageFile.getAbsolutePath();
    }
}
// END