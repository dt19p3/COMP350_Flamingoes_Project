import java.util.Scanner;
/**
 * The abstract, parent screen class
 */
public abstract class Screen {
    public String name; //The name of the Screen
    public String[] options; //The possible inputs (for testing, GUI extension)
    public Scanner in; //The console-scanner used
    public Screen(String name, String[] options, Scanner scnr){
        this.name = name;
        this.in = scnr;
        this.options = new String[options.length];
        for(int i = 0; i < options.length; i++){
            this.options[i] = options[i];
        }
    }

    /** Takes in user input and returns next screen.
     * @return The next Screen
     */
    public abstract Screen input();

    /** Prints a visualization of the current screen.
     */
    public abstract void visualize();
}
