import java.util.ArrayList;
import java.util.Scanner;

public class CreateScheduleScreen extends Screen {
    public Schedule currentSchedule;
    public SessionUser currentUser;
    public CreateScheduleScreen(Scanner scnr,Schedule s,SessionUser currentUser) {
        super("Create New Schedule", new String[] {"Complete","Add"}, scnr);
        this.currentSchedule = s;
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("Complete")){
            return new MySchedulesScreen(in,currentUser);
        } else if(inputWord.equalsIgnoreCase("Add")){
            return new AddCourseScreen(in,currentSchedule,new ArrayList<>(),currentUser);
        } else {
            return null;
        }
    }

    @Override
    public void visualize() {
        //TODO
        System.out.println("CREATE SCHEDULE SCREEN");
    }
}
