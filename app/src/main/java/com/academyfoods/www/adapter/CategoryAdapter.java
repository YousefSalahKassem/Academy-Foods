package com.academyfoods.www.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.CategoryModel;
import com.academyfoods.www.pages.CategoryActivity;
import com.academyfoods.www.pages.DetailsActivity;
import com.academyfoods.www.pages.ResetPasswordActivity;
import com.academyfoods.www.pages.RestaurantActivity;
import com.academyfoods.www.pages.SeeMoreActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.mh> {
    ArrayList<CategoryModel> list;
    Context context;

    public CategoryAdapter(ArrayList<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.mh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.category_card,parent,false);

        return new mh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.mh holder, int position) {
        holder.categoryImage.setAnimation(list.get(position).getImage());
        holder.categoryName.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CategoryActivity.class);
                intent.putExtra("type",list.get(position).getName());
                intent.putExtra("name",((RestaurantActivity)context).name);
                intent.putExtra("image",((RestaurantActivity)context).images);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class mh extends RecyclerView.ViewHolder{
        LottieAnimationView categoryImage;
        TextView categoryName;
        public mh(@NonNull View itemView) {
            super(itemView);
        categoryImage=itemView.findViewById(R.id.categoryImage);
        categoryName=itemView.findViewById(R.id.categoryName);
        }
    }
}
