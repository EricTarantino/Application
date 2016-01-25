package com.example.eric.application;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by Eric on 16.11.2015.
 */

public class databaseHelper extends SQLiteOpenHelper {

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //Database name
    public static final String DATABASE_NAME = "database.db";

    //Table name
    protected static final String TABLENAME = "db_table_name";

    // Common column names
    protected static final String KEY_ROWID = "id";
    protected static final String USER_ID = "user_id";
    protected static final String VERSUCH = "versuch";
    protected static final String MODALITÄT = "modalitaet";
    protected static final String ALARMTYP = "alarmtype";
    protected static final String CLICKEDTYP = "correctiontype";
    protected static final String POPUPTIME = "popup_time";
    protected static final String CLICKTIME = "click_time";
    protected static final String CLEARING = "clearing";

    //Table create statement
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLENAME +" ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_ID + "  INTEGER, "
                    + VERSUCH + "  INTEGER, "
                    + MODALITÄT + " TEXT, "
                    + ALARMTYP + " TEXT, "
                    + CLICKEDTYP + " TEXT, "
                    + POPUPTIME + " TEXT, "
                    + CLICKTIME + " TEXT, "
                    + CLEARING + " TEXT "
                    + ");";
    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // constructors, getters, setters                                                //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    public databaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e(LOG, "Database created opened");
    }

    // creating required tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.e(LOG, "Database finished");
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // activity methods, reaction on changes to the application                      //
    // the functions are self-explaining by their name                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class functions to provide the essential class functionality                  //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    //empties the data base
    public String emptyDatabase(String userID) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLENAME, USER_ID + " =  " + userID, null);

        } catch (Exception e) {
            //if there are any exceptions, return false
            return "Leeren der Datenbank fehlgeschlagen";
        } finally {

        }
        //If there are no errors, return true.
        return "Database ist leer";
    }

    //exports the data base
    public String exportDatabase(Context context) {

        //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */

        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Log.e(LOG, "External storage unavailable");
            return "Export fehlgeschlagen";
        } else {
            //We use the Download directory for saving our .csv file.
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
                Log.e(LOG, "Made download directory");
            }

            File file;
            PrintWriter printWriter = null;

            try {
                file = new File(exportDir, "maxi_db.csv");
                file.createNewFile();
                printWriter = new PrintWriter(new FileWriter(file));

                /**This is our database connector class that reads the data from the database.
                 * The code of this class is omitted for brevity.
                 */

                databaseSource dbcOurDatabaseConnector = new databaseSource(context);
                dbcOurDatabaseConnector.open(); //open the database for reading
                /**Let's read the first table of the database.
                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
                 * containing all records of the table (all fields).
                 * The code of this class is omitted for brevity.
                 */

                Cursor curCSV = this.getCursor();
                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
                printWriter.println("FIRST TABLE OF THE DATABASE");
                printWriter.println("KEY_ROW_ID,USER_ID,VERSUCH,MODALITÄT," +
                        "ALARMTYP,CLICKEDTYP,POPUPTIME,CLICKTIME,KORREKTUR");

                while (curCSV.moveToNext()) {
                    Long key_rowid = curCSV.getLong(curCSV.getColumnIndex(KEY_ROWID));
                    String user_id = curCSV.getString(curCSV.getColumnIndex(USER_ID));
                    String versuch = curCSV.getString(curCSV.getColumnIndex(VERSUCH));
                    String modalitaet = curCSV.getString(curCSV.getColumnIndex(MODALITÄT));
                    String alarmtype = curCSV.getString(curCSV.getColumnIndex(ALARMTYP));
                    String clickedtype = curCSV.getString(curCSV.getColumnIndex(CLICKEDTYP));
                    String popup_time = curCSV.getString(curCSV.getColumnIndex(POPUPTIME));
                    String click_time = curCSV.getString(curCSV.getColumnIndex(CLICKTIME));
                    String clearing = curCSV.getString(curCSV.getColumnIndex(CLEARING));

                    /**Create the line to write in the .csv file.
                     * We need a String where values are comma separated.
                     * The field date (Long) is formatted in a readable text. The amount field
                     * is converted into String.
                     */

                    String record = key_rowid + "," + user_id + "," + versuch + "," + modalitaet + "," + alarmtype
                            + "," + clickedtype + "," + popup_time + "," + click_time + "," + clearing;
                    printWriter.println(record); //write the record in the .csv file
                }
                curCSV.close();
                dbcOurDatabaseConnector.close();
            } catch (Exception e) {
                //if there are any exceptions, return false
                return "Export fehlgeschlagen";
            } finally {
                if (printWriter != null) printWriter.close();
            }
            //If there are no errors, return true.
            return "Database Exported";
        }
    }

    //Get Cursor to the database
    public Cursor getCursor(){
        String query = "SELECT * FROM "+"db_table_name";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
