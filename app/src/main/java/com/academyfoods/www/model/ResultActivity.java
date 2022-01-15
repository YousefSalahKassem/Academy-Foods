package com.academyfoods.www.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.academyfoods.www.R;
import com.academyfoods.www.pages.HomeActivity;
import com.squareup.picasso.Picasso;

public class ResultActivity extends AppCompatActivity {
TextView offer,name;
ImageView image;
CardView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().hide();

        calling();

        setData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, HomeActivity.class));
            }
        });
    }

    private void calling() {
        name=findViewById(R.id.name);
        offer=findViewById(R.id.offer);
        image=findViewById(R.id.image);
        back=findViewById(R.id.back);
    }

    private void setData() {
        name.setText(getIntent().getStringExtra("name"));
        offer.setText(getIntent().getStringExtra("offer"));
        Picasso.get().load(getIntent().getStringExtra("image")).into(image);
    }

    @Override
    public void onBackPressed() {
    }
}

