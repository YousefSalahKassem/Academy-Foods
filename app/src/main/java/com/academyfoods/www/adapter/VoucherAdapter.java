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
import com.academyfoods.www.model.VoucherModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.mh> {
    ArrayList<VoucherModel>list;
    Context context;

    public VoucherAdapter(ArrayList<VoucherModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public VoucherAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.voucher_card,parent,false);
        return new mh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.mh holder, int position) {
        Picasso.get().load(list.get(position).getImage()).into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.code.setText(list.get(position).getCode());
        holder.type.setText(list.get(position).getType());
        holder.valid.setText("valid until "+list.get(position).getValid());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class mh extends RecyclerView.ViewHolder{
    ImageView image;
    TextView name,type,code,valid;
        public mh(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.restaurantImage);
            name=itemView.findViewById(R.id.name);
            type=itemView.findViewById(R.id.offer);
            code=itemView.findViewById(R.id.code);
            valid=itemView.findViewById(R.id.valid);
        }
    }
}
