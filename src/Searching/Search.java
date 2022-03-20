package Searching;

//region Imports

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.text.SimpleDateFormat;

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

    //endregion

    //region Search()

    /**
     * Initializes search class by opening and preparing spreadsheet for search
     */
    public Search() {
        try {
            FileInputStream coursesFIS = new FileInputStream("CourseDB_WithFictionalCapacities.xlsx");
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
     * @return String displaying search results
     */
    public String getResults(SearchParameter searchParams) {

        //region __init__

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

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        StringBuilder value = new StringBuilder();
        Formatter formatter = new Formatter(value);
        String input = searchParams.getValue();

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
            }
        }

        //endregion

        //region Next, filter by time

        if(searchParams.getFlags().contains(SearchParameter.Flag.TIME)) {

            //Assumes start time & end time are new line delineated, in 24hr format
            //TODO: Ask - will they always enter start & finish time or can they enter just start or just finish
            LocalTime start_in = LocalTime.parse(input.split("\n")[nextInput]);
            nextInput++;
            LocalTime end_in = LocalTime.parse(input.split("\n")[nextInput]);
            nextInput++;

            for (Iterator<Row> it = matchingRows.iterator(); it.hasNext(); ) {
                Row row = it.next();
                Cell startTime = row.getCell(COL_START);
                Cell endTime = row.getCell(COL_END);

                if(startTime.getCellTypeEnum().equals(CellType.NUMERIC) && endTime.getCellTypeEnum().equals(CellType.NUMERIC)) {
                    LocalTime start = LocalTime.parse(formatTime.format(startTime.getDateCellValue()));
                    LocalTime finish = LocalTime.parse(formatTime.format(endTime.getDateCellValue()));

                    if(start_in.isAfter(start) || end_in.isBefore(finish)) {
                        it.remove();
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
            }
        }

        //endregion

        //region String building

        for(Row row : matchingRows) {
            String code = row.getCell(COL_CODE).getStringCellValue();
            String title = row.getCell(COL_SHORT_TITLE).getStringCellValue();
            String start = "N/A";
            if(row.getCell(COL_START).getCellTypeEnum().equals(CellType.NUMERIC))
                start = formatTime.format(row.getCell(COL_START).getDateCellValue());
            String end = "N/A";
            if(row.getCell(COL_END).getCellTypeEnum().equals(CellType.NUMERIC))
                end = formatTime.format(row.getCell(COL_END).getDateCellValue());
            String days = row.getCell(COL_DAYS).getStringCellValue();
            String building = row.getCell(COL_BUILDING).getStringCellValue();
            if(building.equals("NULL")) {
                building = "TBD";
            }
            String room = "";
            if(row.getCell(COL_ROOM).toString().contains(".")) {
                room = row.getCell(COL_ROOM).toString().split("\\.")[0];
            }
            int enrollment = (int)row.getCell(COL_ENROLLMENT).getNumericCellValue();
            int capacity = (int)row.getCell(COL_CAPACITY).getNumericCellValue();

            formatter.format("%s %18s %3s %5s %s %5s %5s %4s %d%s%d\n", code, title, days, start, "-", end,
                    building, room, enrollment, "/", capacity);
        }

        //endregion

        return value.toString();
    }

    //endregion

    public static void main(String[] args) {
        Search search = new Search();
        String input = "COMP";
        SearchParameter param = new SearchParameter(true, false, false, false, input);
        System.out.println(search.getResults(param));
    }
}