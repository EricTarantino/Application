package com.example.eric.application;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        //In case that MainActivity was called as an intent from the ListenerService
        //the notification aka alarm pops up
        if(getIntent().hasExtra("alarmTyp")) {
            String alarmTyp = getIntent().getStringExtra("alarmTyp");
            sendNotification(alarmTyp);
        }
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
    }

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
