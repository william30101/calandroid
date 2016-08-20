package com.caldroidsample;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/20.
 */
public class DataDBHelper extends SQLiteOpenHelper {

    final static String DB_Name = "data.sqlite";
    final static  int VERSION = 1;
    final static String CREATE_TABLE_SQL = "CREATE  TABLE main.student (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name VARCHAR,addr VARCHAR, tel VARCHAR)";

    public DataDBHelper(Context context)
    {
        super(context , DB_Name ,null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
