import java.util.Scanner;

public class RegisterScreen extends Screen {
    public RegisterScreen(Scanner scnr) {
        super("Register", new String[] {"Sign-up"}, scnr); //TODO
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("Sign-up")) {
            SessionUser currentUser = new SessionUser(false);
            currentUser.login(new Profile(in.next(),in.next()));
            return new HomeScreen(in,currentUser);
        }
        else {
            return new ExitScreen(in,this);

        }
    }

    @Override
    public void visualize() {
        System.out.println(String.format(
                "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Sign-up                                          from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Sign-up <username> <password>                         |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"));

    }
}
