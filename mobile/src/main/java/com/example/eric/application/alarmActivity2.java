package com.example.eric.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class alarmActivity2 extends AppCompatActivity {

    ProzessProvider processProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
        processProvider = new ProzessProvider();
    }

    public void onClickButton_Prozess1_starten(View view) {
        Button button_Prozess1_starten = (Button)findViewById(R.id.button_Prozess1_starten);
        button_Prozess1_starten.setVisibility(View.INVISIBLE);
        startProzess(0);
        Button button_Prozess1_laeuft = (Button)findViewById(R.id.button_Prozess1_endet);
        button_Prozess1_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess2_starten(View view) {
        Button button_Prozess2_starten = (Button)findViewById(R.id.button_Prozess2_starten);
        button_Prozess2_starten.setVisibility(View.INVISIBLE);
        startProzess(1);
        Button button_Prozess2_laeuft = (Button)findViewById(R.id.button_Prozess2_endet);
        button_Prozess2_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess3_starten(View view) {
        Button button_Prozess3_starten = (Button)findViewById(R.id.button_Prozess3_starten);
        button_Prozess3_starten.setVisibility(View.INVISIBLE);
        startProzess(2);
        Button button_Prozess3_laeuft = (Button)findViewById(R.id.button_Prozess3_endet);
        button_Prozess3_laeuft.setVisibility(View.VISIBLE);
    }

    public void onClickButton_Prozess4_starten(View view) {
        Button button_Prozess4_starten = (Button)findViewById(R.id.button_Prozess4_starten);
        button_Prozess4_starten.setVisibility(View.INVISIBLE);
        startProzess(3);
        Button button_Prozess4_laeuft = (Button)findViewById(R.id.button_Prozess4_endet);
        button_Prozess4_laeuft.setVisibility(View.VISIBLE);
    }

    private void startProzess(int i){

    }
}
