import java.util.Scanner;

public class ConsoleApplication {
    public static void main(String[] args){
        run();
    }
    public static void run(){
        Scanner scnr = new Scanner(System.in);
        Screen curscreen = new LoginScreen(scnr);
        while(curscreen != null){
            curscreen.visualize();
            curscreen = curscreen.input();
        }
        scnr.close();
    }
}
