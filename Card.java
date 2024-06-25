import java.util.ArrayList;
import java.util.Arrays;

public abstract class Card implements Comparable<Card> , Cloneable{
    String name;
    int duration;
    int damage;
    int price;
    int level;
    int id;
    Type type;
    Character character;
    public abstract String properties();
    public abstract String doAbility(ArrayList<Card> map);




    public void levelUp(){
        level++;
    }


    @Override
    public Card clone() {
        try {
            Card clone = (Card) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public enum Type{
        Attack,Defence,Spell;
    }


    //sort cards arraylist
    @Override
    public int compareTo(Card card) {
        Integer n1=1,n2=1;
        if (this.type.equals(Type.Attack)) n1=1;
        if (this.type.equals(Type.Defence)) n1=2;
        if (this.type.equals(Type.Spell)) n1=3;
        if (card.type.equals(Type.Attack)) n2=1;
        if (card.type.equals(Type.Defence)) n2=2;
        if (card.type.equals(Type.Spell)) n2=3;
        return n1.compareTo(n2);
    }

    public ArrayList<Card> sortCards(ArrayList<Card> cards){
        Card[] cards1 = new Card[cards.size()];
        for (int i=0;i<cards.size();i++){
            cards1[i] = cards.get(i);
        }
        Arrays.sort(cards1);
        ArrayList<Card> result = new ArrayList<>();
        for (int i=0;i<cards.size();i++){
            result.add(cards1[i]);
        }
        return result;
    }
}
