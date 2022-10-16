package com.example.myapplication.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    TextView id, password, login, logup;
    boolean loginCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.editID);
        password = findViewById(R.id.editPassword);
        login = findViewById(R.id.signin);
        logup = findViewById(R.id.signup);

        //임시데이터
         loginCorrect = true;

        logup.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            if (loginCorrect) {
                //임시로 로딩화면 이동으로 함 추후 메인화면 구현시 변경요망
                Intent intent = new Intent(this, LodingActivity.class);
                startActivity(intent);
            }
        });
    }
}
