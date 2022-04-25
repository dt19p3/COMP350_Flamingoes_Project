//region Imports

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//endregion

/**
 * Represents a search being performed by the user
 */
public class Search {

    //region Member Variables

    XSSFWorkbook courses = null;
    XSSFSheet sheet = null;

    final int COL_CODE = 0;
    final int COL_SHORT_TITLE = 1;
    final int COL_TITLE = 2;
    final int COL_START = 3;
    final int COL_END = 4;
    final int COL_DAYS = 5;
    final int COL_BUILDING = 6;
    final int COL_ROOM = 7;
    final int COL_ENROLLMENT = 8;
    final int COL_CAPACITY = 9;
    final int COL_CREDITS = 10;

    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

    //endregion

    //region Search()

    /**
     * Initializes search class by opening and preparing spreadsheet for search
     */
    public Search() {
        try {
            FileInputStream coursesFIS = new FileInputStream("new_data.xlsx");
            courses = new XSSFWorkbook(coursesFIS);
        }
        catch(FileNotFoundException e) {
            System.out.println("The specified document could not be retrieved (Make sure it's in the correct directory).");
        }
        catch(IOException io) {
            System.out.println("The specified document could not be converted (is it a .xslx?).");
        }
        //We just have one sheet, so grab the first one
        sheet = courses.getSheetAt(0);
    }

    //endregion

    //region getResults()

    /**
     * Gets results of a search of GCC courses based on search parameters provided by user
     * @param searchParams Query specified by user
     * @return List of courses found by search
     */
    public ArrayList<Course> getResults(SearchParameter searchParams) {

        //region __init__

        formatTime.setLenient(false);
        String input = searchParams.getValue();
        ArrayList<Course> results = new ArrayList<>();

        boolean skipHeader = true;
        ArrayList<Row> matchingRows = new ArrayList<>();
        for(Row row : sheet) {
            if (skipHeader) {
                skipHeader = false;
                continue;
            }
            matchingRows.add(row);
        }

        int nextInput = 0;
        boolean matchesFound = false;

        //endregion

        //region Filter search by Course Code

        if(searchParams.getFlags().contains(SearchParameter.Flag.CODE)) {
            //Assumes that user input is being passed in as a new-line delineated string
            String code = input.split("\n")[nextInput];
            nextInput++;

            //Iterate through ArrayList using iterator so we can remove from it
            for (Iterator<Row> it = matchingRows.iterator(); it.hasNext(); ) {
                Row row = it.next();
                Cell courseCode = row.getCell(COL_CODE);

                //If the codes don't match, remove that row
                if(!(courseCode.getStringCellValue().contains(code))) {
                    it.remove();
                }
                //We need to note that matches were found, if not we need to report that the search found nothing
                else if (!matchesFound) {
                    matchesFound = true;
                }
            }
        }

        //endregion

        //region Next, filter by start time

        if(searchParams.getFlags().contains(SearchParameter.Flag.START_TIME)) {

            //Assumes start time is new line delineated, in 24hr format
            String start_in_str = input.split("\n")[nextInput];
            nextInput++;

            //Check if the user entered valid times in HH:mm format
            //If not, return an empty ArrayList since that search would yield 0 results

            if(!isValidDate(start_in_str)) {
                return new ArrayList<>();
            }

            LocalTime start_in;

            try {
                start_in = LocalTime.parse(start_in_str.trim());
            } catch(DateTimeParseException pe) {
                return new ArrayList<>();
            }

            for (Iterator<Row> it = matchingRows.iterator(); it.hasNext(); ) {
                Row row = it.next();
                Cell startTime = row.getCell(COL_START);

                if(startTime.getCellTypeEnum().equals(CellType.NUMERIC)) {
                    LocalTime start = LocalTime.parse(formatTime.format(startTime.getDateCellValue()));

                    if(start_in.isAfter(start)) {
                        it.remove();
                    }
                    else if(!matchesFound){
                        matchesFound = true;
                    }
                }
                //Remove the class from consideration if the time is NULL
                else {
                    it.remove();
                }
            }
        }

        //endregion

        //region Next, filter by end time

        if(searchParams.getFlags().contains(SearchParameter.Flag.END_TIME)) {

            String end_in_str = input.split("\n")[nextInput];
            nextInput++;

            //Check if the user entered valid times in HH:mm format
            //If not, return an empty ArrayList since that search would yield 0 results
            if(!isValidDate(end_in_str)) {
                return new ArrayList<>();
            }

            LocalTime end_in;
            try {
                end_in = LocalTime.parse(end_in_str.trim());
            } catch(DateTimeParseException pe) {
                return new ArrayList<>();
            }

            for (Iterator<Row> it = matchingRows.iterator(); it.hasNext(); ) {
                Row row = it.next();
                Cell endTime = row.getCell(COL_END);

                if(endTime.getCellTypeEnum().equals(CellType.NUMERIC)) {
                    LocalTime end = LocalTime.parse(formatTime.format(endTime.getDateCellValue()));

                    if(end_in.isBefore(end)) {
                        it.remove();
                    }
                    else if(!matchesFound){
                        matchesFound = true;
                    }
                }
                //Remove the class from consideration if the time is NULL
                else {
                    it.remove();
                }
            }

        }

        //endregion

        //region Next, filter by meeting days

        if(searchParams.getFlags().contains(SearchParameter.Flag.DATE)) {
            String meetDays_in = input.split("\n")[nextInput];
            nextInput++;


            for (Iterator<Row> it = matchingRows.iterator(); it.hasNext(); ) {
                Row row = it.next();
                Cell meetDays = row.getCell(COL_DAYS);

                if(!meetDays.getStringCellValue().contains(meetDays_in)) {
                    it.remove();
                }
                else if (!matchesFound) {
                    matchesFound = true;
                }
            }
        }

        //endregion

        //region Next, filter by course name

        if(searchParams.getFlags().contains(SearchParameter.Flag.NAME)) {
            String name_in = input.split("\n")[nextInput].toUpperCase();

            for (Iterator<Row> it = matchingRows.iterator(); it.hasNext(); ) {
                Row row = it.next();
                Cell name = row.getCell(COL_TITLE);

                if(!name.getStringCellValue().contains(name_in)) {
                    it.remove();
                }
                else if (!matchesFound) {
                    matchesFound = true;
                }
            }
        }

        //region Next, filter by course credit hours

        if(searchParams.getFlags().contains(SearchParameter.Flag.CREDITS)) {
            //Assumes that user input is being passed in as a new-line delineated string
            String code = input.split("\n")[nextInput];
            nextInput++;

            //Iterate through ArrayList using iterator so we can remove from it
            for (Iterator<Row> it = matchingRows.iterator(); it.hasNext(); ) {
                Row row = it.next();
                Cell courseCredits = row.getCell(COL_CREDITS);

                //If the codes don't match, remove that row
                if(!(courseCredits.getStringCellValue().contains(code))) {
                    it.remove();
                }
                //We need to note that matches were found, if not we need to report that the search found nothing
                else if (!matchesFound) {
                    matchesFound = true;
                }
            }
        }


        //endregion

        //region Returns

        for(Row row : matchingRows) {
            String code = row.getCell(COL_CODE).getStringCellValue();
            String shortTitle = row.getCell(COL_SHORT_TITLE).getStringCellValue();
            String longTitle = row.getCell(COL_TITLE).getStringCellValue();
            LocalTime start = null;
            if(row.getCell(COL_START).getCellTypeEnum().equals(CellType.NUMERIC))
                start = LocalTime.parse(formatTime.format(row.getCell(COL_START).getDateCellValue()));
            LocalTime end = null;
            if(row.getCell(COL_END).getCellTypeEnum().equals(CellType.NUMERIC))
                end = LocalTime.parse(formatTime.format(row.getCell(COL_END).getDateCellValue()));
//            LocalTime start = LocalTime.parse(row.getCell(COL_START).getStringCellValue(), DateTimeFormatter.ISO_LOCAL_TIME);
//            LocalTime end = LocalTime.parse(row.getCell(COL_END).getStringCellValue(), DateTimeFormatter.ISO_LOCAL_TIME);
            String days = row.getCell(COL_DAYS).getStringCellValue();
            String building = row.getCell(COL_BUILDING).getStringCellValue();
            if(building.equals("NULL")) {
                building = "TBD";
            }
            String room = "";
            if(row.getCell(COL_ROOM).toString().contains(".")) {
                room = row.getCell(COL_ROOM).toString().split("\\.")[0];
            }
            int enrollment = Integer.valueOf(row.getCell(COL_ENROLLMENT).getStringCellValue());
            int capacity = Integer.valueOf(row.getCell(COL_CAPACITY).getStringCellValue());
            int numCredits = Integer.valueOf(row.getCell(COL_CREDITS).getStringCellValue());

            results.add(new Course(code, shortTitle, longTitle, start, end, days, building, room,
                    enrollment, capacity, numCredits));
        }

        return results;

        //endregion
    }

    //region isValidDate()

    /**
     * Checks if the supplied string is a valid date
     * @param inDate The date to be checked
     * @return True if valid date, otherwise false
     */
    private static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    //endregion

    //endregion

    //region I'm Feeling Lucky

    /**
     * @return 1 completely random course
     */
    public Course feelingLucky() {
        Random rand = new Random();
        int randRow = rand.nextInt(1, sheet.getLastRowNum());

        Row row = sheet.getRow(randRow);

        String code = row.getCell(COL_CODE).getStringCellValue();
        String shortTitle = row.getCell(COL_SHORT_TITLE).getStringCellValue();
        String longTitle = row.getCell(COL_TITLE).getStringCellValue();
        LocalTime start = null;
        if(row.getCell(COL_START).getCellTypeEnum().equals(CellType.NUMERIC))
            start = LocalTime.parse(formatTime.format(row.getCell(COL_START).getDateCellValue()));
        LocalTime end = null;
        if(row.getCell(COL_END).getCellTypeEnum().equals(CellType.NUMERIC))
            end = LocalTime.parse(formatTime.format(row.getCell(COL_END).getDateCellValue()));
        String days = row.getCell(COL_DAYS).getStringCellValue();
        String building = row.getCell(COL_BUILDING).getStringCellValue();
        if(building.equals("NULL")) {
            building = "TBD";
        }
        String room = "";
        if(row.getCell(COL_ROOM).toString().contains(".")) {
            room = row.getCell(COL_ROOM).toString().split("\\.")[0];
        }
        int enrollment = Integer.valueOf(row.getCell(COL_ENROLLMENT).getStringCellValue());
        int capacity = Integer.valueOf(row.getCell(COL_CAPACITY).getStringCellValue());
        int numCredits = Integer.valueOf(row.getCell(COL_CREDITS).getStringCellValue());

        return new Course(code, shortTitle, longTitle, start, end, days, building, room,
                enrollment, capacity, numCredits);
    }

    //endregion

    //region Search by Profile

    /**
     * Searches for courses based on a given user's profile information
     * @param user The user searching for courses
     * @return A list of courses
     */
    public ArrayList<Course> searchByProfile(SessionUser user) {
        short gradYear = user.profile.gradYear;

        //Allow someone whose major is undeclared to still use this feature
        String major = user.profile.major;
        if(major.equals("Undeclared")) {
            major = "";
        }
        if(user.isGuest || (gradYear == 0 && major.equals(""))) {
            return new ArrayList<>();
        }

        String courseCode = major;
        String courseNum;
        int yearsTillGrad = gradYear - LocalDate.now().getYear();
        courseNum = switch (yearsTillGrad) {
            case 0, 1 -> " 4";
            case 2 -> " 3";
            case 3 -> " 2";
            default -> " 1";
        };
        String input = courseCode + courseNum;
        SearchParameter query = new SearchParameter(true, false,
                false, false, false, false, input);
        ArrayList<Course> courses = getResults(query);

        //Since highest course code for HUMAs is 302, change 4's to 3's
        if(courseNum.equals(" 4")) {
            courseNum = " 3";
        }
        input = "HUMA" + courseNum;
        query = new SearchParameter(true, false,
                false, false, false, false, input);
        courses.addAll(getResults(query));

        return courses;
    }

    //endregion

    public static void main(String[] args) {
        Search search = new Search();
        String input = "COMP\n8:00";
        SessionUser user = new SessionUser(false);
//        SearchParameter param = new SearchParameter(true, true, false, false, false, input);
//        for(Course course : search.getResults(param)) System.out.println(course);
        System.out.println(search.searchByProfile(user));
    }
}