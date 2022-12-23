package com.example.kingsquiz;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class GameActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private NavigationView navigationView;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String baseUrl = "https://opentdb.com/api.php?";
    public static ArrayList<Question> questionArrayList = new ArrayList<>();
    private Question currentQuestion;
    private TextView question;
    private TextView page;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private ArrayList<Button> buttons;
    private Button nextQuestion;
    private Button finishQuiz;
    private Button viewAllQuestions;
    private TextView newScoreText;
    private LinearLayout endQuizContainer;
    private ProgressDialog progress;
    private Settings settings;
    private int index = 0;
    private String quizID;
    private int topScoreboardScore;
    public static boolean offlineMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);

        question = findViewById(R.id.quiz_question);
        page = findViewById(R.id.question_number);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        nextQuestion = findViewById(R.id.next_question_button);
        finishQuiz = findViewById(R.id.end_quiz_button);
        viewAllQuestions = findViewById(R.id.view_all_questions_button);
        endQuizContainer = findViewById(R.id.end_quiz_container);
        newScoreText = findViewById(R.id.new_score_text);
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
                    if (currentQuestion.answered) {
                        return;
                    }
                    Question question = questionArrayList.get(index);
                    if (Arrays.asList(question.getWrongAnswers()).contains(button.getText())) {
                        button.setBackgroundColor(Color.RED);
                        getCorrectButton(question.getCorrectAnswer()).setBackgroundColor(Color.GREEN);
                    } else {
                        button.setBackgroundColor(Color.GREEN);
                    }
                    currentQuestion.answered = true;
                    currentQuestion.userAnswer = (String) button.getText();
                    currentQuestion.checkUserAnswer();
                    updateUserScore();
                    if (index == questionArrayList.size() - 1) {
                        checkIsRecordBroken();
                        endQuizContainer.setVisibility(View.VISIBLE);
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
                GameActivity.this.finish();
            }
        });

        viewAllQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, ReviewQuestionsActivity.class);
                startActivity(intent);
            }
        });

        initilizeTopScoreboardScore();
        initializeSettings();
        if (!offlineMode) {
            progress.show();
            getData();
        } else {
            populateData();
            offlineMode = false;
        }

    }

    public Button getCorrectButton(String correctAnswer) {
        return option1.getText().equals(correctAnswer) ? option1 :
                option2.getText().equals(correctAnswer) ? option2 :
                        option3.getText().equals(correctAnswer) ? option3 : option4;
    }

    private void goToNextQuestion() {
        currentQuestion.answered = false;
        nextQuestion.setVisibility(View.GONE);
        index++;
        for (Button button :
                buttons) {
            button.setBackgroundColor(Color.BLACK);
        }
        populateData();
    }

    private void initilizeTopScoreboardScore() {
        Collections.sort(MainActivity.userArrayList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Math.round(o2.getScore() - (o1.getScore()));
            }
        });
        topScoreboardScore = MainActivity.userArrayList.get(0).getScore();
    }

    private void checkIsRecordBroken() {
        MainActivity.userArrayList = MainActivity.userDatabase.fetchUsers();
        for (User user :
                MainActivity.userArrayList) {
            if (user.getEmail().equals(MainActivity.userLoggedIn.getEmail())) {
                if (user.getScore() > topScoreboardScore) {
                    newScoreText.setVisibility(View.VISIBLE);
                    return;
                } else {
                    newScoreText.setVisibility(View.GONE);
                }
            }
        }
    }

    private void updateUserScore() {
        int score = 0;
        if (currentQuestion.answered) {
            int difficulty = Settings.getDifficultyValue(settings.difficulty);
            if (currentQuestion.isUserAnswerCorrect) {
                score += 3 * difficulty;
            } else {
                score -= difficulty;
            }
            int finalScore = MainActivity.userLoggedIn.getScore() + score;
            MainActivity.userDatabase.updateScore(MainActivity.userLoggedIn.getEmail(),
                    finalScore);
            MainActivity.userLoggedIn.setScore(finalScore);
        }
    }

    private String[] getRandomSortOfArray(String[] strings) {
        List<String> strList = Arrays.asList(strings);
        Collections.shuffle(strList);
        return strList.toArray(new String[strList.size()]);
    }

    private void populateData() {
        Question firstQuestion = questionArrayList.get(index);
        currentQuestion = firstQuestion;
        question.setText(firstQuestion.getQuestion());
        String[] shuffledArray = getRandomSortOfArray(
                new String[]{firstQuestion.getWrongAnswers()[0],
                        firstQuestion.getWrongAnswers()[1],
                        firstQuestion.getWrongAnswers()[2],
                        firstQuestion.getCorrectAnswer()});
        currentQuestion.options = shuffledArray;
        option1.setText(shuffledArray[0]);
        option2.setText(shuffledArray[1]);
        option3.setText(shuffledArray[2]);
        option4.setText(shuffledArray[3]);
        page.setText(index + 1 + " / " + questionArrayList.size());
    }

    private void getData() {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        mRequestQueue = Volley.newRequestQueue(this);
        String url = getUrl();
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Question>>() {
                    }.getType();
                    questionArrayList = gson.fromJson(jsonObject.get("results").toString(), type);
                    quizID = UUID.randomUUID().toString();

                    for (Question question :
                            questionArrayList) {
                        MainActivity.questionDatabase.insertQuestion(question, quizID);
                    }
                    Quiz quiz = new Quiz(quizID, questionArrayList.size(), settings.difficulty);
                    MainActivity.quizDatabase.insertQuiz(quiz);
                    populateData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(),
                        "You are not connected to the internet! Offline mode is on",
                        Toast.LENGTH_SHORT).show();
                String quizID = MainActivity.quizArrayList.get(0).quizID;
                questionArrayList = MainActivity.questionDatabase.fetchQuestions(quizID);

                populateData();
              //  GameActivity.this.finish();
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    @SuppressLint("Range")
    private void initializeSettings() {
        Cursor cursor = MainActivity.settingsDatabase.fetchSettings();
        String amount = cursor.getString(cursor.getColumnIndex(SettingsDatabase.QUESTIONS_NUMBER));
        String difficulty = cursor.getString(cursor.getColumnIndex(SettingsDatabase.DIFFICULTY));
        String category = cursor.getString(cursor.getColumnIndex(SettingsDatabase.CATEGORY));
        int categoryValue = SettingActivity.categories.indexOf(category) + 9;
        settings = new Settings(Integer.parseInt(amount), categoryValue, difficulty);
    }

    private String getUrl() {
        return baseUrl + "amount=" + settings.questionAmount + "&category=" + settings.category + "&difficulty="
                + settings.difficulty.toLowerCase() + "&type=multiple";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_scoreboard:
                intent = new Intent(GameActivity.this, ScoreboardActivity.class);
                break;
            case R.id.nav_profile:
                intent = new Intent(GameActivity.this, ProfileActivity.class);
                break;
            default:
                intent = new Intent(GameActivity.this, SettingActivity.class);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(intent);
        return true;
    }
}