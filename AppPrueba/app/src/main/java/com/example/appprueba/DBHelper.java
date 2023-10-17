package com.example.appprueba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "UserData", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table user_details(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists user_details");
        onCreate(DB);
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase DB =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = DB.insert("user_details", null, contentValues);
        if(result==-1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from user_details where username=?", new String[] {username});
        if(cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from user_details where username=? and password=?", new String[]{username, password});
        if(cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }
}
