package com.example.myapplication.Activity.Infomation;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.Board.BoardActivity;
import com.example.myapplication.Activity.EditSign.EditSignActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.util.Arrays;

public class InfomationActivity extends AppCompatActivity {
    TextView name, id, phone, gender, rank;
    Button changePhone, changeGender, back;
    String[] req = new String[10];
    String intentId;
    ServerComponent server;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);

        name = findViewById(R.id.nameTextView);
        id = findViewById(R.id.idTextView);
        phone = findViewById(R.id.phoneTextView);
        gender = findViewById(R.id.genderTextView);
        rank = findViewById(R.id.rankTextView);
        changePhone = findViewById(R.id.buttonPhoneChange);
        changeGender = findViewById(R.id.buttonChangeGender);
        back = findViewById(R.id.buttonReturn);

        Intent getIntent = getIntent();
        intentId = getIntent.getStringExtra("userid");

        req[0] = "req_userdata";
        req[1] = intentId;

        server = new ServerComponent(server.getServerIp(),req);
        server.start();

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

        changeGender.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditSignActivity.class);
            intent.putExtra("userid", intentId);
            intent.putExtra("type","gender");
            startActivity(intent);
        });

        changePhone.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditSignActivity.class);
            intent.putExtra("userid", intentId);
            intent.putExtra("type","phone");
            startActivity(intent);
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, BoardActivity.class);
            intent.putExtra("userid", intentId);
            startActivity(intent);
        });
    }
}
