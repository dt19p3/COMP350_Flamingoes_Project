import java.util.Scanner;

public class HelpScreen extends Screen{
    public SessionUser currentUser;
    public HelpScreen(Scanner scnr, SessionUser currentUser) {
        super("Help", new String[] {"Home"}, scnr);
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("home")){
            return new HomeScreen(in,currentUser);
        }
        else {
            return new ExitScreen(in,this);
        }
    }

    @Override
    public void visualize() {
        //TODO include user info on screen
        System.out.println(String.format(
                        "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Help                                             from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Type the commands that appear on the screen right below the          |\n" +
                        "\t\t\t\t\t| displayed window. It should appear in green.                         |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| None of the functions in this application are case sensitive.        |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| When you encounter < >, add the requested info that pertains to your |\n" +
                        "\t\t\t\t\t| personal account or personal searching needs.                        |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| The phrase, index of course, simply means the number that appears    |\n" +
                        "\t\t\t\t\t| before the course. It can be found inside the straight brackets ([]).|\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter the following:                                                 |\n" +
                        "\t\t\t\t\t|              - Home                                                  |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"));
    }
}
