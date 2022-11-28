package com.example.myapplication.Activity.Board;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
<<<<<<< HEAD
import android.icu.text.IDNA;
=======
import android.graphics.PointF;
>>>>>>> origin/new_version
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.Activity.Infomation.InfomationActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener{

    private MapView mapView;
    private static NaverMap naverMap;
    private Marker marker1,marker2,marker3;
    public static Marker[] markers = new Marker[10];
    long [] timeArray;
    long[] copy_timeArray;
    double[] pointArray;
    double[] copy_pointArray;
    String userId;

    Object[] Object_res;
    String[][][] String_res;

    String[][] markingData = new String[10][10];
    String[][] abc;

    ServerComponent servers;

    ListView listView;
    BoardAdapter boardAdapter;

    Button New_Sort_button;
    Button Old_Sort_button;
    Button Time_Sort_button;
    Button Point_sort_button;
    ArrayList<BoardClass> boardArrayList;
    ArrayList<BoardClass> copy_array;
    SearchAdapter searchAdapter;
    EditText editSearch;
    QuickClass quickClass;
    double latitude, logitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board2);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        markingData[0] = new String[]{"marking", "박휘건", "남", "기흥역", "0930", "5"};
        markingData[1] = new String[]{"marking", "홍길동", "남", "영통역", "0830", "4"};
        markingData[2] = new String[]{"marking", "가나다", "여", "명지대역", "1000", "4.5"};

        Intent getIntent = getIntent();
        String destinations = "";
        destinations = getIntent.getStringExtra("destinations");
        String times = "";
        times = getIntent.getStringExtra("times");
        userId = getIntent.getStringExtra("userid");
        Log.i(TAG, "BoardActivity - after make marker: " + destinations + " : " + times + " : " + userId);
        if (destinations != null && times != null) {
            Log.i(TAG, "BoardActivity - making marker");


            String[] reqData = new String[5];
            reqData[0] = "req_userdata";
            reqData[1] = userId;

            servers = new ServerComponent(servers.getServerIp(),reqData);
            servers.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[][] resData = (String[][]) servers.getRes();
            Log.i(TAG, "BoardActivity - test server data : " + Arrays.deepToString(resData));
            String name = resData[0][0];
            String gender = resData[0][2];
            if (gender.equals("man")) gender = "남";
            else gender = "여";
            markingData[3] = new String[]{"marking", name, gender, destinations, times, "0"};
        }

        Object_res = new Object[3];
        String_res = new String[3][][];
//        for(int i=0; i<3; i++){
//            servers = new ServerComponent();
//            servers = new ServerComponent(servers.getServerIp(),markingData[i]);
//            servers.start();
//            Object_res[i] = servers.getRes();
//            String_res[i] = (String[][]) Object_res[i];
//        }

        abc = (String[][]) Object_res[0];

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button_main = (Button) findViewById(R.id.Button_Main);    // 보드로 이동버튼
        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = getIntent();
                Intent intent = new Intent(getApplicationContext(), InfomationActivity.class);
                intent.putExtra("userid",userId = getIntent.getStringExtra("userid"));
                startActivity(intent);
            }
        });

        Button button_board = (Button) findViewById(R.id.Button_Board);    // 보드로 이동버튼
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = getIntent();
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                intent.putExtra("userid",userId = getIntent.getStringExtra("userid"));
                startActivity(intent);
            }
        });

        Button button_chat = (Button) findViewById(R.id.Button_Chat);    // 채팅으로 이동버튼
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.board_listView);

        boardArrayList = new ArrayList<BoardClass>();

        Log.i(TAG, "BoardActivity - loof checker - markingData checking" + Arrays.deepToString(markingData));
        for(int i=0; markingData[i][0] != null ;i++) {
            Log.i(TAG, "BoardActivity - loof checker" + i);
                boardArrayList.add(new BoardClass(markingData[i][1], markingData[i][2], markingData[i][3], Long.parseLong(markingData[i][4]), Double.parseDouble(markingData[i][5])));
            }
        boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
        listView.setAdapter(boardAdapter);

        New_Sort_button=(Button)findViewById(R.id.button_new_sort);     // 최신순 정렬
        New_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardArrayList.clear();
                for(int i=0; markingData[i][0] != null ;i++) {
                    boardArrayList.add(0,new BoardClass(markingData[i][1], markingData[i][2], markingData[i][3], Long.parseLong(markingData[i][4]), Double.parseDouble(markingData[i][5])));
                }
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

        Old_Sort_button = (Button) findViewById(R.id.button_old_sort2);
        Old_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardArrayList.clear();
                for(int i=0; markingData[i][0] != null ;i++) {
                    boardArrayList.add(new BoardClass(markingData[i][1], markingData[i][2], markingData[i][3], Long.parseLong(markingData[i][4]), Double.parseDouble(markingData[i][5])));
                }
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

        quickClass = new QuickClass();
        timeArray = new long[boardArrayList.size()];
        for(int i=0; i<boardArrayList.size();i++){
            timeArray[i] = (long)boardArrayList.get(i).getTime();
        }

        copy_timeArray = new long[boardArrayList.size()];
        for(int i=0; i<boardArrayList.size();i++){
            copy_timeArray[i] = (long)boardArrayList.get(i).getTime();
        }

        Time_Sort_button =(Button)findViewById(R.id.button_time_sort);     // 오래된 정렬
        Time_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickClass.long_sort(copy_timeArray);
                boardArrayList.clear();
                for(int i=0; i < copy_timeArray.length ;i++) {
                    for (int j = 0; j < timeArray.length; j++) {
                        if (copy_timeArray[i] == timeArray[j]){
                            boardArrayList.add(new BoardClass(markingData[j][1], markingData[j][2], markingData[j][3], Long.parseLong(markingData[j][4]), Double.parseDouble(markingData[j][5])));
                        }
                    }
                }
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

        pointArray = new double[boardArrayList.size()];
        for(int i=0; i<boardArrayList.size();i++){
            pointArray[i] = (double)boardArrayList.get(i).getManner_point();
        }

        copy_pointArray = new double[boardArrayList.size()];
        for(int i=0; i<boardArrayList.size();i++){
            copy_pointArray[i] = (double) boardArrayList.get(i).getManner_point();
        }

        Point_sort_button =(Button)findViewById(R.id.button_point_sort2);     // 오래된 정렬
        Point_sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickClass.double_sort(copy_pointArray);
                boardArrayList.clear();
                for(int i=0; i < copy_pointArray.length ;i++) {
                    for (int j = 0; j < pointArray.length ; j++) {
                        if (copy_pointArray[i] == pointArray[j]){
                            boardArrayList.add(new BoardClass(markingData[j][1], markingData[j][2], markingData[j][3], Long.parseLong(markingData[j][4]), Double.parseDouble(markingData[j][5])));
                        }
                    }
                }
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

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

        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                latitude = latLng.latitude;
                logitude = latLng.longitude;
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, logitude));
                naverMap.moveCamera(cameraUpdate);

                Log.d(TAG,"BoardActivity latitude "+latitude);
                Log.d(TAG,"BoardActivity logitude "+logitude);

                Intent getIntent = getIntent();
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                intent.putExtra("userid",userId = getIntent.getStringExtra("userid"));
                intent.putExtra("latitude", String.valueOf(latitude));
                intent.putExtra("logitude", String.valueOf(logitude));
                startActivity(intent);
            }
        });

        Log.i(TAG, "BoardActivity - mark maker");

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

        markers[0] = marker1;
        markers[1] = marker2;
        markers[2] = marker3;

        Log.i(TAG, "BoardActivity - add markers : " + markers.toString());
        if (markingData[3][0] != null) {
            Log.i(TAG, "BoardActivity - add board checker");
            Object_res[3] = servers.getRes();
            markers[3] = new Marker();
            markers[3].setPosition(new LatLng(latitude, logitude));
            markers[3].setOnClickListener(this);
            markers[3].setMap(naverMap);
        }
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            int save = 0;
            String message = "";
            for (int i = 0; i < markers.length; i++) {
                if (overlay == markers[i]) {
                Log.i(TAG, "BoardActivity - make board");
                message = "이름 : " + markingData[i][1] + "\n성별 : " + markingData[i][2] + "\n목적지 : " + markingData[i][3] +
                        "\n출발시간 : " +markingData[i][4] + "\n매너점수 : " + markingData[i][5];
                save = i;
                }
            }

            int finalSave = save;
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
                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                            intent.putExtra("userName",markingData[finalSave][1]);
                            intent.putExtra("userSex",markingData[finalSave][2]);
                            intent.putExtra("destination",markingData[finalSave][3]);
                            intent.putExtra("userid", "adminid");
                            intent.putExtra("roomCode", "0");
                            intent.putExtra("createOrJoin", "create");
                            startActivity(intent);
                        }
                    })
                    .show();
        }
        //Toast.makeText(this.getApplicationContext(), "마커가 선택되었습니다", Toast.LENGTH_LONG).show();
        return true;
    }
}
