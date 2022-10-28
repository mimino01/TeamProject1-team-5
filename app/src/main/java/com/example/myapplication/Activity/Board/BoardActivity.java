package com.example.myapplication.Activity.Board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    ListView listView;
    BoardAdapter boardAdapter;
    ArrayList<BoardClass> boardArrayList;

    //private List<String> items= Arrays.asList("체리/여/강남역","아몬드/남/잠실역","레인보우/여/노량진역");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Button button_board=(Button)findViewById(R.id.Button_Board);    // 보드로 이동
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                startActivity(intent);
            }
        });

        Button button_chat=(Button)findViewById(R.id.Button_Chat);    // 채팅으로 이동
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.board_listView);

        boardArrayList = new ArrayList<BoardClass>();
        boardArrayList.add(new BoardClass("보라돌이","제목1","내용1"));
        boardArrayList.add(new BoardClass("뚜비","제목2","내용2"));
        boardArrayList.add(new BoardClass("나나","제목3","내용3"));
        boardArrayList.add(new BoardClass("뽀","제목4","내용4"));
        boardArrayList.add(new BoardClass("햇님","제목5","내용5"));

        boardAdapter = new BoardAdapter(BoardActivity.this,boardArrayList);
        listView.setAdapter(boardAdapter);

        /*SearchView searchView = findViewById(R.id.board_searchview);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {return false;}

            @Override
            public boolean onQueryTextChange(String newText) {
                //resultTextView.setText(search(newText));
                return true;}
        });

    }

    /*private String search(String query){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< items.size(); i++) {
            String item = items.get(i);
            if (item.toLowerCase().contains(query.toLowerCase())) {
                sb.append(item);
                if (i != items.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private String getResult() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< items.size(); i++){
            String item = items.get(i);
            sb.append(item);
            if(i != items.size()-1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }*/
    }}
