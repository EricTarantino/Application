package com.example.eric.application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class alarmActivity2 extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    /**
    Die gesamte Versuchsdauer ist von den Prozessen abhaengig.
    Der Versuch wird beendet und "Wenden Sie sich an die Versuchsleitung"
    aufgerufen, durch beenden des vierten Prozesses.
     */

    //TODO: On Pause, On connect etc. ergaenzen
    //TODO: On Back Pressed ergaenzen
    //TODO: Anzeige auf der Watch

    //TODO: Verzoegerung am Anfang einbauen

    //Connection to the Process Durations
    ProzessProvider processProvider;

    // connection to the database
    databaseSource dataSource;

    //currentProcessId is an array index variable
    //one needs to be added to write it to the database
    int currentProcessId = 0;

    int currentProcessDuration = 0;
    int [] processDurations;

    // log data which is passed between activities
    UserInputLog ui_Log;
    UserInputLog2 ui_Log2;

    //Timers
    Timer caretaker;
    Timer caretakerSeconds;

    //date for log of popup and click time
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'um' HH:mm:ss:SS");

    // for the connection to the Wearable
    private com.google.android.gms.wearable.Node mNode;
    private GoogleApiClient mApiClient;
    private static final String DEVICE_MAIN = "DeviceMain";
    private static final String WEAR_PATH = "/from_device";

    //TODO: Check: Is this variable necessary?
    protected static final int CONTINUE_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("A2", "On Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);

        //instantiate the data source
        dataSource = new databaseSource(this);

        //Get the parcelable object to move around data
        Bundle b = getIntent().getExtras();
        ui_Log = b.getParcelable(".hmi.UserInputLog");
        ui_Log2 = b.getParcelable(".hmi.UserInputLog2");

        //Initialize mGoolgeAPIClient
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this )
                .addOnConnectionFailedListener(this)
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();

        processProvider = new ProzessProvider();
        processDurations = processProvider.getProcesses();

        //first process first
        ui_Log2.setProzess_id(Integer.toString(currentProcessId + 1));
        ui_Log2.setProzessBlendInTime(sdf.format(new Date()));
    }

    //Prozess wird durch Userinteraktion gestartet
    public void onClickButton_Prozess_starten(View view) {

        //track user interaction
        ui_Log2.setConfirmationtime(sdf.format(new Date()));

        //write row to database
        dataSource.create(ui_Log2);

        //Halte die Prozesszeit in einer schnellen Variablen fest
        currentProcessDuration = processDurations[currentProcessId];

        //Starte den Prozess
        startProcess(currentProcessDuration);

        //Aktualisiere den Timerstring jede Sekunde
        startSecond();

        //Lege die Anzeige fuer den Timer unter Prozess laeuft fest
        Button button_Prozess_läuft = (Button)findViewById(R.id.button_Prozess_läuft);
        button_Prozess_läuft.setText("Prozess " + (currentProcessId + 1)
                        + " läuft. &lt;br /&gt; &lt;br /&gt" +
                        String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(currentProcessId),
                                TimeUnit.MILLISECONDS.toSeconds(currentProcessId) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentProcessId)))
        );

        //Blende den Prozess starten Button aus
        Button button_Prozess_starten = (Button)findViewById(R.id.button_Prozess_starten);
        button_Prozess_starten.setVisibility(View.INVISIBLE);

        //Blende das Feld mit dem Infotext "Prozess xy laeuft" und der Dauer ein
        button_Prozess_läuft.setVisibility(View.VISIBLE);
    }

    //startet einen Prozess mit Dauer Prozes Duration
    //Erhaelt aus Performance Gruenden currentProcessDuration als Variable
    private void startProcess(int currentProcessDuration) {
        TimerTask action = new TimerTask() {
            public void run() {
                Message msg = handlerProcessEnd.obtainMessage();
                handlerProcessEnd.sendMessage(msg);
            }
        };
        caretaker = new Timer();

        // Process i starten
        caretaker.schedule(action, currentProcessDuration);
    }

    //Fungiert als Sekundenzeiger  im uebertragenen Sinne
    private void startSecond() {

        TimerTask action = new TimerTask() {
            public void run() {
                Message msg = handlerSecondOver.obtainMessage();
                handlerSecondOver.sendMessage(msg);
            }
        };
        caretakerSeconds = new Timer();
        //TODO: KLaeren ob eine Millisekunde Abzug fuer Programmabarbeitung sinnvoll ist
        caretakerSeconds.schedule(action, 59999);
    }

    //Counter abgelaufen: Prozess  i läuft wird invisible
    Handler handlerProcessEnd = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            //End Second Timer
            caretakerSeconds.cancel();

            if(currentProcessId+1==4) {
                goToContinueActivity();
            }

            //Blend in next process
            blendInStartNextProcess();
        }
    };

    //Aktualiere den Timerstring nach Ablauf einer Sekunde
    Handler handlerSecondOver = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            //Verbleibende Prozess Dauer um eine Sekunde verringern
            currentProcessDuration -=10000;

            //Prozessdauer im Feld aktualisieren
            Button button = (Button) findViewById(R.id.button_Prozess_läuft);
            button.setText("Prozess "+ (currentProcessId+1) +
                    " läuft. &lt;br /&gt; &lt;br /&gt"+ (
                    String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(currentProcessId),
                        TimeUnit.MILLISECONDS.toSeconds(currentProcessId) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentProcessId)))
                    ));

            //countdown next second
            startSecond();
        }
    };

    //Passe das Feld Prozess starten an und blende es ein
    public void blendInStartNextProcess(){
        //Gehe zum naechsten Prozess ueber
        currentProcessId += 1;

        ui_Log2.setProzess_id(Integer.toString(currentProcessId+1));
        ui_Log2.setProzessBlendInTime(sdf.format(new Date()));

        //Blende das Feld mit "Prozess laeuft" und der Zeitanzeige zum ablaufenden Prozesses aus
        Button button_Prozess_läuft = (Button)findViewById(R.id.button_Prozess_läuft);
        button_Prozess_läuft.setVisibility(View.INVISIBLE);

        //Passe das Feld "Prozess starten" an und blende es ein
        Button button_Prozess_starten = (Button)findViewById(R.id.button_Prozess_starten);
        button_Prozess_starten.setText("Prozess "+ (currentProcessId+1) +" starten");
        button_Prozess_starten.setVisibility(View.VISIBLE);
    }

    //handler helper to call the get supervisor to continue screen
    private void goToContinueActivity(){
        dataSource.close();
        Intent continueIntent = new Intent(this, continueActivity.class);
        continueIntent.putExtra(".hmi.UserInputLog",  ui_Log);
        continueIntent.putExtra(".hmi.UserInputLog2", ui_Log2);
        startActivityForResult(continueIntent, CONTINUE_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        //set cancel dialog behaviour
        new AlertDialog.Builder(this)
                .setTitle("Abbrechen")
                .setMessage("Die Eingaben dieses Teils des Versuchteils 2 wurden nicht gespeichert. Sind Sie sicher, dass Sie abbrechen möchten?")

                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        caretaker.cancel();
                        caretakerSeconds.cancel();
                        dataSource.deleteData(ui_Log2.getUser_id(), ui_Log2.getModalitaet(), databaseHelper.TABLENAME_V2);
                        dataSource.close();
                        //do not use onBackPressed, but use finish()
                        alarmActivity2.this.finish();
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //on connection to the wearable
    @Override
    public void onConnected(Bundle bundle) {
        Wearable.NodeApi.getConnectedNodes(mApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                        for (com.google.android.gms.wearable.Node node : nodes.getNodes()) {
                            if (node != null && node.isNearby()) {
                                mNode = node;
                                Log.d(DEVICE_MAIN, "Connected to" + node.getDisplayName());
                            }
                            if (mNode == null) {
                                Log.d(DEVICE_MAIN, "Not connected!");
                            }
                        }
                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
    @Override

    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataSource.open();
        mApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mApiClient.disconnect();
        dataSource.close();
    }
}
