import java.util.ArrayList;
import java.util.Scanner;

public class AddCourseScreen extends Screen {

    public Schedule currentSchedule;
    public ArrayList<Course> courses;
    public SessionUser currentUser;

    public AddCourseScreen(Scanner scnr,Schedule currentSchedule,ArrayList<Course> courses,SessionUser currentUser) {
        super("Add course", new String[] {"Add","View","Filter","I'm feeling lucky","Home"}, scnr);
        this.currentSchedule = currentSchedule;
        this.courses = new ArrayList<>(courses);
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        if(inputWord.equalsIgnoreCase("view")){
            return new CreateScheduleScreen(in,currentSchedule,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("add")){
            //TODO fetch visible course based on input
            return new RegisterScreen(in);
        }
        else if(inputLine.trim().equalsIgnoreCase("filter")){
            //TODO searching juju
            ArrayList<Course> newCourses = new ArrayList<Course>();
            return new AddCourseScreen(in,currentSchedule,newCourses,currentUser);
        }
        else if(inputLine.trim().equalsIgnoreCase("I'm feeling lucky")){
            //TODO searching juju
            ArrayList<Course> newCourses = new ArrayList<Course>();
            return new AddCourseScreen(in,currentSchedule,newCourses,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("home")){
            return new HomeScreen(in,currentUser);
        }
        else {
            return new ExitScreen(in,this);
        }
    }

    @Override
    public void visualize() {
        System.out.println(String.format(
                "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Add Course                                       from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Add                                                   |\n" +
                        "\t\t\t\t\t|              - View                                                  |\n" +
                        "\t\t\t\t\t|              - I'm feeling lucky                                     |\n" +
                        "\t\t\t\t\t|              - Home                                                  |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"));  //TODO
    }
}
