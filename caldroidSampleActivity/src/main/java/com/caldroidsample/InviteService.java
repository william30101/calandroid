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

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class),0);
        NotificationCompat.Builder builer = new NotificationCompat.Builder(this);

        builer.setTicker("Message");
        builer.setContentTitle("Title " + str_date2);
        builer.setContentText("Text "+ str_date);
        builer.setSmallIcon(R.drawable.p1);
        builer.setContentIntent(pi);
        builer.setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builer.setSound(uri);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(123, builer.build());

    }
}
