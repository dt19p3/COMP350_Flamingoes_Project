import java.util.Scanner;

public class Scratch {
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        Screen curscreen = new LoginScreen(scnr);
        while(curscreen != null){
            curscreen.visualize();
            curscreen = curscreen.input();
        }
    }
}
