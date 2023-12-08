package controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.superhoodcleaning.R;

public class SuperHoodCleaning_Splash extends AppCompatActivity {

    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_hood_cleaning_splash);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SuperHoodCleaning_Splash.this, TabBarActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}