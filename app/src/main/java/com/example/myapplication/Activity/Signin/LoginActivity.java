package com.example.myapplication.Activity.Signin;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.Board.BoardActivity;
import com.example.myapplication.Activity.EditSign.EditSignActivity;
import com.example.myapplication.Activity.Infomation.InfomationActivity;
import com.example.myapplication.Activity.Other.ChatTestingActivity;
import com.example.myapplication.Activity.Other.ServertestActivity;
import com.example.myapplication.Activity.Signup.SignupActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.util.Arrays;

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

                //로그인 구현 완료
                data[0] = "signin";
                data[1] = id.getText().toString();
                data[2] = password.getText().toString();
                server = new ServerComponent(server.getServerIp(),data);
                server.start();

                String[][] temp = (String[][]) server.getRes();
                Log.i(TAG, "login data : " + temp[0][0]);
                if (temp[0][0].equals("null")) {
                    Toast.makeText(getApplicationContext(), "서버가 연결되지 않았습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Log.i(TAG, "login: " + temp[0][0]);
                    if (temp[0][0].equals("true")) {
                        Toast.makeText(getApplicationContext(), "로그인 성공",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, BoardActivity.class);
                        intent.putExtra("userid", data[1]);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패",Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "LoginActivity\n"+e);
        }
    }
}
