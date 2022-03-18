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
        if(inputWord.equalsIgnoreCase("Add")){
            String scheduleName = in.next();
            //TODO add to schedule with name scheduleName
            Schedule scheduleWithThatName = new Schedule();
            return new CreateScheduleScreen(in,scheduleWithThatName,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Delete")){
            String scheduleName = in.next();
            //TODO delete schedule with name scheduleName
            return new MySchedulesScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Home")){
            return new HomeScreen(in,currentUser);
        }
        else {
            return new ExitScreen(in,this);
        }
    }


    @Override
    public void visualize() {

    }
}
