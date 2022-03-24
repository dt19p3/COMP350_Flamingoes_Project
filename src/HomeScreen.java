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
        String[] words = inputLine.split(" ");
        if(words.length > 2 && (words[0]+words[1]).equalsIgnoreCase("createschedule")){
            Schedule newSchedule = new Schedule(words[2]);
            currentUser.schedules.add(newSchedule);
            return new CreateScheduleScreen(in,newSchedule,currentUser);
        }
        else if(inputLine.trim().equalsIgnoreCase("my schedules")){
            return new MySchedulesScreen(in,currentUser);
        }
        else if(inputLine.trim().equalsIgnoreCase("my profile")){
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
                        "\t\t\t\t\t%72s\n" +
                        "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Home                                             from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Create schedule <name>                                |\n" +
                        "\t\t\t\t\t|              - My schedules                                          |\n" +
                        "\t\t\t\t\t|              - My profile                                            |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n",currentUser.profile.username));
    }
}
