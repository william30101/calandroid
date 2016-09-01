package com.caldroidsample;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent it = new Intent(HomeActivity.this,CaldroidSampleActivity.class);
                startActivity(it);
                finish();

            }
        }.start();


/*
        Intent alarmIntent = new Intent(this, MyAlarm.class);



        long scTime = 10000;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        //long firstime = SystemClock.elapsedRealtime();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //alarmManager.set(AlarmManager.RTC_WAKEUP, mCal.getTimeInMillis(), pendingIntent);

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + scTime, pendingIntent);*/

        //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, 10 * 1000, pendingIntent);



        //可以在特定的頁面裡面在註冊(register)接收器，而且可以註銷(unregister)你的接收器。
/*
        MyAlarm receiver = new MyAlarm();

        //創建一個IntentFilte物件
        IntentFilter filter = new IntentFilter();
        //加入Action的辨識字串
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);

        filter.setPriority(100);
        //註冊我們創建的MyAlarm的Receiver
        registerReceiver(receiver, filter);
*/

    }
}
