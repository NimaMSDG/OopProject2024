import java.util.ArrayList;

public class User {
    String Username;
    String Password;
    String Nickname;
    String Email;
    RecoveryQ recoveryQ;

    ArrayList<Card> Deck = new ArrayList<>();
    int Level;
    int HP;
    int XP;
    int Credit;

    static ArrayList<User> users = new ArrayList<>();

    User(String Username,String Password,String Nickname,String Email){
        this.Username=Username;
        this.Password=Password;
        this.Nickname=Nickname;
        this.Email=Email;
        Level = 1;
        HP = 100;
        XP = 0;
        Credit = 100;
    }
    public void setRecoveryQ(RecoveryQ recoveryQ){
        this.recoveryQ=recoveryQ;
    }

    public static User getUserByUsername(String username){
        for (User user : users) {
            if (user.Username.equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static User getUserByNickname(String nickname){
        for (User user : users) {
            if (user.Nickname.equals(nickname)) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user){
        users.add(user);
        System.out.printf("User %s created successfully!\n",user.Username);
    }


}
