package com.example.myapplication.Activity.Board;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

import java.sql.Time;
import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener{

    private MapView mapView;
    private static NaverMap naverMap;
    private Marker marker1,marker2,marker3;;
    int [] timeArray;
    Button btn1,btn2,btn3;
    Object[] Object_res;
    String[][][] String_res;
    String[][] sending = {{"marking","박휘건", "남", "기흥역", "09:30", "3점"},
            {"marking","홍길동", "남", "영통역", "08:30", "4점"},
            {"marking","가나다", "여", "명지대역", "10:00", "4.5점"}};
    String[] btn_texts;
    String[][] abc;
    EditText edt;
    ServerComponent[] servers;
    ListView listView;
    //ArrayList boardArrayList;
    BoardAdapter boardAdapter;

    Button New_Sort_button;
    Button Old_Sort_button;
    Button Time_Sort_button;
    Button Des_Sort_button;
    ArrayList<BoardClass> boardArrayList;
    ArrayList<BoardClass> copy_array;
    SearchAdapter searchAdapter;
    EditText editSearch;
    QuickClass quickClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board2);

        servers = new ServerComponent[3];
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

        servers = new ServerComponent[3];
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




        Button button_board = (Button) findViewById(R.id.Button_Board);    // 보드로 이동
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                startActivity(intent);
            }
        });

        Button button_chat = (Button) findViewById(R.id.Button_Chat);    // 채팅으로 이동
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.board_listView);

        boardArrayList = new ArrayList<BoardClass>();
        boardArrayList.add(new BoardClass("보라돌이", "여", "강남역", 930, 3.0));
        boardArrayList.add(new BoardClass("뚜비", "남", "서초역", 830, 4.0));
        boardArrayList.add(new BoardClass("나나", "여", "명지대역", 1000, 4.5));
        boardArrayList.add(new BoardClass("뽀", "남", "명지대입구", 1250, 2.0));
        boardArrayList.add(new BoardClass("햇님", "남", "동진저수지", 1640, 5.0));

        boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
        listView.setAdapter(boardAdapter);

        New_Sort_button=(Button)findViewById(R.id.button_new_sort);     // 최신순 정렬
        New_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardArrayList.clear();
                boardArrayList.add(0,new BoardClass("보라돌이", "여", "강남역", 930, 3.0));
                boardArrayList.add(0,new BoardClass("뚜비", "남", "서초역", 830, 4.0));
                boardArrayList.add(0,new BoardClass("나나", "여", "명지대역", 1000, 4.5));
                boardArrayList.add(0,new BoardClass("뽀", "남", "명지대입구", 1250, 2.0));
                boardArrayList.add(0,new BoardClass("햇님", "남", "동진저수지", 1640, 5.0));
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

        Old_Sort_button=(Button)findViewById(R.id.button_old_sort);     // 오래된 정렬
        Old_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardArrayList.clear();
                boardArrayList.add(new BoardClass("보라돌이", "여", "강남역", 930, 3.0));
                boardArrayList.add(new BoardClass("뚜비", "남", "서초역", 830, 4.0));
                boardArrayList.add(new BoardClass("나나", "여", "명지대역", 1000, 4.5));
                boardArrayList.add(new BoardClass("뽀", "남", "명지대입구", 1250, 2.0));
                boardArrayList.add(new BoardClass("햇님", "남", "동진저수지", 1640, 5.0));
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

        quickClass = new QuickClass();
        timeArray = new int[boardArrayList.size()];
        for(int i=0; i<boardArrayList.size();i++){
            timeArray[i] = (int)boardArrayList.get(i).getTime();
        }

        Time_Sort_button =(Button)findViewById(R.id.button_time_sort);     // 오래된 정렬
        Time_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickClass.sort(timeArray);
                boardArrayList.add(new BoardClass("보라돌이", "여", "강남역", 930, 3.0));
                boardArrayList.add(new BoardClass("뚜비", "남", "서초역", 830, 4.0));
                boardArrayList.add(new BoardClass("나나", "여", "명지대역", 1000, 4.5));
                boardArrayList.add(new BoardClass("뽀", "남", "명지대입구", 1250, 2.0));
                boardArrayList.add(new BoardClass("햇님", "남", "동진저수지", 1640, 5.0));
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

        quickClass = new QuickClass();
        timeArray = new int[boardArrayList.size()];

        for(int i=0; i<boardArrayList.size();i++){
            timeArray[i] = (int)boardArrayList.get(i).getTime();
        }

        Time_Sort_button =(Button)findViewById(R.id.button_time_sort);     // 오래된 정렬
        Time_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickClass.sort(timeArray);
                boardArrayList.add(new BoardClass("보라돌이", "여", "강남역", 930, 3.0));
                boardArrayList.add(new BoardClass("뚜비", "남", "서초역", 830, 4.0));
                boardArrayList.add(new BoardClass("나나", "여", "명지대역", 1000, 4.5));
                boardArrayList.add(new BoardClass("뽀", "남", "명지대입구", 1250, 2.0));
                boardArrayList.add(new BoardClass("햇님", "남", "동진저수지", 1640, 5.0));
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });
        quickClass.sort(timeArray);



        // 검색을 위해서 리스트의 모든 데이터를 copy_array에 복사한다.
        copy_array = new ArrayList<BoardClass>();
        copy_array.addAll(boardArrayList);

        // 리스트에 연동될 아답터를 생성한다.
        searchAdapter = new SearchAdapter(boardArrayList, this);
        listView.setAdapter(searchAdapter);

        editSearch = (EditText) findViewById(R.id.board_searchview);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });
    }


    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        boardArrayList.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            boardArrayList.addAll(copy_array);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < copy_array.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (copy_array.get(i).getDestination().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    boardArrayList.add(copy_array.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        searchAdapter.notifyDataSetChanged();

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
