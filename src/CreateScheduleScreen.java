import java.util.ArrayList;
import java.util.Scanner;

public class CreateScheduleScreen extends Screen {
    public Schedule currentSchedule;
    public SessionUser currentUser;
    public CreateScheduleScreen(Scanner scnr,Schedule s,SessionUser currentUser) {
        super("Create New Schedule", new String[] {"Complete","Add","Home"}, scnr);
        this.currentSchedule = s;
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("Complete")){
            return new MySchedulesScreen(in,currentUser);
        } else if(inputWord.equalsIgnoreCase("Add")) {
            return new AddCourseScreen(in, currentSchedule, new ArrayList<Course>(), currentUser);
        }
        else if(inputWord.equalsIgnoreCase("home")){
                return new HomeScreen(in,currentUser);

        } else {
            return new ExitScreen(in,this);
        }
    }

    @Override
    public void visualize() {
        //TODO
        System.out.println(String.format(
                "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Create Schedule                                  from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Add                                                   |\n" +
                        "\t\t\t\t\t|              - Complete                                              |\n" +
                        "\t\t\t\t\t|              - Home                                                  |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"));    }
}
