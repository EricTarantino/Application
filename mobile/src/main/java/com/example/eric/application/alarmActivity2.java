package com.example.eric.application;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class alarmActivity2 extends AppCompatActivity {

    ProzessProvider processProvider;
    int [] processes =processProvider.getProcesses();
    int processCount = 0;
    int processCurrent = 0;
    int zeit = 0;
    Timer caretaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("A2", "On Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
    //processProvider = new ProzessProvider();
    }

    // Diese Methoden schreiben:
    //  Timer Setzen
    //  LOOP 0 <= i < 4
    //  Bei Click auf Prozess starten Button
    //  Timer i starten
    //  Zeitstring mit Timer i Zeit den Wert von Timer i geben
    //  Update den Timerstring jede Sekunde
    //  Counter weniger als 10 sec: Show Prozess Button erhält einen Background (Rahmen)
    //  Counter abgelaufen: Prozess  i läuft wird invisible
    //  Show Prozess Button Background ausblenden (Kein Rahmen)
    //  Prozess i um eins erhöhen
    //  Prozess läuft Button: Text anpassen auf Prozess i läuft
    //  Show: Prozess starten Button text auf Prozess i setzen

    public void onClickButton_Prozess_starten(View view) {
        Button button_Prozess_starten = (Button)findViewById(R.id.button_Prozess_starten);
        button_Prozess_starten.setVisibility(View.INVISIBLE);

        int duration = startProcess();

        Button button_Prozess_läuft = (Button)findViewById(R.id.button_Prozess_läuft);
        button_Prozess_läuft.setText("Prozess "+ (processCount+1) +" läuft. &lt;br /&gt; &lt;br /&gt Zweite Zeile");
        button_Prozess_läuft.setVisibility(View.VISIBLE);
    }

    private int startProcess() {

        TimerTask action = new TimerTask() {
            public void run() {
                Message msg = handlerProcessEnd.obtainMessage();
                msg.arg1 = processCount+1;
                handlerProcessEnd.sendMessage(msg);
            }
        };
        caretaker = new Timer();
        caretaker.schedule(action, processes[processCount]);

        return  processes[processCount];
    }

    private void startSecond() {

        TimerTask action = new TimerTask() {
            public void run() {
                Message msg = handlerSecondOver.obtainMessage();
                handlerSecondOver.sendMessage(msg);
            }
        };
        caretaker = new Timer();
        caretaker.schedule(action, 60000);
    }

    Handler handlerProcessEnd = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Button button = (Button) findViewById(R.id.id_button_alarm);
            button.setBackgroundColor(0xffff0000);

            switch(msg.arg1){

            }

            button.setVisibility(View.VISIBLE);
        }
    };

    Handler handlerSecondOver = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Button button = (Button) findViewById(R.id.button_Prozess_läuft);
            button.setText("Prozess "+ (processCount+1) +" läuft. &lt;br /&gt; &lt;br /&gt Zweite Zeile "+zeit);
            zeit+=1;
            button.setVisibility(View.VISIBLE);
            if(processCount==processCurrent)
                startSecond();
        }
    };
}
