package com.assignment.inventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 private ImageView logout ;
    private DBHelper dbHelper;
    private TextView usernameTextView;
 private LinearLayout add_item,all_items,edit_item,delete_item,notification_btn,myprofile;
    private static final int PERMISSION_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logout);
        add_item = findViewById(R.id.add_item);
        all_items = findViewById(R.id.all_items);
        edit_item = findViewById(R.id.edit_item);
        delete_item = findViewById(R.id.delete_item);
        notification_btn = findViewById(R.id.notification_btn);
        usernameTextView = findViewById(R.id.usernameTextView);
        //get username from intent
        String username = getIntent().getStringExtra("username");
        usernameTextView.setText(username);

        //myprofile = findViewById(R.id.myprofile);
        dbHelper = new DBHelper(this);
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSmsPermission()) {
                    // Permission is granted, proceed with sending SMS messages
                    showPhoneNumberDialog();
                } else {
                    // Permission is not granted, request it from the user
                    requestSmsPermission();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddItem.class));
            }
        });
        all_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AllItems.class));
            }
        });

        edit_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EditItem.class));
            }
        });
        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeleteItem.class));
            }
        });

//        myprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,MyProfile.class));
//            }
//        });
    }

    private boolean checkSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestSmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with sending SMS messages
                showPhoneNumberDialog();
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "SMS permission denied. You can grant the permission in the app settings.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showPhoneNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.phone_number_dialog, null);
        final EditText phoneNumberEditText = dialogView.findViewById(R.id.phoneNumberEditText);

        builder.setView(dialogView)
                .setTitle("Enter Phone Number to receive notification")
                .setPositiveButton("Send Notification", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = phoneNumberEditText.getText().toString().trim();
                        if (!phoneNumber.isEmpty()) {
                            checkAndSendNotification(phoneNumber);
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void checkAndSendNotification(String phoneNumber) {
        // Check for low inventory items in the items table
        List<ItemModel> lowInventoryItems = getLowInventoryItems();

        if (!lowInventoryItems.isEmpty()) {
            // Construct the message for low inventory items
            StringBuilder message = new StringBuilder("Low inventory items:\n");
            for (ItemModel item : lowInventoryItems) {
                message.append("- ").append(item.getItemName()).append(" (Quantity: ").append(item.getQuantity()).append(")\n");
            }

            // Send SMS notification
            sendSMS(phoneNumber, message.toString());
        } else {
            Toast.makeText(this, "No low inventory items found", Toast.LENGTH_SHORT).show();
        }
    }

    private List<ItemModel> getLowInventoryItems() {
        // Implement the logic to retrieve low inventory items from the items table

        List<ItemModel> lowInventoryItems = dbHelper.getLowInventoryItems();

        return lowInventoryItems;
    }
    private void sendSMS(String phoneNumber, String message) {
        // Use SmsManager to send SMS
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(this, "Notification sent successfully", Toast.LENGTH_SHORT).show();
    }
}