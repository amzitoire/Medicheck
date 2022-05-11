package com.android.medicheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BDUser extends SQLiteOpenHelper {
    private static BDUser bdUser;
    public BDUser(@Nullable Context context) {
        super(context, "bd_user.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, login VARCHAR(50) UNIQUE, password VARCHAR(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS user ");
        onCreate(db);
    }

    public boolean addUser(String login, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put("login",login);
            cv.put("password",password);
            db.insert("user",null,cv);
            db.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
    public boolean DeleteUser(String login){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete("user","login ='"+login+"'",null);
            db.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateUser(String login, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put("password",password);
            db.update("user",cv,"login ='"+login+"'",null);
            db.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public List<String> getUsers(){
        List<String> users = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query("user",null,null,null,null,null,null);
            if(cursor.moveToFirst()) {
                do {
                    users.add("{ login : "+cursor.getString(1)+"\n password : "+cursor.getString(2)+" }");
                } while (cursor.moveToNext());
            }
            db.close();
            return users;
        }catch (Exception e){
            e.printStackTrace();
            return users;
        }
    }

    public static synchronized BDUser getInstance(Context context) {
        if (bdUser == null) {
            bdUser = new BDUser(context);
        }
        return bdUser;
    }
}
