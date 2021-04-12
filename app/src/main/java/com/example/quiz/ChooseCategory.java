package com.example.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class ChooseCategory extends Activity {

    public static final String CATEGORY_MESSAGE = "com.example.quiz.CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

    }


    public void onChooseCategory(View v){
        Button selected=findViewById(v.getId());
        String name=selected.getText().toString().toLowerCase();
        Intent intent = new Intent(this, Question.class);
        intent.putExtra(CATEGORY_MESSAGE, name);
        startActivity(intent);


    }
}
