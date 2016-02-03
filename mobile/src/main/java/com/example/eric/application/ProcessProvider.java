package com.example.eric.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Description of the class:
 *
 * VARIABLES:
 * private int [] delay:
 * There are four timer variables represented by
 * delay[0],...,delay[3] where delay[x]
 *
 * interval:
 * duration in which one alarm occurs
 *
 * Math.random():
 * This function returns a random number  between 0.0 and 1.0
 * (e.g. 0.6435).


 * BEHAVIOUR:
 * The class provides four timer durations, which run from the start of Versuch 1.
 * One alarm pops up at the end of a timer. They are saved in delay[0], delay[1],...
 *
 * The first alarm pops up 1m after start of Versuch 1 at the earliest,
 * as the participant should first concentrate on the test task.
 *
 * 7 min remaining duration for the experiment is split into 4 intervals
 * with one alarm each.
 * So one interval lasts 7 min/ 4 = 420.000 ms/ 4 = 105.000 ms.
 *
 * Intervals are (60.000,165.000],(165.000,270.000],(270.000,375.000],...
 *
 * In detail this is the beginning of an interval plus a delay (delayToNext)
 * plus a duration for the exact point of the alarm.
 *
 * The exact point is is set by adding Math.random() * 0.8 * interval.
 * The participant needs some time to react to the alarm, so an alarm
 * does not pop up just before the beginning of the next interval,
 * by multiplying with factor 0.8.
 *
 * E.g. for the second alarm in we have
 * delay[1] = 60.000 + 2 * interval + (int)(Math.random() * 0.8 * interval;
 *
 */

public class ProcessProvider {

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    private int [] processTime;
    private int [] alarm_type = new int[4];

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // constructors, getters, setters                                                //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    ProcessProvider() {

        int a1 = (int)(Math.random()*4+0.5) %4;
        int a2 = (int)(Math.random()*4+0.5) %4;
        int a3 = (int)(Math.random()*4+0.5) %4;
        int a4 = (int)(Math.random()*4+0.5) %4;

        List<Integer> type_List = Arrays.asList(a1,a2,a3,a4);

        processTime = new int[4];
        int startDelay = 60000;
        int interval = 105000;

        processTime[0] = getRandomNumberInRange(0, 60000);
        processTime[1] = getRandomNumberInRange(0, 60000);
        processTime[2] = getRandomNumberInRange(0, 60000);
        processTime[3] = getRandomNumberInRange(0, 60000);

        //delay for test case
        //delay[0] = 50000;
        //delay[1] = 25000;
        //delay[2] = 50000;
        //delay[3] = 75000;

        Collections.shuffle(type_List);
        setAlarmType(type_List);
    }

    protected int[] getAlarmTime(){
        return alarm_type;
    }

    protected int[] getProcesses(){
        return processTime;
    }

    private void setAlarmType(List<Integer> type_List) {
        for(int i = 0; i<4; i++) alarm_type[i] = type_List.get(i);
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
