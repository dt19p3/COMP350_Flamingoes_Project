import java.util.ArrayList;

public class Schedule {

    ArrayList<Course> courses;
    String name;
    public Schedule() {
        name = "Untitled";
        courses = new ArrayList<>();
    }
    public Schedule(String name){
        this.name = name;
        courses = new ArrayList<>();
    }
    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        if (courses.size() == 0) {
            System.out.println("No courses to remove");
        }
        else {
            courses.remove(course);
        }
    }

    public void listCourses() {
        if (courses.size() == 0) {
            System.out.println("\t\t\t\t\t\t-  No courses in this schedule");
        }
        else {
            //for (int i = 0; i < courses.size(); i++) {
            int i = 1;
            for (Course c : courses) {
                System.out.println("\t\t\t\t\t\t"+ i + ". " + c.getLongTitle());
                i++;
            }
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
