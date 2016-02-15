package com.example.eric.application;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class alarmActivity2 extends AppCompatActivity {

    ProzessProvider processProvider;
    int [] processes =processProvider.getProcesses();
    int processCount = 0;
    int processCurrentId = 0;
    int processCurrentDuration = 0;
    int zeit = 0;
    Timer caretaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("A2", "On Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
    //processProvider = new ProzessProvider();
    }

    //  Diese Methoden schreiben:
    //  Timer Setzen
    //  LOOP 0 <= i < 4
    //  onClick_Prozess starten
    //  Prozess_starten ausblenden und starten des Prozesses
    //  Counter weniger als 10 sec: Show Prozess Button erhält einen Background (Rahmen)
    //  Counter abgelaufen: Prozess  i läuft wird invisible
    //  Show Prozess Button Background ausblenden (Kein Rahmen)
    //  Prozess i um eins erhöhen
    //  Prozess läuft Button: Text anpassen auf Prozess i läuft
    //  Show: Prozess starten Button text auf Prozess i setzen
    //  Bei Click auf Prozess starten Button, Process i starten
    //  Feld Prozess läuft den Wert von Timer i geben

    public void onClickButton_Prozess_starten(View view) {
        Button button_Prozess_starten = (Button)findViewById(R.id.button_Prozess_starten);
        button_Prozess_starten.setVisibility(View.INVISIBLE);

        processCurrentDuration = startProcess();

        Button button_Prozess_läuft = (Button)findViewById(R.id.button_Prozess_läuft);
        button_Prozess_läuft.setText("Prozess "+ (processCount+1) +" läuft. &lt;br /&gt; &lt;br /&gt"+ processCurrentDuration);
        button_Prozess_läuft.setVisibility(View.VISIBLE);

        //  Update den Timerstring jede Sekunde
        startSecond();
    }

    private int startProcess() {

        TimerTask action = new TimerTask() {
            public void run() {
                Message msg = handlerProcessEnd.obtainMessage();
                handlerProcessEnd.sendMessage(msg);
            }
        };
        caretaker = new Timer();
        // Process i starten
        caretaker.schedule(action, processes[processCount]);

        return  processes[processCount];
    }

    //  Update den Timerstring jede Sekunde
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

    //  Counter abgelaufen: Prozess  i läuft wird invisible
    Handler handlerProcessEnd = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            blendInStartNextProcess();
        }
    };

    //  Update den Timerstring jede Sekunde
    Handler handlerSecondOver = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            zeit+=1;
            Button button = (Button) findViewById(R.id.button_Prozess_läuft);
            button.setText("Prozess "+ (processCount+1) +
                                    " läuft. &lt;br /&gt; &lt;br /&gt"+ (processCurrentDuration - zeit*10000));
            button.setVisibility(View.VISIBLE);
            if(processCount== processCurrentId) {
                startSecond();
            } else {
                zeit = 0;
            }
        }
    };

    //  Counter abgelaufen: Prozess  i läuft wird invisible
    //  Prozess i um eins erhöhen
    //  Prozess läuft Button: Text anpassen auf Prozess i läuft
    //  Show: Prozess starten Button text auf Prozess i setzen
    public void blendInStartNextProcess(){
        Button button_Prozess_läuft = (Button)findViewById(R.id.button_Prozess_läuft);
        button_Prozess_läuft.setVisibility(View.INVISIBLE);
        processCount += 1;
        Button button_Prozess_starten = (Button)findViewById(R.id.button_Prozess_starten);
        button_Prozess_starten.setText("Prozess "+ (processCount+1) +" starten");
        button_Prozess_starten.setVisibility(View.VISIBLE);
    }
}
