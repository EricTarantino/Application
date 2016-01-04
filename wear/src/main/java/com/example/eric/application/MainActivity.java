package com.example.eric.application;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CardFragment;
import android.widget.TextView;

import android.app.Activity;
import android.view.WindowManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks {

    private static final String WEAR_MESSAGE_PATH = "/message";
    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initGoogleApiClient();

        //In case that MainActivity was called as an intent from the ListenerService
        //the notification aka alarm pops up
        if(getIntent().hasExtra("alarmTyp")) {
            String alarmTyp = getIntent().getStringExtra("alarmTyp");
            sendNotification(alarmTyp);
        }
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

    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
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

    private void sendNotification(String alarmType){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        CardFragment fragment = CardFragment.create(
                getString(R.string.alarm_title),
                alarmType,
                R.drawable.icon
        );
        transaction.add(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
