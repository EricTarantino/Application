package com.example.eric.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Methods which use SQLiteOpenHelper dbHelper Object e.g. delete.
 * Methods which change values and work on values; e.g. use put ...
 * */

public class databaseSource{

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    // Logcat tag
    private static final String LOG = "DatabaseSource";

    //Database is used by databaseHelper methods
    SQLiteDatabase database;

    //The Helper provides all methods which are not directly on the database but use SQLiteOpenHelper
    SQLiteOpenHelper dbHelper;

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // constructors, getters, setters                                                //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    public databaseSource(Context context){
        //create database
        dbHelper = new databaseHelper(context);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // activity methods, reaction on changes to the application                      //
    // the functions are self-explaining by their name                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    //Add user data by use of modified UI_Log
    public UserInputLog create(UserInputLog ui_Log){
        ContentValues values = new ContentValues();
        values.put(databaseHelper.USER_ID, ui_Log.getUser_id());
        values.put(databaseHelper.VERSUCH, ui_Log.getVersuch());
        values.put(databaseHelper.MODALITÄT, ui_Log.getModalitaet());
        values.put(databaseHelper.ALARMTYP, ui_Log.getAlarmtyp());
        values.put(databaseHelper.CLICKEDTYP, ui_Log.getClickedButtonType());
        values.put(databaseHelper.POPUPTIME, ui_Log.getPopuptime());
        values.put(databaseHelper.CLICKTIME, ui_Log.getClicktime());
        values.put(databaseHelper.CLEARING, ui_Log.getClearing());
        database.insert(databaseHelper.TABLENAME, null, values);
        Log.e("Database Source", "ONE ROW INSERTED...");
        return ui_Log;
    }

    //Add user data by use of modified UI_Log
    public UserInputLog2 create(UserInputLog2 ui_Log2){
        ContentValues values = new ContentValues();
        values.put(databaseHelper.USER_ID_V2, ui_Log2.getUser_id());
        values.put(databaseHelper.VERSUCH_V2, ui_Log2.getVersuch());
        values.put(databaseHelper.MODALITÄT_V2, ui_Log2.getModalitaet());
        values.put(databaseHelper.PROZESS_ID_V2, ui_Log2.getProzess_id());
        values.put(databaseHelper.PROCESS_BLENDIN_V2, ui_Log2.getProcessBlendInTime());
        values.put(databaseHelper.CLICKTIME, ui_Log2.getConfirmationtime());
        database.insert(databaseHelper.TABLENAME_V2, null, values);
        Log.e("Database Source", "ONE ROW INSERTED...");
        return ui_Log2;
    }

    //close database, here for logcat
    public void close(){
        if (database != null) {
            dbHelper.close();
        }
        Log.e(LOG, "Database closed");
    }

    //deletes data of a user
    public void deleteData(long User, String modalitaet, String Tablename) {
       String whereClause;
        if(Tablename==databaseHelper.TABLENAME) {
            whereClause = databaseHelper.USER_ID + " = " + User
                    + " AND " + databaseHelper.MODALITÄT + " = " + modalitaet;
            database.delete(databaseHelper.TABLENAME, whereClause, null);
        }
        if(Tablename==databaseHelper.TABLENAME_V2) {
            whereClause = databaseHelper.USER_ID + " = " + User
                    + " AND " + databaseHelper.MODALITÄT_V2 + " = " + modalitaet;
            database.delete(databaseHelper.TABLENAME_V2, whereClause, null);
        }
    }

    //open database
    public void open(){
        database = dbHelper.getWritableDatabase();
        Log.e(LOG, "Database opened");
    }
}
