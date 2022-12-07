package com.example.myapplication.Activity.Review;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.Board.BoardActivity;
import com.example.myapplication.Activity.Chat.ChatActivity;
import com.example.myapplication.Activity.TempBoard.TempBoardActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

public class ReviewActivity extends AppCompatActivity {
    RatingBar Rating_Bar;
    EditText review;
    TextView Rating_Point;
    ServerComponent server;
    String[] data = new String[30];
    String hostName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent getIntent = getIntent();
        // 인포 실행 안됨
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView info = findViewById(R.id.TextView_info);
        info.setText(getIntent.getStringExtra("userName") + " | " + getIntent.getStringExtra("destination") + " | " + getIntent.getStringExtra("time"));
        hostName = getIntent.getStringExtra("userName");

        try {
            Button button_chat = (Button) findViewById(R.id.R_Button_Chat);  // chat으로 이동
            button_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    startActivity(intent);
                }
            });

            Rating_Bar = (RatingBar) findViewById(R.id.R_Ratingbar);
            Rating_Point = (TextView) findViewById(R.id.R_Textview_ratingpoint);
            Rating_Bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    Rating_Bar.setRating(v);
                    Rating_Point.setText(Float.toString(Rating_Bar.getRating()) + " / 5.0");
                }
            });
            review = (EditText) findViewById(R.id.R_EditText_review);
            Button submit = (Button) findViewById(R.id.R_Button_submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    returnToMain();
                }
            });

            Intent intent = getIntent();
            processIntent(intent);

        } catch(Exception e) {
            e.printStackTrace();
            Log.d(TAG, "ReviewActivity\n" + e);
        }
}

    private void processIntent(Intent intent){
        if(intent!=null){
            float rating = intent.getFloatExtra("rating", 0.0f);
            Rating_Bar.setRating(rating);
        }
    }

    public void returnToMain(){  // 별점, 리뷰 메인에 전송하는 함수
        String reviews = review.getText().toString();
        String ratingbars = Float.toString(Rating_Bar.getRating());

        String[] req = new String[5];
        req[0] = "review";
        req[1] = "addReview";
        req[2] = hostName;
        req[3] = Rating_Point.getText().toString();
        req[4] = review.getText().toString();
        server = new ServerComponent(server.getServerIp(),req);
        server.start();

        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "review: " + server.getRes());
        String[][] temp = (String[][]) server.getRes();
        if (temp[0][0].equals("true")) {
            Toast.makeText(getApplicationContext(), "리뷰 작성 성공",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "리뷰 작성 실패", Toast.LENGTH_LONG).show();
        }

    }
}
