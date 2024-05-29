import java.util.regex.Matcher;

public class ProfileMenu extends Menu{

    public void run(){

        String r1 = "Show information";
        String r2 = "Profile change -u (.+)";
        String r3 = "Profile change -n (.+)";
        String r4 = "profile change password -o (.+) -n (.+)";
        String input;

        while (!(input = Main.scanner.nextLine()).equalsIgnoreCase("back")){

            if (input.matches(r1)){
                showInformation();
            }
            else if (input.matches(r2)){
                changeUsername(getMatcher(r2,input));
            }
            else if (input.matches(r3)){
                changeNickname(getMatcher(r3,input));
            }
            else if (input.matches(r4)){
                
            }
        }
    }

    public void showInformation(){
        User user =Menu.mainUser;
        System.out.printf(
                "Username : %s\n"+
                "Password : %s\n"+
                "Nickname : %s\n"+
                "Email : %s\n"+
                "Level : %s\n"+
                "Credit : %s\n"
        ,user.Username,user.Password,user.Nickname
                ,user.Email,user.Level,user.Credit);
    }

    public void changeUsername(Matcher matcher){
        String username = matcher.group(1);
        User user =Menu.mainUser;

        if (User.getUserByUsername(username)!=null){
            System.out.println("username is already taken by another user");
            return;
        }

        user.Username = username;
        System.out.println("username successfully changed");
    }

    public void changeNickname(Matcher matcher){
        String nickname = matcher.group(1);
        User user =Menu.mainUser;

        if (User.getUserByNickname(nickname)!=null){
            System.out.println("nickname is already taken by another user");
            return;
        }

        user.Nickname = nickname;
        System.out.println("nickname successfully changed");
    }
}
