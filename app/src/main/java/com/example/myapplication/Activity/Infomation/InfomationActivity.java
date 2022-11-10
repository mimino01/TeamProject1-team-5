package com.example.myapplication.Activity.Infomation;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.util.Arrays;

public class InfomationActivity extends AppCompatActivity {
    TextView name, id, phone, gender, rank;
    Button submit;
    String[] req = new String[10];
    String intentId;
    ServerComponent server;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);

        name = findViewById(R.id.nameTextView);
        id = findViewById(R.id.idTextView);
        phone = findViewById(R.id.phoneTextView);
        gender = findViewById(R.id.genderTextView);
        rank = findViewById(R.id.rankTextView);
        submit = findViewById(R.id.buttonSubmit);

        Intent getIntent = getIntent();
        intentId = getIntent.getStringExtra("userid");

        req[0] = "req_userdata";
        req[1] = intentId;

        server = new ServerComponent(server.getServerIp(),req);
        server.start();

        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[][] resData = (String[][]) server.getRes();
        Log.i(TAG, "InfomationActivity.onCreate - check response data : " + Arrays.toString(resData[0]));
        name.setText(name.getText().toString() + resData[0][0]);
        id.setText(id.getText().toString() + resData[0][3]);
        phone.setText(phone.getText().toString() + "0" + resData[0][1]);
        if (resData[0][2].equals("man")) {
            gender.setText(gender.getText().toString() + "남");
        } else {
            gender.setText(gender.getText().toString() + "여");
        }
    }
}
