package com.example.kingsquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class QuestionDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "DBQuestion";
    public static final String TABLE_NAME = "QUESTIONS";
    public static final String QUESTION = "question";
    public static final String ID = "id";
    public static final String QUIZ_ID = "quiz_id";
    public static final String CORRECT_ANSWER = "correct_answer";
    public static final String WRONG_ANSWERS = "wrong_answers";
    public static final String ANSWERED = "answered";
    public static final String USER_ANSWER = "user_answer";
    public static final String IS_USER_ANSWER_CORRECT = "is_user_answer_correct";
    private static final String CREATE_TABLE = "create table " +
            TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUESTION + " TEXT NOT NULL, " + CORRECT_ANSWER + " TEXT, "
            + ANSWERED + " TEXT, " + USER_ANSWER + " TEXT, "
            + QUIZ_ID + " TEXT, "
            + IS_USER_ANSWER_CORRECT + " TEXT, " + WRONG_ANSWERS + " TEXT);";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public QuestionDatabase(Context context) {
        super(context, DB_NAME, null, MainActivity.DB_VERSION);
    }

    @SuppressLint("Range")
    public ArrayList<Question> fetchQuestions(String quizID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{
                QUESTION,
                ID,
                QUIZ_ID,
                CORRECT_ANSWER,
                WRONG_ANSWERS,
                ANSWERED,
                USER_ANSWER,
                IS_USER_ANSWER_CORRECT};

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        Log.d(LOG_TAG,  quizID+"!!!");
        ArrayList<Question> questionArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Log.d(LOG_TAG,  cursor.getString(0)+cursor.getString(1)+
                        cursor.getString(2)+cursor.getString(3)+cursor.getString(4)+"???");
                if(Objects.equals(quizID, cursor.getString(5))){
                    String title = cursor.getString(cursor.getColumnIndex(QUESTION));
                    String correctAnswer = cursor.getString(cursor.getColumnIndex(CORRECT_ANSWER));
                    String wrongAnswers = cursor.getString(cursor.getColumnIndex(WRONG_ANSWERS));
                    ArrayList<String> stringArray = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(wrongAnswers);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            stringArray.add(jsonArray.getString(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Question question = new Question(title
                            , correctAnswer
                            , stringArray.toArray(new String[0])
                    );
                    questionArrayList.add(question);
                }

            } while (cursor.moveToNext());

        }

        cursor.close();
        return questionArrayList;
    }

    public void insertQuestion(Question question, String quizID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION, question.getQuestion());
        contentValues.put(CORRECT_ANSWER, question.getCorrectAnswer());
        contentValues.put(ANSWERED, question.answered);
        contentValues.put(USER_ANSWER, question.userAnswer);
        contentValues.put(QUIZ_ID, quizID);
        contentValues.put(IS_USER_ANSWER_CORRECT, question.isUserAnswerCorrect);
        Gson gson = new GsonBuilder().create();
        String jsonArray = gson.toJson(question.getWrongAnswers());
        contentValues.put(WRONG_ANSWERS, jsonArray);
        db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



}
