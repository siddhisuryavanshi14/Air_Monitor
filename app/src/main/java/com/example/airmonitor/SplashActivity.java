package com.example.airmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent startAct = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(startAct);
        }, 500L);
    }

    protected void onPause() {
        super.onPause();
        this.finish();
    }
}