import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    static User mainUser;

    public Matcher getMatcher(String regex, String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }
}
