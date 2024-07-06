import java.util.ArrayList;
import java.util.Objects;

public class Player {

    String username;
    ArrayList<Card> cards;
    int health;
    Player(String username){
        this.username=username;
        health=400;
    }

    Character character;



}