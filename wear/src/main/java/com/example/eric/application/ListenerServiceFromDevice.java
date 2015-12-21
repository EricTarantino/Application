package com.example.eric.application;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CardFragment;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Eric on 15.12.2015.
 */

public class ListenerServiceFromDevice extends WearableListenerService {

    private static final String WEARPATH = "/from-device";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if(messageEvent.getPath().equals(WEARPATH)){
            String alarmType = new String(messageEvent.getData());

            //Do something with the message
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("alarmTyp", alarmType);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
