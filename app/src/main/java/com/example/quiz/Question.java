package com.example.quiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Question extends Activity {
    private boolean answerTrue;
    private String correctAnswer;
    public int points;
    public int number;
    private String messageCorrect="Točan odgovor!";
    private String messageWrong="Netočan odgovor! Točan odgovor je: ";
    ArrayList<OneQuestion>dataSet=new ArrayList<OneQuestion>();
    private FirebaseDatabase database;
    private static final String TAG = "Question";
    public static final String RESULT_MESSAGE = "com.example.quiz.MESSAGE";
    public static final String USERNAME_MESSAGE = "com.example.quiz.USERNAME";
    public static final String CATEGORY_MESSAGE = "com.example.quiz.CATEGORY";
    public static final String POINTS_MESSAGE = "com.example.quiz.POINTS";
    private String category;
    private String username;
    public Question(){
        correctAnswer="";
        answerTrue=false;
        points=0;
        number=1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        category = intent.getStringExtra(ChooseCategory.CATEGORY_MESSAGE);
        username=intent.getStringExtra(ChooseCategory.USERNAME_MESSAGE);
        database= FirebaseDatabase.getInstance("https://quiz-app-be64e-default-rtdb.europe-west1.firebasedatabase.app/");
        loadDataset();
        setContentView(R.layout.question);

    }

    private void loadDataset() {
        DatabaseReference myRef = database.getReference("db").child("quiz").child(category).child("questions");
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSet.size() != 0) {
                    dataSet.clear();
                }
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    OneQuestion question = questionSnapshot.getValue(OneQuestion.class);
                    dataSet.add(question);
                }
                Collections.shuffle(dataSet);
                SetQuestion(dataSet.get(number-1));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public void onClickButton(View v){
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        RadioButton button4 = findViewById(R.id.radio_button4);
        TextView questionText = findViewById(R.id.questionText);
        RadioGroup group=findViewById(R.id.answersGroup);
        int x;
        x=group.getCheckedRadioButtonId();
        if(v.getId()==R.id.nextButton){
            if(x==-1){
                Context context = getApplicationContext();
                String msg="Moraš odabrati jednu od opcija";
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
            points++;
            msgView.setText(messageCorrect);
        }
        else{
            msgView.setText(messageWrong+correctAnswer);
        }
    }

    private void nextQuestion(){
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        RadioButton button4 = findViewById(R.id.radio_button4);
        TextView questionText = findViewById(R.id.questionText);
        RadioGroup group=findViewById(R.id.answersGroup);
            //setting up initial state
            group.clearCheck();
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(true);
            TextView msgView = findViewById(R.id.messageLabel);
            msgView.setText("");
        if(number<=dataSet.size()&&number<=10){
            SetQuestion(dataSet.get(number-1));
        }
        else{
            Intent intent = new Intent(this, QuizResult.class);
            intent.putExtra(RESULT_MESSAGE, String.valueOf(points));
            intent.putExtra(USERNAME_MESSAGE, username);
            intent.putExtra(CATEGORY_MESSAGE,category);
            intent.putExtra(POINTS_MESSAGE,String.valueOf(number-1));
            startActivity(intent);
        }

    }

    private void SetQuestion(OneQuestion q){
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        RadioButton button4 = findViewById(R.id.radio_button4);
        TextView questionText = findViewById(R.id.questionText);
        RadioGroup group=findViewById(R.id.answersGroup);
        questionText.setText(number+". "+q.text);
        ArrayList<String> answers=new ArrayList<String>();
        answers.add(q.answer1);
        answers.add(q.answer2);
        answers.add(q.answer3);
        answers.add(q.correct_answer);
        correctAnswer=q.correct_answer;
        number++;
        Collections.shuffle(answers);
        for (int i = 0; i < group.getChildCount(); i++) {
            ((RadioButton) group.getChildAt(i)).setText(answers.get(i));
        }
    }
}

