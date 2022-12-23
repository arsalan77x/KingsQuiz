package com.example.kingsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingActivity.isBlueOn) {
            this.setTheme(R.style.Theme1);
        } else {
            this.setTheme(R.style.Theme2);
        }
        setContentView(R.layout.activity_register);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button mainMainButton = (Button) findViewById(R.id.mainMenuButton);
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        MainActivity.userArrayList = new ArrayList<>();
        MainActivity.userArrayList = MainActivity.userDatabase.fetchUsers();

        mainMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainMenuActivity(v);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.userArrayList = new ArrayList<>();
                MainActivity.userArrayList = MainActivity.userDatabase.fetchUsers();
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString();
                if (email.matches(emailPattern) && email.length() > 0
                        && !isUserExist(email) && password.length() > 3) {
                    MainActivity.userDatabase.addNewUser(email, password,"0","Player");
                    MainActivity.userArrayList = new ArrayList<>();
                    MainActivity.userArrayList = MainActivity.userDatabase.fetchUsers();
                    Toast.makeText(getApplicationContext(),
                            "you're registered now!", Toast.LENGTH_SHORT).show();
                    launchLoginActivity(v);
                } else if (password.length() <= 3) {
                    Toast.makeText(getApplicationContext(),
                            "password too short!", Toast.LENGTH_SHORT).show();
                } else if (email.length() == 0 || !email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(),
                            "invalid email!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(),
                            "user already exists!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static boolean isUserExist(String email) {

        for (int i = 0; i <  MainActivity.userArrayList.size(); i++) {
            Log.d(LOG_TAG,  MainActivity.userArrayList.get(i).getEmail() + "//" + email);
            if ( MainActivity.userArrayList.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    private void launchLoginActivity(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void launchMainMenuActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}