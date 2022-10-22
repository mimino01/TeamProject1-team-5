package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.option.ChatAdapter;
import com.example.myapplication.option.ChatClass;
import com.example.myapplication.server.ServerComponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private ChatAdapter chatAdapter = new ChatAdapter();
    EditText message;
    Button send;
    RecyclerView recyclerView;
    TextView Info;
    ServerComponent server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

            // 메인으로 이동

        /*Button button_Borad=(Button)findViewById(R.id.Button_Board);    // 보드로 이동
        button_Borad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });*/

            Button button_back = (Button) findViewById(R.id.Button_back);  // 뒤로가기
            button_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });

            Info = (TextView) findViewById(R.id.TextView_info);

            Button button_off = (Button) findViewById(R.id.Button_off);
            button_off.setOnClickListener(new View.OnClickListener() {      // 리뷰쓰러 가기
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    startActivity(intent);
                }
            });

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
                    String text1 = message.getText().toString();
                    ChatClass data = new ChatClass();
                    data.setChat(text1);
                    data.setTime(Time());
                    chatAdapter.addItem(data);
                    recyclerView.setAdapter(chatAdapter);
                    chatAdapter.notifyDataSetChanged();
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
