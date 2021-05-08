package com.example.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizResult extends Activity {

    public static final String USERNAME_MESSAGE = "com.example.quiz.USERNAME";
    public static final String CATEGORY_MESSAGE = "com.example.quiz.CATEGORY";
    String message;
    String username;
    String category;
    int points;
    private FirebaseDatabase database;

    public QuizResult() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result);
        database= FirebaseDatabase.getInstance("https://quiz-app-be64e-default-rtdb.europe-west1.firebasedatabase.app/");
        Intent intent = getIntent();
        message = intent.getStringExtra(Question.RESULT_MESSAGE);
        username = intent.getStringExtra(Question.USERNAME_MESSAGE);
        category=intent.getStringExtra(Question.CATEGORY_MESSAGE);
        points=Integer.parseInt(message);
        String msg = "Čestitamo " + username + "!";
        TextView cong = findViewById(R.id.congratsLabel);
        cong.setText(msg);
        TextView resultText = findViewById(R.id.oneResultLabel);
        resultText.setText("Tvoj rezultat: "+message+"/2");
        addToScoreBoard();

    }

    public void onClickScoreBoardButton(View v) {
        Intent intent = new Intent(this, HighScore.class);
        intent.putExtra(USERNAME_MESSAGE, username);
        intent.putExtra(CATEGORY_MESSAGE,category);
        startActivity(intent);
    }

    private void addToScoreBoard(){
        ArrayList<Scores> scores=new ArrayList<Scores>();
        DatabaseReference myRef = database.getReference("db").child("results").child(category);
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (scores.size() != 0) {
                    scores.clear();
                }
                if(dataSnapshot.exists()) {
                    for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                        Scores scoreValue = new Scores(scoreSnapshot.child("username").getValue().toString(), Integer.parseInt(scoreSnapshot.child("result").getValue().toString()),Integer.parseInt(scoreSnapshot.child("rank").getValue().toString()));
                        scores.add(scoreValue);
                    }
                    int length=scores.size();
                    if(length>0&&points>scores.get(length-1).result){
                        for(int i=length-1;i>0;i--){
                            if(points>=scores.get(i-1).result)
                            {
                                scores.get(i).username=scores.get(i-1).username;
                                scores.get(i).result=scores.get(i-1).result;
                            }
                            else if(length==0){
                                Scores item=new Scores(username,points,length+1);
                                scores.add(item);
                            }
                            else{
                                scores.get(i).username=username;
                                scores.get(i).result=points;
                            }
                        }
                    }
                    else if(length<10){
                        Scores item=new Scores(username,points,length+1);
                        scores.add(item);
                    }

                    database.getReference("db").child("results").child(category).setValue(scores);
                }
                else{
                    Scores item=new Scores(username,points,1);
                    database.getReference("db").child("results").child(category).setValue(item);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public void onClickNewGameButton(View V){
        Intent intent = new Intent(this, ChooseCategory.class);
        String username;
        Intent recivedIntent = getIntent();
        username= recivedIntent.getStringExtra(QuizResult.USERNAME_MESSAGE);
        intent.putExtra(USERNAME_MESSAGE, username);
        startActivity(intent);
    }
}
