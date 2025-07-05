
package com.example.timewise14;

import androidx.appcompat.app.AppCompatActivity; // Import AppCompatActivity
import android.content.Intent; // Import Intent
import android.os.Bundle; // Import Bundle
import android.os.Handler; // Import Handler (untuk postDelayed)
import android.os.Looper; // Dianjurkan untuk menggunakan Looper.getMainLooper() dengan Handler

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
