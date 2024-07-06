import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyController {

    AI ai = new AI();
    DataBase dataBase = new DataBase();
    MyController(){
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Card> cards1 = new ArrayList<>();
        Card c1 = new Card("c1", 30, 3, 36, 1);
        Card c2 = new Card("c2", 39, 1, 20, 2);
        Card c3 = new Card("c3", 32, 4, 32, 3);
        Card c4 = new Card("c4", 37, 2, 36, 1);
        Card c5 = new Card("c5", 35, 2, 36, 1);
        Card c6 = new Card("c6", 36, 3, 36, 1);
        Card c7 = new Card("c7", 32, 4, 40, 1);
        Card c8 = new Card("c8", 33, 5, 35, 1);
        Card c9 = new Card("c9", 31, 1, 36, 1);
        Card c10 = new Card("c10", 30, 3, 30, 10);
        cards.add(c1);cards.add(c2);cards.add(c3);cards.add(c4);cards.add(c5);
        cards.add(c6);cards.add(c7);cards.add(c8);cards.add(c9);cards.add(c10);
        Card c11 = new Card("c11", 30, 3, 36, 1);
        Card c21 = new Card("c21", 39, 1, 20, 2);
        Card c31 = new Card("c31", 32, 4, 36, 3);
        Card c41 = new Card("c41", 37, 2, 36, 1);
        Card c51 = new Card("c51", 35, 2, 36, 1);
        Card c61 = new Card("c61", 36, 3, 36, 1);
        Card c71 = new Card("c71", 32, 4, 40, 1);
        Card c81 = new Card("c81", 33, 5, 35, 1);
        Card c91 = new Card("c91", 31, 1, 36, 1);
        Card c101 = new Card("c101", 30, 3, 30, 10);
        cards1.add(c11);cards1.add(c21);cards1.add(c31);cards1.add(c41);cards1.add(c51);
        cards1.add(c61);cards1.add(c71);cards1.add(c81);cards1.add(c91);cards1.add(c101);
        player1.cards=cards;
        player2.cards=cards1;
    }
    Player player1=new Player("nima");
    Player player2=new Player("alireza");
    ArrayList<Card> playercards1=new ArrayList<Card>();
    ArrayList<Card> playercards2=new ArrayList<Card>();
    ArrayList<Card> cardsingame1=new ArrayList<Card>();
    ArrayList<Card> cardsingame2=new ArrayList<Card>();
    Card[][] plate=new Card[2][21];
    int[][] plateuse=new int[2][21];
    int[][] MAIN_PLATE = new int[2][21];
    int nonblock1;
    int nonblock2;
    boolean showaccess1;
    boolean showaccess2;

    Random rand = new Random();

    int NUMBER_OF_ROUND = 4;
    Date date;
    boolean gameOver() {return player1.health*player2.health<=0;}

    public void run() throws InterruptedException {
        date = new Date();


        Scanner scanner = new Scanner(System.in);
        String r1 = "-Select card number ([0-9]+) player ([0-9]+)";
        String r2 = "-Placing card number ([0-9]+) in block ([0-9]+)";
        String input;


        startcardgiven();
        startplate();
        while (!gameOver()){
            plateBackUp(MAIN_PLATE,plateuse);
            plate = new Card[2][29];
            printTable(0);
            for (int i = 0; i < NUMBER_OF_ROUND; i++) {
                System.out.println("Player 1 turn :");
                while (true){
                    input = scanner.nextLine();
                    if (input.matches(r1)) {
                        showCardInfo(getmatcher(r1,input));
                    } else if (input.matches(r2)) {
                        if (playCard1(getmatcher(r2,input))){
                            Card card = getCardByMatcher(getmatcher(r2,input),1);
                            refreshcard1();
                            upgradeMiddleCard(playercards1,card);
                            break;
                        }
                    }else System.out.println("Invalid command");
                }
                updateMap();
                printTable(0);
                System.out.println("Player 2 turn :");
                while (true){
                    input = scanner.nextLine();
                    if (input.matches(r1)) {
                        showCardInfo(getmatcher(r1,input));
                    } else if (input.matches(r2)) {
                        if (playCard2(getmatcher(r2,input))) {
                            Card card = getCardByMatcher(getmatcher(r2,input),2);
                            refreshcard2();
                            upgradeMiddleCard(playercards2,card);
                            break;
                        }
                    }else System.out.println("Invalid command");
                }
                updateMap();
                printTable(0);
            }
            System.out.println("--------------------------------------------------------------------------------\n.\n.\n.\nTIMELINE START MOVING\n.\n.\n.\n--------------------------------------------------------------------------------");
            TimeUnit.SECONDS.sleep(5);//delay

            startTimeline();
            if (gameOver()){
                if (player1.health<=0){

                }
            }
            if (NUMBER_OF_ROUND!=4) NUMBER_OF_ROUND=4;
            System.out.println("--------------------------------------------------------------------------------\n.\n.\n.\nTIMELINE ENDED\n.\n.\n.\n--------------------------------------------------------------------------------");
        }
    }


    public void startTimeline() throws InterruptedException {
        for (int i=0;i<21 && !gameOver();i++){
            if (plateuse[1][i]==1) player1.health-=plate[1][i].segmentDamage();
            if (plateuse[0][i]==1) player2.health-=plate[0][i].segmentDamage();
            //if(plate[0][i].number==22){
            //    player1.health+=25;
            //}
            //if(plate[1][i].number==22){
            //    player2.health+=25;
            //}
            System.out.println();
            printTable(i+1);
            TimeUnit.SECONDS.sleep(3);//delay
        }
    }


    public void showCardInfo(Matcher matcher){
        int player = Integer.parseInt(matcher.group(2));
        int number = Integer.parseInt(matcher.group(1));
        number--;
        if (player==1){
            System.out.println(playercards1.get(number).properties());
        }if (player==2){
            System.out.println(playercards2.get(number).properties());
        }
    }

    public boolean playCard1(Matcher matcher){
        int number = Integer.parseInt(matcher.group(1));
        int index = Integer.parseInt(matcher.group(2));
        number--;index--;

        int type=1;
        if (index==-1){
            type = 2;
        }

        Card card = playercards1.get(number);
        if (isspecial(card)&&type==1){
            System.out.println("This card is spell");
            return false;
        }
        if (!isspecial(card)&&type==2){
            System.out.println("This card isn't spell");
            return false;
        }

        if (number>playercards1.size()-1 || number<0){
            System.out.println("Card number out of range");
            return false;
        }
        if (index>21-1 || index<-1){
            System.out.println("Block index out of range");
            return false;
        }

        if (type==1){
            index++;
            if(playcardaccess1(card,index)){
                //playercards1.remove(x);
                playingcard1(card,index);
                System.out.println("Card "+card.name+" played");
                return true;
            }
            else{
                System.out.println("The input place is not empty");
                return false;
            }
        } else {

            playingcard1(card,-1);
            specialeffects(card,1);
            //playercards1.remove(x);
            System.out.println("Card "+card.name+" played");
            updateMap();
            printTable(0);
            return false;
        }

    }

    public boolean playCard2(Matcher matcher){
        int number = Integer.parseInt(matcher.group(1));
        int index = Integer.parseInt(matcher.group(2));
        number--;index--;

        int type=1;
        if (index==-1){
            type = 2;
        }

        Card card = playercards2.get(number);
        if (isspecial(card)&&type==1){
            System.out.println("This card is spell");
            return false;
        }
        if (!isspecial(card)&&type==2){
            System.out.println("This card isn't spell");
            return false;
        }

        if (number>playercards2.size()-1 || number<0){
            System.out.println("Card number out of range");
            return false;
        }
        if (index>21-1 || index<-1){
            System.out.println("Block index out of range");
            return false;
        }

        if (type==1){
            index++;
            if(playcardaccess2(card,index)){
                //playercards1.remove(x);
                playingcard2(card,index);
                System.out.println("Card "+card.name+" played");
                return true;
            }
            else{
                System.out.println("The input place is not empty");
                return false;
            }
        } else {
            playingcard2(card,-1);
            specialeffects(card,2);
            //playercards1.remove(x);

            System.out.println("Card "+card.name+" played");
            updateMap();
            printTable(0);
            return false;
        }
    }



    public boolean playcardaccess1(Card c,int n){
        boolean access=true;
        if(n>=0){
            for(int i=0;i<c.duration;i++){
                if(plateuse[0][i+n-1]!=0){
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






    void printTable(int TimelineIndex){
        boolean inTimeLine = TimelineIndex!=0;

        System.out.println(ai.AiDeciotion(plate,plateuse,playercards1,playercards2,player1.health));

        //
        printCards(playercards1);
        //
        System.out.println();
        System.out.printf("player 1 : %s\n",player1.username);
        if (inTimeLine){
            for (int i = 0; i < 6*TimelineIndex-1; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");
        }
        else System.out.println();
        for(int i=0;i<21;i++){
            if(plateuse[0][i]==1){
                if(plate[0][i].number==20){
                    System.out.print("| SH |");
                }
                else if(plate[0][i].number==22){
                    System.out.print("| HE |");
                }
                else  {
                    System.out.print("| "+plate[0][i].level+" |");}}
            else if(plateuse[0][i]==0){
                System.out.print("|    |");
            }
            else if(plateuse[0][i]==-1){
                System.out.print("| ## |");
            }
            else if(plateuse[0][i]==-2){
                System.out.print("| 00 |");
            }

        }
        System.out.print("    "+player1.health);
        System.out.println();
        for(int i=0;i<21;i++) {
            if (plateuse[0][i] > 0) {
                if (plate[0][i].segmentDamage() > 9) System.out.print("| " + plate[0][i].segmentDamage() + " |");
                else System.out.print("| " + plate[0][i].segmentDamage() + "  |");
            } else if (plateuse[0][i] == 0) {
                System.out.print("|    |");
            } else if (plateuse[0][i] == -1) {
                System.out.print("| ## |");
            }

        }

        System.out.println("    "+sumOfDamage(plate[0]));

        ////

        System.out.println("----------------------------------------------------------------"+
                "----------------------------------------------------------------");

        for(int i=0;i<21;i++){
            if(plateuse[1][i]==1){
                if(plate[1][i].number==20){
                    System.out.print("| SH |");
                }
                else if(plate[1][i].number==22){
                    System.out.print("| HE |");
                }
                else {
                    System.out.print("| "+plate[1][i].level+" |");}}
            else if(plateuse[1][i]==0){
                System.out.print("|    |");
            }
            else if(plateuse[1][i]==-1){
                System.out.print("| ## |");
            }

        }
        System.out.print("    "+player2.health);
        System.out.println();
        for(int i=0;i<21;i++){
            if(plateuse[1][i]>0){
                if (plate[1][i].segmentDamage()>9) System.out.print("| "+plate[1][i].segmentDamage()+" |");
                else System.out.print("| "+plate[1][i].segmentDamage()+"  |");
            }
            else if(plateuse[1][i]==0){
                System.out.print("|    |");
            }
            else if(plateuse[1][i]==-1){
                System.out.print("| ## |");
            }

        }
        System.out.println("    "+sumOfDamage(plate[1]));
        if (inTimeLine){
            for (int i = 0; i < 6*TimelineIndex-1; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");
        }
        else System.out.println();
        System.out.printf("player 2 : %s\n",player2.username);
        System.out.println();
        //
        printCards(playercards2);
        //

    }

    static public Matcher getmatcher(String reg, String input){
        Pattern p=Pattern.compile(reg);
        Matcher m = p.matcher(input);
        m.find();
        return m;
    }

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
            plateuse[0][i]=0;
            plateuse[1][i]=0;

        }
        plateuse[0][r1]=-1;
        plateuse[1][r2]=-1;             // 1 ---> 2
        nonblock1=r1;
        nonblock2=r2;

        plateBackUp(plateuse,MAIN_PLATE);
    }
    public void refreshcard1(){
        if (playercards1.size()==5) return;
        int r1= rand.nextInt(player1.cards.size());
        playercards1.add(player1.cards.get(r1));
        player1.cards.remove(r1);
        refreshcard1();
    }
    public void refreshcard2(){
        if (playercards2.size()==5) return;
        int r2= rand.nextInt(player2.cards.size());
        playercards2.add(player2.cards.get(r2));
        player2.cards.remove(r2);
        refreshcard2();
    }

    public void playingcard1(Card c,int n){
        if(n>=0){
            if(!isspecial(c) || c.number==20 || c.number==22){
                for(int i=0;i<c.duration;i++){

                    plate[0][i+n-1]=c.clone();
                    plateuse[0][i+n-1]=1;

                }
            }
        }
        playercards1.remove(c);

        if(!isspecial(c)){
            cardsingame1.add(c);
        }
    }
    public void playingcard2(Card c,int n){
        if(n>=0){
            if(!isspecial(c) || c.number==20 || c.number==22){
                for(int i=0;i<c.duration;i++){
                    plate[1][i+n-1]=c.clone();
                    plateuse[1][i+n-1]=1;
                }}
        }
        playercards2.remove(c);

        if(!isspecial(c)){
            cardsingame2.add(c);
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
                if(plateuse[0][i]==0){
                    freeblocks1.add(i);
                }
            }
            int r1= freeblocks1.get(rand.nextInt(freeblocks1.size()));
            plateuse[0][nonblock1]=0;
            plateuse[0][r1]=-1;
            nonblock1=r1;
            ArrayList<Integer> freeblocks2=new ArrayList<Integer>();
            for(int i=0;i<21;i++){
                if(plateuse[1][i]==0){
                    freeblocks2.add(i);
                }
            }
            int r2= freeblocks2.get(rand.nextInt(freeblocks2.size()));
            plateuse[1][nonblock2]=0;
            plateuse[1][r2]=-1;
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
        //hazf card//
        else if(c.number==10){
            if(n==1){
                refreshcard1();
                int r1= rand.nextInt(playercards2.size());
                playercards1.add(playercards2.get(r1));
                playercards2.remove(r1);
            }
            else if(n==2){
                refreshcard2();
                int r1= rand.nextInt(playercards1.size());
                playercards2.add(playercards1.get(r1));
                playercards1.remove(r1);
            }
        }
        //ghavi kardan
        else if(c.number==16){
            if(n==1){
                int r=rand.nextInt(cardsingame1.size());
                String name = cardsingame1.get(r).name;
                for (Card card : cardsingame1){
                    if (card.name.equals(name)) card.level+=3;
                }
                System.out.println("Card "+cardsingame1.get(r).name+" for player 1 buffed");
            }
            else if(n==2){
                int r=rand.nextInt(cardsingame2.size());
                String name = cardsingame2.get(r).name;
                for (Card card : cardsingame2){
                    if (card.name.equals(name)) card.level+=3;
                }
                System.out.println("Card "+cardsingame2.get(r).name+" for player 2 buffed");
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

    public void updateMap(){
        for (int i = 0; i < 21; i++) {
            if (plateuse[0][i]!=0 && plateuse[1][i]!=0){
                if (plateuse[0][i]!=-1 && plateuse[1][i]!=-1){
                    if (plateuse[0][i]!=-2 || plateuse[1][i]!=-2){
                        Card card1 = plate[0][i];
                        Card card2 = plate[1][i];
                        if (card1.level>card2.level){
                            plate[1][i].damage = 0;
                        }else if (card1.level<card2.level){
                            plate[0][i].damage = 0;
                        }
                        else {plate[0][i].damage = 0;plate[1][i].damage=0;}
                    }
                }
            }
        }
    }


    public Card getCardByMatcher(Matcher matcher,int Nop){
        int index = Integer.parseInt(matcher.group(2));
        return plate[Nop-1][index-1];
    }

    public void printCards(ArrayList<Card> cards){
        for (Card card: cards){
            System.out.print(card.name+" ");
        }
        System.out.println();
        for (Card card: cards){
            System.out.printf("|%s %s| ",card.level,card.damage);
        }
        System.out.println();
        for (Card card: cards){
            System.out.print("|");
            for (int i = 0; i < card.duration; i++) {
                System.out.print("*");
            }
            for (int i = 0; i < 5-card.duration; i++) {
                System.out.print(" ");
            }
            System.out.print("| ");
        }
        System.out.println();
        for (Card card: cards){
            System.out.print("|     | ");
        }
        System.out.println();
        for (Card card: cards){
            if (card.type== Card.Type.spell) System.out.print("|Spell| ");
            else System.out.print("| A/D | ");
        }
        System.out.println();
    }

    public void plateBackUp(int[][] original ,int[][] copy){
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
    }

    public int sumOfDamage(Card[] cards){
        int sum=0;
        for (Card card : cards){
            if (card!=null) sum+=card.segmentDamage();
        }
        return sum;
    }

    public void upgradeMiddleCard(ArrayList<Card> cards,Card card){
        int possibility=0;
        Card.Character character = card.character;
        if (character== Card.Character.DarthVader) possibility = 40;
        if (character== Card.Character.Luke) possibility = 30;
        if (character== Card.Character.BobaFett) possibility = 20;
        if (character== Card.Character.Mandalorian) possibility = 20;

        if (rand.nextInt(100)<possibility){
            if (cards.size()==5) cards.get(2).level+=2;
        }
    }

}
