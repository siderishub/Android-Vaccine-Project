package com.example.myapplication;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/*
Recycler Adapter βασισμένο στου μαθήματος
*/
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {

    ArrayList<String> titlesList ;
    ArrayList<String> dateList ;
    ArrayList<String> timeList ;
    private Context mcontext;

    // creating a constructor class.
    public RecyclerAdapter(ArrayList<String> titlesList, ArrayList<String> dateList, ArrayList<String> timeList, Context mcontext) {
        this.titlesList = titlesList;
        this.dateList = dateList;
        this.timeList = timeList;
        this.mcontext = mcontext;
    }



    private int image =  R.drawable.ic_baseline_add_alarm_24;



    //Class that holds the items to be displayed (Views in card_layout)
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDate;
        TextView itemTime;
        FloatingActionButton itemDelete;


        public ViewHolder(View itemView) {
            super(itemView);


            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDate = itemView.findViewById(R.id.item_date);
            itemTime = itemView.findViewById(R.id.item_time);
            itemDelete = itemView.findViewById(R.id.floatingActionButton);

            itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    όταν πατηθεί το κουμπί στο card view πρέπει να διαγράψουμε
                    τα στοιχεία του συγκεκριμένου card view από τις λίστες του adapter
                    αλλά και απο το database για να εξαφανιστεί απο το recycle view,
                    επίσης πρέπει να διαγράψουμε το notification που αντιστοιχεί σε αυτό
                    το card view
                    */
                    Toast.makeText(mcontext,"Διαγράφτηκε!",Toast.LENGTH_SHORT).show();
                    String title = itemTitle.getText().toString();
                    String date = itemDate.getText().toString();
                    String time = itemTime.getText().toString();

                    int position = getAdapterPosition();

                    int id = lookupReminder(title,date,time);
                    cancelNotification(id,title);
                    removeReminder(title,date,time);


                    
                    titlesList.remove(position);
                    dateList.remove(position);
                    timeList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, titlesList.size());
                }
            });


        }
    }







    //Methods that must be implemented for a RecyclerView.Adapter
    @Override
    public com.example.myapplication.RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appoitment_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemTitle.setText(titlesList.get(position));
        holder.itemDate.setText(dateList.get(position));
        holder.itemTime.setText(timeList.get(position));
        holder.itemImage.setImageResource(image);

    }

    @Override
    public int getItemCount() {
        return titlesList.size();
    }

    // για την εύρεση του id του notification απο το database με τον τίτλο την ημερομηνία και την ώρα
    public int lookupReminder(String title ,String date ,String time) {
        int id = -2;
        DataBaseReminder dbHandler = new DataBaseReminder(mcontext, null, null, 3);
        Reminder reminder = dbHandler.findReminder(title,date,time);
        if (reminder != null) {
            id = reminder.get_id();
        }
        return id;
    }

    //Για την διαγραφή του αντίστοιχου notification απο το database με τον τίτλο την ημερομηνία και την ώρα
    public void removeReminder(String title ,String date ,String time) {
        DataBaseReminder dbHandler = new DataBaseReminder(mcontext, null, null, 3);
        boolean result = dbHandler.deleteReminder(title ,date ,time);
        if (!result) {
            Toast.makeText(mcontext,"Λάθος στην διαφραφή στο database",Toast.LENGTH_SHORT).show();
        }
    }
    /*
    Για να Διαγραφτεί το notification δημιουργούμε το ίδιο pending intent που
    χρησμοποιήσαμε για την δημιουργία του notification
    και με τον alarm manager ακυρόνουμε την ενεργοποιησή του
    */
    private void cancelNotification(int i,String text){

        Intent intent = new Intent(mcontext,NotificationReceiver.class);
        intent.putExtra("id",i);
        intent.putExtra("text",text);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext,i,intent,0);

        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();


    }

}
