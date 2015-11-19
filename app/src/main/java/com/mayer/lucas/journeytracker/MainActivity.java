package com.mayer.lucas.journeytracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import javax.xml.validation.Validator;


public class MainActivity extends Activity {

    static protected GraphView GV;
    GpsManager GM;
    Double speed = 0.0;
    Timer T = new Timer();
    static protected TextView currentspeed;
    static protected TextView averagespeed;
    static protected TextView overralltime;
    int count = 0;
    static Thread t;


    public MainActivity() {
    }
 //llllllooooooopopo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentspeed = (TextView) findViewById(R.id.currentspeedtextView);
        averagespeed = (TextView) findViewById(R.id.averagespeedtextView);
        GV = (GraphView) findViewById(R.id.view);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().toString().equals("Start")) {
                    TextView gpsmodetext = (TextView) findViewById(R.id.gpsmodetextView);
                    GM = new GpsManager(getBaseContext());
                    button.setText("Stop");
                    overralltime = (TextView) findViewById(R.id.overalltimetextView);
                    t = new Thread() {

                        @Override
                        public void run() {
                            try {
                                while (!isInterrupted()) {
                                    Thread.sleep(1000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Update();

                                        }
                                    });
                                }
                            } catch (InterruptedException e) {
                            }
                        }
                    };
                    t.start();
                    gpsmodetext.setText("Online");
                    gpsmodetext.setTextColor(Color.GREEN);
                } else if (button.getText().toString().equals("Stop")) {
                    button.setText("Start");
                    t.interrupt();
                    count = 0;
                    GM.StopTracking();
                    GV.CleartArrayListSpeed();
                    GM = null;

                    currentspeed.setText("N/A");
                    averagespeed.setText("N/A");
                    overralltime.setText("N/A");
                }
            }
        });
    }

    public void Update() {
        overralltime.setText((String.valueOf(count)));
        count++;
    }

    static public void UpdateSpeed(Float i) {
        currentspeed.setText(String.valueOf(i));
    }

    static public void UpdateAverage(Float i) {
        averagespeed.setText(String.valueOf(i));
    }

    @Override
    protected void onResume() {
        if (GM != null) {
            GM.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        if (GM != null) {
            GM.onPause();
        }
        super.onPause();
    }


}
