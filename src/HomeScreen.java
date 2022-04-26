import java.util.Scanner;
/**
 * The home screen
 */
public class HomeScreen extends Screen {
    private SessionUser currentUser;
    public HomeScreen(Scanner scnr, SessionUser sessionUser, String input) {
        super("Home", new String[] {"Create schedule","My schedules","My profile"}, scnr, input);
        this.currentUser = sessionUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        this.input = inputLine;
        String[] words = inputLine.split(" ");
        if(words.length > 2 && (words[0]+words[1]).equalsIgnoreCase("createschedule")){
            Schedule newSchedule = new Schedule(words[2]);
            currentUser.schedules.add(newSchedule);
            return new CreateScheduleScreen(in,newSchedule,currentUser,this.input);
        }
        else if(inputLine.trim().equalsIgnoreCase("my schedules")){
            return new MySchedulesScreen(in,currentUser,this.input);
        }
        else if(inputLine.trim().equalsIgnoreCase("my profile") && !currentUser.isGuest){
            return new ViewProfileScreen(in,currentUser,this.input);
        }
        else if(inputLine.trim().equalsIgnoreCase("help")){
            return new HelpScreen(in,currentUser,this.input);
        }
        else {
            return new ExitScreen(in,this,this.input);

        }
    }

    @Override
    public void visualize() {
        if (!currentUser.isGuest) {
            System.out.println(String.format(
                    "\t\t\t\t\t%72s\n" +
                            "\t\t\t\t\t.______________________________________________________________________.\n" +
                            "\t\t\t\t\t| Home                                             from the Flamingoes |\n" +
                            "\t\t\t\t\t|                                                                      |\n" +
                            "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                            "\t\t\t\t\t|              - Create schedule <name>                                |\n" +
                            "\t\t\t\t\t|              - My schedules                                          |\n" +
                            "\t\t\t\t\t|              - My profile                                            |\n" +
                            "\t\t\t\t\t|              - Help                                                  |\n" +
                            "\t\t\t\t\t|                                                                      |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n", currentUser.profile.username));
        } else {
        System.out.println(String.format(
                        "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Home                                             from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Create schedule <name>                                |\n" +
                        "\t\t\t\t\t|              - My schedules                                          |\n" +
                        "\t\t\t\t\t|              - Help                                                  |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"));
    }
}
}
