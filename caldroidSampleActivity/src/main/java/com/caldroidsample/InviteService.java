package com.caldroidsample;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class InviteService extends IntentService {
    public InviteService() {
        super("InviteService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bData = intent.getExtras();
        String str_date = bData.getString("str_date");
        String str_date2 = bData.getString("str_date2");

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, CaldroidSampleActivity.class),0);
        NotificationCompat.Builder builer = new NotificationCompat.Builder(this);

        builer.setTicker("小小筆記 Alerts");
        if(str_date!="")
        {
            builer.setContentTitle(str_date+ " 小小筆記通知");
        }
        else
        {
            builer.setContentTitle(str_date2+ " 小小筆記通知");
        }
        builer.setContentText("提醒您!注意您的Schedule");
        builer.setSmallIcon(R.drawable.not_36x36);
        builer.setContentIntent(pi);
        builer.setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builer.setSound(uri);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(123, builer.build());

    }
}
