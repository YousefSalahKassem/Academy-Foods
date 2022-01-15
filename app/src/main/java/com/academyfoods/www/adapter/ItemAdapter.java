package com.academyfoods.www.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.PopularModel;
import com.academyfoods.www.pages.DetailsActivity;
import com.academyfoods.www.pages.RestaurantActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.mh> {
    ArrayList<PopularModel> list;
    Context context;

    public ItemAdapter(ArrayList<PopularModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.popular_card,parent,false);
        return new mh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.mh holder, int position) {
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
                intent.putExtra("restaurantName",((RestaurantActivity)context).name);
                intent.putExtra("restaurantImage",((RestaurantActivity)context).images);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class mh extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price;
        public mh(@NonNull View itemView) {
            super(itemView);
         image=itemView.findViewById(R.id.popularImage);
         name=itemView.findViewById(R.id.name);
         price=itemView.findViewById(R.id.price);
        }
    }
}
