package com.example.myapplication.Activity.Other;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
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


/*public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemViewHolder> {
    private ArrayList<ChatData> listData = new ArrayList();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(ChatData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        void onBind(ChatData data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
        }
    }

}*/
