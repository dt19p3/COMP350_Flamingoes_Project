import java.util.ArrayList;
import java.util.Scanner;

public class AddCourseScreen extends Screen {

    public Schedule currentSchedule;
    public ArrayList<Course> courses;
    public SessionUser currentUser;

    public AddCourseScreen(Scanner scnr, Schedule currentSchedule, ArrayList<Course> courses, SessionUser currentUser) {
        super("Add course", new String[]{"Add", "View", "Search", "I'm feeling lucky", "Home"}, scnr);
        this.currentSchedule = currentSchedule;
        this.courses = new ArrayList<>(courses);
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        if (inputWord.equalsIgnoreCase("view")) {
            return new CreateScheduleScreen(in, currentSchedule, currentUser);
        } else if (inputWord.equalsIgnoreCase("add") && courses.size() != 0) {
            String[] entry = inputLine.split(" ");
            if (entry.length < 2) {
                return new ExitScreen(in, this);
            }
            int index = Integer.valueOf(entry[1]); //TODO error checking
            currentSchedule.addCourse(courses.get(index));
            return new CreateScheduleScreen(in, currentSchedule, currentUser);
        }

        //region Filter

        else if (inputLine.trim().equalsIgnoreCase("search")) {

            //region __init__

            Search search = new Search();
            String input = "";
            String searchString = "";
            boolean byCode = false;
            boolean byEndTime = false;
            boolean byStartTime = false;
            boolean byDate = false;
            boolean byName = false;

            //endregion

            //region Get Input
//            ArrayList<Course> results;
//
//            System.out.println("Feeling Lucky? (Enter 'I', otherwise press enter to perform a normal search.");
//            input = in.nextLine();
//            if(input.trim().equalsIgnoreCase("i")) {
//                results = new ArrayList<>();
//                Course course = search.feelingLucky();
//                results.add(course);
//                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
//                System.out.print("[0] " + course);
//                return new AddCourseScreen(in, currentSchedule, results, currentUser);
//            }
            System.out.println("For each filter option, enter what you would like to search by, or type N.");
            System.out.println("Course code: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N")) {
                searchString += input + "\n";
                byCode = true;
            }
            System.out.println("Start time: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N")) {
                searchString += input + "\n";
                byStartTime = true;
            }
            System.out.println("End time: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N")) {
                searchString += input + "\n";
                byEndTime = true;
            }
            System.out.println("Meets: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N")) {
                searchString += input + "\n";
                byDate = true;
            }
            System.out.println("Course name: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N")) {
                searchString += input + "\n";
                byName = true;
            }

            //endregion

            //region Print Results

            ArrayList<Course> results = search.getResults(new SearchParameter(byCode, byStartTime, byEndTime,
                    byDate, byName, searchString));

            if (results.isEmpty()) {
                System.out.println("No results found for the specified query.");
            } else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                for (Course course : results) {
                    System.out.print("[" + entryNo + "] " + course);
                    entryNo++;
                }
            }

            //endregion

            return new AddCourseScreen(in, currentSchedule, results, currentUser);
        }

        //endregion

        else if (inputLine.trim().equalsIgnoreCase("I'm feeling lucky")) {
            Search search = new Search();
            ArrayList<Course> newCourses = new ArrayList<>();

            Course course = search.feelingLucky();
            newCourses.add(course);
            System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
            System.out.print("[0] " + course);

            return new AddCourseScreen(in, currentSchedule, newCourses, currentUser);
        } else if (inputWord.equalsIgnoreCase("home")) {
            return new HomeScreen(in, currentUser);
        } else {
            return new ExitScreen(in, this);
        }
    }

    @Override
    public void visualize() {
        if (courses.size() != 0) {
            System.out.println(String.format(
                    "\t\t\t\t\t%72s\n" +
                            "\t\t\t\t\t.______________________________________________________________________.\n" +
                            "\t\t\t\t\t| Add Course                                       from the Flamingoes |\n" +
                            "\t\t\t\t\t|                                                                      |\n" +
                            "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                            "\t\t\t\t\t|              - Add <index of course>                                 |\n" +
                            "\t\t\t\t\t|              - View                                                  |\n" +
                            "\t\t\t\t\t|              - I'm feeling lucky                                     |\n" +
                            "\t\t\t\t\t|              - Home                                                  |\n" +
                            "\t\t\t\t\t|              - Search                                                |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        } else {
            System.out.println(String.format(
                    "\t\t\t\t\t%72s\n" +
                            "\t\t\t\t\t.______________________________________________________________________.\n" +
                            "\t\t\t\t\t| Add Course                                       from the Flamingoes |\n" +
                            "\t\t\t\t\t|                                                                      |\n" +
                            "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                            "\t\t\t\t\t|              - View                                                  |\n" +
                            "\t\t\t\t\t|              - I'm feeling lucky                                     |\n" +
                            "\t\t\t\t\t|              - Home                                                  |\n" +
                            "\t\t\t\t\t|              - Search                                                |\n" +
                            "\t\t\t\t\t|                                                                      |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        }
    }
}
