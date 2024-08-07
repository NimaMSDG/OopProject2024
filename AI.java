import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AI {
    Random rand = new Random();
    public int AiDeciotion(Card[][] plate, int[][] plateuse, ArrayList<Card> player,int noneblock,int heal){
        boolean hasdefense=false;
        boolean hasheal=false;
        int playnumber=0;
        int defenseplace=-1;
        int healplace=-1;
        ArrayList<FreeSpace> options=new ArrayList<>();
        for(int i=0;i<21;i++){
            if(plateuse[0][i]==1 && plateuse[1][i]==0){
                FreeSpace f=new FreeSpace();
                f.place=i;
                f.card=plate[0][i];
                int k=i-1;
                boolean noright=false;
                while(plateuse[1][k]==0 && k>=0){
                    k--;
                    f.leftfree+=1;
                    f.space+=1;
                }
                int l=0;
                for(int j=0;j<plate[0][i].duration;j++){
                    if(plateuse[1][j+i]==0 && plate[0][j+i].number==plate[0][i].number){
                        f.duration+=1;
                        l++;
                        f.space+=1;
                    }
                    else if(plateuse[1][j+i]==1){
                        noright=true;
                        continue;
                    }
                }
                k=i+plate[1][i].duration;
                if(!noright){
                    while(plateuse[1][k]==0  && k<=20){
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
        ArrayList<playcard> randomplay=new ArrayList<>();
        for(int k=0;k<21;k++){
            if(plateuse[1][k]==0){
                healplace=k;
                continue;
            }
        }
        if(options.size()>0){
        for(int i=0;i<options.size();i++){
            boolean canwin=false;
            ArrayList<playcard> canplay=new ArrayList<>();
            FreeSpace f=options.get(i);

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
                if(player.get(j).duration<=f.space) {
                    if(player.get(i).level>f.card.level){
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
                        p.winnum=c.level-f.card.level;
                    }
                    canplay.add(p);}

                }
                if(f.leftfree>=player.get(i).duration){
                    playcard p=new playcard();
                    p.card=player.get(i);
                    p.place=f.place-f.leftfree;
                    randomplay.add(p);
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

        }}

        else{

            for(int i=0;i<player.size();i++){
                int n=player.get(i).number;
                if(n==7 || n==10 || n==11 || n==16 || n==18 || n==20 || n==22 || n==23 || n==28 || n==30 ){
                }
                else{
                    for(int j=0;j<21;j++){
                        if(j<=noneblock && j+player.get(j).duration>=noneblock){
                        }
                        else{
                            return 100*player.get(i).number+j;
                        }
                    }
                }
            }

        }
        int r1=rand.nextInt(100);
        int finalindex=-1;
        if(finaloptions.size()==0){
            if(hasdefense && defenseplace>=0){
               playcard p=new playcard();
               p.card=new Perfectdefense();
               p.place=defenseplace;
               finaloptions.add(p);
           }
           else if(hasheal && heal<100 ){
                playcard p=new playcard();
                p.card=new Feeltheforce();
               p.place=healplace;
               finaloptions.add(p);

           }

           else{
               if(specialcards.size()==0){
    if(randomplay.size()>0){
    playcard p=randomplay.get(0);
    finaloptions.add(p);
    }
                }
               else{
               int r=rand.nextInt(specialcards.size());

                playcard p=new playcard();
                p.card=specialcards.get(r);
                p.place=30;
                finaloptions.add(p);
            }}
return finaloptions.get(0).place+(100*finaloptions.get(0).card.number);
        }
        else{

            int min=finaloptions.get(0).winnum;
            int minindex=0;
            for(int m=1;m<finaloptions.size();m++){
                if(finaloptions.get(m).winnum<min){
                    minindex=m;
                    min=finaloptions.get(m).winnum;
                }
                finalindex=minindex;
            }
            return finaloptions.get(minindex).place+(100*(finaloptions.get(minindex).card.number));
        }

    }
}
