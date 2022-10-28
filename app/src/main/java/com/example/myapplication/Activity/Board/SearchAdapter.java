package com.example.myapplication.Activity.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BoardClass> board_array;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    public SearchAdapter(ArrayList<BoardClass> list, Context context){
        this.board_array = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return board_array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.board_item,null);
            viewHolder = new ViewHolder();

            viewHolder.username = (TextView) convertView.findViewById(R.id.Username);
            viewHolder.userSex = (TextView) convertView.findViewById(R.id.Usersex);
            viewHolder.destination = (TextView) convertView.findViewById(R.id.Userdestination);

           /* username = (TextView)convertView.findViewById(R.id.Username);
            userSex = (TextView)convertView.findViewById(R.id.Usersex);
            destination = (TextView)convertView.findViewById(R.id.Userdestination);
*/
           convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.username.setText(board_array.get(position).getUsername());
        viewHolder.userSex.setText(board_array.get(position).getUserSex());
        viewHolder.destination.setText(board_array.get(position).getDestination());
        return convertView;
    }

    class ViewHolder{
        public TextView username;
        public TextView userSex;
        public TextView destination;
    }

}
