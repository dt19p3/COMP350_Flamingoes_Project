import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * The add course screen.
 */
public class AddCourseScreen extends Screen {

    public Schedule currentSchedule;
    public ArrayList<Course> courses;
    public SessionUser currentUser;

    public AddCourseScreen(Scanner scnr, Schedule currentSchedule, ArrayList<Course> courses, SessionUser currentUser) {
        super("Add course", new String[]{"Add", "View", "Search", "I'm feeling lucky", "Home", "See recently added"}, scnr);
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
            currentSchedule.addCourse(courses.get(index-1));
            if(currentUser.recentlyAdded.size() > 5) {
                currentUser.recentlyAdded.remove(currentUser.recentlyAdded.get(currentUser.recentlyAdded.size() - 1));
            }
            currentUser.recentlyAdded.add(courses.get(index - 1));
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

            System.out.println("For each filter option, enter what you would like to search by, or type N.");
            System.out.println("Course code: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N") && !input.isEmpty()) {
                searchString += input + "\n";
                byCode = true;
            }
            System.out.println("Start time: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N") && !input.isEmpty()) {
                searchString += input + "\n";
                byStartTime = true;
            }
            System.out.println("End time: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N") && !input.isEmpty()) {
                searchString += input + "\n";
                byEndTime = true;
            }
            System.out.println("Meets: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N") && !input.isEmpty()) {
                searchString += input + "\n";
                byDate = true;
            }
            System.out.println("Course name: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N") && !input.isEmpty()) {
                searchString += input + "\n";
                byName = true;
            }

            //endregion

            //region Print Results

            SearchParameter query = new SearchParameter(byCode, byStartTime, byEndTime,
                    byDate, byName, searchString);

            //Make sure user actually entered a query
            if(query.getFlags().isEmpty()) {
                System.out.println("You must search using at least one filter.");
                return new AddCourseScreen(in, currentSchedule, new ArrayList<>(), currentUser);
            }

            ArrayList<Course> results = search.getResults(query);

            String[] sMeets;
            for (int i = 0; i < results.size(); i++){
                String str1 = results.get(i).meets;
                sMeets = str1.split("");
                for (int j = 0; j < currentSchedule.getCourses().size(); j++){
                    for(int k = 0; k < sMeets.length; k++){
                        if(currentSchedule.getCourses().get(j).meets.contains(sMeets[k])){
                            if (results.get(i).beginTime.isBefore(currentSchedule.getCourses().get(j).endTime) && results.get(i).beginTime.isAfter(currentSchedule.getCourses().get(j).beginTime) || results.get(i).beginTime == currentSchedule.getCourses().get(j).beginTime ){
                                results.get(i).setConflicts(true);
                            }
                            if (currentSchedule.getCourses().get(j).endTime.isBefore(results.get(i).endTime) && currentSchedule.getCourses().get(j).endTime.isAfter(results.get(i).beginTime) || results.get(i).beginTime == currentSchedule.getCourses().get(j).beginTime ){
                                results.get(i).setConflicts(true);
                            }
                        }
                    }
                }
            }

            if (results.isEmpty()) {
                System.out.println("No results found for the specified query.");
            } else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                for (Course course : results) {
                    if (!course.getConflicts()){
                        System.out.print("[" + entryNo + "] " + course);
                        entryNo++;
                    }else{
                        System.out.println("This course can not be added because it has a time conflict with courses that are already in your current schedule:".toUpperCase(Locale.ROOT));
                        System.out.print(course);
                    }
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
            System.out.print("[1] " + course);

            return new AddCourseScreen(in, currentSchedule, newCourses, currentUser);
        } else if (inputWord.equalsIgnoreCase("home")) {
            return new HomeScreen(in, currentUser);
        } else if(inputLine.trim().equalsIgnoreCase("See recently added")){
            if (currentUser.recentlyAdded.isEmpty()) {
                System.out.println("No courses were added recently.");
            } else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                for (Course course : currentUser.recentlyAdded) {
                    System.out.print("[" + entryNo + "] " + course);
                    entryNo++;
                }
            }
            return new AddCourseScreen(in, currentSchedule, currentUser.recentlyAdded, currentUser);
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
                            "\t\t\t\t\t|              - See recently added                                    |\n" +
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
                            "\t\t\t\t\t|              - See recently added                                    |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        }
    }
}
