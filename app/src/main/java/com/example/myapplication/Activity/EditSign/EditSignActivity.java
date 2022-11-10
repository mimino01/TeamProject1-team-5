package com.example.myapplication.Activity.EditSign;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

public class EditSignActivity extends AppCompatActivity {
    EditText phone;
    RadioGroup genderGroup;
    Button delete, submit;
    String gender, id;
    String[] req = new String[10];
    ServerComponent server;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sign);

        try {
            Intent getIntent = getIntent();
            id = getIntent.getStringExtra("userid");

            req[0] = "req_userdata";
            req[1] = id;

            server = new ServerComponent(server.getServerIp(),req);
            server.start();

            sleep(100);

            String[][] DresData = (String[][]) server.getRes();
            String[] resData = DresData[0];
            req = new String[]{"signUpdate", resData[0], resData[1], resData[3], resData[4], resData[2]};

            phone = findViewById(R.id.updatePhone);
            genderGroup = findViewById(R.id.radioGroup);
            delete = findViewById(R.id.buttonDelete);
            submit = findViewById(R.id.buttonSubmit);

            genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i){
                        case R.id.radioMan:
                            gender = "man";
                            break;
                        case R.id.radioWoman:
                            gender = "woman";
                            break;
                    }
                }
            });

            submit.setOnClickListener(view -> {
                Log.i(TAG, "InfomationActivity.submit.setOnClickListener - phone data : " + phone.getText().toString() + " : " + phone.getText().toString().length());
                if (11 == phone.getText().toString().length()) {
                    req[1] = phone.getText().toString();
                    req[5] = gender;

                    server = new ServerComponent(server.getServerIp(),req);
                    server.start();
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            delete.setOnClickListener(view -> {

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
