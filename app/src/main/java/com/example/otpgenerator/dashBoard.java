package com.example.otpgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class dashBoard extends AppCompatActivity {

    Button btn;
    EditText t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        btn=(Button) findViewById(R.id.Logout);
        t1=(EditText)findViewById(R.id.t1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                t1.setText("");
                finish();

            }
        });
    }
}