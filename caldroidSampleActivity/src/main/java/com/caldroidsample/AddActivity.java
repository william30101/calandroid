package com.caldroidsample;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    TextView tv3;
    EditText ed1,ed2;
    long timelnMilliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent it = getIntent();
        String date = it.getStringExtra("date");

        tv3 = (TextView) findViewById(R.id.add_textView3);
        tv3.setText(date);

        ed1 = (EditText) findViewById(R.id.add_editText1);
        ed2 = (EditText) findViewById(R.id.add_editText2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            if(ed1.getText().toString().equals("") || ed2.getText().toString().equals(""))
            {
                Toast.makeText(AddActivity.this,"主旨或內容不能為空值",Toast.LENGTH_SHORT).show();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("新增資料");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("確定要新增資料?");

                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String d = tv3.getText().toString();
                        String t = ed1.getText().toString();
                        String c = ed2.getText().toString();

                        DataDAO dao = new DataDAODBImpl(AddActivity.this);
                        dao.addData(new data(d, t, c));

                       // SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        //String tdate = sDateFormat.format(new Date());
                        //List<data> mylist = dao.checkData(new data(tdate));

                       //for(data a : mylist) {
                        Intent alarmIntent = new Intent(AddActivity.this, MyAlarm.class);
                        alarmIntent.addCategory(d);
                        //Log.d("test",mylist.toString());
                        alarmIntent.putExtra("str_date",d);
                            //alarmIntent.addCategory("Date:" + d);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            Date sdate = sdf.parse(d);
                            timelnMilliseconds = sdate.getTime();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Intent it = new Intent();
                        //String str ="success";
                        it.putExtra("date",d);
                        setResult(RESULT_OK, it);


                        long scTime = 24 * 60 * 60 * 1000;
                        //long scTime = 2 * 60 * 1000;
                        //long scTime = 10000;

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, 0, alarmIntent, 0);
                        //long firstime = SystemClock.elapsedRealtime();
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, timelnMilliseconds - scTime, pendingIntent);
                       // }

                        //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, 10 * 1000, pendingIntent);

                        Toast.makeText(AddActivity.this,"新增成功",Toast.LENGTH_SHORT).show();

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
