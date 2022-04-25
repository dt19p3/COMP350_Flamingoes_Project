import java.util.ArrayList;
import java.util.Scanner;

/**
 * The add course screen.
 */
public class AddCourseScreen extends Screen {

    public Schedule currentSchedule;
    public ArrayList<ScheduleItem> cours;
    public SessionUser currentUser;

    public AddCourseScreen(Scanner scnr, Schedule currentSchedule, ArrayList<ScheduleItem> cours, SessionUser currentUser) {
        super("Add course", new String[]{"Add", "View", "Search", "I'm feeling lucky", "Search by profile", "Home", "See recently added"}, scnr);
        this.currentSchedule = currentSchedule;
        this.cours = new ArrayList<>(cours);
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        if (inputWord.equalsIgnoreCase("view")) {
            return new CreateScheduleScreen(in, currentSchedule, currentUser);
        } else if (inputWord.equalsIgnoreCase("add") && cours.size() != 0) {
            String[] entry = inputLine.split(" ");
            if (entry.length < 2) {
                return new ExitScreen(in, this);
            }
            int index = Integer.valueOf(entry[1]); //TODO error checking
            ScheduleItem scheduleItemToAdd = cours.get(index-1);
            if(scheduleItemToAdd.getConflicts()) {
                System.out.println("This course conflicts with another course in your schedule. Add anyway? (Y/N)");
                String addAnyway = in.next();
                if(addAnyway.equalsIgnoreCase("Y")) {
                    currentSchedule.removeCourse(scheduleItemToAdd.conflictingScheduleItem);
                    currentSchedule.addCourse(scheduleItemToAdd);
                }
                return new CreateScheduleScreen(in, currentSchedule, currentUser);
            }
            currentSchedule.addCourse(scheduleItemToAdd);
            if(currentUser.recentlyAdded.size() > 5) {
                currentUser.recentlyAdded.remove(currentUser.recentlyAdded.get(currentUser.recentlyAdded.size() - 1));
            }
            currentUser.recentlyAdded.add(scheduleItemToAdd);
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
            boolean byCredits = false;

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
            System.out.println("Course credit hours: ");
            input = in.nextLine();
            if (!input.trim().equalsIgnoreCase("N") && !input.isEmpty()) {
                searchString += input + "\n";
                byCredits = true;
            }

            //endregion

            //region Print Results

            SearchParameter query = new SearchParameter(byCode, byStartTime, byEndTime,
                    byDate, byName, byCredits, searchString);

            //Make sure user actually entered a query
            if(query.getFlags().isEmpty()) {
                System.out.println("You must search using at least one filter.");
                return new AddCourseScreen(in, currentSchedule, new ArrayList<>(), currentUser);
            }

            ArrayList<ScheduleItem> results = search.getResults(query);

            if (results.isEmpty()) {
                System.out.println("No results found for the specified query.");
            } else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                boolean areConflicts = false;
                this.checkConflicts(results);
                ArrayList<ScheduleItem> toRemove = new ArrayList<>();

//                for (int s = 0; s < currentSchedule.getCourses().size(); s++){
//                    for (int c = 0; c < results.size(); c++){
//                        if(currentSchedule.getCourses().get(s).code == results.get(c).code){
//                            toRemove.add(results.get(c));
//                        }
//                    }
//                }
//                results.removeAll(toRemove);
                //for (int s = 0; s < currentSchedule.getCourses().size(); s++) {
                    for (int c = 0; c < results.size(); c++) {
                        if (!results.get(c).getConflicts()) {
                            System.out.print("[" + entryNo + "] " + results.get(c) + "\n");
                        } else if (results.get(c).enrollment >= results.get(c).capacity) {
                            System.out.print("FULL " + results.get(c) + "\n");
//                        } else if (currentSchedule.getCourses().get(s).code == results.get(c).code){
//                            System.out.print("COURSE ALREADY IN SCHEDULE");
                        } else {
                            System.out.print("[" + entryNo + "] " + results.get(c) + "    *\n");
                            areConflicts = true;
                        }
                        entryNo++;
                    }
                //}

                if(areConflicts) {
                    System.out.println("* - This course conflicts with a course in your schedule.");
                }
            }

            //endregion

            return new AddCourseScreen(in, currentSchedule, results, currentUser);
        }

        //endregion

        else if (inputLine.trim().equalsIgnoreCase("I'm feeling lucky")) {
            Search search = new Search();
            ArrayList<ScheduleItem> newCours = new ArrayList<>();

            ScheduleItem scheduleItem = search.feelingLucky();
            newCours.add(scheduleItem);
            this.checkConflicts(newCours);
            boolean areConflicts = false;
            System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
            //for (int s = 0; s < currentSchedule.getCourses().size(); s++) {
                if (scheduleItem.enrollment >= scheduleItem.capacity) {
                    System.out.print("COURSE IS FULL, SEARCH AGAIN");
                } else if (!scheduleItem.getConflicts()) {
                    System.out.print("[1] " + scheduleItem + "\n");
//                } else if (currentSchedule.getCourses().get(s).code == course.code) {
//                    System.out.print("COURSE ALREADY IN SCHEDULE");
                } else {
                    System.out.print("[1] " + scheduleItem + " *\n");
                    areConflicts = true;
                }
            //}
            if(areConflicts) {
                System.out.println("* - This course conflicts with a course in your schedule.");
            }

            return new AddCourseScreen(in, currentSchedule, newCours, currentUser);
        }
        else if (inputLine.trim().equalsIgnoreCase("Search by profile")) {
            Search search = new Search();
            ArrayList<ScheduleItem> newCours = search.searchByProfile(currentUser);

            if(newCours.isEmpty()) {
                System.out.println("You must complete account setup to access this feature.");
            }
            else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                boolean areConflicts = false;
                this.checkConflicts(newCours);
                //for (int s = 0; s < currentSchedule.getCourses().size(); s++) {
                    for (int c = 0; c < newCours.size(); c++) {
                        if (newCours.get(c).getConflicts()) {
                            System.out.print("[" + entryNo + "] " + newCours.get(c) + "*\n");
//                        } else if (currentSchedule.getCourses().get(s).code == newCourses.get(c).code){
//                            System.out.print("COURSE ALREADY IN SCHEDULE");
                        } else if (newCours.get(c).enrollment >= newCours.get(c).capacity) {
                            System.out.print("FULL " + newCours.get(c) + "\n");
                        } else {
                            System.out.print("[" + entryNo + "] " + newCours.get(c) + "\n");
                            areConflicts = true;
                        }
                        entryNo++;
                    }
                //}
                if(areConflicts) {
                    System.out.println("* - This course conflicts with a course in your schedule.");
                }
            }
            return new AddCourseScreen(in, currentSchedule, newCours, currentUser);
        } else if (inputWord.equalsIgnoreCase("home")) {
            return new HomeScreen(in, currentUser);
        } else if(inputLine.trim().equalsIgnoreCase("See recently added")){
            if (currentUser.recentlyAdded.isEmpty()) {
                System.out.println("No courses were added recently.");
            } else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                for (ScheduleItem scheduleItem : currentUser.recentlyAdded) {
                    System.out.print("[" + entryNo + "] " + scheduleItem);
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
        if (cours.size() != 0) {
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
