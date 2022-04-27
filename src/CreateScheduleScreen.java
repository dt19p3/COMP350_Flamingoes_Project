import java.util.ArrayList;
import java.util.Scanner;
/**
 * The create schedule screen
 */
public class CreateScheduleScreen extends Screen {
    public Schedule currentSchedule;
    public SessionUser currentUser;
    public CreateScheduleScreen(Scanner scnr,Schedule s,SessionUser currentUser, String input) {
        super("Create New Schedule", new String[] {"Complete","Add","Home","Remove", "Activity"}, scnr, input);
        this.currentSchedule = s;
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        this.input = inputWord;
        if(inputWord.equalsIgnoreCase("Complete")){
            Store.addSchedule(currentUser.profile.username, currentSchedule.name, currentSchedule);
            SimilarityMap sm = new SimilarityMap("simmap.txt");
            sm.processShedule(currentSchedule);
            sm.flush();
            return new MySchedulesScreen(in,currentUser,this.input);
        }
        else if(inputWord.equalsIgnoreCase("Add")) {
            return new AddCourseScreen(in, currentSchedule, new ArrayList<ScheduleItem>(), currentUser,this.input);
        }
        else if(inputWord.equalsIgnoreCase("Activity")) {
            return new AddActivityScreen(in, currentSchedule, currentUser,this.input);
        }
        else if(inputWord.equalsIgnoreCase("Remove")){
            int index = in.nextInt();
            if(index <= this.currentSchedule.cours.size() && index >= 0) {
                this.currentSchedule.cours.remove(this.currentSchedule.cours.get(index-1));
                return new CreateScheduleScreen(in,currentSchedule,currentUser,this.input);
            }
            return new ExitScreen(in,this,this.input);

        }
        else if(inputWord.equalsIgnoreCase("home")){
                return new HomeScreen(in,currentUser,this.input);
        } else {
            return new ExitScreen(in,this,this.input);
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
                    "\t\t\t\t\t|              - Activity                                              |\n" +
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
