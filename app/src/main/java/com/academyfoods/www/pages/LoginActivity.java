package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    CardView login,google;
    TextView resetPassword, signUp;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN = 123;
    DatabaseReference reference;
    UserInformationModel userInformationModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        Calling();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Login();

        LoginWithGoogle();

        createRequest();

        ResetPassword();

        DoNotHaveAnAccount();

    }


    private void Calling() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.passowrd);
        login = findViewById(R.id.login);
         google = findViewById(R.id.google);
        resetPassword = findViewById(R.id.forgot);
        signUp = findViewById(R.id.signUp);
    }

    private void Login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);

                        } else {
                            Toast.makeText(LoginActivity.this, "Something Wrong , Please try again ! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void LoginWithGoogle() {
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    reference = FirebaseDatabase.getInstance().getReference("users").child(auth.getCurrentUser().getUid());
                    userInformationModel = new UserInformationModel(auth.getCurrentUser().getDisplayName(), auth.getCurrentUser().getEmail(), " ", auth.getCurrentUser().getUid(),auth.getCurrentUser().getPhotoUrl().toString());
                    reference.setValue(userInformationModel);

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);

                } else {
                    Toast.makeText(LoginActivity.this, "please try again !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ResetPassword() {
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
                startActivity(intent);
            }
        });
    }

    private void DoNotHaveAnAccount() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);

            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}

