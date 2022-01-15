package com.academyfoods.www.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.OrderModel;
import com.academyfoods.www.pages.OrderDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.mh> {
    ArrayList<OrderModel>list;
    Context context;

    public OrderAdapter(ArrayList<OrderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.history_card,parent,false);
        return new mh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.mh holder, int position) {
        holder.phone.setText(list.get(position).getPhone());
        Picasso.get().load(list.get(position).getRestaurantImage()).into(holder.image);
        holder.time.setText(list.get(position).getDate());
        holder.name.setText(list.get(position).getRestaurantName());
        holder.status.setText(list.get(position).getStatus());

        if(list.get(position).getStatus().equals("Preparing")){
            holder.status.setTextColor(Color.GRAY);
        }
        else if (list.get(position).getStatus().equals("In Progress")){
            holder.status.setTextColor(Color.YELLOW);
        }
        else if(list.get(position).getStatus().equals("Canceled")){
            holder.status.setTextColor(Color.RED);
        }
        else {
            holder.status.setTextColor(Color.GREEN);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderDetails.class);
                intent.putExtra("phone",list.get(position).getPhone());
                intent.putExtra("address",list.get(position).getAddress());
                intent.putExtra("total",list.get(position).getTotal());
                intent.putExtra("time",list.get(position).getTime());
                intent.putExtra("date",list.get(position).getDate());
                intent.putExtra("delivery",list.get(position).getDelivery());
                intent.putExtra("discount",list.get(position).getDiscount());
                intent.putExtra("status",list.get(position).getStatus());
                intent.putExtra("image",list.get(position).getRestaurantImage());
                intent.putExtra("name",list.get(position).getRestaurantName());
                intent.putExtra("subtotal",list.get(position).getSubtotal());
                intent.putExtra("cart",list.get(position).getCart());
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class mh extends RecyclerView.ViewHolder{
        TextView name,status,time,phone;
        ImageView image;
        public mh(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.restaurantName);
            status=itemView.findViewById(R.id.status);
            time=itemView.findViewById(R.id.date);
            image=itemView.findViewById(R.id.restaurantImage);
            phone=itemView.findViewById(R.id.phone);
        }
    }
}
