package com.example.eric.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Created by Eric van Lessen, eric.van@rwth-aachen.de, eric.vanlessen@live.de on 23.01.2016.
 * Description of the class:
 *
 * VARIABLES:
 * private int [] delay:
 * There are four time intervals with one alarm each
 * represented by delay[0],...,delay[3] where delay[x]
 * holds an alarm times from t0 (beginning of the part
 * of the experiment).
 *
 * delayToNext:
 * This is the delay from beginning of one alarm interval
 * to avoid the occurance of two alarms in less than 15 s.
 *
 * Math.random():
 * This function returns a random number  between 0.0 and 1.0
 * (e.g. 0.6435).
 *
 * remainingInterval:
 * This is a help variable which is a time interval minus
 * the delay in the beginning, so the remaining period.

 * BEHAVIOUR:
 * The functionality is basically provided in the constructor.
 *
 * delay[x] is set as beginning of interval plus delayToNext
 * Then the point of the alarm is set by adding Math.random()
 * multiplied with the the remaining time of the interval.
 *
 * We do not want that the last alarm pops up just before the end
 * of the experiment, so for the last interval Math.random()
 * is multiplied with slightly less than the remaining interval time.
 */

public class AlarmProvider {

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    private int [] delay;
    private int [] alarm_type = new int[4];

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // constructors, getters, setters                                                //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    AlarmProvider() {

        int a1 = (int)(Math.random()*4+0.5) %4;
        int a2 = (int)(Math.random()*4+0.5) %4;
        int a3 = (int)(Math.random()*4+0.5) %4;
        int a4 = (int)(Math.random()*4+0.5) %4;

        List<Integer> type_List = Arrays.asList(a1,a2,a3,a4);

        int delayToNext = 15000;
        delay = new int[4];
        int remainingInterval = 120000-delayToNext;

        delay[0] = delayToNext + (int)(Math.random() * remainingInterval);
        delay[1] = 120000 + delayToNext + (int)(Math.random() * remainingInterval);
        delay[2] = 240000 + delayToNext + (int)(Math.random() * remainingInterval);
        delay[3] = 320000 + delayToNext + (int)(Math.random() * (0.8 * remainingInterval));

        //delay for test case
        //delay[0] = 50000;
        //delay[1] = 25000;
        //delay[2] = 50000;
        //delay[3] = 75000;

        Collections.shuffle(type_List);
        setAlarmType(type_List);
    }

    protected int[] getAlarms(){
        return alarm_type;
    }

    protected int[] getDelays(){
        return delay;
    }

    private void setAlarmType(List<Integer> type_List) {
        for(int i = 0; i<4; i++) alarm_type[i] = type_List.get(i);
    }
}
