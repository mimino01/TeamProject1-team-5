package com.example.myapplication.Activity.Loading;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.Other.ServertestActivity;
import com.example.myapplication.Activity.Signin.LoginActivity;
import com.example.myapplication.R;


public class LoadingActivity extends AppCompatActivity {
    TextView sign;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        sign = findViewById(R.id.nextButton);

        sign.setOnClickListener(view -> {
            Log.i(TAG, "LoadingActivity - sign on click listener");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
