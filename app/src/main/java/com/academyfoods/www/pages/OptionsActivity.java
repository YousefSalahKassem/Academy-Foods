package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.AddressAdapter;
import com.academyfoods.www.model.UserInformationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity {
    CardView pick,delivery;
    UserInformationModel userInformationModel;
    DatabaseReference databaseReference;
    ArrayList<String>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        getSupportActionBar().hide();

        calling();

        getData();

        GetAddresses();

        Actions();
    }


    private void calling() {
        pick=findViewById(R.id.pickAndGo);
        delivery=findViewById(R.id.delivery);
    }
    private void getData() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInformationModel = dataSnapshot.getValue(UserInformationModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void GetAddresses() {

        databaseReference= FirebaseDatabase.getInstance().getReference("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getValue().toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Actions() {
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.isEmpty()|| userInformationModel.getPhone().equals(" ")){
                    Toast.makeText(OptionsActivity.this, "You need to add your phone or address !", Toast.LENGTH_SHORT).show();
                }else {
                Intent intent=new Intent(OptionsActivity.this,BuyProductsActivity.class);
                intent.putExtra("type","PickNGo");
                startActivity(intent);
            }}
        });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.isEmpty()|| userInformationModel.getPhone().equals(" ")){
                    Toast.makeText(OptionsActivity.this, "You need to add your phone or address !", Toast.LENGTH_SHORT).show();
                }
                else {
                Intent intent=new Intent(OptionsActivity.this,BuyProductsActivity.class);
                intent.putExtra("type","Delivery");
                startActivity(intent);
            }}
        });
    }

}