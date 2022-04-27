import java.util.ArrayList;
import java.util.Scanner;

public class Scratch {
    public static void main(String[] args) throws Exception {
//        Scanner scnr = new Scanner(System.in);
//        Screen curscreen = new LoginScreen(scnr, "");
//        while(curscreen != null){
//            curscreen.visualize();
//            curscreen = curscreen.input();
//        }
//        scnr.close();
        SimilarityMap sm = new SimilarityMap("simmap.txt");
        for(String key : sm.map.keySet()){
            for(String skey : sm.map.get(key).keySet()){
                System.out.println(key + " " + skey + " " + sm.map.get(key).get(skey));
            }
        }


        Schedule s = new Schedule();
        s.addCourse(new ScheduleItem("COMP 200"));
        s.addCourse(new ScheduleItem("COMP 201"));
        s.addCourse(new ScheduleItem("COMP 345"));
        s.addCourse(new ScheduleItem("COMP 302"));
        sm.processShedule(s);

        Schedule s2 = new Schedule();
        s2.addCourse(new ScheduleItem("COMP 200"));
        s2.addCourse(new ScheduleItem("COMP 345"));
        s2.addCourse(new ScheduleItem("COMP 302"));

        for(String key : sm.map.keySet()){
            for(String skey : sm.map.get(key).keySet()){
                System.out.println(key + " " + skey + " " + sm.map.get(key).get(skey));
            }
        }
        System.out.println(sm.getSimilarCoursesStrings(s2));
        sm.flush();
    }
}
