package com.example.frychicksorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frychicksorderapp.ui.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VarifyPhone extends AppCompatActivity {

    private EditText editText;
    private String varificationId;
    private FirebaseAuth mAuth;
   private Button signBtn;
  private DatabaseReference databaseReference;
   private FirebaseDatabase firebaseDatabase;
   private FirebaseUser user;
   private String phoneNumber;
   private String gName;
   private String gPass;
   private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify_phone);
        getSupportActionBar().hide();
        editText=findViewById(R.id.editTextCode);
        mAuth=FirebaseAuth.getInstance();
         phoneNumber = getIntent().getStringExtra("PN");
         gName=getIntent().getStringExtra("UNAME");
         gPass=getIntent().getStringExtra("UPASS");
            dialog=new ProgressDialog(VarifyPhone.this);
            dialog.setMessage("Processing...");
            dialog.show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");


        sendVerifyCode(phoneNumber);
        signBtn = findViewById(R.id.buttonSignIn);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().trim();
                if(code.isEmpty() || code.length()<6)
                {
                    editText.setError("Enter Code!");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    public void verifyCode (String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(varificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential)
    {
    mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                user=mAuth.getCurrentUser();
                UserModel model=new UserModel(gName,gPass,phoneNumber);

                databaseReference.child(user.getUid())
                        .setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent=new Intent(VarifyPhone.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                });

            }else {
                Toast.makeText(VarifyPhone.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    });
    }
    public void sendVerifyCode(String phoneNumber)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                90,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            varificationId =s;
            dialog.dismiss();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        String code = phoneAuthCredential.getSmsCode();
        dialog.dismiss();
        if(code != null)
        {
            editText.setText(code);
            verifyCode(code);
        }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VarifyPhone.this, e.getMessage(), Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    };
}
