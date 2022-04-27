import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;
/**
 * The register screen
 */
public class RegisterScreen extends Screen {
    public RegisterScreen(Scanner scnr, String input) {
        super("Register", new String[] {"Sign-up"}, scnr, input);
    }

    @Override
    public Screen input() throws Exception {
        String inputWord = in.next();
        this.input = inputWord + "_____ _____";
        if(inputWord.equalsIgnoreCase("Sign-up")) {
            SessionUser currentUser = new SessionUser(false);
            currentUser.register(in.next(),in.next());
            return new HomeScreen(in,currentUser,this.input);
        }
        else {
            return new ExitScreen(in,this,this.input);

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
