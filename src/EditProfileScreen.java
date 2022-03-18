import java.util.Scanner;

public class EditProfileScreen extends Screen {
    public SessionUser currentUser;

    public EditProfileScreen(Scanner scnr, SessionUser s) {
        super("Edit profile", new String[] {"Major","Graduation year","Home"}, scnr);
        this.currentUser = s;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("major")){
            String nextWord = in.next();
            //TODO Change user's profile major to nextWord
            return new EditProfileScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("year")){
            String nextWord = in.next();
            //TODO Change user's profile year to nextWord
            return new EditProfileScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("home")){
            return new HomeScreen(in,currentUser);
        }
        else {
            return new ExitScreen(in,this);
        }
    }

    @Override
    public void visualize() {
        //TODO edit to display current profile data
        System.out.println(String.format(
                "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Edit profile                                     from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Major <new major>                                     |\n" +
                        "\t\t\t\t\t|              - Year <new graduation year>                            |\n" +
                        "\t\t\t\t\t|              - Home                                                  |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"
        ));
    }
}
