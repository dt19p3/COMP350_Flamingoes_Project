import java.util.List;
import java.util.Scanner;

public class LoginScreen extends Screen {

    public LoginScreen(Scanner scnr) {
        super("Login", new String[] {"Login","Continue as guest","Sign-up"},scnr);
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        if(inputWord.equalsIgnoreCase("login")){
            SessionUser currentUser = new SessionUser(false);
            currentUser.login(new Profile(inputLine.split(" ")[0], inputLine.split(" ")[1]));
            return new HomeScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("sign-up")){
            return new RegisterScreen(in);
        }
        else if(inputLine.trim().equalsIgnoreCase("continue as guest")){
            //similar but with guest dummy
            SessionUser currentUser = new SessionUser(true);
            currentUser.login(new Profile("Guest",""));
            return new HomeScreen(in,currentUser);        }
        else {
            return new ExitScreen(in,this);
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
