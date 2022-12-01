package com.example.myapplication.Activity.Chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

// 어댑터 수정본,,, 안돌아갑니다
public class ChatAdapter_type extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatClass> arrayList = new ArrayList<>();

   /* public ChatAdapter(ArrayList<ChatClass> arrayList){
        this.arrayList=arrayList;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemView = layoutInflater.inflate(R.layout.item, parent, false);
//        return new ViewHolder(itemView);

        if (viewType == ChatViewClass.ViewType.LEFT_CONTENT) {
            itemView = layoutInflater.inflate(R.layout.item2, parent, false);
            return new LeftViewHolder(itemView);
        } else {
            itemView = layoutInflater.inflate(R.layout.item, parent, false);
            return new RightViewHolder(itemView);
        }
        // else if CENTER_VIEW can Add
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        holder.onBind(arrayList.get(position));
        if (holder instanceof LeftViewHolder) {
            ((LeftViewHolder) holder).LeftonBind(arrayList.get(position));
//            ((LeftViewHolder) holder).NameView.setText(arrayList.get(position).getName());
//            ((LeftViewHolder) holder).ChatView.setText(arrayList.get(position).getChat());
        } else {
            ((RightViewHolder) holder).RightonBind(arrayList.get(position));
//            ((RightViewHolder) holder).dataView.setText(arrayList.get(position).getChat());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public int getItemViewType(int position) {
        return arrayList.get(position).getViewType();
    }

    public class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView NameView;
        TextView ChatView;
        TextView TimeView;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            NameView = (TextView) itemView.findViewById(R.id.nameView);
            ChatView = (TextView) itemView.findViewById(R.id.textView);
            TimeView = (TextView) itemView.findViewById(R.id.timeView);
        }

        void LeftonBind(ChatClass data) {
            NameView.setText(data.getName());
            ChatView.setText(data.getChat());
            TimeView.setText(data.getTime());
        }

    }

    static class RightViewHolder extends RecyclerView.ViewHolder {
        TextView dataView;
        TextView timeView;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            dataView = itemView.findViewById(R.id.textView);
            timeView = itemView.findViewById(R.id.timeView);
        }

        void RightonBind(ChatClass data) {
            dataView.setText(data.getChat());
            timeView.setText(data.getTime());
        }
    }

    public void addItem(ChatClass item) {
        arrayList.add(item);
        Log.d("#######배열에 넣어짐 -->    ", item.chat + item.time);
    }
}
