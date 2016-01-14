package com.example.eric.application;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 imports unused:
 import java.io.File;
 import java.io.FileWriter;
 import java.io.PrintWriter;
 import android.widget.Button;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.graphics.Color;
 import java.text.DateFormat;
 import java.util.Date;
 import java.util.Locale;
 import android.database.Cursor;
 import android.os.Environment;
 */

public class optionActivity extends AppCompatActivity{

    databaseHelper dbHelper;
    UserInputLog ui_Log;
    String strID;
    //String LOG = "optionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the parcelable object to move around data
        Bundle b = getIntent().getExtras();
        ui_Log = b.getParcelable(".hmi.UserInputLog");
        dbHelper = new databaseHelper(this);
        setContentView(R.layout.activity_option);
        EditText id_shower = (EditText) findViewById(R.id.editText_options_id_show);
        strID = Long.toString(ui_Log.getUser_id());
        id_shower.setText(strID);
    }

    //TODO: Nice to have, function as parameter to confirm the delete, abstract or interface
    public void OnClickPart1(View view) {

    }

    public void OnClickPart2(View view) {

    }

    public void OnClickHMI(View view) {
        //Button button = (Button) findViewById(R.id.button_id_Options_Database);
        //button.setBackgroundColor(Color.BLUE);
    }

    public void OnClickGlasses(View view) {
        //Button button = (Button) findViewById(R.id.button_id_Options_Database);
        //button.setBackgroundColor(Color.BLUE);
    }

    public void OnClickWatch(View view) {
        //Button button = (Button) findViewById(R.id.button_id_Options_Database);
        //button.setBackgroundColor(Color.BLUE);
    }

    public void OnClickConfirm1(View view) {
    }

    public void OnClickDatabase(View view) {
        //Button button = (Button) findViewById(R.id.button_id_Options_Database);
        //button.setBackgroundColor(Color.BLUE);
    }

    public void OnClickConfirm2(View view) {
        //dataSource.deleteData(getEditTextID(), 1, ui_Log.WATCH);
    }

    public void OnClickEmpty(View view) {
        emptyDatabase(this);
    }

    public void OnClickExport(View view) {
        exportDatabase(this);
    }

    public void OnClickConfirm3(View view) {
    }

    private long getEditTextID(){
        EditText id_shower = (EditText) findViewById(R.id.editText_options_id_show);
        id_shower.getText();
        long result=0;
        try{
            result = (long) Integer.parseInt(id_shower.getText().toString());
        }catch(Exception e) {
            Log.e("Options", "Cant parse ID");
        }
        return result;
    }

    //exports the data base
    public void exportDatabase(Context context) {

        //Context context = getApplicationContext();
        String exportResult = dbHelper.exportDatabase(context);

        //set duration of toast
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, exportResult, duration);
        toast.show();
    }

    public void emptyDatabase(Context context) {

        EditText id_shower = (EditText) findViewById(R.id.editText_options_id_show);
        String emptyResult = dbHelper.emptyDatabase(id_shower.getText().toString());

        //set duration of toast
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, emptyResult, duration);
        toast.show();
    }
}
