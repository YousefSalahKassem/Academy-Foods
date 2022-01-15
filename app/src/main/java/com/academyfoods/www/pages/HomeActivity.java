package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.UserInformationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class    HomeActivity extends AppCompatActivity {
    ImageView userImage;
    CardView teus, brazilian, classique, cilantro;
    UserInformationModel userInformationModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        calling();



        getData();

        viewRestaurant();


    }
    
    private void calling() {
        userImage = findViewById(R.id.userImage);
        teus = findViewById(R.id.teus);
        brazilian = findViewById(R.id.brazilian);
        classique = findViewById(R.id.classique);
        cilantro = findViewById(R.id.cilantro);
    }

    private void getData() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInformationModel = dataSnapshot.getValue(UserInformationModel.class);
                if (!userInformationModel.getImage().isEmpty()) {
                    Picasso.get().load(userInformationModel.getImage()).into(userImage);
                } else {
                    userImage.setBackgroundResource(R.drawable.user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void viewRestaurant() {

        teus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurant", "teus");
                intent.putExtra("images","https://firebasestorage.googleapis.com/v0/b/academy-foods.appspot.com/o/teus.png?alt=media&token=97d0b075-fb50-4cf7-977f-2b267556f853");
                intent.putExtra("name",userInformationModel.getName());
                intent.putExtra("image",userInformationModel.getImage());
                startActivity(intent);
                overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);

            }
        });

        brazilian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurant", "brazilian");
                intent.putExtra("images","https://firebasestorage.googleapis.com/v0/b/academy-foods.appspot.com/o/brazilian.jpg?alt=media&token=57f4cb33-68f0-47cd-a7d3-e409e8fcd210");
                intent.putExtra("name",userInformationModel.getName());
                intent.putExtra("image",userInformationModel.getImage());
                startActivity(intent);
                overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            }
        });

        classique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurant", "classique");
                intent.putExtra("images","https://firebasestorage.googleapis.com/v0/b/academy-foods.appspot.com/o/classique.png?alt=media&token=59efea16-c6ae-4bf1-9ba8-677507671367");
                intent.putExtra("name",userInformationModel.getName());
                intent.putExtra("image",userInformationModel.getImage());
                startActivity(intent);
                overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            }
        });

        cilantro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurant", "cilantro");
                intent.putExtra("images","https://firebasestorage.googleapis.com/v0/b/academy-foods.appspot.com/o/cilantro.jpg?alt=media&token=5b42147e-4254-40dd-bb42-eaa58887b0bd");
                intent.putExtra("name",userInformationModel.getName());
                intent.putExtra("image",userInformationModel.getImage());
                startActivity(intent);
                overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }


}