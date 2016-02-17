package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class modification_select_2 extends AppCompatActivity {

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    UserInputLog ui_Log;
    UserInputLog2 ui_Log2;

    private static final String MODSEL2 = "mod_sel_2";
    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // activity methods, reaction on changes to the application                      //
    // the functions are self-explaining by their name                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        //backpressing is deactivated
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_select_2);

        Log.d(MODSEL2, "On Create");

        //invisibleImpossibleCondition();
        //Get the parcelable object to move around data
        Bundle b = getIntent().getExtras();
        ui_Log = b.getParcelable(".hmi.UserInputLog");
        ui_Log2 = b.getParcelable(".hmi.UserInputLog2");
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

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class functions to provide the essential class functionality                  //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    public void goToMonitorVersuch2(View view) {
        ui_Log2.setModalitaet(ui_Log2.MONITOR);
        Intent confirmationToStart = new Intent(this, confirmationToStart.class);
        confirmationToStart.putExtra(".hmi.UserInputLog", ui_Log);
        confirmationToStart.putExtra(".hmi.UserInputLog2", ui_Log2);
        confirmationToStart.putExtra("caller", "Teil2");
        startActivity(confirmationToStart);
    }

    public void goToWatchVersuch(View view) {
        Intent alarmActivity2 = new Intent(this, alarmActivity2.class);
        ui_Log2.setModalitaet(ui_Log2.WATCH);
        alarmActivity2.putExtra(".hmi.UserInputLog", ui_Log);
        alarmActivity2.putExtra(".hmi.UserInputLog2", ui_Log2);
        alarmActivity2.putExtra("caller", "Teil2");
        startActivity(alarmActivity2);
    }

    public void showSettings() {
        Intent settings = new Intent(this, optionActivity.class);
        settings.putExtra(".hmi.UserInputLog", ui_Log);
        settings.putExtra(".hmi.UserInputLog2", ui_Log2);
        startActivity(settings);
    }

    public void goToVersuch1(View view){
        Intent modification_select = new Intent(this, modification_select.class);
        modification_select.putExtra(".hmi.UserInputLog", ui_Log);
        modification_select.putExtra(".hmi.UserInputLog2", ui_Log2);
        startActivity(modification_select);
    }
}
