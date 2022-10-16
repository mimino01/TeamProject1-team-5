package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    EditText password, reviewPassword, name, id, phoneNumber;
    RadioGroup gender_group;
    boolean gender;
    Button checker, submit;
    String correct;

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
        phoneNumber = findViewById(R.id.editPhoneNumber);
        correct = "일치";
        Bundle bundle = new Bundle();


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
            if (3 > name.getText().toString().length()) {
                Toast.makeText(getApplicationContext(), "이름은 2자 이상 적어주세요",Toast.LENGTH_LONG).show();
            } else if (5 > id.getText().toString().length() || id.getText().toString().length() > 15) {
                Toast.makeText(getApplicationContext(), "아이디는 최소 6자 최대 14자 입니다",Toast.LENGTH_LONG).show();
            } else if (5 > password.getText().toString().length() || password.getText().toString().length() > 15) {
                Toast.makeText(getApplicationContext(), "비밀번호는 최소 6자 최대 14자 입니다",Toast.LENGTH_LONG).show();
            } else if (11 != phoneNumber.getText().toString().length()) {
                Toast.makeText(getApplicationContext(), "전화번호는 - 없이 적어주세요",Toast.LENGTH_LONG).show();
            } else if (checker.getText().toString().equals(correct)) {
                bundle.putInt("PhoneNumber", Integer.parseInt(phoneNumber.getText().toString()));
                bundle.putString("Name", name.getText().toString());
                bundle.putString("ID", id.getText().toString());
                bundle.putString("Password", password.getText().toString());
                bundle.putBoolean("Gender", gender);
                Toast.makeText(getApplicationContext(), "회원가입 성공",Toast.LENGTH_LONG).show();
            }
        });
    }
}
