import java.util.Scanner;

public class MySchedulesScreen extends Screen {
    public SessionUser currentUser;
    public MySchedulesScreen(Scanner scnr,SessionUser s) {
        super("My Schedules", new String[] {"Add", "Delete","Edit","Home","Calendar"}, scnr);
        this.currentUser = s;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        if(inputWord.equalsIgnoreCase("Add")){
            String scheduleName = in.next();
            Schedule scheduleWithThatName = new Schedule(scheduleName);
            currentUser.schedules.add(scheduleWithThatName);
            return new CreateScheduleScreen(in,scheduleWithThatName,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Calendar")){
            String scheduleName = in.next();
            boolean found = false;
            for(Schedule s : currentUser.schedules){
                if(s.getName().equals(scheduleName)){
                    ViewCalendarScheduleScreen.printCalendar(s.getCourses());
                    found = true;
                    break;
                }
            }
            if(found) {
                return new MySchedulesScreen(in, currentUser);
            } else {
                return new ExitScreen(in,this);
            }
        }
        else if(inputWord.equalsIgnoreCase("Delete")){
            String scheduleName = in.next();
            boolean removed = false;
            Schedule toRemove = new Schedule("");
            for(Schedule s : currentUser.schedules){
                if(s.getName().equalsIgnoreCase(scheduleName)) {
                    removed = true;
                    toRemove = s;
                    break;
                }
            }
            if(removed) {
                currentUser.schedules.remove(toRemove);
            } else {
                System.out.println("No schedule by that name.");
            }
            return new MySchedulesScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Home")){
            return new HomeScreen(in,currentUser);
        }
        else if(inputWord.equalsIgnoreCase("Edit")){
            String scheduleName = in.next();
            Schedule toEdit = new Schedule("");
            boolean found = false;
            for(Schedule s : currentUser.schedules){
                if(s.getName().equalsIgnoreCase(scheduleName)) {
                    found = true;
                    toEdit = s;
                    break;
                }
            }
            if(!found) {
                System.out.println("No schedule by that name.");
                return new ExitScreen(in,this);
            }
            return new CreateScheduleScreen(in,toEdit,currentUser);
        }
        else {
            return new ExitScreen(in,this);
        }
    }


    @Override
    public void visualize() {
        System.out.println(String.format(
                "\t\t\t\t\t.______________________________________________________________________.\n" +
                        "\t\t\t\t\t| Schedules                                        from the Flamingoes |\n" +
                        "\t\t\t\t\t|                                                                      |\n" +
                        "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                        "\t\t\t\t\t|              - Add <name>                                            |\n" +
                        "\t\t\t\t\t|              - Edit <name>                                           |\n" +
                        "\t\t\t\t\t|              - Delete <name>                                         |\n" +
                        "\t\t\t\t\t|              - Calendar <name>                                       |\n" +
                        "\t\t\t\t\t|              - Home                                                  |\n" +
                        "\t\t\t\t\t|______________________________________________________________________|\n"));
        if(currentUser.schedules == null){
            return;
        }
        for(Schedule s : currentUser.schedules){
            System.out.print("\n\t\t\t\t\tSchedule name: " + s.getName());
            if(s.courses.size() < 4){
                System.out.print("\tNote: This schedule may be considered part time.");
            }
            else if(s.courses.size() > 6){
                System.out.print("\tNote: This schedule may be considered overtime.");
            }
            System.out.println("\n\t\t\t\t\tCourses:");
            s.listCourses();
            System.out.println("\t\t\t\t\t.______________________________________________________________________.\n\n");
        }
    }
}
