package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class confirmationToStart extends AppCompatActivity {

    UserInputLog ui_Log;
    UserInputLog2 ui_Log2;
    String caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the parcelable object to move around data
        Bundle b = getIntent().getExtras();
        ui_Log = b.getParcelable(".hmi.UserInputLog");
        ui_Log2 = b.getParcelable(".hmi.UserInputLog2");
        caller = b.getString("caller");
        String part_of_Experiment;

        TextView textView = (TextView) findViewById(R.id.textView_confirmToStart);

        //check which activity called the confirmation screen and get the experiment part
        if(caller=="Teil1")
            part_of_Experiment = ui_Log.getModalitaet();
        else
            part_of_Experiment = ui_Log2.getModalitaet();

        //set the text due to part of the experiment
        if(ui_Log.MONITOR==part_of_Experiment){
            textView.setText("Bitte bestätigen Sie um den Versuch zu beginnen.");
        }

        if(ui_Log.WATCH==part_of_Experiment){
            textView.setText("Bitte ziehen Sie die Uhr an und bestätigen Sie um den Versuch zu beginnen.");
        }

        if(ui_Log.BRILLE==part_of_Experiment){
            textView.setText("Bitte setzen Sie die Brille auf und bestätigen Sie um den Versuch zu beginnen.");
        }
        setContentView(R.layout.activity_confirmation_to_start);
    }

    public void onClickButton_confirmToStart(View view) {
        if(caller=="Teil1") {
            Intent alarmActivity = new Intent(this, alarmActivity.class);
            alarmActivity.putExtra(".hmi.UserInputLog", ui_Log);
            alarmActivity.putExtra(".hmi.UserInputLog2", ui_Log2);
            startActivity(alarmActivity);
        }else{
            Intent alarmActivity2 = new Intent(this, alarmActivity2.class);
            alarmActivity2.putExtra(".hmi.UserInputLog", ui_Log);
            alarmActivity2.putExtra(".hmi.UserInputLog2", ui_Log2);
            startActivity(alarmActivity2);
        }
    }
}
