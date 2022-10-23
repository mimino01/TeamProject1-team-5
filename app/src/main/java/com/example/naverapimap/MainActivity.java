package com.example.naverapimap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.naverapimap.databinding.ActivityMainBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    TextView sign;
    private Marker marker1;
    private Marker marker2;
    private Marker marker3;
    private Marker marker4;
    private MapView mapView;
    private static NaverMap MyNaverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        sign = findViewById(R.id.signin);

        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.MyNaverMap = naverMap;

        CameraPosition cameraPosition = new CameraPosition(
                // 위도 , 경도 순으로 첫 위치 지정 여기서는 명지대 좌표
                new LatLng(37.22344259294581,  127.18734526333768),
                15 // 줌 레벨

        );
        naverMap.setCameraPosition(cameraPosition);

        marker1 = new Marker();
        marker1.setPosition(new LatLng(37.22344259294581,127.18734526333768));
        marker1.setOnClickListener(this);
        marker1.setMap(naverMap);

        marker2 = new Marker();
        marker2.setPosition(new LatLng( 37.224755790256964,127.18881331477333));
        marker2.setOnClickListener(this);
        marker2.setMap(naverMap);

        marker3 = new Marker();
        marker3.setPosition(new LatLng(37.22219444666843,127.19029421815819 ));
        marker3.setOnClickListener(this);
        marker3.setMap(naverMap);
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            new AlertDialog.Builder(this)
                    .setTitle("알림 팝업")
                    .setMessage("이름 : \n성별 : \n목적지 : \n출발시간 : \n매너점수: \n")
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
            //Toast.makeText(this.getApplicationContext(), "마커가 선택되었습니다", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}