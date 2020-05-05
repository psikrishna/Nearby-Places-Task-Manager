package com.example.admin.taskmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "tasks.db";
    public static final String TABLE_NAME = "record";

    public static final String COL_1 = "title";
    public static final String COL_2 = "details";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (title text, details text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean onInsert(String title, String details){
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, title);
        contentValues.put(COL_2, details);
        long v = db1.insert(TABLE_NAME, null, contentValues);
        if (v != -1){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean onUpdate(String id, String name){
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        int v = db1.update(TABLE_NAME, contentValues, "title = ?",  new String[]{id});
        if (v > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean onRemove(String id){
        SQLiteDatabase db1 = this.getWritableDatabase();
        int v = db1.delete(TABLE_NAME, "title = ?", new String[]{id});
        if (v > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor res =  db1.rawQuery( "select * from "+TABLE_NAME, null );
        return res;
    }
}
