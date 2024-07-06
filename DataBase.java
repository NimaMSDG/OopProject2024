import java.util.ArrayList;

public class DataBase {
    static ArrayList<Card> cards = new ArrayList<>();
    static ArrayList<Card> cards1 = new ArrayList<>();

    DataBase(){
        Card c1 = new Card("c1", 30, 3, 36, 1);
        Card c2 = new Card("c2", 39, 1, 20, 2);
        Card c3 = new Card("c3", 32, 4, 3, 3);
        Card c4 = new Card("c4", 37, 2, 36, 1);
        Card c5 = new Card("c5", 35, 2, 36, 1);
        Card c6 = new Card("c6", 36, 3, 36, 1);
        Card c7 = new Card("c7", 32, 4, 40, 1);
        Card c8 = new Card("c8", 33, 5, 35, 1);
        Card c9 = new Card("c9", 31, 1, 36, 1);
        Card c10 = new Card("c10", 30, 3, 30, 1);
        cards.add(c1);cards.add(c2);cards.add(c3);cards.add(c4);cards.add(c5);
        cards.add(c6);cards.add(c7);cards.add(c8);cards.add(c9);cards.add(c10);
        Card c11 = new Card("c11", 30, 3, 36, 1);
        Card c21 = new Card("c21", 39, 1, 20, 2);
        Card c31 = new Card("c31", 32, 4, 3, 3);
        Card c41 = new Card("c41", 37, 2, 36, 1);
        Card c51 = new Card("c51", 35, 2, 36, 1);
        Card c61 = new Card("c61", 36, 3, 36, 1);
        Card c71 = new Card("c71", 32, 4, 40, 1);
        Card c81 = new Card("c81", 33, 5, 35, 1);
        Card c91 = new Card("c91", 31, 1, 36, 1);
        Card c101 = new Card("c101", 30, 3, 30, 1);
        cards1.add(c11);cards1.add(c21);cards1.add(c31);cards1.add(c41);cards1.add(c51);
        cards1.add(c61);cards1.add(c71);cards1.add(c81);cards1.add(c91);cards1.add(c101);
    }
}
