 abstract public class Card {

    int duration,damage,level,number;
    String name;
    Character character;


    public int segmentDamage(){
        return damage/duration;
    }


    Card(){

    }


    public enum Character{
        DarthVader,Luke,BobaFett,Mandalorian;
    }
}
