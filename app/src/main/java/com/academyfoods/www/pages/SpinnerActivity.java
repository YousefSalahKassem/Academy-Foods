package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.academyfoods.www.R;
import com.academyfoods.www.model.ResultActivity;
import com.academyfoods.www.model.SpinnerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {
    ImageView spin;
    CardView play;
    String [] sectors={"1","2","3","4","5","6","7","8","9","10","11","12"};
    DatabaseReference reference,reference2;
    ArrayList<SpinnerModel>list=new ArrayList<>();
    SpinnerModel spinnerModel;
    String res=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        getSupportActionBar().hide();

        calling();

        Collections.reverse(Arrays.asList(sectors));

        reference= FirebaseDatabase.getInstance().getReference("spinnerImage");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Picasso.get().load((snapshot.getValue(String.class))).into(spin);
                System.out.println(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        playGame();
    }



    private void calling() {
        spin=findViewById(R.id.spin);
        play=findViewById(R.id.play);
    }

    private void playGame() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                int degree=random.nextInt(360);
                RotateAnimation rotateAnimation=new RotateAnimation(0,degree+720,RotateAnimation.RELATIVE_TO_SELF,0.5F,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setDuration(3000);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());

                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        CalculatePoint(degree);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                spin.startAnimation(rotateAnimation);
            }
        });

    }

    private void CalculatePoint(int degree) {
        int initialPoint=0;
        int endPoint=30;
        int i=0;
        do{
            if(degree>initialPoint && degree<endPoint){
                res=sectors[i];
            }
            initialPoint+=30;
            endPoint+=30;
            i++;
        }while (res==null);
        reference2=FirebaseDatabase.getInstance().getReference("spinner").child("offers");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    spinnerModel=new SpinnerModel();
                    spinnerModel.setName(dataSnapshot.child("name").getValue().toString());
                    spinnerModel.setNumber(dataSnapshot.child("number").getValue().toString());
                    spinnerModel.setOffer(dataSnapshot.child("offer").getValue().toString());
                    spinnerModel.setImage(dataSnapshot.child("image").getValue().toString());

                    list.add(spinnerModel);
                    for(int i=0;i<list.size();i++){
                        if(res.equals(list.get(i).getNumber())){
                            Map<String,Object> map=new HashMap<>();
                            map.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            map.put("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            map.put("offer",list.get(i).getOffer());
                            map.put("number",list.get(i).getNumber());
                            map.put("image",list.get(i).getImage());
                            map.put("name",list.get(i).getName());
                            map.put("take","true");

                            FirebaseDatabase.getInstance().getReference("spinner").child("requested").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);
                            Intent intent=new Intent(SpinnerActivity.this, ResultActivity.class);
                            intent.putExtra("name",list.get(i).getName());
                            intent.putExtra("image",list.get(i).getImage());
                            intent.putExtra("offer",list.get(i).getOffer());
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }
}