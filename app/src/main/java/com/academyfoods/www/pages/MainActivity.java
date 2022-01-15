package com.academyfoods.www.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.academyfoods.www.R;
import com.academyfoods.www.manager.LauncherManager;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    LauncherManager launcherManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        launcherManager = new LauncherManager(this);

        splashFunction();

    }

    private void splashFunction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (launcherManager.isFirstTime()) {
                    launcherManager.setFirstLunch(false);
                    startActivity(new Intent(MainActivity.this, welcomeActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
                }
            }
        }, 3000);
    }
}