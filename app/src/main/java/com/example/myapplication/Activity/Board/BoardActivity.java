package com.example.myapplication.Activity.Board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    ListView listView;
    BoardAdapter boardAdapter;
    Button Sort_button;
    ArrayList<BoardClass> boardArrayList;
    ArrayList<BoardClass> copy_array;
    SearchAdapter searchAdapter;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Button button_board = (Button) findViewById(R.id.Button_Board);    // 보드로 이동
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                startActivity(intent);
            }
        });

        Button button_chat = (Button) findViewById(R.id.Button_Chat);    // 채팅으로 이동
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.board_listView);

        boardArrayList = new ArrayList<BoardClass>();
        boardArrayList.add(new BoardClass("보라돌이", "제목1", "내용1"));
        boardArrayList.add(new BoardClass("뚜비", "제목2", "내용2"));
        boardArrayList.add(new BoardClass("나나", "제목3", "내용3"));
        boardArrayList.add(new BoardClass("뽀", "제목4", "내용4"));
        boardArrayList.add(new BoardClass("햇님", "제목5", "내용5"));

        boardAdapter = new BoardAdapter(BoardActivity.this, boardArrayList);
        listView.setAdapter(boardAdapter);

        // 검색을 위해서 리스트의 모든 데이터를 copy_array에 복사한다.
        copy_array = new ArrayList<BoardClass>();
        copy_array.addAll(boardArrayList);

        // 리스트에 연동될 아답터를 생성한다.
        searchAdapter = new SearchAdapter(boardArrayList, this);
        listView.setAdapter(searchAdapter);

        editSearch = (EditText) findViewById(R.id.board_searchview);

       /* Sort_button=(Button)findViewById(R.id.board_sort_button);     // 정렬버튼 누르면
        Sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        boardArrayList.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            boardArrayList.addAll(copy_array);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < copy_array.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (copy_array.get(i).getDestination().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    boardArrayList.add(copy_array.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        searchAdapter.notifyDataSetChanged();
    }
}
