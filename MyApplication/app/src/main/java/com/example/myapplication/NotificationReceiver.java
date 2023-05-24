package com.example.myapplication;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
/*
ο κώδικας βασίζεται στο documentation της google
και είναι για την δημιουργία ενός notification
*/
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int i = bundle.getInt("id");
        String text = bundle.getString("text");
        // Build notification based on Intent
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "mychannel")
                .setSmallIcon(R.drawable.ic_baseline_favorite_24)
                .setContentTitle("Ενημέρωση Εμβολιασμού")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // Show notification

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(i,builder.build());
    }
}
