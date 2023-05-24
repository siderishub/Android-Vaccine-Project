package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Recycler view Activity βασισμένο στου μαθήματος
 */
public class Notificationsview extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private static final int NEW_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationsview);

        //για compatability API26+
        createNotificationChannel();

        DataBaseReminder db = new DataBaseReminder(this, null, null,3);

        /*
        εισάγουμε τις λίστες των τίτλων , ημερωμινιών , ώρας απο
        το database για την εισαγωγή των στοιχείων στο recycle view
        */
        ArrayList<String> titlesList = db.getRecordsTitleList();
        ArrayList<String> dateList = db.getRecordsDateList();
        ArrayList<String> timeList = db.getRecordsTimeList();


        recyclerView = findViewById(R.id.recycler_view);

        //Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Set my Adapter for the RecyclerView
        adapter = new RecyclerAdapter(titlesList, dateList, timeList, this);
        recyclerView.setAdapter(adapter);


    }

    /*
    Όπως είδαμε στο μάθημα για να ανανεωθεί το recycle view μετά
    της εισαγωγής νέων notifications απο το activity NotificationsAdd
     */
    public void newnotifications(View view) {
        Intent i = new Intent(this, NotificationsAdd.class);
        startActivityForResult(i,NEW_ACTIVITY);
    }

    /*
    ο κώδικας βασίζεται στο documentation της google
    και είναι για την δημιουργία ενός notificationChannel
    */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("mychannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    /*
    Όπως είδαμε στο μάθημα για να ανανεωθεί το recycle view μετά
    της εισαγωγής νέων notifications απο το activity NotificationsAdd
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == NEW_ACTIVITY && resultCode == RESULT_OK){
            recreate();
        }
    }


}