package com.example.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.option.Option;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    Option option;
    EditText password, reviewPassword, name, id;
    RadioGroup gender_group;
    boolean gender;
    Button checker, submit;
    String correct, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        checker = findViewById(R.id.buttonCheckPassword);
        password = findViewById(R.id.editPassword);
        reviewPassword = findViewById(R.id.editReviewPassword);
        name = findViewById(R.id.editName);
        id = findViewById(R.id.editID);
        gender_group = findViewById(R.id.radioGroup);
        submit = findViewById(R.id.buttonSubmit);
        phoneNumber = findViewById(R.id.editPhoneNumber).toString();
        correct = "일치";

        checker.setOnClickListener(view -> {
            if (password.getText().toString().equals(reviewPassword.getText().toString())) {
                checker.setText(correct);
            } else {
                Toast.makeText(SignupActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        gender_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioMan:
                        gender = true;
                        break;
                    case R.id.radioWoman:
                        gender = false;
                        break;
                }
            }
        });

        submit.setOnClickListener(view -> {
//            switch (0) {
//                case name.getText().toString().length():
//            }
            if (checker.getText().toString().equals(correct)) {
                option.setOption(name.getText().toString(), id.getText().toString(), password.getText().toString(), gender, phoneNumber);
                Log.i(TAG, "submit: " + option.getOption());
            }
        });
    }
}
