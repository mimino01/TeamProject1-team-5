package com.example.myapplication.Activity;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.server.ServerComponent;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    RatingBar Rating_Bar;
    EditText review;
    TextView Rating_Point;
    ServerComponent server;
    String[] data = new String[30];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

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
            Log.d(TAG, "ReviewnActivity\n" + e);
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

        data[0] = "review";
        data[1] = ratingbars;
        data[2] = reviews;
        server = new ServerComponent(server.getServerIp(),data);
        server.start();

        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "review: " + server.getRes());
        if ((boolean) server.getRes()) {
            Toast.makeText(getApplicationContext(), "리뷰 작성 성공",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "리뷰 작성 실패", Toast.LENGTH_LONG).show();
        }

        /*Intent intent = new Intent();
        intent.putExtra("review", reviews);
        intent.putExtra("ratingbar", ratingbars);

        setResult(RESULT_OK, intent);
        finish();*/
    }
}
