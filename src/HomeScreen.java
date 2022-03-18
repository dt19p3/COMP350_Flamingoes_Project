import java.util.Scanner;

public class HomeScreen extends Screen {
    private SessionUser currentUser;
    public HomeScreen(Scanner scnr, SessionUser sessionUser) {
        super("Home", new String[] {"Create schedule","My schedules","My profile"}, scnr);
        this.currentUser = sessionUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        if(inputLine.trim().equalsIgnoreCase("create schedule")){
            //TODO
            return new CreateScheduleScreen(in,new Schedule(),currentUser);
        }
        else if(inputLine.trim().equalsIgnoreCase("my schedules")){
            //TODO
            return new MySchedulesScreen(in,currentUser);
        }
        else if(inputLine.trim().equalsIgnoreCase("my profile")){
            //TODO
            return new ViewProfileScreen(in,currentUser);
        }
        else {
            return new ExitScreen(in,this);

        }
    }

    @Override
    public void visualize() {
        //TODO add user specific info to string
        System.out.println(String.format(
                        "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Home                                             from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Create schedule                                       |\n" +
                        "\t\t\t\t\t|              - My schedules                                          |\n" +
                        "\t\t\t\t\t|              - My profile                                            |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"));
    }
}
