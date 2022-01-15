package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.academyfoods.www.R;
import com.academyfoods.www.fragments.FoodsFragment;
import com.academyfoods.www.model.UserInformationModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class RestaurantActivity extends AppCompatActivity {

    MeowBottomNavigation meowBottomNavigation;
    public String name,userName,userImage,images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_activty);

        getSupportActionBar().hide();

        name=getIntent().getStringExtra("restaurant");
        userImage=getIntent().getStringExtra("image");
        userName=getIntent().getStringExtra("name");
        images=getIntent().getStringExtra("images");

        meowBottomNavigation=findViewById(R.id.navbottom);
        NavController navController = Navigation.findNavController(this, R.id.fragment2);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.food));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_settings_24));
        meowBottomNavigation.show(1, true);
        navController.navigate(R.id.foodsFragment);
        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        navController.navigate(R.id.foodsFragment);
                        break;
                    case 2:
                        navController.navigate(R.id.settingsFragment);
                        break;
                    default:
                        break;
                }
                return null;
            }
        });


    }
    public  String getName(){
        return getIntent().getStringExtra("restaurant");
    }

    public  String getUserName(){
        return getIntent().getStringExtra("name");
    }

    public  String getImage(){
        return getIntent().getStringExtra("image");
    }



}