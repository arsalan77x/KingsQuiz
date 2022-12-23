package com.example.kingsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingActivity.isBlueOn) {
            this.setTheme(R.style.Theme1);
        } else {
            this.setTheme(R.style.Theme2);
        }
        setContentView(R.layout.activity_user_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView usernameText = (TextView) findViewById(R.id.username);
        TextView emailText = (TextView) findViewById(R.id.email);
        TextView scoreText = (TextView) findViewById(R.id.score);

        usernameText.setText(ScoreboardActivity.username);
        emailText.setText("Email: "+ScoreboardActivity.email);
        scoreText.setText("Score: "+ScoreboardActivity.userScore);

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