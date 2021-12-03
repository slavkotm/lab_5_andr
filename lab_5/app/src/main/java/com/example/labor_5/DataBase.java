package com.example.labor_5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorTreeAdapter;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "People.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRSTNAME";
    public static final String COL_3 = "NAME";
    public static final String COL_4 = "COMMENT";
    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, FIRSTNAME TEXT, COMMENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String firstName, String name, String comment) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, comment);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int countRows() {
        int rowsCount = 0;
        String selectCountRows = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(selectCountRows, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            rowsCount = cursor.getInt(0);
        }
        return rowsCount;
    }

    public Cursor getAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
