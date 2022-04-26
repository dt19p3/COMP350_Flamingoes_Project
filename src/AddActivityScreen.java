import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The add activity screen.
 */
public class AddActivityScreen extends Screen {

    public Schedule currentSchedule;
    public SessionUser currentUser;

    public AddActivityScreen(Scanner scnr, Schedule currentSchedule, SessionUser currentUser) {
        super("Add activity", new String[]{"Add", "View", "Home"}, scnr);
        this.currentSchedule = currentSchedule;
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        if (inputWord.equalsIgnoreCase("view")) {
            return new CreateScheduleScreen(in, currentSchedule, currentUser);
        }
        else if (inputWord.equalsIgnoreCase("add")) {
            System.out.println("Please enter the name of your activity.");
            String activityName = in.nextLine();

            System.out.println("Please enter the hour of the start time of your activity.");
            //LocalTime beginTime; //value for beginTime
            int beginHour = in.nextInt();
            System.out.println("Please enter the minute of the start time of your activity.");
            int beginMinute = in.nextInt();
            LocalTime beginTime = LocalTime.of(beginHour, beginMinute);
            System.out.println("Please enter the hour of the end time of your activity.");
            //LocalTime endTime = LocalTime.of(22,30); //value for endTime
            int endHour = in.nextInt();
            System.out.println("Please enter the minute of the end time of your activity.");
            int endMinute = in.nextInt();
            LocalTime endTime = LocalTime.of(endHour, endMinute);
            System.out.println("What days does this activity meet?");
            String meet = in.next();

            Activity activityToAdd = new Activity(activityName, "", beginTime, endTime, meet, "", "", 0, 0, 0);

            checkConflicts(new ArrayList<>(List.of(activityToAdd)));
            if (activityToAdd.getConflicts()) {
                System.out.println("This activity conflicts with another item in your schedule. Add anyway? (Y/N)");
                String addAnyway = in.nextLine();
                if (addAnyway.equalsIgnoreCase("Y")) {
                    currentSchedule.removeCourse(activityToAdd.conflictingScheduleItem);
                    currentSchedule.addCourse(activityToAdd);
                }
                return new CreateScheduleScreen(in, currentSchedule, currentUser);
            }
            currentSchedule.addCourse(activityToAdd);
            return new CreateScheduleScreen(in, currentSchedule, currentUser);
        }

            else if (inputWord.equalsIgnoreCase("home")) {
                return new HomeScreen(in, currentUser);
            } else {
            return new ExitScreen(in, this);
        }
    }

    //region checkConflicts()

    /**
     * Checks which courses in a given list conflict with currently scheduled ones
     * @param results The list of courses to be checked
     */
    private void checkConflicts(ArrayList<ScheduleItem> results) {
        String[] sMeets;
        for (ScheduleItem result : results) {
            String str1 = result.meets;
            sMeets = str1.split("");
            for (int j = 0; j < currentSchedule.getCourses().size(); j++) {
                for (String sMeet : sMeets) {
                    if (currentSchedule.getCourses().get(j).meets.contains(sMeet)) {
                        if (result.beginTime.isBefore(currentSchedule.getCourses().get(j).endTime)
                                && result.beginTime.isAfter(currentSchedule.getCourses().get(j).beginTime)
                                || result.beginTime.equals(currentSchedule.getCourses().get(j).beginTime)) {
                            result.setConflicts(true);
                            result.setConflictingCourse(currentSchedule.getCourses().get(j));
                        }
                        if (currentSchedule.getCourses().get(j).endTime.isBefore(result.endTime)
                                && currentSchedule.getCourses().get(j).endTime.isAfter(result.beginTime)
                                || result.beginTime.equals(currentSchedule.getCourses().get(j).beginTime)) {
                            result.setConflicts(true);
                            result.setConflictingCourse(currentSchedule.getCourses().get(j));
                        }
                    }
                }
            }
        }
    }

    //endregion

    @Override
    public void visualize() {

            System.out.println(String.format(
                    "\t\t\t\t\t%72s\n" +
                            "\t\t\t\t\t.______________________________________________________________________.\n" +
                            "\t\t\t\t\t| Add Activity                                     from the Flamingoes |\n" +
                            "\t\t\t\t\t|                                                                      |\n" +
                            "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                            "\t\t\t\t\t|              - Add                                 |\n" +
                            "\t\t\t\t\t|              - View                                                  |\n" +
                            "\t\t\t\t\t|              - Home                                                  |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        }


    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}

