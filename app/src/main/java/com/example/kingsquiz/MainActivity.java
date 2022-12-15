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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static User userLoggedIn;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static SQLiteDatabase loggedInUser;
    String email ="";
    public static  DataBaseUser userDatabase;
    public static ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button profileButton = (Button) findViewById(R.id.profileButton);
        Button startButton = (Button) findViewById(R.id.gameButton);
        Button settingButton = (Button) findViewById(R.id.settingButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);
        Button scoreboardButton = (Button) findViewById(R.id.scoreboardButton);

        userDatabase = new DataBaseUser(MainActivity.this);
        userArrayList = new ArrayList<>();
        userArrayList = userDatabase.readCourses();


        loggedInUser = openOrCreateDatabase("loggedInUser", MODE_PRIVATE, null);
        loggedInUser.execSQL("CREATE TABLE IF NOT EXISTS LoggedIn (Username TEXT);");
        Cursor resultSet = loggedInUser.rawQuery("Select * from LoggedIn", null);

        resultSet.moveToFirst();


        if (resultSet.getCount() == 0) {
             email = "";
        } else {
             email = resultSet.getString(0);
             userLoggedIn = LoginActivity.getUserByEmail(email);
        }



        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.equals("")) {
                    launchLoginActivity(view);
                    Toast.makeText(getApplicationContext(),
                            "You are not logged in yet!", Toast.LENGTH_SHORT).show();
                } else {
                    launchProfileActivity(view);
                }
            }
        });

        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchScoreBoardActivity(view);
            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
                System.exit(0);

            }
        });
    }

    private void launchProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    private void launchLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void launchScoreBoardActivity(View view) {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }
}