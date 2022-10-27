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
        data[0] = "req_userdata";
        data[1] = "adminid";

        btn.setOnClickListener(view -> {
            server = new ServerComponent(server.getServerIp(),data);
            server.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
            String[][] resData = (String[][]) server.getRes();

            Log.i(TAG, "ServertestActivity.btn.setOnClickListener - server request data: " + Arrays.deepToString(resData));
        });
    }
}
