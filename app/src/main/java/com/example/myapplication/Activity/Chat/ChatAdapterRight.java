package com.example.myapplication.Activity.Chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;


public class ChatAdapterRight extends RecyclerView.Adapter<ChatAdapterRight.ViewHolder> {
    private ArrayList<ChatClass> arrayList = new ArrayList<>();

   /* public ChatAdapter(ArrayList<ChatClass> arrayList){
        this.arrayList=arrayList;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView dataView;
        TextView timeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataView = itemView.findViewById(R.id.textView);
            timeView = itemView.findViewById(R.id.timeView);
        }
        void onBind(ChatClass data){
            dataView.setText(data.getChat());
            timeView.setText(data.getTime());
        }
    }

    public void addItem(ChatClass item){
        arrayList.add(item);
        Log.d("#######배열에 넣어짐 -->    ",item.chat+item.time);
    }


   /* public void setarrayList(ArrayList<String> arrayList) {

        this.arrayList = arrayList;
    }

    public String getItem(int position) {

        return arrayList.get(position);
    }

    public void setItem(int position, String item) {

        arrayList.set(position, item);
    }*/
}
