package com.example.kingsquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class QuizDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "DBQuiz";
    public static final String TABLE_NAME = "QUIZ";
    public static final String QUESTION_COUNT = "question_count";
    public static final String ID = "id";
    public static final String QUIZ_ID = "quiz_id";
    public static final String DIFFICULTY = "difficulty";
    public static final String DATE = "date";
    private static final String CREATE_TABLE = "create table " +
            TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            QUESTION_COUNT + " INTEGER, " + DIFFICULTY + " TEXT, "
            + QUIZ_ID + " TEXT, " + DATE + " TEXT);";

    public QuizDatabase(Context context) {
        super(context, DB_NAME, null, MainActivity.DB_VERSION);
    }

    @SuppressLint("Range")
    public String getQuizIdByDifficulty(String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{
                ID,
                QUIZ_ID,
                DATE,
                QUESTION_COUNT};
        Cursor cursor = db.query(TABLE_NAME, columns,
                DIFFICULTY + " LIKE " + difficulty,
                null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(QUIZ_ID));
    }

    public void insertQuiz(Quiz quiz) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUIZ_ID, quiz.quizID);
        contentValues.put(QUESTION_COUNT, Integer.toString(quiz.questionsCount));
        contentValues.put(DIFFICULTY, quiz.difficulty);
        contentValues.put(DATE, quiz.date.toString());

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
    public ArrayList<Quiz> fetchQuiz() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorQuiz = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Quiz> quizArrayList = new ArrayList<>();

        if (cursorQuiz.moveToFirst()) {
            do {
                quizArrayList.add(new Quiz(cursorQuiz.getString(3),
                        Integer.parseInt(cursorQuiz.getString(1)), cursorQuiz.getString(2)));
            } while (cursorQuiz.moveToNext());

        }

        cursorQuiz.close();
        return quizArrayList;
    }

}
