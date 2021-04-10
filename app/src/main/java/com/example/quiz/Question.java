package com.example.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Question extends Activity {
    private boolean answerTrue;
    private String correctAnswer;
    private String messageCorrect="Correct answer!";
    private String messageWrong="Wrong answer! Correct answer is answer 1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
    }

    public Question(){
        correctAnswer="answer 1";
        answerTrue=false;
    }

    public void onClickButton(View v){
        switch (v.getId()){
            case R.id.radio_button1:
                RadioButton button1=findViewById(R.id.radio_button1);
                checkAnswer(button1.getText().toString());
                break;
            case R.id.radio_button2:
                RadioButton button2=findViewById(R.id.radio_button2);
                checkAnswer(button2.getText().toString());
                break;
            case R.id.radio_button3:
                RadioButton button3=findViewById(R.id.radio_button3);
                checkAnswer(button3.getText().toString());
                break;
            case R.id.radio_button4:
                RadioButton button4=findViewById(R.id.radio_button4);
                checkAnswer(button4.getText().toString());
                break;
            case R.id.nextButton:
                nextQuestion();
                break;
        }
    }

    private void checkAnswer(String answer){
        TextView msgView = findViewById(R.id.messageLabel);
        msgView.setText(messageCorrect);
        if(answer.equals(correctAnswer)){
            msgView.setText(messageCorrect);
        }
        else{
            msgView.setText(messageWrong);
        }
    }

    private void nextQuestion(){
        String question="question 2";
        TextView textView = findViewById(R.id.questionText);
        textView.setText(question);

    }
}
