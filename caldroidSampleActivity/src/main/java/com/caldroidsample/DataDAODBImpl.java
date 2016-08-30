package com.caldroidsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
public class DataDAODBImpl implements DataDAO {

    SQLiteDatabase db;

    public DataDAODBImpl(Context context)
    {
        DataDBHelper helper = new DataDBHelper(context);
        db = helper.getWritableDatabase();
    }

    @Override
    public void addData(data d) {
        ContentValues cv = new ContentValues();
        cv.put("date",  d.date);
        cv.put("title", d.title);
        cv.put("content", d.content);
        // 執行SQL 語句
        long id = db.insert("data", null, cv);
    }

    @Override
    public void delData(data d) {
        //execSQL 用於新增、刪除、更改、創資料表用
        db.execSQL("Delete from data where date='" + d.date + "'");
    }

    @Override
    public void updateData(data d) {
        db.execSQL("Update data set title ='" + d.title + "', content='" + d.content + "' Where date='" + d.date + "'");
    }

    @Override
    public List getAllData() {
        ArrayList<data> mylist = new ArrayList<>();

        //rawQuery 用於查詢
        Cursor c = db.rawQuery("Select * From data order by date asc", null);
        if (c.moveToFirst())
        {
            do {
                data a = new data(c.getString(1), c.getString(2), c.getString(3));
                mylist.add(a);
            } while (c.moveToNext());
        }

        return mylist;
    }

    @Override
    public List getData(data d) {

        ArrayList<data> mylist = new ArrayList<>();

        //rawQuery 用於查詢
        //Cursor c = db.rawQuery("Select * from data where date=' " + d.date + "'", null);
        //Cursor c = db.rawQuery("Select * from data where date='2016/08/22'", null);
        Cursor c = db.rawQuery("Select * from data where date='" + d.date + "'", null);
        if (c.moveToFirst())
        {
            do {
                data a = new data(c.getString(1), c.getString(2), c.getString(3));
                mylist.add(a);
            } while (c.moveToNext());
        }

        return mylist;
    }

    @Override
    public List checkData(data d) {
        ArrayList<data> mylist = new ArrayList<>();

        //rawQuery 用於查詢
        //Cursor c = db.rawQuery("Select * from data where date>'2016/08/30'", null);
        Cursor c = db.rawQuery("Select * from data where date>'" + d.date + "' order by date asc", null);
        if (c.moveToFirst())
        {
            do {
                data a = new data(c.getString(1), c.getString(2), c.getString(3));
                mylist.add(a);
            } while (c.moveToNext());
        }

        return mylist;
    }

}
