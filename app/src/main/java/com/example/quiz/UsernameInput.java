package com.example.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UsernameInput extends Activity {
    public static final String USERNAME_MESSAGE = "com.example.quiz.USERNAME";
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username);
    }

//click on a button "Dalje" starts a game
        public void onClickButtonNext(View v){
            EditText textBox=findViewById(R.id.usernameInput);
            username=textBox.getText().toString();
            Intent intent = new Intent(this, ChooseCategory.class);
            intent.putExtra(USERNAME_MESSAGE, username);
            startActivity(intent);
        }

}
