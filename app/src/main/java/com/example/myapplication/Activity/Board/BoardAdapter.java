package com.example.myapplication.Activity.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.board_item,null);
            username = (TextView)convertView.findViewById(R.id.Username);
            userSex = (TextView)convertView.findViewById(R.id.Usersex);
            destination = (TextView)convertView.findViewById(R.id.Userdestination);
        }
        username.setText(Board_ArrayList.get(position).getUsername());
        userSex.setText(Board_ArrayList.get(position).getUserSex());
        destination.setText(Board_ArrayList.get(position).getDestination());

        return convertView;
    }
}

