import java.util.Scanner;

public abstract class Screen {
    public String name;
    private String[] options;
    public Scanner in;
    public Screen(String name, String[] options, Scanner scnr){
        this.name = name;
        this.in = scnr;
        this.options = new String[options.length];
        for(int i = 0; i < options.length; i++){
            this.options[i] = options[i];
        }
    }
    
    public abstract Screen input();

    public abstract void visualize();
}
