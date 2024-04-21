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
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

public class ChangePassword extends AppCompatActivity {
private ImageView back_button;
private Button change_password,cancel;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        // Find and assign the back button view.
        back_button = findViewById(R.id.back);
        // Find and assign the change password button view.
        change_password = findViewById(R.id.changePasswordButton);
        // Find and assign the cancel button view.
        cancel = findViewById(R.id.cancelButton);

// Set onClickListener for the back button to finish the activity.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
// Set onClickListener for the change password button to finish the activity.
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
// Set onClickListener for the cancel button to finish the activity.
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
// END
