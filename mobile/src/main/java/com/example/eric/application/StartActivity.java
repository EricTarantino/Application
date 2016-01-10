package com.example.eric.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class StartActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private com.google.android.gms.wearable.Node mNode;
    private GoogleApiClient mApiClient;
    private static final String DEVICE_MAIN = "DeviceMain";
    private static final String WEAR_PATH = "/from_device";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize mGoolgeAPIClient
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this )
                .addOnConnectionFailedListener(this)
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();

        setContentView(R.layout.activity_start);
    }

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
    protected void onStart() {
        super.onStart();
        mApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mApiClient.disconnect();
    }

    public void button_messageClickHandler(View view) {
        sendMessage("A");
    }

    //sends a message to the wearable
    public void sendMessage(String alarmType){
        if(mNode != null && mApiClient != null){
            Wearable.MessageApi.sendMessage(mApiClient,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    public void button_startClickHandler(View view) {
        Intent idEingabe = new Intent(this, idEingabe.class);
        startActivity(idEingabe);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }
}

