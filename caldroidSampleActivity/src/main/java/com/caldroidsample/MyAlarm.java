package com.caldroidsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarm extends BroadcastReceiver {
    public MyAlarm() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm Recieved!", "YAAAY");
        Intent i = new Intent(context, InviteService.class);
        context.startService(i);



    }
}
