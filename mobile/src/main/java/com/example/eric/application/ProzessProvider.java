package com.example.eric.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Description of the class:
 *
 * VARIABLES:
 * private int [] processes;
 *
 * interval:
 * duration in which one alarm occurs
 *
 * Math.random():
 * This function returns a random number  between 0.0 and 1.0
 * (e.g. 0.6435).


 * BEHAVIOUR:
 * The class provides four processes.
 * Four random numbers are generated and then smoothed, hence
 * process durations do not differ much.
 */

public class ProzessProvider {

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    private int [] processes;

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // constructors, getters, setters                                                //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    ProzessProvider() {

        double a1;
        double a2;
        double a3;
        double a4;

        //generate random data
        a1 = Math.random();
        a2 = Math.random();
        a3 = Math.random();
        a4 = Math.random();

        double prozesses[] = {a1, a2, a3, a4};

        //smooth random data
        double max = Math.max( prozesses[0] , Math.max( prozesses[1] , Math.max( prozesses[2] , prozesses[3] )));
        double min = Math.min( prozesses[0] , Math.min( prozesses[1] , Math.min( prozesses[2] , prozesses[3] )));

        while( max - min >= 0.5 ){
            for(int i = 0; i<= 4; i++){
                if(prozesses[i]==max)
                    prozesses[i]-=0.05;
            }
            for(int i = 0; i<4; i++){
                if(prozesses[i]==min)
                    prozesses[i]+=0.05;
            }
        }

        //normalize so that sum ai = 1
        double sumProzesses = prozesses[1] + prozesses[2] + prozesses[3] + prozesses[4];
        for(int i = 0; i<4; i++){
            prozesses[i] = prozesses[i] / sumProzesses;
        }

    }

    protected int[] getProcesses(){
        return processes;
    }
}
