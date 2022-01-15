package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.academyfoods.www.R;
import com.academyfoods.www.model.PopularModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    double quantity, sum,total;
    String name, price, id, image, category, description, stars,restaurantName,restaurantImage;
    TextView foodName, foodQuantity, foodDescription, foodPrice;
    CardView plus, minus, order;
    RatingBar rate;
    DatabaseReference reference;
    ImageView foodImage;
    int i;
    ArrayList<PopularModel>list=new ArrayList<>();
    PopularModel popularModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().hide();

        Calling();

        setData();

        setCalculations();


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
                    popularModel.setRestaurantImage(dataSnapshot.child("restaurantImage").getValue().toString());
                    popularModel.setRestaurantName(dataSnapshot.child("restaurantName").getValue().toString());
                    list.add(popularModel);


                } }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setFunctions();
    }

    private void Calling() {
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        id = getIntent().getStringExtra("id");
        image = getIntent().getStringExtra("image");
        category = getIntent().getStringExtra("category");
        description = getIntent().getStringExtra("description");
        stars = getIntent().getStringExtra("stars");
        restaurantName=getIntent().getStringExtra("restaurantName");
        restaurantImage=getIntent().getStringExtra("restaurantImage");
        foodName = findViewById(R.id.name);
        foodQuantity = findViewById(R.id.quantity);
        foodDescription = findViewById(R.id.description);
        foodPrice = findViewById(R.id.price);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.min);
        order = findViewById(R.id.order);
        rate = findViewById(R.id.rating);
        foodImage = findViewById(R.id.image);
    }

    private void setCalculations() {

        quantity = 1.0;
        sum = Double.parseDouble(price);
        total=sum;
        foodPrice.setText(String.valueOf(sum) + " EGP");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                foodQuantity.setText(String.valueOf(quantity));
                foodPrice.setText(String.valueOf(sum * quantity) + " EGP");
                total=sum*quantity;
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {
                    quantity--;
                    foodQuantity.setText(String.valueOf(quantity));
                    foodPrice.setText(String.valueOf(sum * quantity) + " EGP");
                    total=sum*quantity;
                } else {

                }
            }
        });

    }

    private void setData() {

        foodName.setText(name);

        foodDescription.setText(description);

        rate.setRating(Float.parseFloat(stars));

        Picasso.get().load(image).into(foodImage);
    }

    private void setFunctions(){

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list.isEmpty()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("id",id);
                    map.put("image",image);
                    map.put("quantity",quantity);
                    map.put("price",total);
                    map.put("category",category);
                    map.put("restaurantName",restaurantName);
                    map.put("restaurantImage",restaurantImage);

                    FirebaseDatabase.getInstance().getReference("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id).setValue(map);
                    startActivity(new Intent(DetailsActivity.this,CartActivity.class));
                }
                for( i=0;i<list.size();i++){

                    if(list.get(i).getRestaurantName().equals(restaurantName)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name",name);
                        map.put("id",id);
                        map.put("image",image);
                        map.put("quantity",quantity);
                        map.put("price",total);
                        map.put("category",category);
                        map.put("restaurantName",restaurantName);
                        map.put("restaurantImage",restaurantImage);

                        FirebaseDatabase.getInstance().getReference("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id).setValue(map);
                        startActivity(new Intent(DetailsActivity.this,CartActivity.class));
                    }
                    else {
                        Toast.makeText(DetailsActivity.this, "you need to delete items from this restaurant", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
    }

}