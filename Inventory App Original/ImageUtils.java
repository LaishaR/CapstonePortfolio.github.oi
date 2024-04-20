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
        File directory = context.getDir("item_images", Context.MODE_PRIVATE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + itemName + "_" + timeStamp + ".png";

        File imageFile = new File(directory, fileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(image);
        }

        return imageFile.getAbsolutePath();
    }
}
