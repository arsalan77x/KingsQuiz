package com.example.kingsquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreboardActivity extends AppCompatActivity {

    public static String username = "";
    public static String email = "";
    public static String userScore = "";
    public static String userRank = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout parent = (LinearLayout) findViewById(R.id.parent);

        parent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout fChildLinear = new LinearLayout(this);
        fChildLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        fChildLinear.setOrientation(LinearLayout.HORIZONTAL);
        fChildLinear.setBackground(ContextCompat.getDrawable(this,R.drawable.border_set));

        TextView rank = new TextView(this);
        rank.setText("Rank");
        TextView user = new TextView(this);
        user.setText("User");
        TextView score = new TextView(this);
        score.setText("Score");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rank.setTextSize(16);
        params.weight = 2.0f;
        params.gravity = Gravity.CENTER;
        rank.setGravity(Gravity.CENTER);
        rank.setLayoutParams(params);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        user.setMaxLines(1);
        user.setTextSize(16);
        params2.weight = 1.0f;
        params2.gravity = Gravity.CENTER;
        user.setGravity(Gravity.CENTER);
        user.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        score.setMaxLines(1);
        score.setTextSize(16);
        params3.weight = 1.5f;
        params3.gravity = Gravity.CENTER;
        score.setGravity(Gravity.CENTER);
        score.setLayoutParams(params3);


        parent.addView(fChildLinear);
        fChildLinear.addView(rank);
        fChildLinear.addView(user);
        fChildLinear.addView(score);
        for (int i = 0; i <  MainActivity.userArrayList.size(); i++) {
            LinearLayout childLinear = new LinearLayout(this);
            childLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            childLinear.setOrientation(LinearLayout.HORIZONTAL);
            childLinear.setBackground(ContextCompat.getDrawable(this,R.drawable.border_set));

            int finalI = i;
            int finalI1 = i;
            childLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = MainActivity.userArrayList.get(finalI).getUsername();
                    email = MainActivity.userArrayList.get(finalI).getEmail();
                    userScore = MainActivity.userArrayList.get(finalI).getScore()+"";
                    userRank = finalI1 + 1 + "";
                    launchUserInfoActivity(v);
                }
            });
            rank = new TextView(this);
            rank.setText(i+1+"");
            user = new TextView(this);
            user.setText(MainActivity.userArrayList.get(i).getUsername());
            score = new TextView(this);
            score.setText(MainActivity.userArrayList.get(i).getScore()+"");

            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            rank.setTextSize(16);
            params.weight = 2.0f;
            params.gravity = Gravity.CENTER;
            rank.setGravity(Gravity.CENTER);
            rank.setLayoutParams(params);

            params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            user.setMaxLines(1);
            user.setTextSize(16);
            params2.weight = 1.0f;
            params2.gravity = Gravity.CENTER;
            user.setGravity(Gravity.CENTER);
            user.setLayoutParams(params2);

            params3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            score.setMaxLines(1);
            score.setTextSize(16);
            params3.weight = 1.5f;
            params3.gravity = Gravity.CENTER;
            score.setGravity(Gravity.CENTER);
            score.setLayoutParams(params3);

            if (i == 0) {
                rank.setTextColor(Color.YELLOW);
                user.setTextColor(Color.YELLOW);
                score.setTextColor(Color.YELLOW);
            }

            if (i == 1) {
                rank.setTextColor(Color.GRAY);
                user.setTextColor(Color.GRAY);
                score.setTextColor(Color.GRAY);
            }

            if (i == 2) {
                rank.setTextColor(Color.GREEN);
                user.setTextColor(Color.GREEN);
                score.setTextColor(Color.GREEN);
            }


            parent.addView(childLinear);
            childLinear.addView(rank);
            childLinear.addView(user);
            childLinear.addView(score);

        }

        Button mainMenuButton = new Button(this);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0,40,0,0);
        params.setMarginEnd(40);
        params.setMarginStart(40);
        mainMenuButton.setLayoutParams(params);
        mainMenuButton.setText("MAIN MENU");
        parent.addView(mainMenuButton);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
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

    private void launchUserInfoActivity(View view){
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }


}