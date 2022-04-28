import java.io.*;
import java.util.ArrayList;

public class ExportScheduleScreen {



    public static void PrintToFile (Schedule s) throws IOException {
        //PrintWriter out = new PrintWriter(filePath);
        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", s.getName()+".txt");
        FileWriter fw = new FileWriter(s.getName()+".txt");

        ArrayList<ScheduleItem> items = s.getCourses();
        if (items.size() == 0) {
            System.out.println("\t\t\t\t\t\t-  No courses in this schedule");
        }
        else {
            for (ScheduleItem c : items) {
                //System.out.println("\t\t\t\t\t\t"+ i + ". " + c.getLongTitle());
                fw.write(c.getCode() + " " + c.getLongTitle() + "   " + c.getMeets() + " " + c.getBeginTime() + "-" + c.getEndTime() + "\n");
            }
        }

        pb.start();
        fw.flush();

    }


}
