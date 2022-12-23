package com.example.kingsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    List<String> difficultyList = Arrays.asList("Easy", "Medium", "Hard");
    List<String> categoryList = Arrays.asList("cat1", "cat2");
    int difficultyCount = 0;
    int numberCount = 0;
    int categoryCount = 0;
    public static boolean isBlueOn = false;
    private SettingsDatabase settingsDatabase;
    TextView difficultyText;
    TextView numbersText;
    TextView categoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBlueOn) {
            this.setTheme(R.style.Theme1);
        } else {
            this.setTheme(R.style.Theme2);
        }
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button increaseButton1 = (Button) findViewById(R.id.increaseButton1);
        Button increaseButton2 = (Button) findViewById(R.id.increaseButton2);
        Button increaseButton3 = (Button) findViewById(R.id.increaseButton3);
        Button decreaseButton1 = (Button) findViewById(R.id.decreaseButton1);
        Button decreaseButton2 = (Button) findViewById(R.id.decreaseButton2);
        Button decreaseButton3 = (Button) findViewById(R.id.decreaseButton3);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button blueThemeButton = (Button) findViewById(R.id.blueTheme);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button blackThemeButton = (Button) findViewById(R.id.blackTheme);
        difficultyText = (TextView) findViewById(R.id.difficulty);
        numbersText = (TextView) findViewById(R.id.numbers);
        categoryText = (TextView) findViewById(R.id.category);


        settingsDatabase = new SettingsDatabase(SettingActivity.this);
        initializeSettings();

        increaseButton1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (difficultyText.getText().equals("Hard")) {
                    difficultyText.setText("Easy");
                } else if (difficultyText.getText().equals("Medium")) {
                    difficultyText.setText("Hard");
                } else if (difficultyText.getText().equals("Easy")) {
                    difficultyText.setText("Medium");
                }
                updateSettings();
            }
        });

        decreaseButton1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (difficultyText.getText().toString().equals("Hard")) {
                    difficultyText.setText("Medium");
                } else if (difficultyText.getText().toString().equals("Medium")) {
                    difficultyText.setText("Easy");
                } else if (difficultyText.getText().toString().equals("Easy")) {
                    difficultyText.setText("Hard");
                }
                updateSettings();
            }
        });

        increaseButton2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!numbersText.getText().equals("0")) {
                    numbersText.setText(Integer.parseInt(numbersText.getText().toString()) + 1 + "");
                    updateSettings();
                }
            }
        });

        decreaseButton2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!numbersText.getText().equals("1")) {
                    numbersText.setText(Integer.parseInt(numbersText.getText().toString()) - 1 + "");
                    updateSettings();
                }
            }
        });

        //TODO CATEGORY


        //
        blackThemeButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                isBlueOn = false;
                launchSettingActivity(v);
            }
        });

        blueThemeButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                isBlueOn = true;
                launchSettingActivity(v);
            }
        });


    }

    @SuppressLint("Range")
    private void initializeSettings() {
        Cursor cursor = settingsDatabase.fetchSettings();
        difficultyText.setText(cursor.getString(cursor.getColumnIndex(SettingsDatabase.DIFFICULTY)));
        numbersText.setText(cursor.getString(cursor.getColumnIndex(SettingsDatabase.QUESTIONS_NUMBER)));
        categoryText.setText(cursor.getString(cursor.getColumnIndex(SettingsDatabase.CATEGORY)));
    }

    private void updateSettings() {
        settingsDatabase.updateSettings((String) difficultyText.getText(),
                Integer.parseInt((String) numbersText.getText()), (String) categoryText.getText());
    }

    private void launchSettingActivity(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}