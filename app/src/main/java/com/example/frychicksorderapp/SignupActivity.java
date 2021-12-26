package com.example.frychicksorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    public SharedPreferences preferences;
    private EditText name,password,phone;
    private Button register;
    private FirebaseAuth mAuth;
    AppSession appSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        name=findViewById(R.id.reg_name);
        password=findViewById(R.id.reg_password);
        phone=findViewById(R.id.reg_phon);
        register=findViewById(R.id.btn_register);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        appSession=AppSession.getInstance();
        appSession.getSession(this);


        register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uName = name.getText().toString();
                    String uPass = password.getText().toString();
                    String uPhone = phone.getText().toString();
                    if(TextUtils.isEmpty(uName))
                    {
                        name.setError("Empty Field!");
                        name.requestFocus();
                        return;
                    } if(TextUtils.isEmpty(uPass))
                    {
                        name.setError("Empty Field!");
                        name.requestFocus();
                        return;
                    } if(TextUtils.isEmpty(uPhone))
                    {
                        name.setError("Empty Field!");
                        name.requestFocus();
                        return;
                    }

                    String code="92";
                    String phoneNumber = "+" + code + uPhone;

                    Intent intent = new Intent(SignupActivity.this, VarifyPhone.class);
                    intent.putExtra("PN", phoneNumber);
                    intent.putExtra("UNAME",uName);
                    intent.putExtra("UPASS",uPass);
                    startActivity(intent);


                }
            }
        );


    }
}
