package com.example.kingsquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SettingsDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "settingsDB";
    public static final String TABLE_NAME = "SETTINGS";
    public static final String DIFFICULTY = "difficulty";
    public static final String ID = "id";
    public static final String QUESTIONS_NUMBER = "questions_numbers";
    public static final String CATEGORY = "category";
    private static final String CREATE_TABLE = "create table " +
            TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DIFFICULTY + " TEXT NOT NULL, " + QUESTIONS_NUMBER + " INTEGER, "
            + CATEGORY + " TEXT);";

    public SettingsDatabase(Context context) {
        super(context, DB_NAME, null, MainActivity.DB_VERSION);
    }

    public void updateSettings(String difficulty, Integer questionNumbers, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SettingsDatabase.DIFFICULTY, difficulty);
        contentValues.put(SettingsDatabase.QUESTIONS_NUMBER, questionNumbers);
        contentValues.put(SettingsDatabase.CATEGORY, category);
        int i = db.update(SettingsDatabase.TABLE_NAME, contentValues,
                null,
                null);
    }

    public void insertSettings(SQLiteDatabase sqLiteDatabase, String difficulty, Integer questionsNumber, String category) {
        ContentValues values = new ContentValues();

        values.put(SettingsDatabase.DIFFICULTY, difficulty);
        values.put(SettingsDatabase.QUESTIONS_NUMBER, questionsNumber);
        values.put(SettingsDatabase.CATEGORY, category);

        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public Cursor fetchSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{SettingsDatabase.ID, SettingsDatabase.DIFFICULTY,
                SettingsDatabase.QUESTIONS_NUMBER, SettingsDatabase.CATEGORY};
        Cursor cursor = db.query(SettingsDatabase.TABLE_NAME, columns,
                null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr", "create settings table");
        sqLiteDatabase.execSQL(CREATE_TABLE);
        insertSettings(sqLiteDatabase, "Easy", 10, SettingActivity.categories.get(0));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
