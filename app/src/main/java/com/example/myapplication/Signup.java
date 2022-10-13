package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    TextView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sign = findViewById(R.id.signin);

        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        });
    }
}