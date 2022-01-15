package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.CartAdapter;
import com.academyfoods.www.model.PopularModel;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
CardView checkout,myCard;
RecyclerView myCart;
TextView total;
LottieAnimationView empty;
DatabaseReference reference;
ArrayList<PopularModel>list=new ArrayList<>();
PopularModel popularModel;
ImageView remove;
CartAdapter cartAdapter;
double sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().hide();

        calling();


        checkout.setBackgroundResource(R.drawable.cardviewcornerr);

        myCard.setBackgroundResource(R.drawable.cardviewcorner);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                total.setText("0.0 EGP");
            }
        });

        addToCart();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()==0){
                    Toast.makeText(CartActivity.this, "Please add some items to checkout !", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(CartActivity.this,OptionsActivity.class));
                }
            }
        });

    }


    private void calling() {
        myCart=findViewById(R.id.cart);
        total=findViewById(R.id.total);
        checkout=findViewById(R.id.checkout);
        myCard=findViewById(R.id.card);
        remove=findViewById(R.id.remove);
        empty=findViewById(R.id.empty);
    }

    private void addToCart() {

        myCart.setLayoutManager(new LinearLayoutManager(this));

       reference= FirebaseDatabase.getInstance().getReference("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

       reference.addValueEventListener(new ValueEventListener() {
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
                    popularModel.setQuantity(dataSnapshot.child("quantity").getValue().toString());
                    list.add(popularModel);

                    sum+=Double.parseDouble(popularModel.getPrice());
                    total.setText(String.valueOf(sum) + "EGP");

                }
                cartAdapter=new CartAdapter(list,CartActivity.this);
                myCart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();

                if(cartAdapter.getItemCount()==0){
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
}