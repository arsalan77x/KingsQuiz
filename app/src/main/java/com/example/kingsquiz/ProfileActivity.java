package com.example.kingsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        Button applyEmail = (Button) findViewById(R.id.applyButton);
        Button applyPass = (Button) findViewById(R.id.applyButton2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button applyUser = (Button) findViewById(R.id.applyButton3);
        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        EditText changeEmail = (EditText) findViewById(R.id.changeEmailText);
        EditText changePassword = (EditText) findViewById(R.id.changePasswordText);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText changeUsername = (EditText) findViewById(R.id.changeUserName);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainMenuActivity(v);
            }
        });

        applyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = changeEmail.getText().toString();

                if (newEmail.matches(emailPattern) && newEmail.length() > 0
                        && !RegisterActivity.isUserExist(newEmail)) {
                    MainActivity.userDatabase.updateUser(MainActivity.userLoggedIn.getEmail(),
                            newEmail, MainActivity.userLoggedIn.getPassword(), Integer.toString(MainActivity.userLoggedIn.getScore()),
                            MainActivity.userLoggedIn.getUsername());
                    Toast.makeText(getApplicationContext(),
                            "Email changed!", Toast.LENGTH_SHORT).show();
                    MainActivity.loggedInUser.delete("LoggedIn", "Username=?", new String[]{MainActivity.userLoggedIn.getEmail()});
                    ContentValues values = new ContentValues();
                    values.put("Username", newEmail);
                    MainActivity.loggedInUser.insert("LoggedIn", null, values);
                    MainActivity.userArrayList = new ArrayList<>();
                    MainActivity.userArrayList = MainActivity.userDatabase.readCourses();
                    MainActivity.userLoggedIn = LoginActivity.getUserByEmail(newEmail);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "invalid email!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        applyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = changePassword.getText().toString();

                if (newPass.length() > 0 && !newPass.equals(MainActivity.userLoggedIn.getPassword())) {
                    MainActivity.userDatabase.updatePass(MainActivity.userLoggedIn.getPassword(),
                            MainActivity.userLoggedIn.getEmail(), newPass,
                            Integer.toString(MainActivity.userLoggedIn.getScore()),MainActivity.userLoggedIn.getUsername());
                    Toast.makeText(getApplicationContext(),
                            "Password changed!", Toast.LENGTH_SHORT).show();
                    MainActivity.userArrayList = new ArrayList<>();
                    MainActivity.userArrayList = MainActivity.userDatabase.readCourses();
                    MainActivity.userLoggedIn = LoginActivity.getUserByEmail(MainActivity.userLoggedIn.getEmail());
                } else {
                    Toast.makeText(getApplicationContext(),
                            "invalid password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        applyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUser = changeUsername.getText().toString();
                if (newUser.length() > 0) {
                    MainActivity.userDatabase.updateUser(MainActivity.userLoggedIn.getEmail(),
                            MainActivity.userLoggedIn.getEmail(), MainActivity.userLoggedIn.getPassword(), Integer.toString(MainActivity.userLoggedIn.getScore()),
                            newUser);
                    Toast.makeText(getApplicationContext(),
                            "Username changed!", Toast.LENGTH_SHORT).show();
                    MainActivity.userArrayList = new ArrayList<>();
                    MainActivity.userArrayList = MainActivity.userDatabase.readCourses();
                    MainActivity.userLoggedIn = LoginActivity.getUserByEmail(MainActivity.userLoggedIn.getEmail());
                } else {
                    Toast.makeText(getApplicationContext(),
                            "invalid username!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor resultSet = MainActivity.loggedInUser.rawQuery("Select * from LoggedIn", null);
                resultSet.moveToFirst();
                String email = resultSet.getString(0);
                MainActivity.loggedInUser.delete("LoggedIn", "Username=?", new String[]{email});
                launchMainActivity(view);
            }
        });
    }

    private void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void launchMainMenuActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}