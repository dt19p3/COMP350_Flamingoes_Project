import java.util.ArrayList;

public class Schedule {

    ArrayList<ScheduleItem> cours;
    String name;
    public Schedule() {
        name = "Untitled";
        cours = new ArrayList<>();
    }
    public Schedule(String name){
        this.name = name;
        cours = new ArrayList<>();
    }
    public void addCourse(ScheduleItem scheduleItem) { cours.add(scheduleItem); }

    public void removeCourse(ScheduleItem scheduleItem) {
        if (cours.size() == 0) {
            System.out.println("No courses to remove");
        }
        else {
            cours.remove(scheduleItem);
        }
    }

    public void listCourses() {
        if (cours.size() == 0) {
            System.out.println("\t\t\t\t\t\t-  No courses in this schedule");
        }
        else {
            //for (int i = 0; i < courses.size(); i++) {
            int i = 1;
            for (ScheduleItem c : cours) {
                System.out.println("\t\t\t\t\t\t"+ i + ". " + c.getLongTitle());
                i++;
            }
        }
    }

    public ArrayList<ScheduleItem> getCourses() {
        return cours;
    }

    public void setCourses(ArrayList<ScheduleItem> cours) {
        this.cours = cours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
