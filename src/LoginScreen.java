import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
/**
 * The login screen
 */
public class LoginScreen extends Screen {

    public LoginScreen(Scanner scnr, String input) {
        super("Login", new String[] {"Login","Continue as guest","Sign-up"},scnr, input);
    }

    @Override
    public Screen input() throws Exception {
        String inputWord = in.next();
        this.input = inputWord + "_____ _____";
        String inputLine = inputWord + in.nextLine();

        if(inputWord.equalsIgnoreCase("login")){
            SessionUser currentUser = new SessionUser(false);
            if(inputLine.split(" ").length < 3){
                return new ExitScreen(in,this, this.input);
            }
            currentUser.login(new Profile(inputLine.split(" ")[1], inputLine.split(" ")[2]));
            return new HomeScreen(in,currentUser,this.input);
        }
        else if(inputWord.equalsIgnoreCase("sign-up")){
            return new RegisterScreen(in,this.input);
        }
        else if(inputLine.trim().equalsIgnoreCase("continue as guest")){
            //similar but with guest dummy
            SessionUser currentUser = new SessionUser(true);
//            currentUser.login(new Profile("Guest",""));
            return new HomeScreen(in,currentUser,this.input);        }
        else {
            return new ExitScreen(in,this,this.input);
        }
    }

    @Override
    public void visualize() {
        System.out.println(String.format(
                        "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Login                                            from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Login <username> <password>                           |\n" +
                        "\t\t\t\t\t|              - Sign-up                                               |\n" +
                        "\t\t\t\t\t|              - Continue as guest                                     |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"
        ));
    }
}
