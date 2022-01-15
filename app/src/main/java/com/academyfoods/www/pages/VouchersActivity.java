package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.VoucherAdapter;
import com.academyfoods.www.model.VoucherModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VouchersActivity extends AppCompatActivity {

    ArrayList<VoucherModel> list=new ArrayList<>();
    VoucherAdapter voucherAdapter;
    DatabaseReference reference;
    RecyclerView vouchers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);

        getSupportActionBar().hide();

        calling();
        
        GetVouchers();
    }

    private void GetVouchers() {
        vouchers.setLayoutManager(new LinearLayoutManager(this));
        reference= FirebaseDatabase.getInstance().getReference("vouchers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    VoucherModel voucherModel=new VoucherModel();
                    voucherModel.setName(dataSnapshot.child("name").getValue().toString());
                    voucherModel.setCode(dataSnapshot.child("code").getValue().toString());
                    voucherModel.setType(dataSnapshot.child("type").getValue().toString());
                    voucherModel.setValid(dataSnapshot.child("valid").getValue().toString());
                    voucherModel.setImage(dataSnapshot.child("image").getValue().toString());
                    voucherModel.setValue(dataSnapshot.child("value").getValue().toString());
                    list.add(voucherModel);
                }
                voucherAdapter=new VoucherAdapter(list,VouchersActivity.this);
                vouchers.setAdapter(voucherAdapter);
                voucherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void calling() {
        vouchers=findViewById(R.id.vouchers);
    }
}