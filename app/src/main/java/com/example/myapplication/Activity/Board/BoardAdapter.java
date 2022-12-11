package com.example.myapplication.Activity.Board;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    Context context;
    ArrayList<BoardClass> Board_ArrayList;

    TextView username;
    TextView userSex;
    TextView destination;
    ServerComponent servers = new ServerComponent();

    public BoardAdapter(Context context, ArrayList<BoardClass> board_ArrayList) {
        this.context = context;
        Board_ArrayList = board_ArrayList;
    }

    @Override
    public int getCount() {
        return this.Board_ArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.Board_ArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.board_item,null);
            username = (TextView)convertView.findViewById(R.id.Username);
            userSex = (TextView)convertView.findViewById(R.id.Usersex);
            destination = (TextView)convertView.findViewById(R.id.Userdestination);
        }
        username.setText(Board_ArrayList.get(position).getUsername());
        userSex.setText(Board_ArrayList.get(position).getUserSex());
        destination.setText(Board_ArrayList.get(position).getDestination());

        LinearLayout bar = (LinearLayout) convertView.findViewById(R.id.itemBar);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "BoardAdapter - Override getView run test");
                String[] joinData = new String[]{"chat", "addUser", Board_ArrayList.get(position).getUsername(), Board_ArrayList.get(position).getUserid()};
                Log.i(TAG, "BoardAdapter.barListener.onclick - input data " + Board_ArrayList.get(position).getTime());
                servers = new ServerComponent(servers.getServerIp(), joinData);
                servers.start();

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userName",username.getText().toString());
                intent.putExtra("userSex",userSex.getText().toString());
                intent.putExtra("destination",destination.getText().toString());
                intent.putExtra("userid", Board_ArrayList.get(position).getUserid());
                intent.putExtra("time",String.valueOf(Board_ArrayList.get(position).getTime()));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

