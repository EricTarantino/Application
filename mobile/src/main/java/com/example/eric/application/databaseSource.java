package com.example.eric.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by e.lessen on 16.11.2015.
 * Methods which use SQLiteOpenHelper dbHelper Object e.g. delete.
 * Methods which change values and work on values; e.g. use put ...
 * */

public class databaseSource{

    // Logcat tag
    private static final String LOG = "DatabaseSource";

    //Database is used by databaseHelper methods
    SQLiteDatabase database;

    //The Helper provides all methods which are not
    // directly on the database but use SQLiteOpenHelper
    SQLiteOpenHelper dbHelper;

    public databaseSource(Context context){
        //create database
        dbHelper = new databaseHelper(context);
    }

    //Add user data by use of modified UI_Log
    public UserInputLog create(UserInputLog ui_Log){
        ContentValues values = new ContentValues();
        values.put(databaseHelper.USER_ID, ui_Log.getUser_id());
        values.put(databaseHelper.VERSUCH, ui_Log.getVersuch());
        values.put(databaseHelper.MODALITÄT, ui_Log.getModalitaet());
        values.put(databaseHelper.ALARMTYP, ui_Log.getAlarmtyp());
        values.put(databaseHelper.POPUPTIME, ui_Log.getPopuptime());
        values.put(databaseHelper.CLICKTIME, ui_Log.getClicktime());
        database.insert(databaseHelper.TABLENAME, null, values);
        Log.e("Database Source", "ONE ROW INSERTED...");
        return ui_Log;
    }

    public void deleteData(long User) {
        String whereClause =
                databaseHelper.USER_ID + " = " + User;
        database.delete(databaseHelper.TABLENAME, whereClause, null);
    }

    public void deleteData(UserInputLog ui_Log) {
        String whereClause =
                databaseHelper.USER_ID + " = " + ui_Log.getUser_id() + " AND " +
                        databaseHelper.VERSUCH + " = " + ui_Log.getVersuch() + " AND " +
                        databaseHelper.MODALITÄT + " = " + ui_Log.getModalitaet();
        database.delete(databaseHelper.TABLENAME, whereClause, null);
    }

    public void deleteData(long user_id, int versuch, String modalität) {
        String whereClause =
                databaseHelper.USER_ID + " = " + user_id + " AND " +
                        databaseHelper.VERSUCH + " = " + versuch + " AND " +
                        databaseHelper.MODALITÄT + " = " + modalität;
        database.delete(databaseHelper.TABLENAME, whereClause, null);
    }

    //open database
    public void open(){
        database = dbHelper.getWritableDatabase();
        Log.e(LOG, "Database opened");
    }

    //close database, here for logcat
    public void close(){
        if (database != null) {
            dbHelper.close();
        }
        Log.e(LOG, "Database closed");
    }

//    public void deleteData(long user_id, int versuch, String modalität) {
//
//    }

//    public void deleteData(long user_id, int versuch, String modalität) {
//
//    }

    //Add user data with explicit information
    //public void addInformation(long user_id, String versuch, String modalität,
    //                           String alarmtyp, String popuptime, String clicktime){
    //
    //      UserInputLog UI_Log = new UserInputLog();
    //      UI_Log.setUser_id(user_id);
    //      UI_Log.setVersuch(versuch);
    //      UI_Log.setModalitaet(modalität);
    //      UI_Log.setAlarmtyp(alarmtyp);
    //      UI_Log.setPopuptime(popuptime);
    //      UI_Log.setClicktime(clicktime);
    //
    //      create(UI_Log);
    //      Log.e("DATABASE OPERATIONS", "CLICK INFO INSERTED...");
    //}
}
