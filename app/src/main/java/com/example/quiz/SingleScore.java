package com.example.quiz;

public class SingleScore{
    private String scoreUsername;
    private String scoreResult;

    public SingleScore(String sID,String sU, String sR){
        scoreUsername=sID+". "+sU;
        scoreResult=sR;
    }
    public String getScoreUsername(){
        return scoreUsername;
    }
    public String getScoreResult(){
        return scoreResult;
    }

}