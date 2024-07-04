import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class gameplate {
    Player player1=new Player();
    Player player2=new Player();
    ArrayList<Card> playercards1=new ArrayList<Card>();
    ArrayList<Card> playercards2=new ArrayList<Card>();
    ArrayList<Card> cardsingame1=new ArrayList<Card>();
    ArrayList<Card> cardsingame2=new ArrayList<Card>();
    Card[][] plate=new Card[2][21];
    int[][] plateuse=new int[2][21];
    int nonblock1;
    int nonblock2;
    boolean showaccess1;
    boolean showaccess2;

    Random rand = new Random();

    public void startcardgiven(){
        for(int i=0;i<5;i++){
            int r1= rand.nextInt(player1.cards.size());
            playercards1.add(player1.cards.get(r1));
            player1.cards.remove(r1);

            int r2= rand.nextInt(player2.cards.size());
            playercards2.add(player2.cards.get(r2));
            player2.cards.remove(r2);
        }
    }
    public void startplate(){
        int r1= rand.nextInt(21);
        int r2= rand.nextInt(21);
        for(int i=0;i<21;i++){
            plateuse[1][i]=0;
            plateuse[2][i]=0;

        }
        plateuse[1][r1]=-1;
        plateuse[1][r2]=-1;
        nonblock1=r1;
        nonblock2=r2;

    }
    public void refreshcard1(){
        int r1= rand.nextInt(player1.cards.size());
        playercards1.add(player1.cards.get(r1));
        player1.cards.remove(r1);
    }
    public void refreshcard2(){
        int r2= rand.nextInt(player2.cards.size());
        playercards2.add(player2.cards.get(r2));
        player2.cards.remove(r2);
    }
    public int findcart1byname(String n){
        int k=-1;
        for(int i=0;i<playercards1.size();i++){
            if(n.equals(playercards1.get(i).name)){
                k=i;
            }
        }
        return k;
    }
    public int findcart2byname(String n){
        int k=-1;
        for(int i=0;i<playercards2.size();i++){
            if(n.equals(playercards2.get(i).name)){
                k=i;
            }
        }
        return k;
    }



    public boolean playcardaccess1(Card c,int n){
        boolean access=true;
            if(n>=0){
                for(int i=0;i<c.duration;i++){
                   if(plateuse[1][i+n-1]!=0){
                       access=false;
                   }
                }
                if(!access){
                    System.out.println("The input place is not empty");
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                return true;
            }

    }
    public boolean playcardaccess2(Card c,int n){
        boolean access=true;
        if(n>=0){
            for(int i=0;i<c.duration;i++){
                if(plateuse[2][i+n-1]!=0){
                    access=false;
                }
            }
            if(!access){
                System.out.println("The input place is not empty");
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }

    }

    public void specialeffects(Card c,int n){
        //تظعیف کنندع حریف
        if(c.number==7){
            if(n==2){
            int r1=rand.nextInt(playercards1.size());
            int r2=r1+(rand.nextInt(playercards1.size()-1));
            playercards1.get(r1).level-=2;
            playercards1.get(r2).damage-=2*playercards1.get(r2).duration;
            System.out.println("Card "+playercards1.get(r1).name+"'s level decreasd");
            System.out.println("Card "+playercards1.get(r2).name+"'s damage decreasd");}
            else if(n==1){
                int r1=rand.nextInt(playercards2.size());
                int r2=r1+(rand.nextInt(playercards2.size()-1));
                playercards2.get(r1).level-=2;
                playercards2.get(r2).damage-=2*playercards2.get(r2).duration;
                System.out.println("Card "+playercards2.get(r1).name+"'s level decreasd");
                System.out.println("Card "+playercards2.get(r2).name+"'s damage decreasd");
            }
        }
        //تغییر دهنده مکان
        else if(c.number==18){
            ArrayList<Integer> freeblocks1=new ArrayList<Integer>();
            for(int i=0;i<21;i++){
                if(plateuse[1][i]==0){
                    freeblocks1.add(i);
                }
            }
            int r1= rand.nextInt(freeblocks1.size());
            plateuse[1][nonblock1]=0;
            plateuse[1][r1]=-1;
            nonblock1=r1;
            ArrayList<Integer> freeblocks2=new ArrayList<Integer>();
            for(int i=0;i<21;i++){
                if(plateuse[2][i]==0){
                    freeblocks2.add(i);
                }
            }
            int r2= rand.nextInt(freeblocks2.size());
            plateuse[2][nonblock2]=0;
            plateuse[2][r2]=-1;
            nonblock2=r2;
        }
        //tamir
        else if(c.number==23){
            if(n==1){
                plateuse[1][nonblock1]=0;
                nonblock1=-1;
            }
            else if(n==2){
                plateuse[2][nonblock2]=0;
                nonblock2=-1;
            }
        }
        //hazf card
        else if(c.number==10){
            if(n==1){
                int r1= rand.nextInt(playercards2.size());
                playercards1.add(playercards2.get(r1));
                playercards2.remove(r1);
            }
            else if(n==2){
                int r1= rand.nextInt(playercards1.size());
                playercards2.add(playercards1.get(r1));
                playercards1.remove(r1);
            }
        }
        //ghavi kardan
        else if(c.number==16){
            if(n==1){
                int r=rand.nextInt(cardsingame1.size());
                cardsingame1.get(r).level+=3;
                System.out.println("Card "+cardsingame1.get(r).name+" for player 1 buffed");
            }
            else if(n==2){
                int r=rand.nextInt(cardsingame2.size());
                cardsingame2.get(r).level+=3;
                System.out.println("Card "+cardsingame2.get(r).name+" for player 2 buffed");
            }
        }
    }
    public void spinner(Card c,int n){
        if(n==1){
            playercards1.add(c);
        }
        else if(n==2){
            playercards2.add(c);
        }
    }
    public void playingcard1(Card c,int n){
        if(n>=0){
            if(!isspecial(c) || c.number==20 || c.number==22){
        for(int i=0;i<c.duration;i++){
        plate[1][i+n-1]=c;
        plateuse[1][i+n-1]=1;
        if(!isspecial(c)){
            cardsingame1.add(c);
        }
        }}
        }
    }
    public void playingcard2(Card c,int n){
        cardsingame2.add(c);
        if(n>=0){
            if(!isspecial(c) || c.number==20 || c.number==22){
                for(int i=0;i<c.duration;i++){
                    plate[2][i+n-1]=c;
                    plateuse[2][i+n-1]=1;}}
        }
        if(!isspecial(c)){
            cardsingame1.add(c);
        }
    }
    public void playcard1(String r,String a,boolean b,int maxround,int type){
        Matcher m=gameplate.getmatcher(r,a);
        int n=Integer.parseInt(m.group(2));
        int x=-1;
        b=true;
        x=findcart1byname(m.group(1));
        if(x==-1){
            System.out.println("Couldn't find the card");
            b=false;
        }
        else{if(n>=0 && n<21 && type==1){

            Card c=playercards1.get(x);
            if(playcardaccess1(c,n)){
                playercards1.remove(x);
                refreshcard1();
                playingcard1(c,n);
                System.out.println("Card "+c.name+" played");
            }
            else{
                System.out.println("The input place is not empty");
                b=false;
            }

        }
        else if(n==-1){
            Card c=playercards1.get(x);
            if(type==2 && c.number!=28){
                System.out.println("invalid input");
                b=false;
            }
            if(c.number==28 && type==2){
                int y=findcart1byname(m.group(3));
                if(y==-1){
                    System.out.println("Couldn't find the card");
                    b=false;
                }
                Card c2=playercards1.get(y);
                spinner(c2,1);
                playercards1.remove(x);
                refreshcard1();
            }
            else if(c.number==11 && type==1){
                maxround-=1;
                refreshcard1();
            }
            else if(c.number==11 && type==1){
                showaccess2=false;
                refreshcard1();
            }
            else{
                if(type==2){
                    specialeffects(c,2);
                    playercards1.remove(x);
                    refreshcard1();
                    System.out.println("Card "+c.name+" played");}}
        }
        else{
            System.out.println("The input place is not in the box");
            b=false;
        }
        }
    }
    public void playcard2(String r,String a,boolean b,int maxround,int type){
        Matcher m=gameplate.getmatcher(r,a);
        int n=Integer.parseInt(m.group(2));
        int x=-1;
        b=true;
    x=findcart2byname(m.group(1));
        if(x==-1){
            System.out.println("Couldn't find the card");
            b=false;
        }
        else{if(n>=0 && n<21 && type==1){

            Card c=playercards2.get(x);
            if(playcardaccess2(c,n)){
                playercards2.remove(x);
                refreshcard2();
                playingcard2(c,n);
                System.out.println("Card "+c.name+" played");
            }
            else{
                System.out.println("The input place is not empty");
                b=false;
            }

        }
        else if(n==-1){
            Card c=playercards2.get(x);
            if(type==2 && c.number!=28){
                System.out.println("invalid input");
                b=false;
            }
            if(c.number==28 && type==2){
                int y=findcart2byname(m.group(3));
                if(y==-1){
                    System.out.println("Couldn't find the card");
                    b=false;
                }
                Card c2=playercards2.get(y);
                spinner(c2,2);
                playercards2.remove(x);
                refreshcard2();
            }
            else if(c.number==11 && type==1){
                maxround-=1;
            }
            else if(c.number==11 && type==1){
                showaccess1=false;
                playercards2.remove(x);
                refreshcard2();
            }
            else{
                if(type==2){
                specialeffects(c,2);
                playercards2.remove(x);
                refreshcard2();
                System.out.println("Card "+c.name+" played");}}
        }
        else{
            System.out.println("The input place is not in the box");
            b=false;
        }
        }
    }
    static boolean isspecial(Card c){
        int n=c.number;
        if(n==7 || n==10 || n==11 || n==16 || n==18 || n==20 || n==22 || n==23 || n==28 || n==30 ){
            return true;
        }
        else{
            return false;
        }

    }
    public void gamestart(){
        for(int i=0;i<21;i++){
            printtable();
            if((plateuse[1][i]==0 || plateuse[1][i]==-1) && plateuse[2][i]==1){
                if(!isspecial(plate[2][i])){
                    player1.health-=plate[2][i].segmentDamage();
                }
                else if(plate[2][i].number==22){
                    player2.health+=25;
                }
            }
            else if((plateuse[2][i]==0 || plateuse[2][i]==-1)  && plateuse[1][i]==1){
                if(!isspecial(plate[1][i])){
                    player2.health-=plate[1][i].segmentDamage();
                }
                else if(plate[1][i].number==22){
                    player1.health+=25;
                }
            }
            else if(plateuse[1][i]==1 && plateuse[2][i]==1){
                if(!isspecial(plate[1][i]) && !isspecial(plate[2][i])){
                    if(plate[1][i].level > plate[2][i].level){
                        player2.health-=plate[1][i].segmentDamage();
                    }
                    else if(plate[1][i].level < plate[2][i].level){
                        player1.health-=plate[2][i].segmentDamage();
                    }
                }
                else if(plate[1][i].number==22 && (!isspecial(plate[2][i])|| plate[2][i].number==20)){
                    player1.health+=25;
                }
                else if(plate[2][i].number==22 && (!isspecial(plate[1][i])|| plate[1][i].number==20)){
                    player2.health+=25;
                }
                else if(plate[2][i].number==22 && plate[1][i].number==22){
                    player2.health+=25;
                    player1.health+=25;
                }


            }
            System.out.println("Block number "+(i+1)+" played");
        }

    }

    int heal1;
    int heal2;
    gameplate(Player p1,Player p2) {
        player1=p1;
        player2=p2;
        startplate();
        startcardgiven();
        showaccess1=true;
        showaccess2=true;
    }

    int plateleanth;
    int[][] blocks=new int[21][2];


     void printtable(){
        for(int i=0;i<playercards1.size();i++){
            System.out.print(playercards1.get(i)+" | ");
        }
         System.out.println();
         System.out.print("player 1 :");
         for(int i=0;i<21;i++){
             if(plateuse[1][i]==1){
                 if(plate[1][i].number==20){
                     System.out.print("SH | ");
                 }
                 else if(plate[1][i].number==22){
                     System.out.print("HE | ");
                 }
                 else if(plate[1][i].number==20){
             System.out.print(plate[1][i].level+" | ");}}
             else if(plateuse[1][i]==0){
                 System.out.print("00 | ");
             }
             else if(plateuse[1][i]==-1){
                 System.out.print("   | ");
             }

         }
         System.out.print("    "+player1.health);
         System.out.println();
         for(int i=0;i<21;i++){
             if(plateuse[1][i]>0){
                 System.out.println(plate[1][i].segmentDamage()+" | ");}
             else if(plateuse[1][i]==0){
                 System.out.println("00 | ");
             }
             else if(plateuse[1][i]==-1){
                 System.out.println("   | ");
             }

         }
         System.out.println();
         System.out.print("player 2 :");
         for(int i=0;i<21;i++){
             if(plateuse[2][i]==1){
                 if(plate[2][i].number==20){
                     System.out.print("SH | ");
                 }
                 else if(plate[2][i].number==22){
                     System.out.print("HE | ");
                 }
                 else if(plate[2][i].number==20){
                     System.out.print(plate[1][i].level+" | ");}}
             else if(plateuse[2][i]==0){
                 System.out.println("00 | ");
             }
             else if(plateuse[1][i]==-1){
                 System.out.println("   | ");
             }

         }
         System.out.print("    "+player2.health);
         System.out.println();
         for(int i=0;i<21;i++){
             if(plateuse[1][i]>0){
                 System.out.println(plate[1][i].segmentDamage()+" | ");}
             else if(plateuse[1][i]==0){
                 System.out.println("00 | ");
             }
             else if(plateuse[1][i]==-1){
                 System.out.println("   | ");
             }

         }
         for(int i=0;i<player2.cards.size();i++){
             if(player2.inUse[i]==0){
             System.out.print(player1.cards.get(i)+"|");}
         }
    }
    static public Matcher getmatcher(String reg, String input){
        Pattern p=Pattern.compile(reg);
        Matcher m = p.matcher(input);
        m.find();
        return m;
    }

  static public void rungame(Player p1,Player p2){
      Scanner ali=new Scanner(System.in);
         String r1="set ([A-Za-z//s]+) to ([0-9]+)";
         String r2="set ([A-Za-z//s]+) to ([0-9]+) ([A-Za-z//s])";
         boolean endgame=false;
         int roundnumber=1;
         int maxround=4;
         p1.health=400;
         p2.health=400;
         while(!endgame){
             if(roundnumber<=maxround){
                 gameplate g=new gameplate(p1,p2);
                 for(int i=0;i<4;i++){
                     g.printtable();
                     boolean b=false;
                     while(!b){
                         System.out.print("player 1 turn:");
                        String a=ali.nextLine();
                         if(a.matches(r1)){
                     g.playcard1(r1,a,b,maxround,1);}
                             else if(a.matches(r2)){
                                 g.playcard1(r1,a,b,maxround,2);
                             }

                     else{
                             System.out.println("invalid input");
                         }}
                     b=false;
                     while(!b){
                         System.out.print("player 2 turn:");
                         String a=ali.nextLine();
                         if(a.matches(r1)){
                             g.playcard2(r1,a,b,maxround,1);}
                             else if(a.matches(r2)){
                                 g.playcard2(r1,a,b,maxround,2);
                             }
                     else{
                             System.out.println("invalid input");}
                     }
                 }
                 System.out.println("round "+roundnumber+" finished");
                 roundnumber+=1;
                 System.out.println("ready to start the game");

             }
         }
   }

}
