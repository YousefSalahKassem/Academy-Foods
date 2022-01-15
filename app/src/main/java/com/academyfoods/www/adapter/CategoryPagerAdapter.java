package com.academyfoods.www.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.PopularModel;
import com.academyfoods.www.pages.CategoryActivity;
import com.academyfoods.www.pages.DetailsActivity;
import com.academyfoods.www.pages.SeeMoreActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryPagerAdapter extends RecyclerView.Adapter<CategoryPagerAdapter.mh> {
ArrayList<PopularModel>list;
Context context;

    public CategoryPagerAdapter(ArrayList<PopularModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryPagerAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.popular_card,parent,false);
        return new mh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPagerAdapter.mh holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice()+ " EGP");
        Picasso.get().load(list.get(position).getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsActivity.class);
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("category",list.get(position).getCategory());
                intent.putExtra("image",list.get(position).getImage());
                intent.putExtra("price",list.get(position).getPrice());
                intent.putExtra("stars",list.get(position).getStars());
                intent.putExtra("description",list.get(position).getDescription());
                intent.putExtra("restaurantName",((CategoryActivity)context).name);
                intent.putExtra("restaurantImage",((CategoryActivity)context).image);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class mh extends RecyclerView.ViewHolder{
        TextView name,price;
        ImageView image;
        public mh(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.popularImage);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
}
