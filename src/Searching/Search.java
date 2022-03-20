package Searching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Search {
    XSSFWorkbook courses = null;
    XSSFSheet sheet = null;

    private final int CODE_COL = 0;
    private final int TITLE_LONG_COL = 2;
    private final int START_COL = 3;
    private final int END_COL = 4;
    private final int DAYS_COL = 5;

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
        sheet = courses.getSheetAt(0);
    }

    /**
     * Gets results of a search of GCC courses based on search parameters provided by user
     * @param searchParams Query specified by user
     * @return String displaying search results
     */
    public String getResults(SearchParameter searchParams) {
        StringBuilder value = new StringBuilder();
        String input = searchParams.getValue();

        if(searchParams.getFlags().contains(SearchParameter.Flag.CODE)) {
//            Row row = sheet.getRow(0); //returns the logical row
            String code = input.split("\n")[0];
            System.out.println(code);
            for(Row row : sheet) {
                Cell cell = row.getCell(CODE_COL);
//                System.out.println(cell.toString());
                if(cell.getStringCellValue().contains(code)) {
                    value.append(cell.getStringCellValue()).append("\n");
                }
            }
//            Cell cell=row.getCell(0); //getting the cell representing the given column
//            value = cell.getStringCellValue();
        }

        return value.toString();
    }

    public static void main(String[] args) {
        Search search = new Search();
        String input = "COMP\n";
        SearchParameter param = new SearchParameter(true, true, true, input);
        System.out.println(search.getResults(param));
    }
}
