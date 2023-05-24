package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/*
Activity "φόρμα" για δημιουργία τριών νέων notifications,
για το πρώτο ραντεβού εμβολιασμού,το δεύτερο ραντεβού,και
όταν αποκτήσει ικανή αντισωματική απάντηση δηλαδή μια σχετική "Ανωσία"
που γίνεται δύο εβδομάδες μετά το δεύτερο ραντεβού
*/
public class NotificationsAdd extends AppCompatActivity {


    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;
    Calendar c = Calendar.getInstance();
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    Calendar c3 = Calendar.getInstance();
    boolean gave_date_1 = false;
    boolean gave_date_2 = false;

    /*
     Όπως και στις διαφάνειες του μαθήματος για αλλαγή orientation της συσκευής κτλ.
     να αποθηκεύονται οι αλλαγές
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)  {
        TextView date1 = (TextView) findViewById(R.id.show_date_1);
        TextView date2 = (TextView) findViewById(R.id.show_date_2);
        TextView date3 = (TextView) findViewById(R.id.show_date_3);
        EditText title = findViewById(R.id.title);
        super.onSaveInstanceState(outState);//Save data to the Bundle (other methods include putInt(), putBoolean() etc)
        CharSequence firstdoseText = date1.getText();
        outState.putCharSequence("savedfirstdoseText", firstdoseText);
        CharSequence seconddoseText = date2.getText();
        outState.putCharSequence("savedseconddoseText", seconddoseText);
        CharSequence immuneText = date3.getText();
        outState.putCharSequence("savedimmuneText", immuneText);
        CharSequence titleText = title.getText();
        outState.putCharSequence("savedtitletext", titleText);

        outState.putInt("year1", c1.get(Calendar.YEAR));
        outState.putInt("month1", c1.get(Calendar.MONTH));
        outState.putInt("day1", c1.get(Calendar.DAY_OF_MONTH));
        outState.putInt("hour1", c1.get(Calendar.HOUR_OF_DAY));
        outState.putInt("minute1", c1.get(Calendar.MINUTE));
        outState.putInt("second1", c1.get(Calendar.SECOND));
        outState.putInt("year2", c2.get(Calendar.YEAR));
        outState.putInt("month2", c2.get(Calendar.MONTH));
        outState.putInt("day2", c2.get(Calendar.DAY_OF_MONTH));
        outState.putInt("hour2", c2.get(Calendar.HOUR_OF_DAY));
        outState.putInt("minute2", c2.get(Calendar.MINUTE));
        outState.putInt("second2", c2.get(Calendar.SECOND));
        outState.putInt("year3", c3.get(Calendar.YEAR));
        outState.putInt("month3", c3.get(Calendar.MONTH));
        outState.putInt("day3", c3.get(Calendar.DAY_OF_MONTH));
        outState.putInt("hour3", c3.get(Calendar.HOUR_OF_DAY));
        outState.putInt("minute3", c3.get(Calendar.MINUTE));
        outState.putInt("second3", c3.get(Calendar.SECOND));

        outState.putBoolean("saved_gave_date_1",gave_date_1);
        outState.putBoolean("saved_gave_date_2",gave_date_2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_add);

        TextView date1 = (TextView) findViewById(R.id.show_date_1);
        TextView date2 = (TextView) findViewById(R.id.show_date_2);
        TextView date3 = (TextView) findViewById(R.id.show_date_3);
        EditText title = findViewById(R.id.title);

        /*
        Όπως και στις διαφάνειες του μαθήματος για αλλαγή orientation της συσκευής κτλ
        να αποθηκεύονται οι αλλαγές
         */
        if (savedInstanceState!= null){
            //Retrieve data from the Bundle (other methods include getInt(), getBoolean() etc)
            CharSequence firstdoseText = savedInstanceState.getCharSequence("savedfirstdoseText");
            CharSequence seconddoseText = savedInstanceState.getCharSequence("savedseconddoseText");
            CharSequence immuneText = savedInstanceState.getCharSequence("savedimmuneText");
            CharSequence titleText = savedInstanceState.getCharSequence("savedtitletext");

            //Restore the dynamic state of the UI
            date1.setText(firstdoseText);
            date2.setText(seconddoseText);
            date3.setText(immuneText);
            title.setText(titleText);
            c1.set(Calendar.YEAR, savedInstanceState.getInt("year1"));
            c1.set(Calendar.MONTH, savedInstanceState.getInt("month1"));
            c1.set(Calendar.DAY_OF_MONTH, savedInstanceState.getInt("day1"));
            c1.set(Calendar.HOUR_OF_DAY, savedInstanceState.getInt("hour1"));
            c1.set(Calendar.MINUTE, savedInstanceState.getInt("minute1"));
            c1.set(Calendar.SECOND, savedInstanceState.getInt("second1"));
            c2.set(Calendar.YEAR, savedInstanceState.getInt("year2"));
            c2.set(Calendar.MONTH, savedInstanceState.getInt("month2"));
            c2.set(Calendar.DAY_OF_MONTH, savedInstanceState.getInt("day2"));
            c2.set(Calendar.HOUR_OF_DAY, savedInstanceState.getInt("hour2"));
            c2.set(Calendar.MINUTE, savedInstanceState.getInt("minute2"));
            c2.set(Calendar.SECOND, savedInstanceState.getInt("second2"));
            c3.set(Calendar.YEAR, savedInstanceState.getInt("year3"));
            c3.set(Calendar.MONTH, savedInstanceState.getInt("month3"));
            c3.set(Calendar.DAY_OF_MONTH, savedInstanceState.getInt("day3"));
            c3.set(Calendar.HOUR_OF_DAY, savedInstanceState.getInt("hour3"));
            c3.set(Calendar.MINUTE, savedInstanceState.getInt("minute3"));
            c3.set(Calendar.SECOND, savedInstanceState.getInt("second3"));

            gave_date_1 = savedInstanceState.getBoolean("saved_gave_date_1");
            gave_date_2 = savedInstanceState.getBoolean("saved_gave_date_2");
        }
        //για compatability API26+
        createNotificationChannel();


        Button buttontime1 = findViewById(R.id.date1);

        buttontime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 Όταν πατιέται το κουμπί ο χρήστης εισάγει την ημερομηνία (με DatePickerDialog )
                 και την ώρα (με TimePickerDialog) του πρώτου ραντεβού
                 και εισάγουμε τις τιμές σε ένα αντικείμενο calendar
                */


                c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NotificationsAdd.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                c1.set(Calendar.YEAR, year);
                                c1.set(Calendar.MONTH, monthOfYear);
                                c1.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                                date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                mHour = c1.get(Calendar.HOUR_OF_DAY);
                                mMinute = c1.get(Calendar.MINUTE);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(NotificationsAdd.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @SuppressLint("SetTextI18n")
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                                                c1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                c1.set(Calendar.MINUTE, minute);
                                                c1.set(Calendar.SECOND, 0);

                                                mHour = hourOfDay;
                                                mMinute = minute;

                                                //αλλάζουμε το textview για να βλέπει τις αλλαγές ο χρήστης
                                                date1.setText("Πρώτο ραντεβού: "+date_time+" "+hourOfDay + ":" + minute);
                                                //για να ξέρουμε ότι έγινε υποβολή της πρώτης ημερομηνίας
                                                gave_date_1 = true;
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        Button buttontime2 = findViewById(R.id.date2);

        buttontime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 Όταν πατιέται το κουμπί ο χρήστης εισάγει την ημερομηνία (με DatePickerDialog)
                 και την ώρα (με TimePickerDialog) του δεύτερου ραντεβού
                 και εισάγουμε τις τιμές σε ένα αντικείμενο calendar
                */


                c2 = Calendar.getInstance();
                c3 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NotificationsAdd.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                c2.set(Calendar.YEAR, year);
                                c2.set(Calendar.MONTH, monthOfYear);
                                c2.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                c3.set(Calendar.YEAR, year);
                                c3.set(Calendar.MONTH, monthOfYear);
                                c3.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                                date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                mHour = c2.get(Calendar.HOUR_OF_DAY);
                                mMinute =c2.get(Calendar.MINUTE);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(NotificationsAdd.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @SuppressLint("SetTextI18n")
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                                                c2.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                c2.set(Calendar.MINUTE, minute);
                                                c2.set(Calendar.SECOND, 0);
                                                c3.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                c3.set(Calendar.MINUTE, minute);
                                                c3.set(Calendar.SECOND, 0);

                                                mHour = hourOfDay;
                                                mMinute = minute;

                                                //αλλάζουμε το textview για να βλέπει τις αλλαγές ο χρήστης
                                                date2.setText("Δεύτερο ραντεβού: "+date_time+" "+hourOfDay + ":" + minute);

                                                /*
                                                σε τρίτο αντικείμενο calendar προσθέτουμε 2 εβδομάδες απο το δεύτερο ραντεβού
                                                επειδή τότε αποκτά "Ανωσία" ο άνθωπος
                                                */

                                                c3.add(Calendar.DAY_OF_YEAR, 14);
                                                mYear = c3.get(Calendar.YEAR);
                                                mMonth = c3.get(Calendar.MONTH);
                                                mDay = c3.get(Calendar.DAY_OF_MONTH);
                                                mHour = c3.get(Calendar.HOUR_OF_DAY);
                                                mMinute = c3.get(Calendar.MINUTE);

                                                date_time = mDay + "-" + (mMonth + 1) + "-" + mYear;
                                                date3.setText("\"Ανωσία\": "+date_time+" "+mHour + ":" + mMinute);
                                                //για να ξέρουμε ότι έγινε υποβολή της δεύτερης ημερομηνίας
                                                gave_date_2 = true;

                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        Button buttonSubmit = findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                DataBaseReminder db = new DataBaseReminder(NotificationsAdd.this, null, null,3);

                /*
                όταν γίνει η υποβολή βεβαιωνόμαστε άμα έχει δωθεί όνομα
                */
                if(title.getText().toString().equals("")){
                    Toast.makeText(NotificationsAdd.this,"Δέν δόθηκε όνομα",Toast.LENGTH_SHORT).show();
                }
                else {
                    /*
                    Άμα έχει δοθεί , πρέπει να είναι διαφορετικό απο αυτά που ήδη υπάρχουν
                    */
                    if(db.isTitle(title.getText().toString())){
                        Toast.makeText(NotificationsAdd.this,"το όνομα χρησημοποιείται",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        /*
                        Επίσης βλέπουμε άμα έχουν γίνει υποβολές για τις ημερωμηνίες των ραντεβού και
                        άμα έχει γίνει σωστή υποβολή των ημερομηνιών(να μην είναι πριν την τωρινή ημερωμηνία
                        και ώρα , και το δεύτερο ραντεβού να μην είναι πριν το πρώτο ραντεβού)
                        */
                        if(c1.before(c) || !gave_date_1 ){
                            Toast.makeText(NotificationsAdd.this,"To πρώτου ραντεβού πρέπει να είναι μετά την τωρινή ημερωμηνία και ώρα",Toast.LENGTH_LONG).show();
                            Toast.makeText(NotificationsAdd.this,"το δεύτερου ραντεβού πρέπει να είναι μετά το πρώτο ",Toast.LENGTH_LONG).show();
                        }
                        /*
                        Άμα περάσει τους ελέγχους πρέπει να δημιουργήσουμε τις τρείς
                        ειδοποιήσεις , να τις αποθηκεύσουμε στο database και να επιστρέψουμε
                        στο παλιό activity
                        */
                        else{

                            int max_id = Integer.parseInt(db.getMaxID());

                            Toast.makeText(NotificationsAdd.this,"Υποβολήθηκαν οι αλλαγές!",Toast.LENGTH_SHORT).show();

                            String date = c1.get(Calendar.DAY_OF_MONTH) + "-" + (c1.get(Calendar.MONTH) + 1) + "-" + c1.get(Calendar.YEAR);
                            String time = c1.get(Calendar.HOUR_OF_DAY)+":"+c1.get(Calendar.MINUTE);
                            newNotification(max_id+1,"1ο ραντεβού "+title.getText(),c1);
                            Reminder reminder = new Reminder(max_id+1,"1ο ραντεβού "+title.getText(),date,time);
                            db.addReminder(reminder);


                            date = c2.get(Calendar.DAY_OF_MONTH) + "-" + (c2.get(Calendar.MONTH) + 1) + "-" + c2.get(Calendar.YEAR);
                            time = c2.get(Calendar.HOUR_OF_DAY)+":"+c2.get(Calendar.MINUTE);
                            newNotification(max_id+2,"2o ραντεβού "+title.getText(),c2);
                            reminder = new Reminder(max_id+2,"2o ραντεβού "+title.getText(),date,time);
                            db.addReminder(reminder);

                            date = c3.get(Calendar.DAY_OF_MONTH) + "-" + (c3.get(Calendar.MONTH) + 1) + "-" + c3.get(Calendar.YEAR);
                            time = c3.get(Calendar.HOUR_OF_DAY)+":"+c3.get(Calendar.MINUTE);
                            newNotification(max_id+3,"\"Ανωσία\" "+title.getText(),c3);
                            reminder = new Reminder(max_id+3,"\"Ανωσία\" "+title.getText(),date,time);
                            db.addReminder(reminder);
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }
                }


            }
        });






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
    Για να βγεί το notification την στιγμή που θέλουμε
    Δημιουργούμε ένα pending intent με το intent της δημιουργίας του notification
    και με τον alarm manager το βάζουμε να ενεργοποιηθεί την χρονική στιγμή του
    αντικειμένου calendar που εισάγαμε
    */
    private void newNotification(int i,String text,Calendar cal){
        Intent intent = new Intent(NotificationsAdd.this,NotificationReceiver.class);
        intent.putExtra("id",i);
        intent.putExtra("text",text);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationsAdd.this,i,intent,0);

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);

    }


}