package com.academyfoods.www.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.ItemAdapter;
import com.academyfoods.www.adapter.SeeMoreAdapter;

public class SeeMoreActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static String name,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_activty);
        
        getSupportActionBar().hide();
        
        calling();

        name=getIntent().getStringExtra("name");
        image=getIntent().getStringExtra("image");
        
        getData();
    }

    private void getData() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        SeeMoreAdapter itemAdapter=new SeeMoreAdapter(getIntent().getParcelableArrayListExtra("list"),this);

        recyclerView.setAdapter(itemAdapter);

        itemAdapter.notifyDataSetChanged();
    }

    private void calling() {
        recyclerView=findViewById(R.id.more);
    }
    
}