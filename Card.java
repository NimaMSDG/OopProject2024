public class Card implements Cloneable{

    Card(String name,int level,int duration,int damage,int number){
        this.name=name;
        this.level=level;
        this.damage=damage;
        this.duration=duration;
        this.number=number;
        character = Character.DarthVader;
    }

    int duration,damage,level,number;
    String name;
    Character character;
    Type type;


    public int segmentDamage(){
        return damage/duration;
    }

    //public abstract void doAbility();

    public String properties(){
        return "no info";
    }


    public enum Character{
        DarthVader,Luke,BobaFett,Mandalorian;
    }
    public enum Type{
        spell,normal;
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
}
