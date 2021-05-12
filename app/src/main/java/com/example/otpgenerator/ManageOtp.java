package com.example.otpgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ManageOtp extends AppCompatActivity {

    EditText t2;
    TextView t3;
    Button b2;
    String phNo;
    FirebaseAuth mAuth;
    String VerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);

        t2=(EditText) findViewById(R.id.t2);
        b2=(Button) findViewById(R.id.b2);
        t3=(TextView) findViewById(R.id.etNumber);

        phNo= getIntent().getStringExtra("mobile").toString();

        mAuth = FirebaseAuth.getInstance();
        t3.setText(phNo);

        initiateOtp();

        getSupportActionBar().setTitle("OTP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(t2.getText().toString().isEmpty()){
                   Toast.makeText(getApplicationContext(),"Empty field can not be processed",Toast.LENGTH_LONG).show();
               }
               else{
                   PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VerificationId,t2.getText().toString());
                   signInWithPhoneAuthCredential(credential);

               }
           }
       });

    }
    private void initiateOtp(){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                VerificationId=s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(ManageOtp.this,dashBoard.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(ManageOtp.this,"Otp did't match Verify",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}