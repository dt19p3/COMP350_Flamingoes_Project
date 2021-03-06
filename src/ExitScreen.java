import java.util.Scanner;
/**
 * The exit screen
 */
public class ExitScreen extends Screen {
    private Screen lastScreen; //the previous screen

    public ExitScreen(Scanner scnr, Screen lastScreen, String input) {
        super("Exit", new String[] {"Exit","Cancel"}, scnr, input);
        this.lastScreen = lastScreen;
    }

    @Override
    public Screen input() {

        String inputWord = in.next();
        this.input = inputWord;
        if(inputWord.equalsIgnoreCase("Exit")){
            return null;
        }
        else {
            return lastScreen;
        }
    }

    @Override
    public void visualize() {
        System.out.println("\t\t\t\t\t(If you weren't expecting an exit screen, you may have been directed here because you entered an invalid command)\n" +
                "\t\t\t\t\t.______________________________________________________________________.\n" +
                "\t\t\t\t\t| Exit                                             from the Flamingoes |\n" +
                "\t\t\t\t\t|                                                                      |\n" +
                "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                "\t\t\t\t\t|              - Exit                                                  |\n" +
                "\t\t\t\t\t|              - Cancel                                                |\n" +
                "\t\t\t\t\t|                                                                      |\n" +
                "\t\t\t\t\t|                                                                      |\n" +
                "\t\t\t\t\t|                                                                      |\n" +
                "\t\t\t\t\t|______________________________________________________________________|\n");
    }
}
