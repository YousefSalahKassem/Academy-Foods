package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.AddressAdapter;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {
    LottieAnimationView empty;
    RecyclerView addresses;
    CardView add;
    DatabaseReference databaseReference;
    ArrayList<String>list=new ArrayList<>();
    AddressAdapter addressAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        getSupportActionBar().hide();

        calling();

        GetAddresses();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this,AddAddressesActivity.class));
            }
        });


    }

    private void GetAddresses() {
        addresses.setLayoutManager(new LinearLayoutManager(this));

        databaseReference= FirebaseDatabase.getInstance().getReference("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getValue().toString());
                }
                addressAdapter=new AddressAdapter(list,AddressActivity.this);
                addresses.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();

                if(addressAdapter.getItemCount()==0){
                    empty.setVisibility(View.VISIBLE);
                    empty.playAnimation();
                }
                else {
                    empty.setVisibility(View.GONE);
                    empty.pauseAnimation();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void calling() {
        addresses=findViewById(R.id.addresses);
        add=findViewById(R.id.addAddress);
        empty=findViewById(R.id.emptyaddress);
    }
}