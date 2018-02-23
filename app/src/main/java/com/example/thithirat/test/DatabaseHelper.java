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
    private static final String TABLE_NAME = "user";
    private static final String COL_ID = "_id";
    private static final String COL_USERANME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_EMAIL = "email";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT)";

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertData(String username, String password, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERANME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_EMAIL, email);
    }
}
