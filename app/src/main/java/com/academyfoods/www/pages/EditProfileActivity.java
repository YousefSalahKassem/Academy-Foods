package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.academyfoods.www.R;
import com.academyfoods.www.model.UserInformationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    CardView save;
    EditText email,phone,username;
    UserInformationModel userInformationModel;
    ImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().hide();

        calling();

        GetData();

    }




    private void calling() {
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        username=findViewById(R.id.username);
        save=findViewById(R.id.save);
        userImage=findViewById(R.id.image);
    }

    private void GetData() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInformationModel = dataSnapshot.getValue(UserInformationModel.class);
                if (!userInformationModel.getImage().isEmpty()) {
                    Picasso.get().load(userInformationModel.getImage()).into(userImage);
                    email.setText(userInformationModel.getEmail());
                    username.setText(userInformationModel.getName());
                    phone.setText(userInformationModel.getPhone());


                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String,Object> map=new HashMap<>();
                            map.put("name",username.getText().toString().trim());
                            map.put("phone",phone.getText().toString().trim());
                            map.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            map.put("image",userInformationModel.getImage());
                            map.put("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);
                            startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
                        }
                    });
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}