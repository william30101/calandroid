package com.caldroidsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyAlarm extends BroadcastReceiver {
    public MyAlarm() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

            Bundle bData = intent.getExtras();

            Log.d("Alarm Recieved!", "YAAAY");
            String str_date  = bData.getString("str_date");
            //String str_date2  = bData.getString("str_date2");
            String msg = bData.getString("msg");

            Intent i = new Intent(context, InviteService.class);
            i.putExtra("str_date",str_date);
            //i.putExtra("str_date2",str_date2);
            //i.putExtra("msg",msg);

            context.startService(i);

    }
}
