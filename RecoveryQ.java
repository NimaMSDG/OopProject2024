package com.example.starwars_theageofrebellion;

public class RecoveryQ {
    String Question;
    String Answer;

    RecoveryQ(String Question,String Answer){
        this.Question=Question;
        this.Answer=Answer;
    }

    RecoveryQ(int Qnumber){
        if (Qnumber==1){
            Question="What is your father’s name ?";
        }
        if (Qnumber==2){
            Question="What is your favourite color ?";
        }
        if (Qnumber==3){
            Question="What was the name of your first pet ?";
        }
    }

    public void setAnswer(String Answer){
        this.Answer=Answer;
    }


    public boolean checkAnswer(String answer){
        return Answer.equalsIgnoreCase(answer);
    }
}