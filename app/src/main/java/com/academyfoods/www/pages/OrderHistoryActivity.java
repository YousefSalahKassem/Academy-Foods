package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.OrderAdapter;
import com.academyfoods.www.model.OrderModel;
import com.academyfoods.www.model.PopularModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {
    RecyclerView history;
    OrderModel orderModel;
    ArrayList<OrderModel>list=new ArrayList<>();
    DatabaseReference reference;
    OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        getSupportActionBar().hide();

        calling();

        getOrderHistory();

    }



    private void calling() {
        history=findViewById(R.id.orderhistory);
    }

    private void getOrderHistory() {
        history.setLayoutManager(new LinearLayoutManager(this));

        reference= FirebaseDatabase.getInstance().getReference("myOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    orderModel=dataSnapshot.getValue(OrderModel.class);
                    list.add(orderModel);
                }
                orderAdapter=new OrderAdapter(list,OrderHistoryActivity.this);
                history.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}