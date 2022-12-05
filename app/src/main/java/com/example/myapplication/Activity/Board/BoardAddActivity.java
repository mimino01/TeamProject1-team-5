package com.example.myapplication.Activity.Board;

import static android.content.ContentValues.TAG;

import static com.example.myapplication.server.ServerComponent.getServerIp;
import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.Activity.TempBoard.TempBoardActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;


import java.util.List;

public class BoardAddActivity extends AppCompatActivity implements OnMapReadyCallback {
    EditText destination_add;
    EditText time_add;
    Button add_submit;
    ServerComponent server;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_add);

        try {

            Button button_main = (Button) findViewById(R.id.Button_Main);    // 메인으로 이동
            button_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                    startActivity(intent);
                }
            });

            Button button_chat = (Button) findViewById(R.id.Button_Chat);  // chat으로 이동
            button_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    startActivity(intent);
                }
            });

            destination_add = (EditText) findViewById(R.id.board_destination);
            time_add = (EditText) findViewById(R.id.board_time);
            add_submit = (Button) findViewById(R.id.R_Button_submit);

            data = new String[10];

            Intent getIntent = getIntent();
            Intent intent = new Intent(getApplicationContext(), BoardActivity.class);

            data[0] = "chat";
            data[1] = "create";
            data[2] = getIntent.getStringExtra("userid");
            data[3] = getIntent.getStringExtra("latitude");
            data[4] = getIntent.getStringExtra("logitude");

            add_submit.setOnClickListener(view -> {

                data[5] = time_add.getText().toString();
                data[6] = destination_add.getText().toString();
                data[7] = "1130";

                server = new ServerComponent(server.getServerIp(), data);
                Log.d(TAG, "returnToMain data = " + data[0] + " " + data[1] +
                        " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5]
                        + " " +data[6] + " " + data[7] );
                server.start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                intent.putExtra("userid", data[2]);
                intent.putExtra("Mark","mark");
                intent.putExtra("destinations", data[6]);
                intent.putExtra("times", data[5]);
                intent.putExtra("ToBoardlat", data[3]);
                intent.putExtra("ToBoardlog", data[4]);

                startActivity(intent);
                Log.i(TAG, "returnToMain to main page");
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "BoardAddActivity\n" + e);
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

    }
}