package com.example.starwars_theageofrebellion;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NormalCards extends Card{

    NormalCards(String name, int level, int damage, int duration, int price, int upgradeLevel, int upgradeCost, Character character,String imgPath) {
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.duration = duration;
        this.price = price;
        this.upgradeLevel = upgradeLevel;
        this.upgradeCost = upgradeCost;
        this.character = character;
        this.type = Type.normal;
        number=1;
        try{
            FileInputStream fileInputStream = new FileInputStream(imgPath);
            Image image1 = new Image(fileInputStream);
            image = new ImagePattern(image1);
        }

        catch (Exception e){System.out.println(name);}
    }
    NormalCards(String name, int level, int damage, int duration, int price, int upgradeLevel, int upgradeCost, Character character,Type type,int number,String imgPath)  {
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.duration = duration;
        this.price = price;
        this.upgradeLevel = upgradeLevel;
        this.upgradeCost = upgradeCost;
        this.character = character;
        this.type = type;
        this.number=number;
        try{
        FileInputStream fileInputStream = new FileInputStream(imgPath);
        Image image1 = new Image(fileInputStream);
        image = new ImagePattern(image1);
        }

        catch (Exception e){System.out.println(name);}

    }

    @Override
    public String properties() {
        return null;
    }
}
