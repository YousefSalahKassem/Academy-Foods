package com.academyfoods.www.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.OfferModel;
import com.academyfoods.www.model.ResultActivity;
import com.academyfoods.www.model.SpinnerModel;
import com.academyfoods.www.model.UserInformationModel;
import com.academyfoods.www.pages.AddressActivity;
import com.academyfoods.www.pages.EditProfileActivity;
import com.academyfoods.www.pages.ForgotPassword;
import com.academyfoods.www.pages.LoginActivity;
import com.academyfoods.www.pages.OrderHistoryActivity;
import com.academyfoods.www.pages.RestaurantActivity;
import com.academyfoods.www.pages.SpinnerActivity;
import com.academyfoods.www.pages.VouchersActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class settingsFragment extends Fragment {
    LinearLayout edit,changePassword,addresses,vouchers,orderHistory,logout,profile,spinner;
    ImageView userImage;
    TextView userName;
    Query reference;
    List<OfferModel>list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calling(view);



        actions();

        getData();
    }




    private void calling(View view) {
        edit=view.findViewById(R.id.editprofile);
        profile=view.findViewById(R.id.edit);
        changePassword=view.findViewById(R.id.changepassword);
        addresses=view.findViewById(R.id.addresses);
        vouchers=view.findViewById(R.id.voucher);
        orderHistory=view.findViewById(R.id.orderhistory);
        logout=view.findViewById(R.id.logout);
        userName=view.findViewById(R.id.name);
        userImage=view.findViewById(R.id.image);
        spinner=view.findViewById(R.id.spinner);
    }

    private void getData() {
        userName.setText(((RestaurantActivity)getActivity()).getUserName());

        Picasso.get().load(((RestaurantActivity)getActivity()).getImage()).into(userImage);
    }

    private void actions() {

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ForgotPassword.class));
            }
        });

        vouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), VouchersActivity.class));
            }
        });

        addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddressActivity.class));
            }
        });

        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
            }
        });

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference=FirebaseDatabase.getInstance().getReference("spinner").child("requested").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            OfferModel offerModel=new OfferModel();
                            offerModel.setEmail(dataSnapshot.child("email").getValue().toString());
                            offerModel.setImage(dataSnapshot.child("image").getValue().toString());
                            offerModel.setName(dataSnapshot.child("name").getValue().toString());
                            offerModel.setNumber(dataSnapshot.child("number").getValue().toString());
                            offerModel.setOffer(dataSnapshot.child("offer").getValue().toString());
                            offerModel.setUid(dataSnapshot.child("uid").getValue().toString());
                            offerModel.setTake(dataSnapshot.child("take").getValue().toString());
                            list.add(offerModel);
                            for(int i=0;i<list.size();i++){
                                if((FirebaseAuth.getInstance().getCurrentUser().getUid().equals(list.get(i).getUid())&&list.get(i).getTake().equals("true"))){
                                    Intent intent=new Intent(getActivity(), ResultActivity.class);
                                    intent.putExtra("name",list.get(i).getName());
                                    intent.putExtra("image",list.get(i).getImage());
                                    intent.putExtra("offer",list.get(i).getOffer());
                                    startActivity(intent);
                                }
                                else {
                                    startActivity(new Intent(getActivity(), SpinnerActivity.class));
                                }}

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                startActivity(new Intent(getActivity(), SpinnerActivity.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

    }
}