package com.example.kingsquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static User userLoggedIn;
    public static SQLiteDatabase loggedInUser;
    String email = "";
    public static DataBaseUser userDatabase;
    public static SettingsDatabase settingsDatabase;
    public static QuestionDatabase questionDatabase;
    public static QuizDatabase quizDatabase;
    public static ArrayList<User> userArrayList;
    public static ArrayList<Quiz> quizArrayList;
    public static final int DB_VERSION = 1;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingActivity.isBlueOn) {
            this.setTheme(R.style.Theme1);
        } else {
            this.setTheme(R.style.Theme2);
        }
        setContentView(R.layout.activity_main);


        Button profileButton = (Button) findViewById(R.id.profileButton);
        Button startButton = (Button) findViewById(R.id.gameButton);
        Button settingButton = (Button) findViewById(R.id.settingButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);
        Button scoreboardButton = (Button) findViewById(R.id.scoreboardButton);

        settingsDatabase = new SettingsDatabase(MainActivity.this);
        questionDatabase = new QuestionDatabase(MainActivity.this);
        quizDatabase = new QuizDatabase(MainActivity.this);
        userDatabase = new DataBaseUser(MainActivity.this);
        userArrayList = new ArrayList<>();
        userArrayList = userDatabase.fetchUsers();
        quizArrayList = new ArrayList<>();
        quizArrayList = quizDatabase.fetchQuiz();



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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() !=
                        NetworkInfo.State.CONNECTED &&
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() !=
                                NetworkInfo.State.CONNECTED) {
                    Toast.makeText(getApplicationContext(),
                            "You are not connected to the internet! Offline mode is on",
                            Toast.LENGTH_SHORT).show();
                    GameActivity.offlineMode = true;
                    Cursor cursor = settingsDatabase.fetchSettings();
                    String difficulty = cursor.getString(cursor.getColumnIndex(SettingsDatabase.DIFFICULTY));
                    String quizID = quizDatabase.getQuizIdByDifficulty(difficulty);
                    GameActivity.questionArrayList = questionDatabase.fetchQuestions(quizID);
                }
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchScoreBoardActivity(view);
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSettingActivity(view);
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

    private void launchSettingActivity(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}