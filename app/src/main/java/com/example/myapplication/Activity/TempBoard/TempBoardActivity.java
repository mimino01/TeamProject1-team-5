package com.example.myapplication.Activity.TempBoard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.R;

public class TempBoardActivity extends AppCompatActivity {
    EditText roomEditText;
    Button nextButton;
    public String userid;
    public int roomid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_board);

        roomEditText = findViewById(R.id.roomCode);
        nextButton = findViewById(R.id.button);

        Intent getIntent = getIntent();
        userid = getIntent.getStringExtra("userid");

        nextButton.setOnClickListener(view -> {
            roomid = Integer.parseInt(roomEditText.getText().toString());

            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("userid", userid);
            intent.putExtra("roomCode", roomid);
            intent.putExtra("createOrJoin", "create");
            startActivity(intent);
        });
    }
}
