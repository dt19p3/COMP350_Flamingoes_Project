import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        Screen curscreen = new LoginScreen(scnr,"Beginning session @ " + System.currentTimeMillis() + ": "); //The current screen
        StringBuilder log = new StringBuilder();
        log.append("\n");
        while(curscreen != null){
//            System.out.println(curscreen.input);
            log.append(curscreen.input);
            log.append("\n");
            curscreen.visualize();
            curscreen = curscreen.input();
        }
        scnr.close();
        try {
            Files.write(Paths.get("log.txt"), log.toString().getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.out.println("Couldn't find log file :/");
        }
    }
}