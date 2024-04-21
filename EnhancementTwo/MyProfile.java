/*
 * Programmer: Laisha R.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */
package com.assignment.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

public class MyProfile extends AppCompatActivity {
    private ImageView back_button;
    private  Button changePassword;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_layout);
        // Initialize UI components
        back_button = findViewById(R.id.back);
        changePassword = findViewById(R.id.changePasswordButton);
// Back button click listener.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            // Finish the activity when back button is clicked.
            public void onClick(View view) {
                finish();
            }
        });
        // Change password button click listener.
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the ChangePassword activity.
                startActivity(new Intent(MyProfile.this,ChangePassword.class));
            }
        });
    }
}
// END