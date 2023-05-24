package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

// Η κλάση DataBaseReminder αναπαριστά την βάση δεδομένων στην οποία αποθηκέονται μονίμως οι υπενθυμίσης του χρήστη,
// δηλαδή τα αντικείμενα της κλάσης Reminder
public class DataBaseReminder extends SQLiteOpenHelper {


    //Σταθερές για τη ΒΔ (όνομα ΒΔ, έκδοση, πίνακες κλπ)
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "reminderDB.db";
    public static final String TABLE_Reminders = "reminders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REMINDERTITLE = "reminderTitle";
    public static final String COLUMN_REMINDERDATE = "reminderDate";
    public static final String COLUMN_REMINDERTIME = "reminderTime";


    // Κατασκευαστής της βάσης δεδομένων.
    public DataBaseReminder(Context context, String title,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // Η συνάρτηση onCreate δημιουργεί το σχήμα της βάσης δεδομένων.
    // Συγκεκριμένα δημιουργεί των πίνακα στον οποίο θα αποθηκεύονται τα αντικείμενα της κλάσης Reminder,
    // μαζί με τα πεδία και τους τύπους τους.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " +
                TABLE_Reminders + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_REMINDERTITLE + " TEXT," +
                COLUMN_REMINDERDATE + " TEXT," +
                COLUMN_REMINDERTIME + " TEXT" + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }

    // Η συνάρτηση onUpgrade εκτελεί μια αναβάθμιση στην βάση δεδομένων. Η αναβάθμιση γίνεται κατόπιν διαγραφής
    // και ξαναδημιουργίας της βάσης.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Reminders);
        onCreate(db);
    }

    // Η συνάρτηση addReminder προσθέτει ένα αντικείμενο της κλάσης Reminder
    // στην βάση δεδομένων μαζί με τα αντίστοιχα πεδία του.
    public void addReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_REMINDERTITLE, reminder.getTitle());
        values.put(COLUMN_REMINDERDATE, reminder.getDate());
        values.put(COLUMN_REMINDERTIME, reminder.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_Reminders, null, values);
        db.close();
    }

    // Η συνάρτηση findReminder αναζητεί ένα αντικείμενο της κλάσης Reminder μέσα στην βάση δεδομένων
    // σύμφωνα με τις δοθέντες τιμές των πεδίων που δίνονται ως παράμετροι, εκτελώντας το κατάληλο ερώτημα(query).
    public Reminder findReminder(String reminderTitle, String date, String time) {
        String query = "SELECT * FROM " + TABLE_Reminders + " WHERE " +
                COLUMN_REMINDERTITLE + " = '" + reminderTitle + "'" + " and " + COLUMN_REMINDERDATE + " = '" + date + "'" + " and " + COLUMN_REMINDERTIME + " = '" + time + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Reminder reminder = new Reminder();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            reminder.set_id(Integer.parseInt(cursor.getString(0)));
            reminder.setTitle(cursor.getString(1));
            reminder.setDate(cursor.getString(2));
            reminder.setTime(cursor.getString(3));
            cursor.close();
        } else {
            reminder = null;
        }
        db.close();
        return reminder;
    }

    // Η συνάρτηση deleteReminder αφαιρεί ένα αντικείμενο της κλάσης Reminder απο τη βάση δεδομένων
    // σύμφωνα με τις δοθέντες τιμές των πεδίων που δίνονται ως παράμετροι, εκτλώντας το κατάληλο ερώτημα(query).
    public boolean deleteReminder(String reminderTitle, String date, String time) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_Reminders + " WHERE " +
                COLUMN_REMINDERTITLE + " = '" + reminderTitle + "'" + " and " + COLUMN_REMINDERDATE + " = '" + date + "'" + " and " + COLUMN_REMINDERTIME + " = '" + time + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Reminder reminder = new Reminder();
        if (cursor.moveToFirst()) {
            reminder.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_Reminders, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(reminder.get_id()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


    // Η συνάρτηση getMaxID εντοπίζει και επιστρέφει την τιμή του μεγαλύτερου ID που υπάρχει στη βάση δεδομένων.
    public String getMaxID(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arraylist = new ArrayList<String>();
        String[] columns = new String[]{  COLUMN_ID };
        Cursor c = db.query(TABLE_Reminders, columns, null, null, null, null, null);
        int iName = c.getColumnIndex(COLUMN_ID);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            arraylist.add(c.getString(iName));

        c.close();
        String max;
        if(arraylist.isEmpty())
            return String.valueOf(0);
        else
            max = Collections.max(arraylist);

        return max;
    }

    // Η συνάρτηση isTitle βλέπει αν ο τίτλος που δόθηκε υπάρχει στη βάση δεδομένων.
    public Boolean isTitle(String title) {

        String selectQuery = "SELECT "+COLUMN_REMINDERTITLE+" FROM " + TABLE_Reminders;
        ArrayList<String> arraylist = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            arraylist.add(c.getString(c.getColumnIndex(COLUMN_REMINDERTITLE)));

        c.close();
        db.close();
        if(arraylist.contains("1ο ραντεβού "+title)){
            return true;
        }

        if(arraylist.contains("2o ραντεβού "+title)){
            return true;
        }

        if(arraylist.contains("\"Ανωσία\" "+title)){
            return true;
        }

        return false;
    }

    // Η συνάρτηση getRecordsTitleList δημιουργεί και επιστρέφει μια λίστα η οποία περιέχει όλες τις τιμές
    // των πεδίων Title απο όλα τα Reminders που υπάρχουν στη βάση δεδομένων.
    public ArrayList<String> getRecordsTitleList() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arraylist = new ArrayList<String>();
        String[] columns = new String[]{  COLUMN_REMINDERTITLE };
        Cursor c = db.query(TABLE_Reminders, columns, null, null, null, null, null);
        int iName = c.getColumnIndex(COLUMN_REMINDERTITLE);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            arraylist.add(c.getString(iName));

        c.close();

        return arraylist;
    }

    // Η συνάρτηση getRecordsDateList δημιουργεί και επιστρέφει μια λίστα η οποία περιέχει όλες τις τιμές
    // των πεδίων Date απο όλα τα Reminders που υπάρχουν στη βάση δεδομένων.
    public ArrayList<String> getRecordsDateList() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arraylist = new ArrayList<String>();
        String[] columns = new String[]{  COLUMN_REMINDERDATE };
        Cursor c = db.query(TABLE_Reminders, columns, null, null, null, null, null);
        int iName = c.getColumnIndex(COLUMN_REMINDERDATE);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            arraylist.add(c.getString(iName));

        c.close();

        return arraylist;
    }


    // Η συνάρτηση getRecordsTimeList δημιουργεί και επιστρέφει μια λίστα η οποία περιέχει όλες τις τιμές
    // των πεδίων Time απο όλα τα Reminders που υπάρχουν στη βάση δεδομένων.
    public ArrayList<String> getRecordsTimeList() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> arraylist = new ArrayList<String>();
        String[] columns = new String[]{  COLUMN_REMINDERTIME };
        Cursor c = db.query(TABLE_Reminders, columns, null, null, null, null, null);
        int iName = c.getColumnIndex(COLUMN_REMINDERTIME);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            arraylist.add(c.getString(iName));

        c.close();

        return arraylist;
    }


}