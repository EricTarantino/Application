package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class modification_select extends AppCompatActivity {

    UserInputLog ui_Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_select);

        //invisibleImpossibleCondition();
        //Get the parcelable object to move around data
        Bundle b = getIntent().getExtras();
        ui_Log = b.getParcelable(".hmi.UserInputLog");
    }

    public void goToMonitorVersuch(View view) {
        Intent alarmActivity = new Intent(this, alarmActivity.class);
        ui_Log.setModalitaet(ui_Log.MONITOR);
        alarmActivity.putExtra(".hmi.UserInputLog", ui_Log);
        startActivity(alarmActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modification_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Handle item selection
        try{
            switch (id) {
                case R.id.action_settings:
                    showSettings();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }catch(Exception e) {
            Log.e("StartActivity", "Error in Settings");
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToWearableVersuch(View view) {
        Intent alarmActivityWithWearable = new Intent(this, alarmActivity.class);
        ui_Log.setModalitaet(ui_Log.WATCH);
        alarmActivityWithWearable.putExtra(".hmi.UserInputLog", ui_Log);
        startActivity(alarmActivityWithWearable);
    }

    public void showSettings() {
        Intent settings = new Intent(this, optionActivity.class);
        settings.putExtra(".hmi.UserInputLog", ui_Log);
        startActivity(settings);
    }
}

