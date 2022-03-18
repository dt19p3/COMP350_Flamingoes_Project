import java.util.Scanner;

public class ExitScreen extends Screen {
    private Screen lastScreen;

    public ExitScreen(Scanner scnr, Screen lastScreen) {
        super("Exit", new String[] {"Exit","Cancel"}, scnr);
        this.lastScreen = lastScreen;
    }

    @Override
    public Screen input() {

        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("Exit")){
            return null;
        }
        else {
            return lastScreen;
        }
    }

    @Override
    public void visualize() {
        System.out.println("\t\t\t\t\t.______________________________________________________________________.\n" +
                "\t\t\t\t\t| Exit                                             from the Flamingoes |\n" +
                "\t\t\t\t\t|                                                                      |\n" +
                "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                "\t\t\t\t\t|              - Exit                                                  |\n" +
                "\t\t\t\t\t|              - Cancel                                                |\n" +
                "\t\t\t\t\t|                                                                      |\n" +
                "\t\t\t\t\t|                                                                      |\n" +
                "\t\t\t\t\t|______________________________________________________________________|\n");
    }
}
