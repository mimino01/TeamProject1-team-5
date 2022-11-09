package com.example.myapplication.Activity.Other;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;
import com.naver.maps.geometry.LatLng;

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

                double 위도 = 37.22219444666843;
                double 경도 = 127.19029421815819;
                String 이름 = "admin";
                int 출발시간 = 1030; //10시 30분
                String 도착지 = "명지대역";
                int 방생성시간 = 102030;
                data = new String[10]; //10시 20분 30초
                data[0] = "chat"; //프로토콜입니다
                data[1] = "create"; //프로토콜입니다
                data[2] = 이름; //방 생성자 이름 입니다
                data[3] = Double.toString(위도); //방생성자 위치기준 위도 입니다
                data[4] = Double.toString(경도); //방생성자 위치 기준 경도 입니다
                data[5] = Integer.toString(출발시간);
                data[6] = 도착지;
                data[7] = Integer.toString(방생성시간);
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
