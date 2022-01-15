package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.academyfoods.www.R;
import com.academyfoods.www.model.UserInformationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText email, phone, username, password;
    CardView createAccount;
    TextView haveAnAccount;
    FirebaseAuth auth;
    DatabaseReference reference;
    UserInformationModel userInformationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        calling();

        auth = FirebaseAuth.getInstance();

        createAccount();

        HaveAnAccount();
    }

    private void HaveAnAccount() {
        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                overridePendingTransition(R.anim.slideoutleft, R.anim.slideinright);
                startActivity(intent);
            }
        });
    }

    private void calling() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.passowrd);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        createAccount = findViewById(R.id.createAccount);
        haveAnAccount = findViewById(R.id.signIn);
    }

    private void createAccount() {
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            reference = FirebaseDatabase.getInstance().getReference("users").child(auth.getCurrentUser().getUid());
                            userInformationModel = new UserInformationModel(username.getText().toString(), email.getText().toString(), phone.getText().toString(), auth.getCurrentUser().getUid(),"");
                            reference.setValue(userInformationModel);

                            Toast.makeText(SignUpActivity.this, "Welcome " + username.getText().toString(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);

                        } else {
                            Toast.makeText(SignUpActivity.this, "Something wrong , Please try again ! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}