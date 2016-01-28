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

    //close database, here for logcat
    public void close(){
        if (database != null) {
            dbHelper.close();
        }
        Log.e(LOG, "Database closed");
    }

    //deletes data of a user
    public void deleteData(long User) {
        String whereClause =
                databaseHelper.USER_ID + " = " + User;
        database.delete(databaseHelper.TABLENAME, whereClause, null);
    }

    //open database
    public void open(){
        database = dbHelper.getWritableDatabase();
        Log.e(LOG, "Database opened");
    }
}
