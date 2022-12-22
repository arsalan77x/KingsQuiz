package com.example.kingsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingActivity.isBlueOn) {
            this.setTheme(R.style.Theme1);
        } else {
            this.setTheme(R.style.Theme2);
        }
        setContentView(R.layout.activity_login);

        Button registerButton = (Button) findViewById(R.id.loginButton);
        EditText emailText = (EditText) findViewById(R.id.loginEmailText);
        EditText passwordText = (EditText) findViewById(R.id.loginPasswordText);
        Button noAccountButton = (Button) findViewById(R.id.noAccountButton);
        Button mainMainButton = (Button) findViewById(R.id.mainMenuButton);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        noAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegisterActivity(view);
            }
        });

        mainMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity(v);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString();
                if (email.matches(emailPattern) && email.length() > 0
                        && RegisterActivity.isUserExist(email) && password.length() > 3 &&
                        isPasswordMatch(email,password)) {
                    Toast.makeText(getApplicationContext(),
                            "you're logged in!", Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("Username", email);
                    MainActivity.loggedInUser.insert("LoggedIn", null, values);
                    User.loggedInUser = getUserByEmail(email);
                    launchMainActivity(v);

                } else{
                    Toast.makeText(getApplicationContext(),
                            "incorrect info!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean isPasswordMatch(String email, String password) {
        User user = getUserByEmail(email);
        return user.getPassword().equals(password);
    }

    public static User getUserByEmail(String email){
        for (int i = 0; i < MainActivity.userArrayList.size(); i++) {
            if (MainActivity.userArrayList.get(i).getEmail().equals(email)){
                return MainActivity.userArrayList.get(i);
            }
        }
        return null;
    }

    private void launchRegisterActivity(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}