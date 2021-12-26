package com.example.frychicksorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frychicksorderapp.ui.Models.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText phone, password;
    private Button login;
    FirebaseDatabase mUserDb;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser currentUser;
    private String stPhone, stPassword, phoneNo;
    AppSession session;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        phone = findViewById(R.id.phon);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnlogin);
        initDatabase();
        initSession();
        dialog=new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Processing....");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stPhone = phone.getText().toString();
                stPassword = password.getText().toString();
                if (TextUtils.isEmpty(stPhone)) {
                    phone.setError("Empty Phones");
                    phone.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(stPassword)) {
                    phone.setError("Empty Password");
                    phone.requestFocus();
                    return;
                }
                dialog.show();
                phoneNo = "+92" + stPhone;
                loadSession(v);



            }
        });


    }

    public void initDatabase() {
        FirebaseApp.initializeApp(this);
        mUserDb = FirebaseDatabase.getInstance();
        mRef = mUserDb.getReference("User");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    public void initSession() {
        session = AppSession.getInstance();
        session.getSession(LoginActivity.this);

    }

    public void loadSession(final View v) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mySnap : dataSnapshot.getChildren()) {
                    UserModel userModel = mySnap.getValue(UserModel.class);
                    if ((userModel.getPhon().equals(phoneNo)) && (userModel.getPass().equals(stPassword))) {

                        updateUI(true);
                    } else {
                        dialog.dismiss();
                        Snackbar snackbar = Snackbar.make(v, "Error! Sign Up First", Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("Sign Up", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                            }
                        });
                        snackbar.show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void updateUI(boolean allow) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        dialog.dismiss();
    }

    public void gotoSignup(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

}
