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

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    Context context;
    ArrayList<BoardClass> Board_ArrayList;

    TextView username;
    TextView userSex;
    TextView destination;

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

        Log.i(TAG, "BoardAdapter - Override getView run test");

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
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}

