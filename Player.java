import java.util.ArrayList;
import java.util.Objects;

public class Player {
   ArrayList<Card> cards;
   int[] inUse=new int[20];
   int health;
   Player(){
       health=400;
       characterEffects();

for(int i=0;i<20;i++){
    inUse[i]=0;
}
   }

    Character character;
    public void characterEffects(){
        for(int i=0;i<this.cards.size();i++){
            if(Objects.equals(cards.get(i).character,this.character)){
                cards.get(i).level+=2;
            }
        }
    }


}
