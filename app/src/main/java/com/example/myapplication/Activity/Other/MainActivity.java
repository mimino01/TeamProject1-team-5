package com.example.myapplication.Activity.Other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Activity.Signup.SignupActivity;
import com.example.myapplication.R;


public class MainActivity extends AppCompatActivity {
    TextView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign = findViewById(R.id.signin);

        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }
}