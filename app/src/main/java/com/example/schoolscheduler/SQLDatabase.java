package com.example.schoolscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class SQLDatabase extends SQLiteOpenHelper {
    String TableName;
    public SQLDatabase(@Nullable Context context
            , @Nullable String dataBaseName
            , @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, dataBaseName, factory, version);
        this.TableName = TableName;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TaskName TEXT, " +
                "Subject TEXT," +
                "Type TEXT," +
                "Due TEXT," +
                "Details TEXT" +
                ");";
        db.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + TableName;
        db.execSQL(SQL);

    }
    //check if table exists
    public void checkTable(){
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "TaskName TEXT, " +
                        "Subject TEXT," +
                        "Type TEXT," +
                        "Due TEXT," +
                        "Details TEXT" +
                        ");");
            cursor.close();
       }
    }

    //add data
    public void addData(String name, String elseInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TaskName", name);
        values.put("Details", elseInfo);
        db.insert(TableName, null, values);
    }

    //show task titles
    public ArrayList<String> showOne() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName, null);
        ArrayList<String> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            String id = c.getString(1);
            arrayList.add(id);
        }
        return arrayList;
    }
}