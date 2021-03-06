package com.example.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class QuizResult extends Activity {

    public QuizResult(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Question.RESULT_MESSAGE);
        String username = intent.getStringExtra(Question.USERNAME_MESSAGE);
        String msg="Congratulation "+username+"!";
        TextView cong=findViewById(R.id.congratsLabel);
        cong.setText(msg);
        TextView resultText = findViewById(R.id.oneResultLabel);
        resultText.setText(message);

    }
}
