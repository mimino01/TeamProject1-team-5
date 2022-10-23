package com.example.temp_naverapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.temp_naverapi.databinding.ActivityMainBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener {

    private MapView mapView;
    private static NaverMap naverMap;
    private Marker marker1;
    private String[] marker1_string = {"박휘건", "남", "기흥역", "09:30", "3점"};
    private Marker marker2;
    private String[] marker2_string = {"홍길동", "남", "영통역", "08:30", "4점"};
    private Marker marker3;
    private String[] marker3_string = {"가나다", "여", "명지대역", "10:00", "4.5점"};
    private Marker marker4;
    TextView text1;
    Button btn1;
    Button btn2;
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);*/


        //네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        CameraPosition cameraPosition = new CameraPosition(
                // 위도 , 경도 순으로 첫 위치 지정 여기서는 명지대 좌표
                new LatLng(37.22344259294581, 127.18734526333768),
                15 // 줌 레벨

        );
        naverMap.setCameraPosition(cameraPosition);

        marker1 = new Marker();
        marker1.setPosition(new LatLng(37.22344259294581, 127.18734526333768));
        marker1.setOnClickListener(this);
        marker1.setMap(naverMap);

        marker2 = new Marker();
        marker2.setPosition(new LatLng(37.224755790256964, 127.18881331477333));
        marker2.setOnClickListener(this);
        marker2.setMap(naverMap);

        marker3 = new Marker();
        marker3.setPosition(new LatLng(37.22219444666843, 127.19029421815819));
        marker3.setOnClickListener(this);
        marker3.setMap(naverMap);
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            String message = "";
            if (overlay == marker1) {
                message = "이름 : " + marker1_string[0] + "\n성별 : " + marker1_string[1] + "\n목적지 : " + marker1_string[2] +
                        "\n출발시간 : " + marker1_string[3] + "\n매너점수 : " + marker1_string[4];
            } else if (overlay == marker2) {
                message = "이름 : " + marker2_string[0] + "\n성별 : " + marker2_string[1] + "\n목적지 : " + marker2_string[2] +
                        "\n출발시간 : " + marker2_string[3] + "\n매너점수 : " + marker2_string[4];
            } else if (overlay == marker3) {
                message = "이름 : " + marker3_string[0] + "\n성별 : " + marker3_string[1] + "\n목적지 : " + marker3_string[2] +
                        "\n출발시간 : " + marker3_string[3] + "\n매너점수 : " + marker3_string[4];
            }
            new AlertDialog.Builder(this)
                    .setTitle("마커 정보")
                    .setMessage(message)
                    .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNeutralButton(" chat", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
        //Toast.makeText(this.getApplicationContext(), "마커가 선택되었습니다", Toast.LENGTH_LONG).show();
        return true;
    }
}