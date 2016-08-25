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
        /*
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.drawable.p1;
        CharSequence tickerText = "New Invite!";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        Context context = getApplicationContext();
        CharSequence contentTitle = "Title";
        CharSequence contentText = "Text";
        Intent notificationIntent = new Intent(this, HomeActivity.class);
        Bundle partyBundle = new Bundle();

        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        int NOTIFICATION_ID = 1;

        Log.d("NOTIFICATION_ID", "" + NOTIFICATION_ID);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
        */

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class),0);
        NotificationCompat.Builder builer = new NotificationCompat.Builder(this);

        builer.setTicker("Message");
        builer.setContentTitle("Title");
        builer.setContentText("Text");
        builer.setSmallIcon(R.drawable.p1);
        builer.setContentIntent(pi);
        builer.setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builer.setSound(uri);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(123, builer.build());
    }
}
