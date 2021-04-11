package com.example.quiz;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        int x;
        RadioGroup group=findViewById(R.id.answersGroup);
        x=group.getCheckedRadioButtonId();
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        RadioButton button4 = findViewById(R.id.radio_button4);
        if(v.getId()==R.id.nextButton){
            if(x==-1){
                Context context = getApplicationContext();
                String msg="You need to choose one of the options";
                int duration= Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, msg, duration);
                toast.show();
            }
            else{
                nextQuestion();
            }
        }
        else {
            switch (x) {
                case R.id.radio_button1:
                    checkAnswer(button1.getText().toString());
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    button4.setEnabled(false);
                    break;
                case R.id.radio_button2:
                    checkAnswer(button2.getText().toString());
                    button1.setEnabled(false);
                    button3.setEnabled(false);
                    button4.setEnabled(false);
                    break;
                case R.id.radio_button3:
                    checkAnswer(button3.getText().toString());
                    button2.setEnabled(false);
                    button1.setEnabled(false);
                    button4.setEnabled(false);
                    break;
                case R.id.radio_button4:
                    checkAnswer(button4.getText().toString());
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    button1.setEnabled(false);
                    break;
            }
        }
    }

    private void checkAnswer(String answer){
        TextView msgView = findViewById(R.id.messageLabel);
        if(answer.equals(correctAnswer)){
            msgView.setText(messageCorrect);
        }
        else{
            msgView.setText(messageWrong);
        }
    }

    private void nextQuestion(){
        //setting up initial state
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        RadioButton button4 = findViewById(R.id.radio_button4);
        RadioGroup group=findViewById(R.id.answersGroup);
        group.clearCheck();
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        TextView msgView = findViewById(R.id.messageLabel);
        msgView.setText("");
        String question="question 2";
        TextView textView = findViewById(R.id.questionText);
        textView.setText(question);
    }
}
