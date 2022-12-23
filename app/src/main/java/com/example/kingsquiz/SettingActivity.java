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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static boolean isBlueOn = false;
    private TextView difficultyText;
    private TextView numbersText;
    private Spinner categorySpinner;
    public static ArrayList<String> categories = new ArrayList<>(Arrays.asList("General Knowledge",
            "Entertainment: Books",
            "Entertainment: Film",
            "Entertainment: Music",
            "Entertainment: Musicals & Theatres",
            "Entertainment: Television",
            "Entertainment: Video Games",
            "Entertainment: Board Games",
            "Science & Nature",
            "Science: Computers",
            "Science: Mathematics",
            "Mythology",
            "Sports",
            "Geography",
            "History",
            "Politics",
            "Art",
            "Celebrities",
            "Animals",
            "Vehicles",
            "Entertainment: Comics",
            "Science: Gadgets",
            "Entertainment: Japanese Anime & Manga",
            "Entertainment: Cartoon & Animations"));

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
        Button decreaseButton1 = (Button) findViewById(R.id.decreaseButton1);
        Button decreaseButton2 = (Button) findViewById(R.id.decreaseButton2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button blueThemeButton = (Button) findViewById(R.id.blueTheme);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button blackThemeButton = (Button) findViewById(R.id.blackTheme);
        difficultyText = (TextView) findViewById(R.id.difficulty);
        numbersText = (TextView) findViewById(R.id.numbers);

        categorySpinner = findViewById(R.id.category_spinner);
        categorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);

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
        Cursor cursor = MainActivity.settingsDatabase.fetchSettings();
        difficultyText.setText(cursor.getString(cursor.getColumnIndex(SettingsDatabase.DIFFICULTY)));
        numbersText.setText(cursor.getString(cursor.getColumnIndex(SettingsDatabase.QUESTIONS_NUMBER)));
        categorySpinner.setSelection(categories.indexOf(cursor.getString(cursor.getColumnIndex(SettingsDatabase.CATEGORY))));
    }

    private void updateSettings() {
        MainActivity.settingsDatabase.updateSettings((String) difficultyText.getText(),
                Integer.parseInt((String) numbersText.getText()), categorySpinner.getSelectedItem().toString());
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateSettings();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}