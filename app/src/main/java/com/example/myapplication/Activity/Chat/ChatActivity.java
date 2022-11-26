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
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private ChatAdapter chatAdapter = new ChatAdapter();
    ChatClass data;

    //레이아웃 연결
    EditText message;
    Button send;
    RecyclerView recyclerView;
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

        if (!create) { // 설명부탁
            if (userid.equals("adminid"))
            {
                create = true;

                String[] request = new String[]{"chat", "create", userid, "37.22344259294581", "127.18734526333768", "1030", "명지대", "1010"};
                server = new ServerComponent(server.getServerIp(),request);
                server.start();
            } else {
                String[] request = new String[]{"chat", "addUser", "adminid", "subadminid"};
                server = new ServerComponent(server.getServerIp(),request);
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

        Button button_board=(Button)findViewById(R.id.Button_Board);    // 보드로 이동
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
                    intent.putExtra("userName",getIntent.getStringExtra("userName"));
                    intent.putExtra("destination",getIntent.getStringExtra("destination"));
                    intent.putExtra("time",getIntent.getStringExtra("time"));
                    startActivity(intent);
                }
            });

            // 레이아웃과 연결
            message = (EditText) findViewById(R.id.EditText_chat);
            send = (Button) findViewById(R.id.Button_send);
            recyclerView = (RecyclerView) findViewById(R.id.chatting);
            recyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(this);
            /*((LinearLayoutManager) linearLayoutManager).setReverseLayout(true);
            ((LinearLayoutManager) linearLayoutManager).setStackFromEnd(true);*/

            recyclerView.setLayoutManager(linearLayoutManager);

            send.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String[] request = new String[]{"chat", "loadChat", userid};
                    server = new ServerComponent(server.getServerIp(),request);
                    server.start();

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: front");

                    String[][] response = (String[][]) server.getRes();
                    if (response[0][0] != null){
//                    Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: inside 1");

                        for (int i = 0; response[i][0] != null; i++) {
//                        Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data : " + Arrays.toString(response[i]));
                            if (!userid.equals(response[i][0])) {
//                            Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: inside 2");

                                data = new ChatClass();
                                data.setChat(response[i][2]);
                                data.setTime(Time());
                                chatAdapter.addItem(data);
                            }
                        }
                    } else {
//                    Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - response data is null ");
                    }

//                Log.i(TAG, "ChatTestingActivity.onCreate.sendButton.onclick - callback test: back");


                    String text1 = message.getText().toString();
                    ChatClass data = new ChatClass();
                    data.setChat(text1);
                    data.setTime(Time());
                    data.setViewType(0);
                    chatAdapter.addItem(data);
                    recyclerView.setAdapter(chatAdapter);
                    chatAdapter.notifyDataSetChanged();

                    sendedData[0] = "chat";
                    sendedData[1] = "addChat";
                    sendedData[2] = userid;
                    sendedData[3] = text1;

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

    //init();

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
