package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Prozess1starten extends AppCompatActivity {

    UserInputLog ui_Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prozess1starten);
    }

    public void button_Prozess1_starten(View view) {
        Intent prozess1laeuft = new Intent(this, Prozess1laeuft.class);
        ui_Log.setModalitaet(ui_Log.MONITOR);
        prozess1laeuft.putExtra(".hmi.UserInputLog", ui_Log);
        startActivity(prozess1laeuft);
    }
}
