package com.academyfoods.www.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.PopularModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.mh> {
    ArrayList<PopularModel>list;
    Context context;

    public CartAdapter(ArrayList<PopularModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cart_card,parent,false);
        return new mh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.mh holder, int position) {
        holder.card.setBackgroundResource(R.drawable.cardviewcornerrr);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice() + " EGP");
        holder.quantity.setText(String.valueOf(list.get(position).getQuantity()));
        Picasso.get().load(list.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class mh extends RecyclerView.ViewHolder{
        TextView name,price,quantity;
        ImageView image,plus,minus;
        CardView card;
        public mh(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
            image=itemView.findViewById(R.id.image);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);
            card=itemView.findViewById(R.id.plusMin);
        }
    }
}
