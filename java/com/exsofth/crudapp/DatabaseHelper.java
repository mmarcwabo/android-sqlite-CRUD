package com.exsofth.crudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    //variable declaration
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME= "student_table";
    public static final String COL_1= "ID";
    public static final String COL_2= "NAMES";
    public static final String COL_3= "EMAIL";
    public static final String COL_4= "MARKS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAMES TEXT, EMAIL TEXT, MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    //CRUD : Create
    public boolean insertData(String names, String email, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(COL_2, names);
        values.put(COL_3, email);
        //Casting the string value to integer
        values.put(COL_4, marks);
        long result = db.insert(TABLE_NAME, null, values);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    //CRUD : Read - All data
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return result;
    }

    //CRUD : Update
    public boolean updateData(String id, String[] dataToUpdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(COL_2, dataToUpdate[0]);
        values.put(COL_3, dataToUpdate[1]);
        values.put(COL_4, dataToUpdate[2]);

        db.update(TABLE_NAME, values, " ID = ?", new String[]{id});
        return true;
    }

    //CRUD : Delete
    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, " ID = ?", new String[]{id});
    }
}
