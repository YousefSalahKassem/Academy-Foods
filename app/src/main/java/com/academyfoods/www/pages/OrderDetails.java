package com.academyfoods.www.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.CartAdapter;
import com.academyfoods.www.adapter.CheckoutCartAdapter;
import com.academyfoods.www.adapter.OrderAdapter;
import com.academyfoods.www.model.PopularModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {

    TextView name,subtotal,delivery,discount,total,address,status,date,phone;
    ImageView image;
    ArrayList<PopularModel>list=new ArrayList<>();
    RecyclerView cart;
    CheckoutCartAdapter cartAdapter;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getSupportActionBar().hide();

        calling();

        setData();

        getOrders();

        getPrices();
    }

    private void calling() {
        name=findViewById(R.id.restaurantName);
        image=findViewById(R.id.restaurantImage);
        subtotal=findViewById(R.id.subtotal);
        delivery=findViewById(R.id.delivery);
        discount=findViewById(R.id.discount);
        total=findViewById(R.id.total);
        address=findViewById(R.id.address);
        cart=findViewById(R.id.cart);
        status=findViewById(R.id.status);
        phone=findViewById(R.id.phone);
        date=findViewById(R.id.date);
        layout=findViewById(R.id.linear1);
    }

    private void setData() {
        name.setText(getIntent().getStringExtra("name"));
        Picasso.get().load(getIntent().getStringExtra("image")).into(image);
        address.setText(getIntent().getStringExtra("address"));
        if(getIntent().getStringExtra("type").equals("PickNGo")){
            layout.setVisibility(View.GONE);
        }
        phone.setText(getIntent().getStringExtra("phone"));
        status.setText(getIntent().getStringExtra("status"));
        if(getIntent().getStringExtra("status").equals("Preparing")){
            status.setTextColor(Color.GRAY);
        }
        else if(getIntent().getStringExtra("status").equals("In Progress")){
            status.setTextColor(Color.YELLOW);
        }
        else if(getIntent().getStringExtra("status").equals("Canceled")){
            status.setTextColor(Color.RED);
        }
        else {
            status.setTextColor(Color.GREEN);
        }
        date.setText(getIntent().getStringExtra("date"));
    }

    private void getOrders() {
        list=getIntent().getParcelableArrayListExtra("cart");
        System.out.println(list.toString());

        cart.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        cartAdapter=new CheckoutCartAdapter(list,OrderDetails.this);
        cart.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

    }

    private void getPrices() {
        subtotal.setText(getIntent().getStringExtra("subtotal"));
        delivery.setText(getIntent().getStringExtra("delivery"));
        discount.setText(getIntent().getStringExtra("discount"));
        total.setText(getIntent().getStringExtra("total"));
    }




}