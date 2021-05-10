package com.example.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HighScore extends Activity {
    private FirebaseDatabase database;
    ArrayList<SingleScore> scores=new ArrayList<SingleScore>();
    private ScoreAdapter adapter;
    public static final String USERNAME_MESSAGE = "com.example.quiz.USERNAME";
    private String category;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);
        Intent intent = getIntent();
        category = intent.getStringExtra(QuizResult.CATEGORY_MESSAGE);
        TextView naslov=findViewById(R.id.scoreTitle);
        naslov.setText("Ukupni rezultat ("+category+")");
        database= FirebaseDatabase.getInstance("https://quiz-app-be64e-default-rtdb.europe-west1.firebasedatabase.app/");
        loadScores(database);

    }


    private void loadScores(FirebaseDatabase database) {
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
                        SingleScore scoreValue = new SingleScore(scoreSnapshot.child("rank").getValue().toString(), scoreSnapshot.child("username").getValue().toString(), scoreSnapshot.child("result").getValue().toString());
                        scores.add(scoreValue);
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HighScore.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ScoreAdapter(scores);
        recyclerView.setAdapter(adapter);
    }
    public void onClickNewGameButton(View v) {
        Intent intent = new Intent(this, ChooseCategory.class);
        String username;
        Intent recivedIntent = getIntent();
        username= recivedIntent.getStringExtra(QuizResult.USERNAME_MESSAGE);
        intent.putExtra(USERNAME_MESSAGE, username);
        startActivity(intent);
    }
}

