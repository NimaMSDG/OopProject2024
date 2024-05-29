import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;

public class SignUpMenu extends Menu{


    public void run(){

        String r1 = "user create -u (.+) -p (.+) (.+) –email (.+) -n (.+)";
        String r2 = "user create -u (.+) -p (random) –email (.+) -n (.+)";
        String input;

        while (!(input = Main.scanner.nextLine()).equalsIgnoreCase("")){
            if (input.matches(r1)){
                submitUser(getMatcher(r1,input));
            }
            else if (input.matches(r2)){
                submitUser(getMatcher(r2,input));
            }
        }

    }

    public User makeUser(Matcher matcher){
        if (matcher.group(2).equals("random")){
            String username = matcher.group(1);
            String email = matcher.group(3);
            String nickname = matcher.group(4);
            if (username.isEmpty()) {System.out.println("Please enter your Username");return null;}
            if (email.isEmpty()) {System.out.println("Please enter your Email");return null;}
            if (nickname.isEmpty()) {System.out.println("Please enter your Nickname");return null;}
            if (!username.matches("^[0-9A-Za-z_]+$")){
                System.out.println("Incorrect format for username!");
                return null;
            }
            if (User.getUserByUsername(username)==null){
                System.out.println("Username already exists!");
                return null;
            }
            if (!email.matches("")){
                System.out.println("Invalid email format");
                return null;
            }

            String randomPass = randomPasswordGenerator();
            System.out.printf("Your random password: %s\n" +
                    "Please enter your password :...\n",randomPass);
            String input;
            while (!((input = Main.scanner.nextLine()).equals("back"))){
                if (input.equals(randomPass)){
                    return new User(username,randomPass,nickname,email);
                }
            }
            return null;
        }
        else {
            String username = matcher.group(1);
            String password = matcher.group(2);
            String confirmPass = matcher.group(3);
            String email = matcher.group(4);
            String nickname = matcher.group(5);
            if (username.isEmpty()) {System.out.println("Please enter your Username");return null;}
            if (password.isEmpty()) {System.out.println("Please enter your Password");return null;}
            if (confirmPass.isEmpty()) {System.out.println("Please enter your Confirmation Password");return null;}
            if (email.isEmpty()) {System.out.println("Please enter your Email");return null;}
            if (nickname.isEmpty()) {System.out.println("Please enter your Nickname");return null;}


            if (!username.matches("^[0-9A-Za-z_]+$")){
                System.out.println("Incorrect format for username!");
                return null;
            }
            if (User.getUserByUsername(username)==null){
                System.out.println("Username already exists!");
                return null;
            }
            if (password.length()<8){
                System.out.println("Password is too short!");
            }
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]+$")){
                System.out.println("Password is too weak!");
                return null;
            }
            if (!email.matches("")){
                System.out.println("Invalid email format");
                return null;
            }
            return new User(username,password,nickname,email);
        }
    }


    public void submitUser(Matcher matcher){

        User user = makeUser(matcher);

        if (!showRecoveryQ(user)){
            return;
        }
        if (!showCaptcha()){
            return;
        }

        User.addUser(user);
    }

    public static String randomPasswordGenerator(){
        Random random = new Random();
        char a = (char)('a' + random.nextInt(26));
        char A = (char)('A' + random.nextInt(26));
        String signs = "!@#$%^&*";
        char shape = signs.charAt(random.nextInt(signs.length()));
        int length = 8+random.nextInt(13);
        char[] password = new char[length];
        Arrays.fill(password, '|');
        password[random.nextInt(password.length)]=a;
        int i = random.nextInt(password.length);
        while (password[i]!='|'){
            i = random.nextInt(password.length);
        }
        password[i] = A;
        i = random.nextInt(password.length);
        while (password[i]!='|'){
            i = random.nextInt(password.length);
        }
        password[i] = shape;
        for (int j = 0; j < password.length; j++) {
            if (password[j]=='|'){
                String chars = "abcdefghijklmnopqrstuvwxyz" +
                        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "!@#$%^&*";
                password[j]=chars.charAt(random.nextInt(chars.length()));
            }
        }
        return new String(password);
    }

    public boolean showRecoveryQ(User user){
        String Question = "";
        String Answer = "";
        System.out.println("User created successfully. Please choose a security question :\n" +
                "• 1-What is your father’s name ?\n" +
                "• 2-What is your favourite color ?\n" +
                "• 3-What was the name of your first pet ?");
        String input;
        while (!(input = Main.scanner.nextLine()).equals("back")){
            if (!input.equals("1") && !input.equals("2") && !input.equals("3")){
                System.out.println("Please enter number between 1 to 3");
            }
            else {
                int Qnumber = Integer.parseInt(input);
                if (Qnumber==1){
                    Question="What is your father’s name ?";
                }
                else if (Qnumber==2){
                    Question="What is your favourite color ?";
                }
                else{
                    Question="What was the name of your first pet ?";
                }
                Answer = Main.scanner.nextLine();
                break;
            }
        }
        if (input.equals("back")){
            return false;
        }
        user.setRecoveryQ(new RecoveryQ(Question,Answer));
        return true;
    }

    public boolean showCaptcha(){
        String input = "";
        String captcha;
        while (!input.equals("back")){
            input = Main.scanner.nextLine();
            captcha = Captcha.randomCaptcha();
            if (input.equals(captcha)){
                return true;
            }
            else System.out.println("Wrong answer for captcha");
        }
        return false;
    }


}




