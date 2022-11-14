package com.example.myapplication.Activity.Other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.Chat.ChatAdapter;
import com.example.myapplication.Activity.Chat.ChatClass;
import com.example.myapplication.Activity.Infomation.InfomationActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatTestingActivity extends AppCompatActivity {
    String name;
    private com.example.myapplication.Activity.Chat.ChatAdapter chatAdapter = new ChatAdapter();
    String[] sendedData = new String[5];
    ServerComponent server = new ServerComponent();
    boolean create = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_testing);

        Intent getIntent = getIntent();
        name = getIntent.getStringExtra("userid");

        if (!create) {
            if (name.equals("adminid"))
            {
                create = true;

                String[] request = new String[]{"chat", "create", name, "37.22344259294581", "127.18734526333768", "1030", "명지대", "1010"};
                server = new ServerComponent(server.getServerIp(),request);
                server.start();
            } else {
                String[] request = new String[]{"chat", "addUser", "adminid", "subadminid"};
                server = new ServerComponent(server.getServerIp(),request);
                server.start();
            }
        }

        EditText message = (EditText) findViewById(R.id.EditText_chat);
        Button send = (Button) findViewById(R.id.Button_send);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chatting);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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

                sendedData[0] = "chat";
                sendedData[1] = "addChat";
                sendedData[2] = name;
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
