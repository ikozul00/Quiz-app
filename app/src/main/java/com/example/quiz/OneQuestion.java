package com.example.quiz;

public class OneQuestion {
    public String text;
    public String answer1;
    public String answer2;
    public String answer3;
    public String correct_answer;

    public OneQuestion(){}
    public OneQuestion(String question_text,String question_answer1, String question_answer2, String question_answer3, String question_correct_answer){
        text=question_text;
        answer1=question_answer1;
        answer2=question_answer2;
        answer3=question_answer3;
        correct_answer=question_correct_answer;
    }

}
