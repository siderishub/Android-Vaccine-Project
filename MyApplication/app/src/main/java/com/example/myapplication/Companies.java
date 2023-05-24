package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/*
Βασικό Activity menu πλοήγησης της σελίδας των εταιρειών
με συναρτήσεις για τα click των κουμπιών
*/
public class Companies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
    }


    public void pfizerInfo(View view) {
        Intent i = new Intent(this, PfizerInfo.class);
        startActivity(i);
    }

    public void astraZenecaInfo(View view) {
        Intent i = new Intent(this, AstraZenecaInfo.class);
        startActivity(i);
    }

    public void modernaInfo(View view) {
        Intent i = new Intent(this, ModernaInfo.class);
        startActivity(i);
    }


}