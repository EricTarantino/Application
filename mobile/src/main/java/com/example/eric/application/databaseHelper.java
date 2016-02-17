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
 * Description of the class
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

    //Table name
    protected static final String TABLENAME_V2 = "db_table_name_V2";

    // Common column names
    protected static final String KEY_ROWID_V2 = "id_V2";
    protected static final String USER_ID_V2 = "user_id_V2";
    protected static final String VERSUCH_V2 = "versuch_V2";
    protected static final String MODALITÄT_V2 = "modalitaet_V2";
    protected static final String PROZESS_ID_V2 = "prozess_Id_V2";
    protected static final String PROCESS_BLENDIN_V2 = "prozess_anzeigezeit_V2";
    protected static final String CONFIRMATIONTIME_V2 = "prozess_startzeit_V2";

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

    //Table create statement
    private static final String TABLE_CREATE_V2 =
            "CREATE TABLE " + TABLENAME_V2 +" ("
                    + KEY_ROWID_V2 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_ID_V2 + "  INTEGER, "
                    + VERSUCH_V2 + "  INTEGER, "
                    + MODALITÄT_V2 + " TEXT, "
                    + PROZESS_ID_V2 + " TEXT, "
                    + PROCESS_BLENDIN_V2 + " TEXT, "
                    + CONFIRMATIONTIME_V2 + " TEXT, "
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
        db.execSQL(TABLE_CREATE_V2);
        Log.e(LOG, "Database finished");
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // activity methods, reaction on changes to the application                      //
    // the functions are self-explaining by their name                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    //TODO: CHECK THIS USAGE?
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME_V2);
        onCreate(db);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class functions to provide the essential class functionality                  //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    //empties the data base
    public String emptyDatabase(String userID, String Tablename) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(Tablename, USER_ID + " =  " + userID, null);

        } catch (Exception e) {
            //if there are any exceptions, return false
            return "Leeren der Tabelle fehlgeschlagen";
        } finally {

        }
        //If there are no errors, return true.
        return "Leeren der Tabelle erfolgreich";
    }

    //exports the data base
    public String exportDatabase(Context context, String Tablename) {

        if(!(Tablename== TABLENAME || Tablename==TABLENAME_V2)){
            Log.e(LOG, "Tabelle exisitert nicht");
            return "Export fehlgeschlagen";
        }
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
                Log.e(LOG, "Ordner \" Download \" erstellt");
            }

            File file;
            PrintWriter printWriter = null;

            try {
                if (Tablename == TABLENAME) {
                    file = new File(exportDir, "MaxiMMI_db_v1.csv");
                } else {
                    file = new File(exportDir, "MaxiMMI_db_v2.csv");
                }
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

                Cursor curCSV = this.getCursor(Tablename);
                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
                printWriter = printLines(Tablename, curCSV, printWriter);

                curCSV.close();
                dbcOurDatabaseConnector.close();
            } catch (Exception e) {
                //if there are any exceptions, return false
                return "Export fehlgeschlagen";
            } finally {
                if (printWriter != null) printWriter.close();
            }
            //If there are no errors, return true.
            if (Tablename == TABLENAME) {
                return "Datenbank zu Teil 1 exportiert";
            } else {
                return "Datenbank zu Teil 2 exportiert";
            }
        }
    }

    //Get Cursor to the database
    public Cursor getCursor(String Tablename){
        String query = "SELECT * FROM "+Tablename;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    private PrintWriter printLines(String Tablename, Cursor curCSV, PrintWriter printWriter) {
        if (Tablename == TABLENAME) {
            printWriter.println("TABELLE ZU VERSUCH 1");
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
                 * The field date (Long) is formatted in a readable text.
                 */

                String record = key_rowid + "," + user_id + "," + versuch + "," + modalitaet + "," + alarmtype
                        + "," + clickedtype + "," + popup_time + "," + click_time + "," + clearing;
                printWriter.println(record); //write the record in the .csv file
            }
            //Tabelle 2 soll exportiert werden
        } else {
            printWriter.println("TABELLE ZU VERSUCH 2");
            printWriter.println("KEY_ROW_ID_V2,USER_ID_V2, VERSUCH_V2, MODALITÄT_V2," +
                    " PROZESS_ID_V2, PROZESS_BLENDIN_V2, CONFIRMATIONTIME_V2");

            while (curCSV.moveToNext()) {
                Long key_rowid_V2 = curCSV.getLong(curCSV.getColumnIndex(KEY_ROWID_V2));
                String user_id = curCSV.getString(curCSV.getColumnIndex(USER_ID));
                String versuch_V2 = curCSV.getString(curCSV.getColumnIndex(VERSUCH_V2));
                String modalitaet_V2 = curCSV.getString(curCSV.getColumnIndex(MODALITÄT_V2));
                String process_Id_V2 = curCSV.getString(curCSV.getColumnIndex(PROZESS_ID_V2));
                String process_blendIn_V2 = curCSV.getString(curCSV.getColumnIndex(PROCESS_BLENDIN_V2));
                String confirmationtime_V2 = curCSV.getString(curCSV.getColumnIndex(CONFIRMATIONTIME_V2));

                /**Create the line to write in the .csv file.
                 * We need a String where values are comma separated.
                 * The field date (Long) is formatted in a readable text.
                 */

                String record = key_rowid_V2 + "," + user_id + "," + versuch_V2 + "," + modalitaet_V2 + ","
                        + process_Id_V2 + "," + process_blendIn_V2 + "," + confirmationtime_V2;
                printWriter.println(record); //write the record in the .csv file
            }
        }
        return printWriter;
    }
}
