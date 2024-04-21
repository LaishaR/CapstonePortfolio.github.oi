/*
 * Programmer: Laisha Ramos.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    // Button for submitting password reset request.
    private Button submitRequest;
    // TextView for canceling the password reset process.
    private TextView cancel;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);  // Set the layout for the activity.
        submitRequest = findViewById(R.id.submitRequest); // Initialize submitRequest button.
        cancel = findViewById(R.id.cancel_button); // Initialize cancel button.
// Set click listener for submitRequest button.
        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //Finish the activity when submitRequest button is clicked.
            }
        });
// Set click listener for cancel button.
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //Finish activity when cancel button is clicked.
            }
        });
    }
}
// END