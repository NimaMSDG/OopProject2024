import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AI {
    Random rand = new Random();
    public int AiDeciotion(Card[][] plate, int[][] plateuse, ArrayList<Card> player,ArrayList<Card> opponent,int heal){
        boolean hasdefense=false;
        boolean hasheal=false;
        int defenseplace=-1;
        int healplace=-1;
        ArrayList<FreeSpace> options=new ArrayList<>();
        for(int i=0;i<21;i++){
            if(plateuse[1][i]==1 && plateuse[2][i]==0){
                FreeSpace f=new FreeSpace();
                f.place=i;
                f.card=plate[1][i];
                int k=i-1;
                boolean noright=false;
                while((plateuse[2][k]==0 && plateuse[1][k]==0) && k>=0){
                    k--;
                    f.leftfree+=1;
                    f.space+=1;
                }
                int l=0;
                for(int j=0;j<plate[1][i].duration;j++){
                    if(plateuse[2][j+i]==0 && plate[1][j+i].number==plate[1][i].number){
                        f.duration+=1;
                        l++;
                        f.space+=1;
                    }
                    else if(plateuse[2][j+i]==1){
                        noright=true;
                        continue;
                    }
                }
                k=i+plate[1][i].duration;
                if(!noright){
                    while((plateuse[2][k]==0 && plateuse[1][k]==0) && k<=20){
                        k++;
                        f.rightfree+=1;
                        f.space+=1;
                    }
                }
                options.add(f);
                i+=l;
            }


        }
ArrayList<playcard> finaloptions=new ArrayList<>();
        ArrayList<Card> specialcards=new ArrayList<>();
        for(int i=0;i<options.size();i++){
            boolean canwin=false;
            ArrayList<playcard> canplay=new ArrayList<>();
            FreeSpace f=options.get(i);
            for(int k=0;k<21;k++){
                if(plateuse[2][k]==0){
                    healplace=k;
                    continue;
                }
            }
            if(f.card.level>=25 && (f.duration+f.rightfree)>=3){
                defenseplace=f.place;
            }
            for(int j=0;j<player.size();j++){
                int n=player.get(i).number;
                if(n==20){
                    hasdefense=true;
                }
                if(n==22){
                    hasheal=true;
                }
                if(n==7 || n==10 || n==11 || n==16 || n==18 || n==23 || n==28 || n==30 ){
specialcards.add(player.get(j));
                }

                else{
                if(player.get(j).duration<=f.space && player.get(i).level>=f.card.level) {
                    canwin=true;
                    Card c = player.get(i);
                    playcard p = new playcard();
                    p.card=c;
                    if (f.leftfree == 0) {
p.place=f.place;
p.winnum=c.level-f.card.level;
                    }
                    else if((f.leftfree+f.duration)>=c.duration){
                        p.place=f.place+f.duration-c.duration;
                        p.winnum=c.level-f.card.level;
                    }
                    else if((f.leftfree+f.duration)<c.duration){
                        p.place=f.place-f.leftfree;
                    }
                    canplay.add(p);
                }
                }
            }
            if(canwin){
                int min=canplay.get(0).winnum;
                int minindex=0;
                for(int z=0;z<canplay.size();z++){
                   if(canplay.get(z).winnum<min){
                       min=canplay.get(z).winnum;
                       minindex=z;
                   }
                }
                finaloptions.add(canplay.get(minindex));
            }

        }
        if(finaloptions.size()==0){

            if(hasdefense){
               playcard p=new playcard();
               p.card=new Perfectdefense();
               p.place=defenseplace;
               finaloptions.add(p);
           }
           else if(hasheal && heal<100){
                playcard p=new playcard();
                p.card=new Feeltheforce();
               p.place=healplace;
               finaloptions.add(p);

           }
           else{
               int r=rand.nextInt(specialcards.size());

                playcard p=new playcard();
                p.card=specialcards.get(r);
                p.place=30;
                finaloptions.add(p);
            }
        }

        else{

            int min=finaloptions.get(0).winnum;
            int minindex=0;
            for(int m=1;m<finaloptions.size();m++){
                if(finaloptions.get(m).winnum<min){
                    finaloptions.remove(minindex);
                    minindex=m;
                    min=finaloptions.get(m).winnum;
                }
                else{
                    finaloptions.remove(m);
                }
            }

        }
return finaloptions.get(0).place+(100*(finaloptions.get(0).card.number));
    }
}
