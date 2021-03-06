import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class EditProfileScreen extends Screen {
    public SessionUser currentUser;

    public EditProfileScreen(Scanner scnr, SessionUser s, String input) {
        super("Edit profile", new String[] {"Major","Graduation year","Home"}, scnr, input);
        this.currentUser = s;
    }

    @Override
    public Screen input() throws IOException, ParseException {
        String inputWord = in.next();
        this.input = inputWord;
        if(inputWord.equalsIgnoreCase("major")){
            String nextWord = in.next();
            this.input += nextWord;
            currentUser.profile.major = nextWord;
            currentUser.profile.storeMajor(nextWord);
            return new ViewProfileScreen(in,currentUser,this.input);
        }
        else if(inputWord.equalsIgnoreCase("year")){
            String nextWord = in.next();
            this.input += nextWord;
            currentUser.profile.gradYear = Short.valueOf(nextWord);
            currentUser.profile.storeGradYear(Short.valueOf(nextWord));
            return new ViewProfileScreen(in,currentUser,this.input);
        }
        else if(inputWord.equalsIgnoreCase("home")){
            return new HomeScreen(in,currentUser,this.input);
        }
        else {
            return new ExitScreen(in,this,this.input);
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
