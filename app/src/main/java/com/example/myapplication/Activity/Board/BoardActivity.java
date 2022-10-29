package com.example.myapplication.Activity.Board;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

class BoardActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener{

    private MapView mapView;
    private static NaverMap naverMap;
    private Marker marker1,marker2,marker3;;
    Button btn1,btn2,btn3;
    Object[] Object_res;
    String[][][] String_res;
    String[][] sending = {{"marking","박휘건", "남", "기흥역", "09:30", "3점"},
            {"marking","홍길동", "남", "영통역", "08:30", "4점"},
            {"marking","가나다", "여", "명지대역", "10:00", "4.5점"}};
    String[] btn_texts;
    String[][] abc;
    EditText edt;
    ListView listView;
    ArrayList boardArrayList;
    BoardAdapter boardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board2);

        ServerComponent[] servers = new ServerComponent[3];
        Object_res = new Object[3];
        String_res = new String[3][][];
        for(int i=0; i<3; i++){
            servers[i] = new ServerComponent();
            servers[i] = new ServerComponent(servers[i].getServerIp(),sending[i]);
            servers[i].start();
            Object_res[i] = servers[i].getRes();
            String_res[i] = (String[][]) Object_res[i];
        }

        abc = (String[][]) Object_res[0];

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button button_board=(Button)findViewById(R.id.Button_Board);    // 보드로 이동
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                startActivity(intent);
            }
        });

        Button button_chat=(Button)findViewById(R.id.Button_Chat);    // 채팅으로 이동
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.board_listView);

        boardArrayList = new ArrayList<BoardClass>();
        boardArrayList.add(new BoardClass("보라돌이","제목1","내용1"));
        boardArrayList.add(new BoardClass("뚜비","제목2","내용2"));
        boardArrayList.add(new BoardClass("나나","제목3","내용3"));
        boardArrayList.add(new BoardClass("뽀","제목4","내용4"));
        boardArrayList.add(new BoardClass("햇님","제목5","내용5"));

        boardAdapter = new BoardAdapter(BoardActivity.this,boardArrayList);
        listView.setAdapter(boardAdapter);

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
                message = "이름 : " + sending[0][1] + "\n성별 : " + sending[0][2] + "\n목적지 : " + sending[0][3] +
                        "\n출발시간 : " +sending[0][4] + "\n매너점수 : " + sending[0][5];
            } else if (overlay == marker2) {
                message = "이름 : " + sending[1][1] + "\n성별 : " + sending[1][2] + "\n목적지 : " + sending[1][3] +
                        "\n출발시간 : " + sending[1][4] + "\n매너점수 : " + sending[1][5];
            } else if (overlay == marker3) {
                message = "이름 : " +sending[2][1] + "\n성별 : " + sending[2][2] + "\n목적지 : " + sending[2][3] +
                        "\n출발시간 : " + sending[2][4] + "\n매너점수 : " + sending[2][5];
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
