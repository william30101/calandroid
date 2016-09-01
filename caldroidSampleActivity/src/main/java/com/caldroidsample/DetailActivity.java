package com.caldroidsample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView tv3;
    EditText ed1,ed2;
    //data d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent it = getIntent();
        String date2 = it.getStringExtra("date2");

        tv3 = (TextView) findViewById(R.id.detail_textView3);
        ed1 = (EditText) findViewById(R.id.detail_editText1);
        ed2 = (EditText) findViewById(R.id.detail_editText2);

        //tv3.setText(date2);

        DataDAO dao = new DataDAODBImpl(DetailActivity.this);
        List<data> mylist = dao.getData(new data(date2));
        //List<data> mylist = dao.getAllData();


        for(data d : mylist)
        {
            tv3.setText(d.date);
            ed1.setText(d.title);
            ed2.setText(d.content);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {

            if(ed1.getText().toString().equals("") || ed2.getText().toString().equals(""))
            {
                Toast.makeText(DetailActivity.this,"主旨或內容不能為空值",Toast.LENGTH_SHORT).show();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("更新資料");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("確定要更新資料?");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DataDAO dao = new DataDAODBImpl(DetailActivity.this);
                        data d = new data(tv3.getText().toString(), ed1.getText().toString(), ed2.getText().toString());
                        dao.updateData(d);
                        Toast.makeText(DetailActivity.this, "更新成功", Toast.LENGTH_SHORT).show();

                        new Thread(){
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                finish();
                            }
                        }.start();


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }

        }

        if (id == R.id.action_delete) {

            if(ed1.getText().toString().equals("") || ed2.getText().toString().equals(""))
            {
                finish();
                //Toast.makeText(DetailActivity.this,"主旨或內容沒有填入資料",Toast.LENGTH_SHORT).show();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("刪除資料");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("確定要刪除資料?");

                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataDAO dao = new DataDAODBImpl(DetailActivity.this);
                        dao.delData(new data(tv3.getText().toString()));
                        Toast.makeText(DetailActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();

                        new Thread(){
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                finish();
                            }
                        }.start();


                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.create().show();
            }

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
