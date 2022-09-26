package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign = findViewById(R.id.signin);

        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        });
    }
}