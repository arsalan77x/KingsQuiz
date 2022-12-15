package com.example.kingsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button mainMainButton = (Button) findViewById(R.id.mainMenuButton);
        TextView usernameText = (TextView) findViewById(R.id.username);
        TextView emailText = (TextView) findViewById(R.id.email);
        TextView scoreText = (TextView) findViewById(R.id.score);

        usernameText.setText(ScoreboardActivity.username);
        emailText.setText("Email: "+ScoreboardActivity.email);
        scoreText.setText("Score: "+ScoreboardActivity.userScore);

        mainMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainMenuActivity(v);
            }
        });

    }


    private void launchMainMenuActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}