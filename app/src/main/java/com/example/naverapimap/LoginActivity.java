package com.example.naverapimap;

import static android.content.ContentValues.TAG;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TextView  login, logup;
    EditText id, password;
    boolean loginCorrect;
    ServerComponent server;
    String[] data = new String[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
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
                data[0] = "login";
                data[1] = id.getText().toString();
                data[2] = password.getText().toString();
                server = new ServerComponent(server.getServerIp(),data);
                server.start();

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "login: " + server.getRes());
                if ((boolean) server.getRes()) {
                    Toast.makeText(getApplicationContext(), "로그인 성공",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 실패",Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "LoginActivity\n"+e);
        }
    }
}
