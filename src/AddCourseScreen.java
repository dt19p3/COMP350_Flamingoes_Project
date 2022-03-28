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
        super("Add course", new String[]{"Add", "View", "Search", "I'm feeling lucky", "Search by profile", "Home", "See recently added"}, scnr);
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
            Course courseToAdd = courses.get(index-1);
            if(courseToAdd.getConflicts()) {
                System.out.println("This course conflicts with another course in your schedule. Add anyway? (Y/N)");
                String addAnyway = in.next();
                if(addAnyway.equalsIgnoreCase("Y")) {
                    currentSchedule.removeCourse(courseToAdd.conflictingCourse);
                    currentSchedule.addCourse(courseToAdd);
                }
                return new CreateScheduleScreen(in, currentSchedule, currentUser);
            }
            currentSchedule.addCourse(courseToAdd);
            if(currentUser.recentlyAdded.size() > 5) {
                currentUser.recentlyAdded.remove(currentUser.recentlyAdded.get(currentUser.recentlyAdded.size() - 1));
            }
            currentUser.recentlyAdded.add(courseToAdd);
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

            if (results.isEmpty()) {
                System.out.println("No results found for the specified query.");
            } else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                this.checkConflicts(results);
                for (Course course : results) {
                    if (!course.getConflicts()) {
                        System.out.print("[" + entryNo + "] " + course + "\n");
                    } else {
                        System.out.print("[" + entryNo + "] " + course + "    *\n");
                    }
                    entryNo++;
                }
                System.out.println("* - This course conflicts with a course in your schedule.");
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
            this.checkConflicts(newCourses);
            System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
            if (!course.getConflicts()) {
                System.out.print("[1] " + course + "\n");
            } else {
                System.out.print("[1] " + course + " *\n");
            }
            System.out.println("* - This course conflicts with a course in your schedule.");

            return new AddCourseScreen(in, currentSchedule, newCourses, currentUser);
        }
        else if (inputLine.trim().equalsIgnoreCase("Search by profile")) {
            Search search = new Search();
            ArrayList<Course> newCourses = search.searchByProfile(currentUser);

            if(newCourses.isEmpty()) {
                System.out.println("You must complete account setup to access this feature.");
            }
            else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                this.checkConflicts(newCourses);
                for (Course course : newCourses) {
                    if (!course.getConflicts()) {
                        System.out.print("[" + entryNo + "] " + course + "\n");
                    } else {
                        System.out.print("[" + entryNo + "] " + course + "*\n");
                    }
                    entryNo++;
                }
                System.out.println("* - This course conflicts with a course in your schedule.");
            }
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

    //region checkConflicts()

    /**
     * Checks which courses in a given list conflict with currently scheduled ones
     * @param results The list of courses to be checked
     */
    private void checkConflicts(ArrayList<Course> results) {
        String[] sMeets;
        for (Course result : results) {
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
                            "\t\t\t\t\t|              - Search                                                |\n" +
                            "\t\t\t\t\t|              - Search by profile                                     |\n" +
                            "\t\t\t\t\t|              - Home                                                  |\n" +
                            "\t\t\t\t\t|              - See recently added                                    |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        } else {
            System.out.println(String.format(
                    "\t\t\t\t\t%72s\n" +
                            "\t\t\t\t\t.______________________________________________________________________.\n" +
                            "\t\t\t\t\t| Add Course                                       from the Flamingoes |\n" +
                            "\t\t\t\t\t|                                                                      |\n" +
                            "\t\t\t\t\t| Enter one of the following:                                          |\n" +
                            "\t\t\t\t\t|              - Add <index of course>                                 |\n" +
                            "\t\t\t\t\t|              - View                                                  |\n" +
                            "\t\t\t\t\t|              - I'm feeling lucky                                     |\n" +
                            "\t\t\t\t\t|              - Search                                                |\n" +
                            "\t\t\t\t\t|              - Search by profile                                     |\n" +
                            "\t\t\t\t\t|              - Home                                                  |\n" +
                            "\t\t\t\t\t|              - See recently added                                    |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        }
    }
}
