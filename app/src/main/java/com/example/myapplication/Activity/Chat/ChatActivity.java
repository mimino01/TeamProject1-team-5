package com.example.myapplication.Activity.Chat;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.Board.BoardActivity;
import com.example.myapplication.Activity.Board.BoardAddActivity;
import com.example.myapplication.Activity.Review.ReviewActivity;
import com.example.myapplication.Activity.Signin.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager1;
    private ChatAdapter_type chatAdapterMarge = new ChatAdapter_type(); // 합병 어댑터
    private ArrayList<ChatClass> datalist = new ArrayList<>();
    private final Handler handler = new Handler();
    int lastPos = 0;
    int pos = 0;
    String[] userList = new String[]{"","","",""};
    String userid = null, createOrJoin, hostName;
    String[] sendedData = new String[5];
    String[][] chattingData;

    //레이아웃 연결
    Button chatserv;
    EditText searchText;
    Button searchButton;
    EditText message;
    Button send;
    RecyclerView recyclerView_L;
    TextView Info;

    //서버 연결
    ServerComponent server = new ServerComponent();
    ChatClass data1;
    ChatClass joinData;
    TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        refreshStarter();

        Intent getIntent = getIntent();
        userid = getIntent.getStringExtra("userid");

        Log.i(TAG, "ChatActivity - get intent data checker : " + getIntent.getStringExtra("createOrJoin") + getIntent.getStringExtra("userid") + getIntent.getStringExtra("roomCode"));
        createOrJoin = getIntent.getStringExtra("createOrJoin");
                // 상단에 '목적지 | 시간 ' 표시
        Info = (TextView) findViewById(R.id.TextView_info);
        Info.setText(getIntent.getStringExtra("userName") + " | " + getIntent.getStringExtra("destination") + " | " + getIntent.getStringExtra("time"));

        recyclerView_L = (RecyclerView) findViewById(R.id.chatting_Left);
        recyclerView_L.setHasFixedSize(true);

        linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView_L.setLayoutManager(linearLayoutManager1);

        hostName = getIntent.getStringExtra("userName");
        Button button_main = (Button) findViewById(R.id.Button_Main);    // 메인으로 이동
        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                refreshEnder();
                startActivity(intent);
            }
        });

        Button button_board = (Button) findViewById(R.id.Button_Board);    // 보드로 이동
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                refreshEnder();
                startActivity(intent);
            }
        });

        Button button_back = (Button) findViewById(R.id.Button_back);  // 뒤로가기
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                intent.putExtra("userid",userid);
                refreshEnder();
                startActivity(intent);
            }
        });

        Button button_off = (Button) findViewById(R.id.Button_off);
        button_off.setOnClickListener(new View.OnClickListener() {      // 리뷰쓰러 가기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("userid", userid);
                intent.putExtra("userName", getIntent.getStringExtra("userName"));
                intent.putExtra("destination", getIntent.getStringExtra("destination"));
                intent.putExtra("time", getIntent.getStringExtra("time"));
                refreshEnder();
                startActivity(intent);
            }
        });

        // 레이아웃과 연결
//        chatserv = findViewById(R.id.Button_chatserv);
        searchText = (EditText) findViewById(R.id.SearchText);
        searchButton = (Button) findViewById(R.id.SearchButton);
        message = (EditText) findViewById(R.id.EditText_chat);
        send = (Button) findViewById(R.id.Button_send);

        searchButton.setOnClickListener(new View.OnClickListener() {    // 검색 버튼 클릭
            @Override
            public void onClick(View view) {

                String S_text = searchText.getText().toString();    // 검색 텍스트값 변환

                for(int i=0; chattingData[i][0] != null ; i++){
                    if(chattingData[i][2].equals(S_text)){

                        Toast.makeText(getApplicationContext(),"검색 성공",Toast.LENGTH_SHORT).show();
                        linearLayoutManager1.scrollToPositionWithOffset(Integer.valueOf(chattingData[i][3])+1,100);
                        break;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), chattingData[i][3] + "번째 값 검색 실패",Toast.LENGTH_SHORT).show();
                    }
                }
//                ChatSearch(S_text);                                 // 리사이클러뷰에서 검색텍스트를 탐색하여 해당 위치로 스크롤하는 것까지 실행하는 함수
            }
        });

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String text1 = message.getText().toString();
                sendedData[0] = "chat";
                sendedData[1] = "addChat";
                sendedData[2] = userid;
                sendedData[3] = text1;
                sendedData[4] = "right";    // 임시 왼/오 확인

                server = new ServerComponent(server.getServerIp(), sendedData);
                server.start();
            }
        });
    }

    private String Time() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String Time = dateFormat.format(date);

        return Time;
    }

    public void refreshStarter() {
        task = new TimerTask() {
            @Override
            public void run() {
//                Log.i(TAG, "ChatActivity.refreshStarter - starter is start");
                refresh();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    public void refreshEnder() {
        task.cancel();
    }

    protected void refresh() {
        Runnable refresher = new Runnable() {
            @Override
            public void run() {
//                Log.i(TAG, "ChatActivity.refresh - refresh is called");
                String[] request = new String[]{"chat", "loadChat", userid};
                server = new ServerComponent(server.getServerIp(), request);
                server.start();

                chattingData = (String[][]) server.getRes();
                Log.i(TAG, "ChatActivity.refresh.run - chat load data : " + Arrays.deepToString(chattingData));
                for (int i = 0; chattingData[0][i] != null; i++) {
                    if (!chattingData[0][i].equals(userList[i])) {
                        joinData = new ChatClass(chattingData[0][i], chattingData[0][i] + " 님이 채팅방에 참가하셨습니다.", Time(), 2);
                        chatAdapterMarge.addItem(joinData);
                        recyclerView_L.setAdapter(chatAdapterMarge);
                        chatAdapterMarge.notifyDataSetChanged();

                        userList[i] = chattingData[0][i];
                    }
                }

                for (int i = lastPos + 1; chattingData[i][3] != null ; i++) {
                    pos = Integer.parseInt(chattingData[i][3]);
                }
                if (pos == -1) {
                    Log.i(TAG, "ChatActivity.refresh.run - data not include");
                } else {
                    Log.i(TAG, "ChatActivity.refresh.run - pos : " + pos + " lastPos : " + lastPos);
                    for (int i = lastPos + 1; i < pos + 1; i++) {
                        if (chattingData[i][0].equals(userid)) {
                            data1 = new ChatClass(userid, chattingData[i][2], Time(), 1);
                        } else {
                            data1 = new ChatClass(chattingData[i][1], chattingData[i][2], Time(), 0);
                        }
                        chatAdapterMarge.addItem(data1);
                        recyclerView_L.setAdapter(chatAdapterMarge);
                        chatAdapterMarge.notifyDataSetChanged();
                    }
                    lastPos = pos;
                }
            }
        };
        handler.post(refresher);
    }

}