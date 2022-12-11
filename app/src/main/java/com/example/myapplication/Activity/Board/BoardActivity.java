package com.example.myapplication.Activity.Board;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener{

    private MapView mapView;
    private static NaverMap naverMap;
    public static Marker[] markers = new Marker[10];
    long [] timeArray;
    long[] copy_timeArray;
    double[] pointArray;
    double[] copy_pointArray;

    String userId;
    String[][] markingData = new String[10][10];
    int marker_length = 0;
    String gender;
    String[][] roomData = null;
    int roomData_length = 0;

    ServerComponent servers;
    ServerComponent roomServer;

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

        // 마킹 데이터 데이터 타입
        // 0 = marking
        // 1 = 이름
        // 2 = 성별
        // 3 = 목적지
        // 4 = 출발 시간
        // 5 = 평점
        // 6 = 위도
        // 7 = 경도

        Intent getIntent = getIntent();
        String destinations = "";
        String times = "";
        destinations = getIntent.getStringExtra("destinations");
        times = getIntent.getStringExtra("times");
        userId = getIntent.getStringExtra("userid");

        String[] reqData = new String[5];
        reqData[0] = "req_userdata";
        reqData[1] = userId;

        // 로그인 통해서 안들어오고 바로 들어오게 해놔서 바꿔놓음
        servers = new ServerComponent(servers.getServerIp(), reqData);
        servers.start();

        String[][] resData = (String[][])servers.getRes();
        Log.i(TAG, "BoardActivity - server data = " + Arrays.toString(resData[0]));

        // 이름과 성별은 resData를 통해 받음 현재는 남자로만 출력됨
        gender = resData[0][2];
        Log.i(TAG, "BoardActivity - gender checker : " + gender);

        String temp_data[];
        temp_data = new String[]{"chat", "loadAllChat"};
        roomServer = new ServerComponent(servers.getServerIp(), temp_data);
        roomServer.start();

        // loadAllChat을 통해 받은 데이터
        roomData = (String[][]) roomServer.getRes();
        Log.i(TAG, "BoardActivity - room data : " + Arrays.deepToString(roomData));

        String[] nulldata = null;
        // 마킹 데이터 값과 서버 데이터 값의 순서가 다름 데이터를 맞는데 넣음
        int mark_counter = 0;
        for(int i=0; roomData[i] != nulldata ;i++){
            Log.i(TAG, "BoardActivity - room data : " + i);
            Log.i(TAG, "BoardActivity - mark_counter : " + mark_counter);
            if (roomData[i][7].equals(gender)) {
                markingData[mark_counter][0] = "marking";
                markingData[mark_counter][1] = roomData[i][0];
                markingData[mark_counter][2] = roomData[i][7];
                markingData[mark_counter][3] = roomData[i][1];
                markingData[mark_counter][4] = roomData[i][4];
                markingData[mark_counter][5] = roomData[i][2];
                markingData[mark_counter][6] = roomData[i][5];
                markingData[mark_counter][7] = roomData[i][6];
                mark_counter++;
            }
            roomData_length++;
            Log.i(TAG, "BoardActivity - markingData : " + Arrays.toString(markingData[mark_counter]));
        }
        // 입력받은 배열의 길이 계산
        while (true) {
            if (markingData[marker_length][0] == null) {
                break;
            }
            marker_length++;
        }

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

        // 1 = 이름
        // 2 = 성별
        // 3 = 도착지
        // 4 = 시간
        // 5 = 평점
        Log.i(TAG, "BoardActivity - loof checker - markingData checking" +
                Arrays.deepToString(markingData));

        boardArrayList.clear();
        for(int i=0; i<mark_counter ;i++) {
            Log.i(TAG, "BoardActivity - loof checker " + i);
                boardArrayList.add(0,new BoardClass(markingData[i][1],markingData[i][2],
                        markingData[i][3], Long.parseLong(markingData[i][4]),
                        Double.parseDouble(markingData[i][5])));
                Log.i(TAG,"BoardActivity - initial boardArrayList vaule = " +
                        Arrays.toString(markingData[i]));
            }

        boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
        listView.setAdapter(boardAdapter);
        Log.i(TAG,"BoardActivity - intial adapter set");

        // 서버로부터 모든 데이터 받아옴 이것을 배열에 저장
        // 저장된 배열로 부터 성별이 같은 데이터들만 종합하여 ~~_sorted_data에 저장
        // ~~_sorted_data의 정보들로 BoardArrayList의 add메소드를 통해 정보만들어줌
        // 4가지 정렬 방법 모두 같은 방법 사용함
        New_Sort_button=(Button)findViewById(R.id.button_new_sort);     // 최신순 정렬
        New_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] Ascending_temp = new String[]{"sort", "AscendingTime"};
                servers = new ServerComponent(servers.getServerIp(), Ascending_temp);
                servers.start();
                String[][] ascending_data = (String[][])servers.getRes();
                String[][] gender_sorted_data = new String[10][10];
                int ascending_counter = 0;

                for (int j=0; j<roomData_length; j++){
                    String temp_gender = ascending_data[j][7];
                    if(temp_gender.equals(gender)) {
                        gender_sorted_data[ascending_counter] = ascending_data[j];
                        ascending_counter++;
                    }
                }
                boardArrayList.clear();

                // 1 = 이름
                // 2 = 성별
                // 3 = 도착지
                // 4 = 시간
                // 5 = 평점
                for(int i=0; i<ascending_counter ;i++) {
                    boardArrayList.add(0,new BoardClass(gender_sorted_data[i][0],
                            gender_sorted_data[i][7],
                            gender_sorted_data[i][1], Long.parseLong(gender_sorted_data[i][3]),
                            Double.parseDouble(gender_sorted_data[i][2])));
                    Log.i(TAG,"BoardActivity - ascending data = "+ Arrays.toString(gender_sorted_data[i]));
                }

                Log.i(TAG,"BoardActivity\n ");
                boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
                listView.setAdapter(boardAdapter);
            }
        });

        Old_Sort_button = (Button) findViewById(R.id.button_old_sort2); // 오래된 순 정렬
        Old_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] Ascending_temp = new String[]{"sort", "DescendingTime"};
                servers = new ServerComponent(servers.getServerIp(), Ascending_temp);
                servers.start();
                String[][] descending_data = (String[][])servers.getRes();
                String[][] gender_sorted_data = new String[10][10];
                int descending_counter = 0;

                for (int j=0; j<roomData_length; j++){
                    String temp_gender = descending_data[j][7];
                    if(temp_gender.equals(gender)) {
                        gender_sorted_data[descending_counter] = descending_data[j];
                        descending_counter++;
                    }
                }
                boardArrayList.clear();

                // 1 = 이름
                // 2 = 성별
                // 3 = 도착지
                // 4 = 시간
                // 5 = 평점
                for(int i=0; i<descending_counter ;i++) {
                    boardArrayList.add(0,new BoardClass(gender_sorted_data[i][0],
                            gender_sorted_data[i][7],gender_sorted_data[i][1],
                            Long.parseLong(gender_sorted_data[i][3]),
                            Double.parseDouble(gender_sorted_data[i][2])));
                    Log.i(TAG,"BoardActivity - descending data = "+ Arrays.toString(gender_sorted_data[i]));

                }
                Log.i(TAG,"BoardActivity\n ");
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

        Time_Sort_button =(Button)findViewById(R.id.button_time_sort);     // 시간 순으로 정렬
        Time_Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickClass.long_sort(copy_timeArray);

                String[] time_temp = new String[]{"sort", "StartingTime"};
                servers = new ServerComponent(servers.getServerIp(), time_temp);
                servers.start();
                String[][] StartingTime_data = (String[][])servers.getRes();
                String[][] time_sorted_data = new String[10][10];
                int time_counter = 0;

                for (int j=0; j<roomData_length; j++){
                    String temp_gender = StartingTime_data[j][7];
                    if(temp_gender.equals(gender)) {
                        time_sorted_data[time_counter] = StartingTime_data[j];
                        time_counter++;
                    }
                }

                boardArrayList.clear();
                for(int i=0; i < copy_timeArray.length ;i++) {
                    for (int j = 0; j < timeArray.length; j++) {
                        if (copy_timeArray[i] == timeArray[j]){
                            boardArrayList.add(0,new BoardClass(time_sorted_data[i][0],
                                    time_sorted_data[i][7],time_sorted_data[i][1],
                                    Long.parseLong(time_sorted_data[i][3]),
                                    Double.parseDouble(time_sorted_data[i][2])));
                            Log.i(TAG,"BoardActivity - time sorted data = " + Arrays.toString(time_sorted_data[i]));
                        }
                    }
                }

                Log.i(TAG,"BoardActivity\n ");
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

        Point_sort_button =(Button)findViewById(R.id.button_point_sort2);     // 매너점수순? 정렬
        Point_sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickClass.double_sort(copy_pointArray);

                String[] point_temp = new String[]{"sort", "Destination"};
                servers = new ServerComponent(servers.getServerIp(), point_temp);
                servers.start();
                String[][] Destinations_data = (String[][])servers.getRes();
                String[][] Destinations_sorted_data = new String[10][10];
                int Destinations_counter = 0;

                for (int j=0; j<roomData_length; j++){
                    String temp_gender = Destinations_data[j][7];
                    if(temp_gender.equals(gender)) {
                        Destinations_sorted_data[Destinations_counter] = Destinations_data[j];
                        Destinations_counter++;
                    }
                }

                boardArrayList.clear();
                for(int i=0; i < Destinations_counter ;i++) {
                    boardArrayList.add(0, new BoardClass(Destinations_sorted_data[i][0],
                            Destinations_sorted_data[i][7], Destinations_sorted_data[i][1],
                            Long.parseLong(Destinations_sorted_data[i][3]),
                            Double.parseDouble(Destinations_sorted_data[i][2])));
                    Log.i(TAG, "BoardActivity - destinations_sorted_data = " +
                            Arrays.toString(Destinations_sorted_data[i]));

                }
                Log.i(TAG,"BoardActivity\n ");
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
        Log.i(TAG, "BoardActivity.onMapReady activate");

        CameraPosition cameraPosition = new CameraPosition(
                // 위도 , 경도 순으로 첫 위치 지정 여기서는 명지대 좌표
                new LatLng(37.22344259294581, 127.18734526333768),
                15 // 줌 레벨

        );
        naverMap.setCameraPosition(cameraPosition);

        // 맵 클릭시 보드로 넘어가면서 위도 경도 넘기면서 데이터 받는 리스너
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
                intent.putExtra("userid",userId);
                intent.putExtra("latitude", String.valueOf(latitude));
                intent.putExtra("logitude", String.valueOf(logitude));
                startActivity(intent);
            }
        });

        double temp_lat=0, temp_log=0;

        // 메소드 통해서 markingData 받아옴
        markingData = get_marking_data();
        String temp_gender = get_gender();
        Log.i(TAG, "BoardActivity.onMapReady.InfinityLoofData : " + Arrays.deepToString(markingData));

        for(int i = 0; i < marker_length; i++) {
//            Log.i(TAG, "BoardActivity.onMapReady.InfinityLoofData");
            Marker temp_marker;
            temp_marker = new Marker();

            temp_lat = Double.parseDouble(markingData[i][6]);
            temp_log = Double.parseDouble(markingData[i][7]);

            temp_marker.setPosition(new LatLng(temp_lat, temp_log));
            temp_marker.setOnClickListener(this);
            temp_marker.setMap(naverMap);

            markers[i] = temp_marker;
        }
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            int save = 0;
            String message = "";
            for (int i = 0; i < markers.length; i++) {
                if (overlay == markers[i]) {
                Log.i(TAG, "BoardActivity - make message");
                message = "이름 : " + markingData[i][1] + "\n성별 : " + genderTranslate(markingData[i][2]) + "\n목적지 : " + markingData[i][3] +
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
                            intent.putExtra("userSex",genderTranslate(markingData[finalSave][2]));
                            intent.putExtra("destination",markingData[finalSave][3]);
                            intent.putExtra("time",markingData[finalSave][4]);
                            intent.putExtra("userid", userId);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
        //Toast.makeText(this.getApplicationContext(), "마커가 선택되었습니다", Toast.LENGTH_LONG).show();
        return true;
    }

    public String[][] get_marking_data(){
        return markingData;
    }

    public String get_gender(){
        return gender;
    }

    public String genderTranslate(String eng) {
        if (eng.equals("man")) {
            return "남";
        } else {
            return "여";
        }
    }
}
