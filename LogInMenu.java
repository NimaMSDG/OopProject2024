import java.util.Date;
import java.util.regex.Matcher;

public class LogInMenu extends Menu{

    int numberOfErrors=0;
    long Time;
    public void run(){

        String r1 = "user login -u (.+) -p (.+)";
        String r2 = "Forgot my password -u (.+)";
        String r3 = "log out";
        String input;

        Time = System.currentTimeMillis()/1000;


        while (!(input = Main.scanner.nextLine()).equalsIgnoreCase("back")){

            if (input.matches(r1)){
                login(getMatcher(r1,input),Time);
            }
            else if (input.matches(r2)){
                forgotPassword(getMatcher(r2,input));
            }
            else if (input.matches(r3)){
                logOut();
            }
        }
    }

    public void login(Matcher matcher,long t){
        if (Menu.mainUser!=null){
            System.out.println("you already logged in");
        }
        if (timeError(System.currentTimeMillis()/1000-t)){
            return;
        }
        String username = matcher.group(1);
        String password = matcher.group(2);

        User user = User.getUserByUsername(username);
        if (user==null){
            System.out.println("Username doesn’t exist!");
            numberOfErrors++;
            Time =System.currentTimeMillis()/1000;
            return;
        }
        if (!user.Password.equals(password)){
            System.out.println("Password and Username don’t match!");
            numberOfErrors++;
            Time = System.currentTimeMillis()/1000;
            return;
        }
    }

    public void forgotPassword(Matcher matcher){
        if (Menu.mainUser!=null){
            System.out.println("you already logged in");
        }

        String username = matcher.group(1);

        User user = User.getUserByUsername(username);
        if (user==null){
            System.out.println("Username doesn’t exist!");
            return;
        }

        if (!answerRecoveryQ(user)){
            return;
        }

        Menu.mainUser = user;
        System.out.println("user logged in successfully");
    }

    public void logOut(){
        Menu.mainUser=null;
        System.out.println("logged out successfully");
    }



    public boolean timeError(long deltaT){
        if ((long)numberOfErrors*5-deltaT>0){
            System.out.printf("Try again in %s seconds\n",(int)((long)numberOfErrors*5-deltaT));
            return true;
        }
        return false;
    }

    public boolean answerRecoveryQ(User user){
        RecoveryQ recoveryQ = user.recoveryQ;
        String Question = recoveryQ.Question;
        System.out.println(Question);

        String input = Main.scanner.nextLine();
        if (input.equals(recoveryQ.Answer)){
            return true;
        }
        System.out.println("incorrect answer!!!");
        return false;
    }
}
