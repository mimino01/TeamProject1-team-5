package com.example.myapplication.Activity.Chat;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager1;
    private LinearLayoutManager linearLayoutManager2;
    private ChatAdapter chatAdapter1 = new ChatAdapter();
    private ChatAdapter chatAdapter2 = new ChatAdapter();
    private ArrayList<ChatClass> datalist; // 상대방 채팅
    ChatClass data;

    //레이아웃 연결
    EditText searchText;
    Button searchButton;
    EditText message;
    Button send;
    RecyclerView recyclerView_L;
    RecyclerView recyclerView_R;
    TextView Info;

    //서버 연결
    ServerComponent server = new ServerComponent();
    String userid = null, roomNumber, createOrJoin;
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
        roomNumber = getIntent.getStringExtra("roomCode");

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

        if (!create) {
            if (userid.equals("adminid")) {
                create = true;

                String[] request = new String[]{"chat", "create", userid, "37.22344259294581", "127.18734526333768", "1030", "명지대", "1010"};
                server = new ServerComponent(server.getServerIp(), request);
                server.start();
            } else {
                String[] request = new String[]{"chat", "addUser", "adminid", "subadminid"};
                server = new ServerComponent(server.getServerIp(), request);
                server.start();
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
                startActivity(intent);
            }
        });

        Button button_off = (Button) findViewById(R.id.Button_off);
        button_off.setOnClickListener(new View.OnClickListener() {      // 리뷰쓰러 가기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("userName", getIntent.getStringExtra("userName"));
                intent.putExtra("destination", getIntent.getStringExtra("destination"));
                intent.putExtra("time", getIntent.getStringExtra("time"));
                startActivity(intent);
            }
        });

        // 레이아웃과 연결
        searchText = (EditText) findViewById(R.id.SearchText);
        searchButton = (Button) findViewById(R.id.SearchButton);
        message = (EditText) findViewById(R.id.EditText_chat);
        send = (Button) findViewById(R.id.Button_send);
        recyclerView_R = (RecyclerView) findViewById(R.id.chatting_Right);
        recyclerView_L = (RecyclerView) findViewById(R.id.chatting_Left);
        recyclerView_R.setHasFixedSize(true);
        recyclerView_L.setHasFixedSize(true);
        linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            /*((LinearLayoutManager) linearLayoutManager).setReverseLayout(true);
            ((LinearLayoutManager) linearLayoutManager).setStackFromEnd(true);*/

        recyclerView_R.setLayoutManager(linearLayoutManager2);
        recyclerView_L.setLayoutManager(linearLayoutManager1);

        //채팅 검색 기능, 근데 이제 임시로 클릭하면 상대방 채팅이 뜨는
        searchButton.setOnClickListener(new View.OnClickListener() {    // 검색 버튼 클릭
            @Override
            public void onClick(View view) {
                String text = "안녕하십니까";
                ChatClass data = new ChatClass(text, Time(), 0);
                chatAdapter1.addItem(data);
                recyclerView_L.setAdapter(chatAdapter1);
                chatAdapter1.notifyDataSetChanged();


//                String S_text = searchText.getText().toString();    // 검색 텍스트값 변환
//                ChatSearch(S_text);                                 // 리사이클러뷰에서 검색텍스트를 탐색하여 해당 위치로 스크롤하는 것까지 실행하는 함수
            }
        });

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String[] request = new String[]{"chat", "loadChat", userid};
                server = new ServerComponent(server.getServerIp(), request);
                server.start();

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: front");

                String[][] response = (String[][]) server.getRes();
                if (response[0][0] != null) {
//                    Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: inside 1");

                    for (int i = 0; response[i][0] != null; i++) {
//                        Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data : " + Arrays.toString(response[i]));
                        if (!userid.equals(response[i][0])) {
//                            Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: inside 2");

                            data = new ChatClass(response[i][2], Time(), 0);
//                            data.setChat();
//                            data.setTime(Time());
                            chatAdapter2.addItem(data);
                        }
                    }
                } else {
//                    Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data is null ");
                }

//                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: back");


                String text1 = message.getText().toString();
                ChatClass data = new ChatClass(text1, Time(), 0);
                chatAdapter2.addItem(data);
                recyclerView_R.setAdapter(chatAdapter2);
                chatAdapter2.notifyDataSetChanged();

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

    // 검색을 수행하는 메소드
//    public void ChatSearch(String chatText) {
//        // chatText와 chatLog에서의 채팅값끼리 비교하여 해당 인덱스값을 포지션으로 하여
//        // 리사이클러.ScrollToPosition(포지션)으로 이동한다.
//
//        //그게 아니라면 리사이클러 전체를 탐색하여 찾고, 포지션은,, 어카지?
//
//    }
//}

    /*public void returnToMain(){  // 채팅, 사용자 정보를 메인으로 전송하는 함수
        ArrayList chattings = adapter.getList();

        Intent intent = new Intent();
        intent.putExtra("chattingList", chattings);

        setResult(RESULT_OK, intent);
        finish();
    }

    /*private void init() {
        RecyclerView recyclerView = findViewById(R.id.chatting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);
    }

    /*private void getData(EditText message) {
        ChatData data = new ChatData();
        data.setTitle(message.getText().toString());
        data.setContent("이름 또는 시간");
        adapter.addItem(data);
    }
   // adapter.notifyDataSetChanged();*/
