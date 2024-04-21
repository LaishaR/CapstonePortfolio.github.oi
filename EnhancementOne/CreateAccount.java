/*
 * Programmer: Laisha Ramos.
 * Contact Info: helloitslaishar@gmail.com
 * Date: 12/6/2023
 * Revision Date: 3/28/2024
 * Version: 1
 */

package com.assignment.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {
    private Button createAccountButton;
    private TextView login;
    private DBHelper dbHelper;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_ui);
        // Find and assign the create account button view.
        createAccountButton = findViewById(R.id.createAccountButton);
        // Find and assign the login text view.
        login = findViewById(R.id.login);

// Set onClickListener for the login text view to finish the activity.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Initialize database helper.
        dbHelper = new DBHelper(this);
        // Find and assign the username, password, and confirm password edit text views.
        final EditText usernameEditText = findViewById(R.id.usernameEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final EditText confirmPasswordEditText = findViewById(R.id.confirmpasswordEditText);
// Set onClickListener for the create account button.
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username, password, and confirm password from edit text views.
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (password.equals(confirmPassword)) {
                    // Save the user to the database if the passwwords match.
                    saveUser(username, password);
                    Toast.makeText(CreateAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, navigate to the login activity.
                    finish();
                } else {
                    // Display toast message if passwords do not match.
                    Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to save user to the database.
    private void saveUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Put username and password values.
        values.put(DBHelper.COLUMN_USERNAME, username);
        values.put(DBHelper.COLUMN_PASSWORD, password);
        // Insert user data into the database.
        db.insert(DBHelper.TABLE_USERS, null, values);
        db.close();
    }
}

//END