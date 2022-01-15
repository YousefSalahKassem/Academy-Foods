package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.CategoryPagerAdapter;
import com.academyfoods.www.adapter.ItemAdapter;
import com.academyfoods.www.model.PopularModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    TextView type;
    RecyclerView category;
    Query query;
    PopularModel popularModel;
    ArrayList<PopularModel>list=new ArrayList<>();
    public static String name,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().hide();

        calling();

        getData();
    }

    private void getData() {
        name=getIntent().getStringExtra("name");
        image=getIntent().getStringExtra("image");

        type.setText(getIntent().getStringExtra("type"));

        category.setLayoutManager(new GridLayoutManager(this,2));

        query= FirebaseDatabase.getInstance().getReference(name).child("items").orderByChild("category").equalTo(getIntent().getStringExtra("type"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    popularModel = new PopularModel();
                    popularModel.setName(dataSnapshot.child("name").getValue().toString());
                    popularModel.setId(dataSnapshot.child("id").getValue().toString());
                    popularModel.setImage(dataSnapshot.child("image").getValue().toString());
                    popularModel.setPrice(dataSnapshot.child("price").getValue().toString());
                    popularModel.setCategory(dataSnapshot.child("category").getValue().toString());
                    popularModel.setStars(dataSnapshot.child("stars").getValue().toString());
                    popularModel.setDescription(dataSnapshot.child("description").getValue().toString());
                    list.add(popularModel);
                }
                CategoryPagerAdapter CategoryPagerAdapter = new CategoryPagerAdapter(list,CategoryActivity.this);
                category.setAdapter(CategoryPagerAdapter);
                CategoryPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void calling() {
        type=findViewById(R.id.type);
        category=findViewById(R.id.categories);
    }
}