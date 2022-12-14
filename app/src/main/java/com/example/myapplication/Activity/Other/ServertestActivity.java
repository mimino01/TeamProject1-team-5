package com.example.myapplication.Activity.Other;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.io.IOException;
import java.util.Arrays;

public class ServertestActivity extends AppCompatActivity {
    ServerComponent server = new ServerComponent();
    Button btn;
    TextView tv;
    String[] data = new String[5];


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_test);

        btn = findViewById(R.id.changeButton);
        tv = findViewById(R.id.textView2);

        btn.setOnClickListener(view -> {
            try {

                data = new String[5];
                data[0] = "review";
                data[1] = "addReview";
                data[2] = "adminid";
                data[3] = "3.5";
                data[4] = "리뷰 예제 입니다";
                server = new ServerComponent(server.getServerIp(),data);
                server.start();

                Thread.sleep(1000);

                String[][] resData = (String[][]) server.getRes();
                Log.i(TAG, "ServertestActivity.btn.setOnClickListener - add review : " + Arrays.deepToString(resData));



//                data = new String[5];
//                data[0] = "req_userdata";
//                data[1] = "adminid";
//                server = new ServerComponent(server.getServerIp(),data);
//                server.start();
//
//                Thread.sleep(1000);
//
//                String[][] resData = (String[][]) server.getRes();
//                Log.i(TAG, "ServertestActivity.btn.setOnClickListener - server request data: " + Arrays.deepToString(resData));
//
//                data = new String[5];
//                data[0] = "chat";
//                data[1] = "create";
//                data[2] = "adminid";
//                server = new ServerComponent(server.getServerIp(),data);
//                server.start();
//
//                Thread.sleep(1000);
//
//                resData = (String[][]) server.getRes();
//                Log.i(TAG, "ServertestActivity.btn.setOnClickListener - chatting room create: " + Arrays.deepToString(resData));
//
//                data = new String[5];
//                data[0] = "chat";
//                data[1] = "addChat";
//                data[2] = "adminid";
//                data[3] = "안녕하세요 채팅 데이터 추가 입니다";
//                server = new ServerComponent(server.getServerIp(),data);
//                server.start();
//
//                Thread.sleep(1000);
//
//                resData = (String[][]) server.getRes();
//                Log.i(TAG, "ServertestActivity.btn.setOnClickListener - add chat: " + Arrays.deepToString(resData));
//
//                data = new String[5];
//                data[0] = "chat";
//                data[1] = "loadChat";
//                data[2] = "adminid";
//                server = new ServerComponent(server.getServerIp(),data);
//                server.start();
//
//                Thread.sleep(1000);
//
//                resData = (String[][]) server.getRes();
//                Log.i(TAG, "ServertestActivity.btn.setOnClickListener - load chat: " + Arrays.deepToString(resData));

            } catch (InterruptedException e) {

            }
        });
    }
}
