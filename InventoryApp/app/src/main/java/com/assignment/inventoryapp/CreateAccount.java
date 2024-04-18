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
        createAccountButton = findViewById(R.id.createAccountButton);
        login = findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dbHelper = new DBHelper(this);

        final EditText usernameEditText = findViewById(R.id.usernameEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final EditText confirmPasswordEditText = findViewById(R.id.confirmpasswordEditText);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (password.equals(confirmPassword)) {
                    // Save the user to the database
                    saveUser(username, password);
                    Toast.makeText(CreateAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, navigate to the login activity
                    finish();
                } else {
                    Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USERNAME, username);
        values.put(DBHelper.COLUMN_PASSWORD, password);
        db.insert(DBHelper.TABLE_USERS, null, values);
        db.close();
    }
}