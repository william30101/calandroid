package com.caldroidsample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hirondelle.date4j.DateTime;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public BootBroadcastReceiver() {
    }

    SQLiteDatabase db;
    long timelnMilliseconds;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("Restart Alarm Recieved!", "YAAAY");
            //Intent i = new Intent(context, InviteService.class);
            //context.startService(i);

            //DataDBHelper helper = new DataDBHelper(context);
            //db = helper.getWritableDatabase();

            //Date date = new Date();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String tdate = sDateFormat.format(new Date());

            DataDAO dao = new DataDAODBImpl(context);
            List<data> mylist = dao.checkData(new data(tdate));

            for(data d : mylist)
            {
                Intent alarmIntent = new Intent(context, MyAlarm.class);
                alarmIntent.putExtra("str_date2",d.date);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    Date mdate = sdf.parse(d.date);

                    timelnMilliseconds = mdate.getTime();
                    //timelnMilliseconds -= 24 * 60 * 60 * 1000;

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //timelnMilliseconds -= 24 * 60 * 60 * 1000;
                //long scTime = 24 * 60 * 60 * 1000;
                //long scTime = 2 * 60 * 1000;
                long scTime = 24 * 60 * 60 * 1000;
                Log.d("Alarm Recieved!", "test" );
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
                //long firstime = SystemClock.elapsedRealtime();
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

                //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + scTime, pendingIntent);

                alarmManager.set(AlarmManager.RTC_WAKEUP, timelnMilliseconds - scTime, pendingIntent);
            }
        }
    }
}
