package com.academyfoods.www.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.academyfoods.www.R;

import java.util.ArrayList;

public class CheckOutAddressAdapter extends RecyclerView.Adapter<CheckOutAddressAdapter.mh> {
    ArrayList<String>list;
    Context context;
    int i=0;
    public CheckOutAddressAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CheckOutAddressAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.checkout_address,parent,false);
        return new mh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutAddressAdapter.mh holder, int position) {
        holder.address.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class mh extends RecyclerView.ViewHolder{
        TextView address;
        ImageView image;
        public mh(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.address);
            image=itemView.findViewById(R.id.correct);
        }
    }
}
