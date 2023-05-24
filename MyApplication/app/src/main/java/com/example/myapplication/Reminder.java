package com.example.myapplication;

// Η κλάση Reminder αναπαριστά μια υπενθύμιση που μπορεί να ρυθμίσει ο κάθε χρήστης στην εφαρμογή
public class Reminder {
    // Χαρακτηριστικά του κάθε αντικειμένου reminder
    private int _id;    // Ξεχωριστό id για την βάση δεδομένων στην οποία αποθηκεύεται το κάθε αντικείμενο.
    private String Title;   // Τίτλος του reminder.
    private String Date;    // Ημερομηνία υπενθύμισης του reminder.
    private String Time;    // Ώρα υπενθύμισης του reminder.


    public Reminder() {
    }

    // Κατασκευαστής του αντικειμένου reminder με τα κατάληλα χαρακτηριστικά του
    // και την αυτόματη εισαγωγή του αυξανόμενου id για την βάση δεδομένων.
    public Reminder(int id, String title, String date, String time) {
        this.set_id(id);
        this.Title = title;
        this.Date = date;
        this.Time = time;
    }

    // Συναρτήσεις getters και setters προκειμένου είτε να αλλάζουν οι τιμές των χαρακτηριστικών του κάθε reminder,
    // είτε να επιστρέφονται για τις ανάγκες του εκάστοτε developer.
    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
    public String getTitle() {
        return this.Title;
    }

    public void setDate(String date) { this.Date = date; }
    public String getDate() {
        return this.Date;
    }

    public void setTime(String time) {
        this.Time = time;
    }
    public String getTime() {
        return this.Time;
    }


}