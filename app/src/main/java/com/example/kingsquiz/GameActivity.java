package com.example.kingsquiz;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://opentdb.com/api.php?amount=10&category=23&difficulty=medium&type=multiple";
    ArrayList<Question> questionArrayList = new ArrayList<>();
    int index = 0;
    boolean answered = false;
    TextView question;
    TextView page;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    ArrayList<Button> buttons;
    Button nextQuestion;
    Button finishQuiz;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        getData();
        question = findViewById(R.id.quiz_question);
        page = findViewById(R.id.question_number);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        nextQuestion = findViewById(R.id.next_question_button);
        finishQuiz = findViewById(R.id.end_quiz_button);
        buttons = new ArrayList<>();
        buttons.add(option1);
        buttons.add(option2);
        buttons.add(option3);
        buttons.add(option4);
        for (Button button :
                buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answered) {
                        return;
                    }
                    Question question = questionArrayList.get(index);
                    if (Arrays.asList(question.getWrongAnswers()).contains(button.getText())) {
                        button.setBackgroundColor(Color.RED);
                        getCorrectButton(question.getCorrectAnswer()).setBackgroundColor(Color.GREEN);
                    } else {
                        button.setBackgroundColor(Color.GREEN);
                    }
                    answered = true;
                    if (index == questionArrayList.size() - 1) {
                        finishQuiz.setVisibility(View.VISIBLE);
                    } else {
                        nextQuestion.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNextQuestion();
            }
        });


        finishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private Button getCorrectButton(String correctAnswer) {
        return option1.getText().equals(correctAnswer) ? option1 :
                option2.getText().equals(correctAnswer) ? option2 :
                        option3.getText().equals(correctAnswer) ? option3 : option4;
    }

    private void goToNextQuestion() {
        answered = false;
        nextQuestion.setVisibility(View.GONE);
        index++;
        for (Button button :
                buttons) {
            button.setBackgroundColor(Color.BLACK);
        }
        populateData();
    }

    private String[] getRandomSortOfArray(String[] strings) {
        List<String> strList = Arrays.asList(strings);
        Collections.shuffle(strList);
        return strList.toArray(new String[strList.size()]);
    }

    private void populateData() {
        Question firstQuestion = questionArrayList.get(index);
        question.setText(firstQuestion.getQuestion());
        Log.i("aaaaaaaaaaaaaaa", firstQuestion.getWrongAnswers().length + "");
        String[] shuffledArray = getRandomSortOfArray(
                new String[]{firstQuestion.getWrongAnswers()[0],
                        firstQuestion.getWrongAnswers()[1],
                        firstQuestion.getWrongAnswers()[2],
                        firstQuestion.getCorrectAnswer()});
        option1.setText(shuffledArray[3]);
        option2.setText(shuffledArray[0]);
        option3.setText(shuffledArray[1]);
        option4.setText(shuffledArray[2]);
        page.setText(index + 1 + " / " + questionArrayList.size());
    }

    private void getData() {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        mRequestQueue = Volley.newRequestQueue(this);
        Toast.makeText(getApplicationContext(), "Response :", Toast.LENGTH_LONG).show();//display the response on screen
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Question>>() {
                    }.getType();
                    questionArrayList = gson.fromJson(jsonObject.get("results").toString(), type);
                    populateData();
                    progress.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}