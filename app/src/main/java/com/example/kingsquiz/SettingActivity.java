package com.example.kingsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {

    List<String> difficultyList = Arrays.asList("Easy", "Medium", "Hard");
    List<String> categoryList = Arrays.asList("cat1", "cat2");
    int difficultyCount = 0;
    int numberCount = 0;
    int categoryCount = 0;
    public static boolean isBlueOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBlueOn) {
            this.setTheme(R.style.Theme1);
        } else {
            this.setTheme(R.style.Theme2);
        }
        setContentView(R.layout.activity_setting);

        Button mainMainButton = (Button) findViewById(R.id.mainMenuButton);
        Button increaseButton1 = (Button) findViewById(R.id.increaseButton1);
        Button increaseButton2 = (Button) findViewById(R.id.increaseButton2);
        Button increaseButton3 = (Button) findViewById(R.id.increaseButton3);
        Button decreaseButton1 = (Button) findViewById(R.id.decreaseButton1);
        Button decreaseButton2 = (Button) findViewById(R.id.decreaseButton2);
        Button decreaseButton3 = (Button) findViewById(R.id.decreaseButton3);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button blueThemeButton = (Button) findViewById(R.id.blueTheme);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button blackThemeButton = (Button) findViewById(R.id.blackTheme);
        TextView difficultyText = (TextView) findViewById(R.id.difficulty);
        TextView numbersText = (TextView) findViewById(R.id.numbers);
        TextView categoryText = (TextView) findViewById(R.id.category);

        mainMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainMenuActivity(v);
            }
        });

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
            }
        });

        increaseButton2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!numbersText.getText().equals("0")) {
                    numbersText.setText(Integer.parseInt(numbersText.getText().toString()) + 1 + "");
                }
            }
        });

        decreaseButton2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!numbersText.getText().equals("1")) {
                    numbersText.setText(Integer.parseInt(numbersText.getText().toString()) - 1 + "");
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

    private void launchSettingActivity(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void launchMainMenuActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}