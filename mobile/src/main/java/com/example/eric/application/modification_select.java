package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class modification_select extends AppCompatActivity {

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    UserInputLog ui_Log;
    UserInputLog2 ui_Log2;

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
        setContentView(R.layout.activity_modification_select);

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

    public void goToMonitorVersuch(View view) {
        ui_Log.setModalitaet(ui_Log.MONITOR);
        Intent confirmationToStart = new Intent(this, confirmationToStart.class);
        confirmationToStart.putExtra(".hmi.UserInputLog", ui_Log);
        confirmationToStart.putExtra(".hmi.UserInputLog2", ui_Log2);
        confirmationToStart.putExtra("caller", "Teil1");
        startActivity(confirmationToStart);

    }

    public void goToWatchVersuch(View view) {
        ui_Log.setModalitaet(ui_Log.WATCH);
        Intent confirmationToStart = new Intent(this, confirmationToStart.class);
        confirmationToStart.putExtra(".hmi.UserInputLog", ui_Log);
        confirmationToStart.putExtra(".hmi.UserInputLog2", ui_Log2);
        confirmationToStart.putExtra("caller", "Teil1");
        startActivity(confirmationToStart);
    }

    public void showSettings() {
        Intent settings = new Intent(this, optionActivity.class);
        settings.putExtra(".hmi.UserInputLog", ui_Log);
        settings.putExtra(".hmi.UserInputLog2", ui_Log2);
        startActivity(settings);
    }

    public void goToVersuch2(View view){
        Intent modification_select_2 = new Intent(this, modification_select_2.class);
        modification_select_2.putExtra(".hmi.UserInputLog", ui_Log);
        modification_select_2.putExtra(".hmi.UserInputLog2", ui_Log2);
        startActivity(modification_select_2);
    }
}

