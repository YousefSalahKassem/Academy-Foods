package com.academyfoods.www.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.academyfoods.www.R;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.mh> {
    ArrayList<String>list;
    Context context;

    public AddressAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.address_card,parent,false);
        return new mh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.mh holder, int position) {
        holder.address.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class mh extends RecyclerView.ViewHolder{
        TextView address;
        public mh(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.address);
        }
    }
}
