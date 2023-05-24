package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
Βασικό Activity menu πλοήγησης της αρχικής σελίδας
με συναρτήσεις για τα click των κουμπιών
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void notifications(View view) {
        Intent i = new Intent(this, Notificationsview.class);
        startActivity(i);
    }

    public void companiesInfo(View view) {
        Intent i = new Intent(this, Companies.class);
        startActivity(i);
    }

    public void aboutUsInfo(View view) {
        Intent i = new Intent(this, AboutUs.class);
        startActivity(i);
    }

    public void why(View view) {
        Intent i = new Intent(this, Safety.class);
        startActivity(i);
    }
}

