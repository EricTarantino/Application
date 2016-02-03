package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class alarmActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
    }

    public void onClickButton_Prozess1_beendet(View view) {
        Button button_Prozess1_starten = (Button)findViewById(R.id.button_Prozess1_laeuft);
        button_Prozess1_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess1_laeuft = (Button)findViewById(R.id.button_Prozess1_laeuft);
        button_Prozess1_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess2_beendet(View view) {
        Button button_Prozess1_starten = (Button)findViewById(R.id.button_Prozess_1_starten);
        button_Prozess1_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess1_laeuft = (Button)findViewById(R.id.button_Prozess_1_laeuft);
        button_Prozess1_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess3_beendet(View view) {
        Button button_Prozess1_starten = (Button)findViewById(R.id.button_Prozess_1_starten);
        button_Prozess1_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess1_laeuft = (Button)findViewById(R.id.button_Prozess_1_laeuft);
        button_Prozess1_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess4_beendet(View view) {
        Button button_Prozess1_starten = (Button)findViewById(R.id.button_Prozess_1_starten);
        button_Prozess1_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess1_laeuft = (Button)findViewById(R.id.button_Prozess_1_laeuft);
        button_Prozess1_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess1_starten(View view) {
        Button button_Prozess1_starten = (Button)findViewById(R.id.button_Prozess1_starten);
        button_Prozess1_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess1_laeuft = (Button)findViewById(R.id.button_Prozess1_laeuft);
        button_Prozess1_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess2_starten(View view) {
        Button button_Prozess2_starten = (Button)findViewById(R.id.button_Prozess2_starten);
        button_Prozess2_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess2_laeuft = (Button)findViewById(R.id.button_Prozess2_laeuft);
        button_Prozess2_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess3_starten(View view) {
        Button button_Prozess3_starten = (Button)findViewById(R.id.button_Prozess3_starten);
        button_Prozess3_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess3_laeuft = (Button)findViewById(R.id.button_Prozess3_laeuft);
        button_Prozess3_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess4_starten(View view) {
        Button button_Prozess4_starten = (Button)findViewById(R.id.button_Prozess4_starten);
        button_Prozess4_starten.setVisibility(View.INVISIBLE);
        Button button_Prozess4_laeuft = (Button)findViewById(R.id.button_Prozess4_laeuft);
        button_Prozess4_laeuft.setVisibility(View.VISIBLE);
    }


}
