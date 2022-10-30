package com.example.myapplication.Activity.Board;

import static android.content.ContentValues.TAG;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.Activity.TempBoard.TempBoardActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

public class BoardAddActivity extends AppCompatActivity {
    EditText destination_add;
    EditText time_add;
    Button add_submit;
    ServerComponent server;
    String[] data = new String[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_add);

        try {

            Button button_main = (Button) findViewById(R.id.Button_Main);    // 메인으로 이동
            button_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                    startActivity(intent);
                }
            });

            Button button_chat = (Button) findViewById(R.id.Button_Chat);  // chat으로 이동
            button_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    startActivity(intent);
                }
            });

            destination_add = (EditText) findViewById(R.id.board_destination);
            time_add = (EditText) findViewById(R.id.board_time);
            add_submit = (Button) findViewById(R.id.R_Button_submit);
            add_submit.setOnClickListener(view -> {
                String destinations = destination_add.getText().toString();
                String times = time_add.getText().toString();

                Log.i(TAG, "returnToMain: return to main");

                Intent getIntent = getIntent();
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                intent.putExtra("destinations", destinations);
                intent.putExtra("times", times);
                intent.putExtra("userid",getIntent.getStringExtra("userid"));
                startActivity(intent);
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "BoardAddActivity\n" + e);
        }
    }
}