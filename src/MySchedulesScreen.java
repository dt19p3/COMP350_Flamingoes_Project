import java.util.Scanner;

public class MySchedulesScreen extends Screen {
    public SessionUser currentUser;
    public MySchedulesScreen(Scanner scnr,SessionUser s) {
        super("My Schedules", new String[] {"Add", "Delete","Home"}, scnr);
        this.currentUser = s;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String scheduleName = in.next();
        if(inputWord.equalsIgnoreCase("Add")){
            //TODO add to schedule with name scheduleName
            Schedule scheduleWithThatName = new Schedule();
            return new CreateScheduleScreen(in,scheduleWithThatName,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Delete")){
            //TODO delete schedule with name scheduleName
            return new MySchedulesScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Home")){
            return new HomeScreen(in,currentUser);
        }
        else {
            return null;
        }
    }


    @Override
    public void visualize() {

    }
}
