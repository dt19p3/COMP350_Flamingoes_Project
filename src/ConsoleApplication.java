import java.util.Scanner;

/**
 * The 'main' console-based application.
 */
public class ConsoleApplication {
    public static void main(String[] args) throws Exception {
        run();
    }
    public static void run() throws Exception {
        Scanner scnr = new Scanner(System.in);
        Screen curscreen = new LoginScreen(scnr); //The current screen
        while(curscreen != null){
            curscreen.visualize();
            curscreen = curscreen.input();
        }
        scnr.close();
    }
}