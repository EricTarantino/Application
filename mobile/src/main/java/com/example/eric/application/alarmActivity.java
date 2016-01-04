package com.example.eric.application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import org.w3c.dom.Node;

public class alarmActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private com.google.android.gms.wearable.Node mNode;
    private GoogleApiClient mGoogleApiClient;
    private static final String DEVICE_MAIN = "DeviceMain";
    private static final String WEAR_PATH = "/from_device";

    //TODO: visibility on resume aufheben
    public static final int CONTINUE_REQUEST_CODE = 10;
    private AlarmProvider alarm = new AlarmProvider();
    private int [] type = alarm.getAlarms();
    int counterModification=0;
    private boolean alarmOn;
    databaseSource dataSource;
    UserInputLog ui_Log;

    private final String ALARM_A = "A";
    private final String ALARM_B = "B";
    private final String ALARM_C = "C";
    private final String ALARM_D = "D";

    Timer caretaker, caretaker1, caretaker2, caretaker3, caretaker4;
//    Long caretaker_time, caretaker1_time, caretaker2_time_time, caretaker4_time;

    //date for log of popup and click time
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'um' HH:mm:ss:SS");

    //do this on start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instantiate the data source
        dataSource = new databaseSource(this);

        //Initialize mGoolgeAPIClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this )
                .addOnConnectionFailedListener(this)
                .build();

        //Get the parcelable object to move around data
        Bundle b = getIntent().getExtras();
        ui_Log = b.getParcelable(".hmi.UserInputLog");

        //this is the first part of the lab
        ui_Log.setVersuch(1);
        setTimers();
        set_alarmOn(false);
        //TextView textView = (TextView) findViewById(R.id.textView2);
        //textView.setText("A0: " + type[0] + ", " + "A1: " + type[1] + ", " + "A2: " + type[2] + ", " + "A3: " + type[3] + "\n" );
        setContentView(R.layout.activity_alarm);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                        for(com.google.android.gms.wearable.Node node : nodes.getNodes()){
                            if(node!= null && node.isNearby()) {
                                mNode = node;
                                Log.d(DEVICE_MAIN, "Connected to"+ node.getDisplayName());
                            }
                            if(mNode==null){
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
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    //sends a message to the wearable
    public void sendMessageToWear(String alarmType){
        if(mNode != null && mGoogleApiClient != null){
            Wearable.MessageApi.sendMessage(mGoogleApiClient,
                    mNode.getId(), WEAR_PATH, alarmType.getBytes())
                    .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            if(!sendMessageResult.getStatus().isSuccess()){
                                Log.d(WEAR_PATH, "Failed message");
                            } else {
                                Log.d(WEAR_PATH, "Message succeeded");
                            }
                        }
                    });
        }
    }

    //sets the timers for the alarm
    private void setTimers() {

        TimerTask action = new TimerTask() {
            public void run() {
                Message msg = handler.obtainMessage();
                msg.arg1 = type[0];
                handler.sendMessage(msg);
            }
        };
        caretaker = new Timer();
        caretaker.schedule(action, alarm.getDelays()[0]);

        TimerTask action1 = new TimerTask() {
            public void run() {
                Message msg = handler.obtainMessage();
                msg.arg1 = type[1];
                handler.sendMessage(msg);
            }
        };
        caretaker1 = new Timer();
        caretaker1.schedule(action1, alarm.getDelays()[1]);

        TimerTask action2 = new TimerTask() {
            public void run() {
                Message msg = handler.obtainMessage();
                msg.arg1 = type[2];
                handler.sendMessage(msg);
            }
        };
        caretaker2 = new Timer();
        caretaker2.schedule(action2, alarm.getDelays()[2]);


        TimerTask action3 = new TimerTask() {
            public void run() {
                Message msg = handler.obtainMessage();
                msg.arg1 = type[3];
                handler.sendMessage(msg);
            }
        };
        caretaker3 = new Timer();
        caretaker3.schedule(action3, alarm.getDelays()[3]);

        TimerTask action4 = new TimerTask() {
            public void run() {
                handlerContinue.sendEmptyMessage(0);
            }
        };
        caretaker4 = new Timer();
        caretaker4.schedule(action4, 640000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_alarm, menu);
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
                    cancelTimers();
                    dataSource.close();
                    showSettings();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }catch(Exception e) {
            Log.e("StartActivity", "Error in Settings");
        }

        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.new_game:
//                newGame();
//                return true;
//            case R.id.help:
//                showHelp();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public void onBackPressed() {
        //set cancel dialog behaviour
        new AlertDialog.Builder(this)
                .setTitle("Abbrechen")
                .setMessage("Die Eingaben des Versuchsteils wurden nicht gespeichert. Sind Sie sicher, dass Sie abbrechen möchten?")

                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        cancelTimers();
                        dataSource.deleteData(ui_Log);
                        dataSource.close();
                        //do not use onBackPressed, but use finish()
                        alarmActivity.this.finish();
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

    //Click Handler for the Buttons
    public void button_alarm_AClickHandler(View view) {
        sendMessageToWear("A");
        ui_Log.setClickedButtonType(ALARM_A);
        button_alarm_ClickHandler_Helper(view);
    }

    public void button_alarm_BClickHandler(View view) {
        ui_Log.setClickedButtonType(ALARM_B);
        button_alarm_ClickHandler_Helper(view);
    }

    public void button_alarm_CClickHandler(View view) {
        ui_Log.setClickedButtonType(ALARM_C);
        button_alarm_ClickHandler_Helper(view);
    }

    public void button_alarm_DClickHandler(View view) {
        ui_Log.setClickedButtonType(ALARM_D);
        button_alarm_ClickHandler_Helper(view);
    }

    //Helping Method for the Button Click Handler
    private void button_alarm_ClickHandler_Helper(View view){
        if(get_alarmOn()) {
            if (ui_Log.getAlarmtyp() == ui_Log.getClickedButtonType()) {
                Button button = (Button) findViewById(R.id.id_button_alarm);
                set_alarmOn(false);
                button.setVisibility(View.INVISIBLE);
            }
            addClickData(view);
            showCorrection();
        }
    }

    //To control whether alarm is on or off
    public boolean get_alarmOn() {
        return alarmOn;
    }

    public void set_alarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    //Handler for the Timer, action on Alarm
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //TextView textView = (TextView) findViewById(R.id.textView2);
            //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            // textView is the TextView view that should display it
            //textView.setText( textView.getText() + currentDateTimeString +" "+ msg.arg1 + "\n");
            Button button = (Button) findViewById(R.id.id_button_alarm);
            button.setBackgroundColor(0xffff0000);

            switch(msg.arg1){
                case 0:{
                    ui_Log.setAlarmtyp(ALARM_A);//textView.setText(textView.getText() + " " + get_alarmType() + "\n");
                }break;

                case 1: {
                    ui_Log.setAlarmtyp(ALARM_B);
                    //textView.setText(textView.getText() + " " + get_alarmType() + "\n");
                }break;

                case 2: {
                    ui_Log.setAlarmtyp(ALARM_C);
                    //textView.setText(textView.getText() + " " + get_alarmType() + "\n");
                }break;
                case 3: {
                    ui_Log.setAlarmtyp(ALARM_D);
                    //textView.setText(textView.getText() + " " + get_alarmType() + "\n");
                }break;
                default: break;
            }
            button.setText("Alarm " + ui_Log.getAlarmtyp());
            ui_Log.setPopuptime(sdf.format(new Date()));
            set_alarmOn(true);
            button.setVisibility(View.VISIBLE);
        }
    };

    //This method calls the Correction Show handler
    private void showCorrection() {
        Runnable r = new Runnable() {
            //use a Thread to wait for two seconds
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 3000;
                handlerCorrectionShow.sendEmptyMessage(0);
                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime - System.currentTimeMillis());
                        }catch(Exception ignored){}
                    }
                }
                handlerCorrectionUnshow.sendEmptyMessage(0);
            }
        };
        Thread waitThread = new Thread(r);
        waitThread.start();
    }

    //Handler gives the user feedback about the alarm correction for 3 sec
    Handler handlerCorrectionShow = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TextView tv = (TextView) findViewById(R.id.id_fehler_behoben);
            if(get_alarmOn()){
                tv.setText(R.string.fehler_nicht_behoben);
            }else {
                tv.setText(R.string.fehler_behoben);
            }
            tv.setVisibility(View.VISIBLE);
            Button button = (Button) findViewById(R.id.id_button_alarm);
            Button button_a = (Button) findViewById(R.id.id_button_alarm_a_beheben);
            Button button_b = (Button) findViewById(R.id.id_button_alarm_b_beheben);
            Button button_c = (Button) findViewById(R.id.id_button_alarm_c_beheben);
            Button button_d = (Button) findViewById(R.id.id_button_alarm_d_beheben);
            button.setVisibility(View.INVISIBLE);
            button_a.setVisibility(View.INVISIBLE);
            button_b.setVisibility(View.INVISIBLE);
            button_c.setVisibility(View.INVISIBLE);
            button_d.setVisibility(View.INVISIBLE);
        }
    };

    Handler handlerCorrectionUnshow = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            TextView tv = (TextView) findViewById(R.id.id_fehler_behoben);
            tv.setVisibility(View.INVISIBLE);
            Button button_a = (Button) findViewById(R.id.id_button_alarm_a_beheben);
            Button button_b = (Button) findViewById(R.id.id_button_alarm_b_beheben);
            Button button_c = (Button) findViewById(R.id.id_button_alarm_c_beheben);
            Button button_d = (Button) findViewById(R.id.id_button_alarm_d_beheben);
            button_a.setVisibility(View.VISIBLE);
            button_b.setVisibility(View.VISIBLE);
            button_c.setVisibility(View.VISIBLE);
            button_d.setVisibility(View.VISIBLE);

            if (get_alarmOn()){
                Button button = (Button) findViewById(R.id.id_button_alarm);
                button.setVisibility(View.VISIBLE);
            }
        }
    };

    //Handler to get supervisor after this modification is dealt with
    Handler handlerContinue = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            goToContinueActivity();
        }
    };

    //Handler helper to call the get supervisor to continue screen
    public void goToContinueActivity(){
        dataSource.close();
        Intent continueIntent = new Intent(this, continueActivity.class);
        continueIntent.putExtra(".hmi.UserInputLog",  ui_Log);
        startActivityForResult(continueIntent, CONTINUE_REQUEST_CODE);
    }

    // TODO: Is onActivityResult necessary?
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CONTINUE_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                counterModification = data.getIntExtra(continueActivity.COUNTER, 0);
            }
        }
        setTimers();
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("");
    }

    //Adds Log Data to the
    private void addClickData(View view){
        ui_Log.setClicktime(sdf.format(new Date()));
        dataSource.create(ui_Log);
    }

    private void cancelTimers(){
        caretaker.cancel();
        caretaker1.cancel();
        caretaker2.cancel();
        caretaker3.cancel();
        caretaker4.cancel();
        Log.i("Activity", "Timers canceled");
    }

//    private void pauseTimers(){
//        Timer caretaker, caretaker1, caretaker2, caretaker3, caretaker4;
//        Long caretaker_time, caretaker1_time, caretaker2_time_time, caretaker4_time;
//    }

    private void showSettings() {
        Intent settings = new Intent(this, optionActivity.class);
        settings.putExtra(".hmi.UserInputLog", ui_Log);
        startActivity(settings);
    }
}
