package com.example.kingsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QuestionDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "questionDB";
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

    public QuestionDatabase(Context context) {
        super(context, DB_NAME, null, MainActivity.DB_VERSION);
    }

    public void insertQuestion(Question question, String quizID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION, question.getQuestion());
        contentValues.put(QUIZ_ID, quizID);
        contentValues.put(CORRECT_ANSWER, question.getCorrectAnswer());
        contentValues.put(ANSWERED, question.answered);
        contentValues.put(USER_ANSWER, question.userAnswer);
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
