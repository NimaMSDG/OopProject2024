package com.example.starwars_theageofrebellion;


import java.util.ArrayList;
import java.util.Random;

public class Player {
    String username;
    ArrayList<Card> cards;
    int health;
    Character character;


    Player(User user){
        username=user.Username;
        cards = copyOf(user.Deck());
        health = user.HP();
    }
    Player(Character character){
        username=character.toString();
        cards=new DataBase().existedCards;
        if (character==Character.Mandalorian) health=100;
        if (character==Character.BobaFett) health=125;
        if (character==Character.Luke) health=150;
        if (character==Character.DarthVader) health=250;
        this.character=character;
    }

    Player(){
        username="bot";
        health=50;
    }

    public void setCharacter(Character character){
        this.character=character;
    }

    public ArrayList<Card> copyOf(ArrayList<Card> cards){
        ArrayList<Card> storage = new ArrayList<>();
        for (Card card:cards){
            storage.add(card.clone());
        }
        return storage;
    }






}

