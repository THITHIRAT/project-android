package com.example.thithirat.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thithirat on 22/2/2561.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_USER_NAME = "user";
    private static final String COL_ID = "_id";
    private static final String COL_TOKEN = "token";

    private static final String USER_CREATE = "CREATE TABLE " + TABLE_USER_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, token TEXT)";

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_USER_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertData(String token) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TOKEN, token);
    }
}
