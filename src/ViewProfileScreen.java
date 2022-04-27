import java.util.Scanner;

public class ViewProfileScreen extends Screen {
    public SessionUser currentUser;
    public ViewProfileScreen(Scanner scnr, SessionUser currentUser, String input) {
        super("My Profile", new String[] {"Edit","Home"}, scnr, input);
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        this.input = inputWord;
        if(inputWord.equalsIgnoreCase("home")){
            return new HomeScreen(in,currentUser,this.input);
        }
        else if(inputWord.equalsIgnoreCase("edit")){
            return new EditProfileScreen(in,currentUser,this.input);
        } else {
            return new ExitScreen(in,this,this.input);
        }
    }

    @Override
    public void visualize() {
        //TODO include user info on screen
        System.out.println(String.format(
                "\t\t\t\t\tYear: %-30sMajor: %-29s\n" +
                        "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Profile                                          from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Edit                                                  |\n" +
                        "\t\t\t\t\t|              - Home                                                  |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n",
                currentUser.profile.gradYear,currentUser.profile.major));
    }

}
