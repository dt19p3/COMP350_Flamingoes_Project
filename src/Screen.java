import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

/**
 * The abstract, screen base class
 */
public abstract class Screen {
    public String name;
    public String[] options;
    public Scanner in;
    public String input;
    public Screen(String name, String[] options, Scanner scnr, String input){
        this.name = name;
        this.in = scnr;
        this.options = new String[options.length];
        for(int i = 0; i < options.length; i++){
            this.options[i] = options[i];
        }
        this.input = input;
    }
    
    public abstract Screen input() throws Exception;

    public abstract void visualize();
}
