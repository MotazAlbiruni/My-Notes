package com.motazalbiruni.mynotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.motazalbiruni.mynotes.MyValues;
import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.ui.main.MainActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.finish();
                SplashScreen.this.startActivity(intent);
            }
        }, MyValues.SPLASH_DISPLAY_TIME);
    }//end onCreate()
}//end Class SplashScreen