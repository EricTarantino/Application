package com.example.eric.application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {

    private static final String WEAR_MESSAGE_PATH = "/message";
    private static final String DEVICE_MAIN = "DeviceMain";
    private static final String MAIN_WEAR = "Wearable Main";
    private static final String WEAR_PATH = "/from_device";
    private GoogleApiClient mApiClient;
    private com.google.android.gms.wearable.Node mNode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMessageReceived( final MessageEvent messageEvent ) {
        Log.d(MAIN_WEAR, "message received");
        if(messageEvent.getPath().equals(WEAR_PATH)){
            String alarmType = new String(messageEvent.getData());
            TextView appText = (TextView) findViewById(R.id.text_wear);
            ImageView alarmIcon = (ImageView)findViewById(R.id.imageViewAlarm);
            if(alarmIcon.getVisibility() == View.VISIBLE) {
                appText.setText("Es wurde momentan kein Alarm ausgeloest.");
                alarmIcon.setVisibility(View.INVISIBLE);
            }else if(alarmIcon.getVisibility() == View.INVISIBLE) {
                appText.setText(alarmType);
                alarmIcon.setVisibility(View.VISIBLE);
            }
            //Do something with the message
            //Intent intent = new Intent(this, alarm.class);
            //intent.putExtra("alarmTyp", alarmType);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
            //        Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(intent);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
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
    protected void onStop() {
        if ( mApiClient != null ) {
            Wearable.MessageApi.removeListener( mApiClient, this );
            if ( mApiClient.isConnected() ) {
                mApiClient.disconnect();
            }
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if( mApiClient != null )
            mApiClient.unregisterConnectionCallbacks( this );
        super.onDestroy();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

//    @Override
//    public void onExitAmbient() {
//        super.onExitAmbient();
//    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
    }
}
