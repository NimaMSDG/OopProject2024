package com.example.starwars_theageofrebellion;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ShopViewController implements Initializable {

    private static final double FRAMES_PER_SECOND = 0.1;
    Stage stage;
    User user;
    int num;

    @FXML
    AnchorPane anchorPane;
    Timer timer;

    ShopViewController(Stage stage,User user,int num){
        this.stage=stage;
        this.user=user;
        this.num=num;
    }

    DataBase dataBase = new DataBase();
    ArrayList<Card> ownedCards = new ArrayList<>();
    ArrayList<Card> unOwnedCards = new ArrayList<>();
    ArrayList<Card> mainCards = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ownedCards = dataBase.playerCards(user);
        for (Card card:dataBase.allCards()){
            if (getCardByName(card.name,ownedCards)==null) unOwnedCards.add(card);
        }
        if (num==1) mainCards=unOwnedCards;
        else mainCards=ownedCards;

        int ip=0;
        int height = 100;
        for (int i=0;i<mainCards.size();i++){
            if (ip==6) {
                ip=0;
                height+=200;
            }
            Card card = mainCards.get(i);
            card.cardView.setLayout(100+ip*(80+40),height);
            ip++;
            anchorPane.getChildren().add(card.cardView.rectangle);
            anchorPane.getChildren().add(card.cardView.level);
            anchorPane.getChildren().add(card.cardView.damage);
        }
        startTimer();

    }

    public void startTimer(){
        this.timer=new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {

                        for (Card card:mainCards){
                            if (card.cardView.rectangle.isPressed()){

                            }
                        }

                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(10.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }







    public Card getCardBuyNumber(int number,ArrayList<Card> cards){
        return cards.get(number-1);
    }

    public Card getCardByName(String name,ArrayList<Card> cards){
        for (Card card:cards){
            if (card.name.equals(name)) return card;
        }
        return null;
    }
}
