package com.github.workcubed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cam on 4/2/16.
 */
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        Thread splash = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent splashintent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(splashintent);
                    finish();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }
}
