package com.example.eric.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//import java.util.Set;

/**
 * Created by Eric on 10.10.2015.
 */
public class AlarmProvider {
    //private HashMap<String, Long> hashMapAlarm = new HashMap<String, Long>();
    private int [] delay;
    private int [] alarm_type = new int[4];

    AlarmProvider() {

        int a1 = (int)(Math.random()*4+0.5) %4;
        int a2 = (int)(Math.random()*4+0.5) %4;
        int a3 = (int)(Math.random()*4+0.5) %4;
        int a4 = (int)(Math.random()*4+0.5) %4;

        List<Integer> type_List = Arrays.asList(a1,a2,a3,a4);

        //12 random numbers delay_1 - delay_12 that hold:
        //30 sec in between if multiplied with 480.000ms
        //Alarm max time is now at 460000, alarm min time is at 20000
        int delayToNext = 15000;
        delay = new int[4];

        //Generate random delays, 10 sec in the beginning
        //10 seconds delay, 10 seconds left until program end
        //delay[0] = delayToNext + (int)(Math.random() * 105000);
        //delay[1] = 160000 + delayToNext + (int)(Math.random() * 105000);
        //delay[2] = 320000 + delayToNext + (int)(Math.random() * 105000);
        //delay[3] = 480000 + delayToNext + (int)(Math.random() * 95000);

        delay[0] = 5000;
        delay[1] = 20000;
        delay[2] = 30000;
        delay[3] = 40000;

        Collections.shuffle(type_List);
        setAlarmType(type_List);
    }

    private void setAlarmType(List<Integer> type_List) {
        for(int i = 0; i<4; i++) alarm_type[i] = type_List.get(i);
    }

    public int[] getDelays(){
        return delay;
    }

    public int[] getAlarms(){
        return alarm_type;
    }

}
