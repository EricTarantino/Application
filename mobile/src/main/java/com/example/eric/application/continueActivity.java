package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;

public class continueActivity extends AppCompatActivity {

    UserInputLog ui_Log;

    public static final String COUNTER = "counter";
//    private Integer modificationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        ui_Log = b.getParcelable(".hmi.UserInputLog");

        super.onCreate(savedInstanceState);
//        modificationCounter = getIntent().getIntExtra(alarmActivity.COUNTER, 0);
//        if(modificationCounter==3){
//            TextView textView = (TextView) findViewById(R.id.id_textView_fortfahren);
//            textView.setText("Versuch 1 ist beendet.");
//            Button button = (Button) findViewById(R.id.id_button_fortfahren);
//            button.setText("Weiter mit Versuch 2");
//        }
        setContentView(R.layout.activity_continue);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_continue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO: selectCondition needs to be the caller
    public void nextModification(View view) {
//        if(modificationCounter!=3){
//            getIntent().putExtra(COUNTER, modificationCounter + 1);
//            setResult(RESULT_OK, getIntent());
//            finish();
//        }
        Intent modificationSelect = new Intent(this, modification_select.class);
        modificationSelect.putExtra(".hmi.UserInputLog", ui_Log);
        startActivity(modificationSelect);
    }
}
