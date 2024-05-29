import java.util.ArrayList;
import java.util.Random;

public class Captcha {
    String[] Number;
    Captcha(int number){
        if (number==0) Number = new String[]{
                " .d8888b.     ",
                "d88P  Y88b    ",
                "888    888    ",
                "888    888    ",
                "888    888    ",
                "888    888    ",
                "Y88b  d88P    ",
                " \"Y8888P\"     "};
        if (number==1) Number = new String[]{
                " d888         " ,
                "d8888         " ,
                "  888         " ,
                "  888         " ,
                "  888         " ,
                "  888         " ,
                "  888         " ,
                "8888888       "};
        if (number==2) Number = new String[]{
                " .d8888b.     " ,
                "d88P  Y88b    " ,
                "       888    " ,
                "     .d88P    " ,
                " .od888P\"     " ,
                "d88P\"         " ,
                "888\"          " ,
                "888888888     "};
        if (number==3) Number = new String[]{
                " .d8888b.     " ,
                "d88P  Y88b    " ,
                "     .d88P    " ,
                "    8888\"     " ,
                "     \"Y8b.    " ,
                "888    888    " ,
                "Y88b  d88P    " ,
                " \"Y8888P\"     "};
        if (number==4) Number = new String[]{
                "    d8888     " ,
                "   d8P888     " ,
                "  d8P 888     " ,
                " d8P  888     " ,
                "d88   888     " ,
                "8888888888    " ,
                "      888     " ,
                "      888     "};
        if (number==5) Number = new String[]{
                "888888888     " ,
                "888           " ,
                "888           " ,
                "8888888b.     " ,
                "     \"Y88b    " ,
                "       888    " ,
                "Y88b  d88P    " ,
                " \"Y8888P\"     "};
        if (number==6) Number = new String[]{
                " .d8888b.     " ,
                "d88P  Y88b    " ,
                "888           " ,
                "888d888b.     " ,
                "888P \"Y88b    " ,
                "888    888    " ,
                "Y88b  d88P    " ,
                " \"Y8888P\"     "};
        if (number==7) Number = new String[]{
                "8888888888    " ,
                "      d88P    " ,
                "     d88P     " ,
                "    d88P      " ,
                " 88888888     " ,
                "  d88P        " ,
                " d88P         " ,
                "d88P          "};
        if (number==8) Number = new String[]{
                " .d8888b.     " ,
                "d88P  Y88b    " ,
                "Y88b. d88P    " ,
                " \"Y88888\"     " ,
                ".d8P\"\"Y8b.    " ,
                "888    888    " ,
                "Y88b  d88P    " ,
                " \"Y8888P\"     "};
        if (number==9) Number = new String[]{
                " .d8888b.     " ,
                "d88P  Y88b    " ,
                "888    888    " ,
                "Y88b. d888    " ,
                " \"Y888P888    " ,
                "       888    " ,
                "Y88b  d88P    " ,
                " \"Y8888P\"     "};
    }

    public static String[] addNumbers(String[] s1, String[] s2){
        String[] result = new String[8];
        for (int i=0;i<8;i++){
            result[i] = s1[i] + s2[i];
        }
        return result;
    }

    public static void showCaptcha(String[] captcha){
        for (int i = 0; i < 8; i++) {
            System.out.println(captcha[i]);
        }
        System.out.println();
    }

    public void addNoise(){
        Random rand = new Random();

        for (int i=0;i<new Captcha(0).Number.length;i++){
            for (int j=0;j<new Captcha(0).Number[0].length();j++){
                if (rand.nextInt(100)<20 ){
                    char[] chars = this.Number[i].toCharArray();
                    chars[j] = '.';
                    this.Number[i]=String.valueOf(chars);
                }
            }
        }
    }

    public static String randomCaptcha(){
        Random rand = new Random();
        int length = rand.nextInt(3)+5;
        String number = "";
        Captcha[] captcha = new Captcha[length];
        String[] Captcha = new String[8];

        for (int i=0;i<length;i++){
            int digit = rand.nextInt(10);
            number+=String.valueOf(digit);
            captcha[i] = new Captcha(digit);
            captcha[i].addNoise();
        }

        Captcha = captcha[0].Number;
        for (int i=1;i<length;i++){
            Captcha = addNumbers(Captcha,captcha[i].Number);
        }

        showCaptcha(Captcha);
        return number;
    }
}
