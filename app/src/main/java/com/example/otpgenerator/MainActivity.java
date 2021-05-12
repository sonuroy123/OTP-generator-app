package com.example.otpgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    //EditText ccp;
    EditText t1;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        t1=(EditText) findViewById(R.id.t1);
        b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.btnSignUp);
        ccp=(CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerPhoneNumberTextView(t1);
        //ccp=(EditText)findViewById(R.id.ccp);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter Mobile NO",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent=new Intent(MainActivity.this,ManageOtp.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().trim().toString());
                //intent.putExtra("name",ccp.getText().toString());
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,sign_up.class);
                startActivity(intent);
            }
        });
    }
}