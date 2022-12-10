package com.example.myapplication.Activity.Chat;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class ChatActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager1;
    private ChatAdapter_type chatAdapterMarge = new ChatAdapter_type(); // 합병 어댑터
    private ArrayList<ChatClass> datalist = new ArrayList<>();
    ChatClass data;

    String[][] lastChatLog;
    int pos = -1;

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
    String userid = null, createOrJoin, hostName;
    String[] sendedData = new String[5];
    String[] createData = new String[5];
    boolean create = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 상대방 채팅 삽입
//        datalist = new ArrayList<>();
//        datalist.add(new ChatClass("HI, CLASS", Time(), ChatViewClass.ViewType.LEFT_CONTENT));
//        recyclerView.setAdapter(chatAdapter);
//        chatAdapter.notifyDataSetChanged();


        Intent getIntent = getIntent();
        userid = getIntent.getStringExtra("userid");

        Log.i(TAG, "ChatActivity - get intent data checker : " + getIntent.getStringExtra("createOrJoin") + getIntent.getStringExtra("userid") + getIntent.getStringExtra("roomCode"));
        createOrJoin = getIntent.getStringExtra("createOrJoin");
                // 상단에 '목적지 | 시간 ' 표시
        Info = (TextView) findViewById(R.id.TextView_info);
        Info.setText(getIntent.getStringExtra("userName") + " | " + getIntent.getStringExtra("destination") + " | " + getIntent.getStringExtra("time"));

       /* 이전 create
       if (createOrJoin.equals("create")) {
            Log.i(TAG, "ChatActivity - create checker");
            createData[0] = "chat";
            createData[1] = "create";
            createData[2] = userid;
            server = new ServerComponent(server.getServerIp(),createData);
            server.start();
        }*/

        recyclerView_L = (RecyclerView) findViewById(R.id.chatting_Left);
        recyclerView_L.setHasFixedSize(true);

        linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            /*((LinearLayoutManager) linearLayoutManager).setReverseLayout(true);
            ((LinearLayoutManager) linearLayoutManager).setStackFromEnd(true);*/

        recyclerView_L.setLayoutManager(linearLayoutManager1);

        hostName = getIntent.getStringExtra("userName");
        if (!create) {
            if (userid.equals(hostName)) {     // 필요없을지도.. 게시물 작성자가 본인의 채팅방에 접근할때의 경우
                create = true;

                String[] request = new String[]{"chat", "create", userid, "37.22344259294581", "127.18734526333768", "1030", "명지대", "1010"};
                server = new ServerComponent(server.getServerIp(), request);
                server.start();
            } else {            // 게시물 작성자가 아닌 사용자가 접근 시 채팅방에 JOIN
                String[] request = new String[]{"chat", "addUser", hostName, userid};
                server = new ServerComponent(server.getServerIp(), request);
                server.start();

                ChatClass d = new ChatClass(userid,  userid + "님이 채팅방에 참가하였습니다.", Time(), 2);
                chatAdapterMarge.addItem(d);
                recyclerView_L.setAdapter(chatAdapterMarge);
                chatAdapterMarge.notifyDataSetChanged();
            }
        }


        Button button_main = (Button) findViewById(R.id.Button_Main);    // 메인으로 이동
        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        Button button_board = (Button) findViewById(R.id.Button_Board);    // 보드로 이동
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                startActivity(intent);
            }
        });

        Button button_back = (Button) findViewById(R.id.Button_back);  // 뒤로가기
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                intent.putExtra("userid",userid);
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
                startActivity(intent);
            }
        });

        // 레이아웃과 연결
//        chatserv = findViewById(R.id.Button_chatserv);
        searchText = (EditText) findViewById(R.id.SearchText);
        searchButton = (Button) findViewById(R.id.SearchButton);
        message = (EditText) findViewById(R.id.EditText_chat);
        send = (Button) findViewById(R.id.Button_send);

//while 문
//            try {
//                while (true) {
//                    sleep(1000);
//
//                    //여기서부터
//                    String[] request = new String[]{"chat", "loadChat", userid};
//                    server = new ServerComponent(server.getServerIp(), request);
//                    server.start();
////                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: front");
//
//                    String[][] response = (String[][]) server.getRes();
//
//                    lastChatLog = response.clone();
//                    if (response[0][0] != null) {       // 빈 채팅이 아닐 경우
////                    Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: inside 1");
//
//                        for (int i = 0; response[i][0] != null; i++) {
////                        Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data : " + Arrays.toString(response[i]));
//                            if (!userid.equals(response[i][0])) {
//                                if (pos == Integer.getInteger(response[i][3])) {
//                            Log.i(TAG, "ChatActivity - pos test : inside if " + pos);
//                                    data = new ChatClass(response[i][1], response[i][2], Time(), 0);
//                                    chatAdapterMarge.addItem(data);
//                                    pos++;
//                                }
//                            }
//                        }
//                    } else { // 빈 채팅 일 경우우
////                   Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data is null ");
//                    }
//                }
//                }catch(InterruptedException e){
//                    e.printStackTrace();
////                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: back");
//            }
//                // 여기까지 채팅 불러오는,. 따라서 while문으로 돌릴것

//

        // 상대방 채팅이 뜨는
//        chatserv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                String text = "안녕하십니까";
////                ChatClass data = new ChatClass("신서연",text, Time(), 0);  // 채팅 보내는 상대방 이름으로 수정
////                chatAdapterMarge.addItem(data);
////                recyclerView_L.setAdapter(chatAdapterMarge);
////                chatAdapterMarge.notifyDataSetChanged();
////
////                sendedData[0] = "chat";
////                sendedData[1] = "addChat";
////                sendedData[2] = userid;
////                sendedData[3] = text;
////                sendedData[4] = "left";    // 임시 왼/오 확인
////
////                server = new ServerComponent(server.getServerIp(), sendedData);
////                server.start();
//
//            }
//        });

        //채팅 검색 기능 여기요 여기 제훈님
        searchButton.setOnClickListener(new View.OnClickListener() {    // 검색 버튼 클릭
            @Override
            public void onClick(View view) {

                String S_text = searchText.getText().toString();    // 검색 텍스트값 변환

                for(int i=0; lastChatLog[i][0] != null ; i++){
                    Log.i(TAG, "Last Chat Log : " + i);
                    Toast.makeText(getApplicationContext(), i +"번째 반복문 들어왔다",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), lastChatLog[i][2] + " 와 같은가?",Toast.LENGTH_SHORT).show();       // lastChatLog를 삭제해도 안뜨고 막무가내로 종료됨

                    if(lastChatLog[i][2].equals(S_text)){
                        Log.i(TAG, "Last Chat Log : is true");
                        Toast.makeText(getApplicationContext(),"검색 성공",Toast.LENGTH_SHORT).show();
                        linearLayoutManager1.scrollToPositionWithOffset(Integer.valueOf(lastChatLog[i][3])+1,100);
                        break;
                    }
                    else{
                        Log.i(TAG, "Last Chat Log : is false");
                        Toast.makeText(getApplicationContext(), lastChatLog[i][3] + "번째 값 검색 실패",Toast.LENGTH_SHORT).show();
                    }
                }
//                ChatSearch(S_text);                                 // 리사이클러뷰에서 검색텍스트를 탐색하여 해당 위치로 스크롤하는 것까지 실행하는 함수
            }
        });

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//              원래 채팅 서버연결 있던 곳
                String[] request = new String[]{"chat", "loadChat", userid};
                    server = new ServerComponent(server.getServerIp(), request);
                    server.start();
//                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: front");

                    String[][] response = (String[][]) server.getRes();

                    lastChatLog = response.clone();
                    if (response[0][0] != null) {       // 빈 채팅이 아닐 경우
//                    Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: inside 1");

                        for (int i = 0; response[i][0] != null; i++) {
//                        Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data : " + Arrays.toString(response[i]));
                            if (!userid.equals(response[i][0])) {
//                                if (pos == Integer.getInteger(response[i][3])) {
//                            Log.i(TAG, "ChatActivity - pos test : inside if " + pos);
                                    data = new ChatClass(response[i][1], response[i][2], Time(), 0);
                                    chatAdapterMarge.addItem(data);
//                                    pos++;
//                                }
                            }
                        }
                    } else { // 빈 채팅 일 경우
//                   Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data is null ");
                    }


//                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: back")

                // 전송하면 서버 및 리스트에 채팅 추가
                String text1 = message.getText().toString();
                ChatClass data1 = new ChatClass(userid, text1, Time(), 1);
                chatAdapterMarge.addItem(data1);
                pos ++;
                recyclerView_L.setAdapter(chatAdapterMarge);
                chatAdapterMarge.notifyDataSetChanged();

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

}