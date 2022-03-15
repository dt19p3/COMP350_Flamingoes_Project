import java.util.Scanner;

public class ViewProfileScreen extends Screen {
    public SessionUser currentUser;
    public ViewProfileScreen(Scanner scnr, SessionUser currentUser) {
        super("My Profile", new String[] {"Edit","Home"}, scnr);
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("home")){
            return new HomeScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("edit")){
            return new EditProfileScreen(in,currentUser);
        } else {
            return null;
        }
    }

    @Override
    public void visualize() {
        //TODO
        System.out.println("VIEW PROFILE SCREEN");
    }
}
