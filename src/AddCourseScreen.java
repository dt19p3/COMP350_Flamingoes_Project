import java.util.ArrayList;
import java.util.Scanner;

/**
 * The add course screen.
 */
public class AddCourseScreen extends Screen {

    public Schedule currentSchedule;
    public ArrayList<ScheduleItem> cours;
    public SessionUser currentUser;

    public AddCourseScreen(Scanner scnr, Schedule currentSchedule, ArrayList<ScheduleItem> cours, SessionUser currentUser, String input) {
        super("Add course", new String[]{"Add", "View", "Search", "I'm feeling lucky", "Search by profile", "Home", "See recently added", "See suggestions"}, scnr, input);
        this.currentSchedule = currentSchedule;
        this.cours = new ArrayList<>(cours);
        this.currentUser = currentUser;
    }

    @Override
    public Screen input() {
        String inputWord = in.next();
        String inputLine = inputWord + in.nextLine();
        this.input = inputLine;

        if (inputWord.equalsIgnoreCase("view")) {
            return new CreateScheduleScreen(in, currentSchedule, currentUser,this.input);
        } else if (inputWord.equalsIgnoreCase("add") && cours.size() != 0) {
            String[] entry = inputLine.split(" ");
            if (entry.length < 2) {
                return new ExitScreen(in, this,this.input);
            }
            String indexString = entry[1];
            while((!isInteger(indexString) ||
                    (Integer.parseInt(indexString) > cours.size() || Integer.parseInt(indexString) < 1))) {
                System.out.println("Please enter one of the integers listed above to add a course.");
                indexString = in.nextLine();
            }
            int index = Integer.parseInt(indexString);
            ScheduleItem scheduleItemToAdd = cours.get(index-1);
            if(scheduleItemToAdd.getConflicts()) {
                System.out.println("This course conflicts with another course in your schedule. Add anyway? (Y/N)");
                String addAnyway = in.next();
                if(addAnyway.equalsIgnoreCase("Y")) {
                    currentSchedule.removeCourse(scheduleItemToAdd.conflictingScheduleItem);
                    currentSchedule.addCourse(scheduleItemToAdd);
                }
                return new CreateScheduleScreen(in, currentSchedule, currentUser,this.input);
            }
            currentSchedule.addCourse(scheduleItemToAdd);
            if(currentUser.recentlyAdded.size() > 5) {
                currentUser.recentlyAdded.remove(currentUser.recentlyAdded.get(currentUser.recentlyAdded.size() - 1));
            }
            currentUser.recentlyAdded.add(scheduleItemToAdd);
            return new CreateScheduleScreen(in, currentSchedule, currentUser,this.input);
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
                return new AddCourseScreen(in, currentSchedule, new ArrayList<>(), currentUser,this.input);
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

                if(currentSchedule.getCourses().size() > 0) {
                    for (int s = 0; s < currentSchedule.getCourses().size(); s++) {
                        for (int c = 0; c < results.size(); c++) {
                            if (!results.get(c).getConflicts()) {
                                if (results.get(c).enrollment >= results.get(c).capacity){
                                    if(currentSchedule.getCourses().get(s).code == results.get(c).code){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {
                                        System.out.print("FULL " + results.get(c) + "\n");
                                    }
                                } else {
                                    if(currentSchedule.getCourses().get(s).code == results.get(c).code){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {
                                        System.out.print("[" + entryNo + "] " + results.get(c) + "\n");
                                    }
                                }
                            } else {
                                if (results.get(c).enrollment >= results.get(c).capacity){
                                    if(currentSchedule.getCourses().get(s).code.equalsIgnoreCase(results.get(c).code)){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {
                                        System.out.print("FULL"  + results.get(c) + "    *\n");
                                        areConflicts = true;
                                    }
                                } else {
                                    if(currentSchedule.getCourses().get(s).code.equalsIgnoreCase(results.get(c).code)){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {
                                        System.out.print("[" + entryNo + "] " + results.get(c) + "    *\n");
                                        areConflicts = true;
                                    }
                                }
                            }
                            entryNo++;
                        }
                    }
                } else {
                    for (int c = 0; c < results.size(); c++) {
                        if (!results.get(c).getConflicts()) {
                            if (results.get(c).enrollment >= results.get(c).capacity){
                                System.out.print("FULL " + results.get(c) + "\n");
                            }else{
                                System.out.print("[" + entryNo + "] " + results.get(c) + "\n");
                            }
                        } else {
                            if (results.get(c).enrollment >= results.get(c).capacity){
                                System.out.print("FULL " + results.get(c) + "    *\n");
                            }else {
                                System.out.print("[" + entryNo + "] " + results.get(c) + "    *\n");
                                areConflicts = true;
                            }
                        }
                        entryNo++;
                    }
                }

                if(areConflicts) {
                    System.out.println("* - This course conflicts with a course in your schedule.");
                }
            }

            //endregion

            return new AddCourseScreen(in, currentSchedule, results, currentUser,this.input);
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
            if (currentSchedule.getCourses().size() > 0) {
                for (int s = 0; s < currentSchedule.getCourses().size(); s++) {
                    if (scheduleItem.enrollment >= scheduleItem.capacity) {
                        System.out.print("COURSE IS FULL, SEARCH AGAIN\n");
                    } else if (!scheduleItem.getConflicts()) {
                        if (currentSchedule.getCourses().get(s).code.equalsIgnoreCase(scheduleItem.code)){
                            System.out.print("COURSE ALREADY IN SCHEDULE\n");
                        } else {
                            System.out.print("[1] " + scheduleItem + "\n");
                        }
                    } else {
                        if (currentSchedule.getCourses().get(s).code.equalsIgnoreCase(scheduleItem.code)){
                            System.out.print("COURSE ALREADY IN SCHEDULE\n");
                        } else {
                            System.out.print("[1] " + scheduleItem + "    *\n");
                            areConflicts = true;
                        }
                    }
                }
            } else {
                if (scheduleItem.enrollment >= scheduleItem.capacity) {
                    System.out.print("COURSE IS FULL, SEARCH AGAIN");
                } else if (!scheduleItem.getConflicts()) {
                    System.out.print("[1] " + scheduleItem + "\n");
                } else {
                    System.out.print("[1] " + scheduleItem + "    *\n");
                    areConflicts = true;
                }
            }

            if(areConflicts) {
                System.out.println("* - This course conflicts with a course in your schedule.");
            }

            return new AddCourseScreen(in, currentSchedule, newCours, currentUser,this.input);
        }
        else if (inputLine.trim().equalsIgnoreCase("Search by profile")) {
            Search search = new Search();
            ArrayList<ScheduleItem> newCours = search.searchByProfile(currentUser);

            if(newCours.isEmpty()) {
                System.out.println("You must complete account setup to access this feature.");
            } else {
                System.out.println(" #  Course Code        Course Name        Meets        Location   E/C");
                int entryNo = 1;
                boolean areConflicts = false;
                this.checkConflicts(newCours);
                //enter if there are courses in the schedule
                if (currentSchedule.getCourses().size() > 0) {
                    //s = index of course in current schedule
                    for (int s = 0; s < currentSchedule.getCourses().size(); s++) {
                        //c= index of course returned by searching
                        for (int c = 0; c < newCours.size(); c++) {
                            //enter if the course returned from search does not have a time conflict with a course in current schedule
                            if (!newCours.get(c).getConflicts()) {
                                //enter if the course is full
                                if (newCours.get(c).enrollment >= newCours.get(c).capacity){
                                    //enter if the course is already in schedule
                                    if(currentSchedule.getCourses().get(s).code == newCours.get(c).code){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {     //enter if the course is not in schedule already and the course is full
                                        System.out.print("FULL " + newCours.get(c) + "\n");
                                    }
                                } else {    //enter if the course is not full
                                    //enter if the course is already in schedule
                                    if(currentSchedule.getCourses().get(s).code == newCours.get(c).code){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {     //enter if the course is not in the schedule already and course is not not full
                                        System.out.print("[" + entryNo + "] " + newCours.get(c) + "\n");
                                    }
                                }
                            } else {    //enter if there are no time conflicts
                                //enter if the course is full
                                if (newCours.get(c).enrollment >= newCours.get(c).capacity){
                                    //enter if the course is already in schedule
                                    if(currentSchedule.getCourses().get(s).code.equalsIgnoreCase(newCours.get(c).code)){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {     //enter if the course is not in schedule and course is full
                                        System.out.print("FULL"  + newCours.get(c) + "    *\n");
                                        areConflicts = true;
                                    }
                                } else {    //enter if the course is not full
                                    //enter if the course is already in schedule
                                    if(currentSchedule.getCourses().get(s).code.equalsIgnoreCase(newCours.get(c).code)){
                                        System.out.print("COURSE ALREADY IN SCHEDULE\n");
                                    }else {     //enter if the course is not in schedule and course is not full
                                        System.out.print("[" + entryNo + "] " + newCours.get(c) + "    *\n");
                                        areConflicts = true;
                                    }
                                }
                            }
                            entryNo++;
                        }
                    }
                } else {
                    for (int c = 0; c < newCours.size(); c++) {
                        if (!newCours.get(c).getConflicts()) {
                            if (newCours.get(c).enrollment >= newCours.get(c).capacity){
                                System.out.print("FULL " + newCours.get(c) + "\n");
                            }else{
                                System.out.print("[" + entryNo + "] " + newCours.get(c) + "\n");
                            }
                        } else {
                            if (newCours.get(c).enrollment >= newCours.get(c).capacity){
                                System.out.print("FULL " + newCours.get(c) + "    *\n");
                            }else {
                                System.out.print("[" + entryNo + "] " + newCours.get(c) + "    *\n");
                                areConflicts = true;
                            }
                        }
                        entryNo++;
                    }
                }

                if(areConflicts) {
                    System.out.println("* - This course conflicts with a course in your schedule.");
                }
            }
            return new AddCourseScreen(in, currentSchedule, newCours, currentUser,this.input);
        } else if (inputWord.equalsIgnoreCase("home")) {
            return new HomeScreen(in, currentUser,this.input);
        } else if(inputLine.trim().equalsIgnoreCase("See recently added")) {
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
            return new AddCourseScreen(in, currentSchedule, currentUser.recentlyAdded, currentUser, this.input);
        } else if(inputLine.trim().equalsIgnoreCase("See suggestions")) {
            SimilarityMap sm = new SimilarityMap("simmap.txt");
            System.out.println(sm.getSimilarCoursesStrings(currentSchedule));
            return this;
        } else {
            return new ExitScreen(in, this,this.input);
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
                            "\t\t\t\t\t|              - View                                                  |\n" +
                            "\t\t\t\t\t|              - I'm feeling lucky                                     |\n" +
                            "\t\t\t\t\t|              - Search                                                |\n" +
                            "\t\t\t\t\t|              - Search by profile                                     |\n" +
                            "\t\t\t\t\t|              - Home                                                  |\n" +
                            "\t\t\t\t\t|              - See recently added                                    |\n" +
                            "\t\t\t\t\t|              - See suggestions                                       |\n" +
                            "\t\t\t\t\t|______________________________________________________________________|\n",currentSchedule.getName()));
        }
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
