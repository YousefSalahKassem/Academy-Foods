package com.academyfoods.www.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.Adapter;

public class welcomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    Adapter pagerAdapter;
    int[] layouts;
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        getSupportActionBar().hide();

        calling();

        Slider();

        nextButton();

    }


    private void calling() {
        viewPager = findViewById(R.id.view);
        next = findViewById(R.id.next);
    }

    private void Slider() {

        layouts = new int[]{
                R.layout.slider1,
                R.layout.slider2,
        };
        pagerAdapter = new Adapter(this, layouts);
        viewPager.setAdapter(pagerAdapter);
    }

    private void nextButton() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() + 1 < layouts.length) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}