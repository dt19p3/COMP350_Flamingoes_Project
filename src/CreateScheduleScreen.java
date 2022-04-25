import java.util.ArrayList;
import java.util.Scanner;
/**
 * The create schedule screen
 */
public class CreateScheduleScreen extends Screen {
    public Schedule currentSchedule;
    public SessionUser currentUser;
    public CreateScheduleScreen(Scanner scnr,Schedule s,SessionUser currentUser) {
        super("Create New Schedule", new String[] {"Complete","Add","Home","Remove"}, scnr);
        this.currentSchedule = s;
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("Complete")){
            Store.addSchedule(currentUser.profile.username, currentSchedule.name, currentSchedule);
            return new MySchedulesScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Add")) {
            return new AddCourseScreen(in, currentSchedule, new ArrayList<ScheduleItem>(), currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Remove")){
            int index = in.nextInt();
            if(index <= this.currentSchedule.cours.size() && index >= 0) {
                this.currentSchedule.cours.remove(this.currentSchedule.cours.get(index-1));
                return new CreateScheduleScreen(in,currentSchedule,currentUser);
            }
            return new ExitScreen(in,this);

        }
        else if(inputWord.equalsIgnoreCase("home")){
                return new HomeScreen(in,currentUser);
        } else {
            return new ExitScreen(in,this);
        }
    }

    @Override
    public void visualize() {
        if(currentSchedule.getCourses().isEmpty()){
            System.out.println(String.format(
                    "\t\t\t\t\t%72s\n" +
                    "\t\t\t\t\t.______________________________________________________________________.\n" +
                    "\t\t\t\t\t| Create Schedule                                  from the Flamingoes |\n" +
                    "\t\t\t\t\t|                                                                      |\n" +
                    "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                    "\t\t\t\t\t|              - Add                                                   |\n" +
                    "\t\t\t\t\t|              - Home                                                  |\n" +
                    "\t\t\t\t\t|                                                                      |\n" +
                    "\t\t\t\t\t|                                                                      |\n" +
                    "\t\t\t\t\t|                                                                      |\n" +
                    "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
            return;
        }
        System.out.println(String.format(
                "\t\t\t\t\t%72s\n" +
                "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Create Schedule                                  from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Add                                                   |\n" +
                        "\t\t\t\t\t|              - Remove <index of course>                              |\n" +
                        "\t\t\t\t\t|              - Complete                                              |\n" +
                        "\t\t\t\t\t|              - Home                                                  |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        System.out.println("\t\t\t\t\tCourses:");
        currentSchedule.listCourses();
    }
}
