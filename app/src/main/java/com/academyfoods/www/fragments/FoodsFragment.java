package com.academyfoods.www.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.academyfoods.www.R;
import com.academyfoods.www.adapter.CategoryAdapter;
import com.academyfoods.www.adapter.ItemAdapter;
import com.academyfoods.www.model.CategoryModel;
import com.academyfoods.www.model.PopularModel;
import com.academyfoods.www.model.UserInformationModel;
import com.academyfoods.www.pages.CartActivity;
import com.academyfoods.www.pages.RestaurantActivity;
import com.academyfoods.www.pages.SeeMoreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;


public class FoodsFragment extends Fragment {

    TextView more;
    ImageView cart;
    RecyclerView category, popular;
    ArrayList<CategoryModel> list = new ArrayList<>();
    ArrayList<PopularModel> popularModelArrayList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    DatabaseReference reference;
    PopularModel popularModel;
    ItemAdapter itemAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foods, container, false);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calling(view);

        categoryList();

        PopularList();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        System.out.println(((RestaurantActivity)getActivity()).getName());

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SeeMoreActivity.class);
                intent.putExtra("name",((RestaurantActivity)getActivity()).name);
                intent.putExtra("image",((RestaurantActivity)getActivity()).images);
                intent.putExtra("list",popularModelArrayList);
                startActivity(intent);
            }
        });
    }

    private void Calling(View v) {
        cart = v.findViewById(R.id.cart);
        category = v.findViewById(R.id.category);
        more = v.findViewById(R.id.all);
        popular = v.findViewById(R.id.popular);
    }


    private void categoryList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        category.setLayoutManager(layoutManager);
        if(((RestaurantActivity)getActivity()).getName().equals("teus")) {
            list.add(new CategoryModel(R.raw.burger, "Burger"));
            list.add(new CategoryModel(R.raw.hotdog, "Hot Dog"));
            list.add(new CategoryModel(R.raw.fries, "French Fries"));
            list.add(new CategoryModel(R.raw.breakfast, "Break Fast"));
            list.add(new CategoryModel(R.raw.coffe, "Coffee"));
            list.add(new CategoryModel(R.raw.juice, "Fresh Juices"));

            categoryAdapter = new CategoryAdapter(list, getActivity());
            category.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
        }else if(((RestaurantActivity)getActivity()).getName().equals("cilantro")){
            list.add(new CategoryModel(R.raw.breakfast, "Break Fast"));
            list.add(new CategoryModel(R.raw.coffe, "Coffee"));
            list.add(new CategoryModel(R.raw.juice, "Fresh Juices"));
            list.add(new CategoryModel(R.raw.salad, "Salad"));
            list.add(new CategoryModel(R.raw.desserts, "Desserts"));
            list.add(new CategoryModel(R.raw.sandwich, "Sandwich"));
            list.add(new CategoryModel(R.raw.breakfast, "Snacks"));
            categoryAdapter = new CategoryAdapter(list, getActivity());
            category.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
        }
        else if(((RestaurantActivity)getActivity()).getName().equals("classique")){
            list.add(new CategoryModel(R.raw.breakfast, "Break Fast"));
            list.add(new CategoryModel(R.raw.coffe, "Coffee"));
            list.add(new CategoryModel(R.raw.juice, "Fresh Juices"));
            list.add(new CategoryModel(R.raw.desserts, "Desserts"));
            list.add(new CategoryModel(R.raw.sandwich, "Sandwich"));
            list.add(new CategoryModel(R.raw.pizza, "Pizza"));
            categoryAdapter = new CategoryAdapter(list, getActivity());
            category.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
        }

        else if (((RestaurantActivity)getActivity()).getName().equals("brazilian")){
            list.add(new CategoryModel(R.raw.snacks, "Snacks"));
            list.add(new CategoryModel(R.raw.desserts, "Desserts"));
            list.add(new CategoryModel(R.raw.sandwich, "Sandwich"));
            list.add(new CategoryModel(R.raw.breakfast, "Break Fast"));
            list.add(new CategoryModel(R.raw.coffe, "Coffee"));
            list.add(new CategoryModel(R.raw.juice, "Fresh Juices"));

            categoryAdapter = new CategoryAdapter(list, getActivity());
            category.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
        }
    }

    private void PopularList() {
        popular.setLayoutManager(new GridLayoutManager(getContext(), 2));
        System.out.println(((RestaurantActivity)getActivity()).getName());
        reference = FirebaseDatabase.getInstance().getReference(((RestaurantActivity)getActivity()).getName()).child("items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                popularModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    popularModel = new PopularModel();
                    popularModel.setName(dataSnapshot.child("name").getValue().toString());
                    popularModel.setId(dataSnapshot.child("id").getValue().toString());
                    popularModel.setImage(dataSnapshot.child("image").getValue().toString());
                    popularModel.setPrice(dataSnapshot.child("price").getValue().toString());
                    popularModel.setCategory(dataSnapshot.child("category").getValue().toString());
                    popularModel.setStars(dataSnapshot.child("stars").getValue().toString());
                    popularModel.setDescription(dataSnapshot.child("description").getValue().toString());
                    popularModelArrayList.add(popularModel);
                }
                itemAdapter = new ItemAdapter(popularModelArrayList, getActivity());
                popular.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}