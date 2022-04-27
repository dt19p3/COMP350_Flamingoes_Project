import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Formatter;

public class ScheduleItem {
    String code;
    String shortTitle; //name in class diagram
    String longTitle; //description in class diagram
    LocalTime beginTime;
    LocalTime endTime;
    String meets;
    String building;
    String room;
    int enrollment;
    int capacity;
    int numCredits;
    String professor;
    String department;//
    String semester;
    ArrayList<ScheduleItem> labs;
    ArrayList<ScheduleItem> prereqs;
    boolean conflicts;
    ScheduleItem conflictingScheduleItem;

    // this is a constructor that uses all fields in the database
    public ScheduleItem(String code, String shortTitle, String longTitle, LocalTime beginTime, LocalTime endTime,
                        String meets, String building, String room, int enrollment, int capacity, int numCredits){
        this(code,  shortTitle, longTitle,beginTime, endTime, meets, building, room, enrollment, capacity,
                numCredits, "", "", "", new ArrayList<>(), new ArrayList<>());
        this.conflicts = false;
        this.conflictingScheduleItem = null;
    }

    public ScheduleItem(String code){ //Constructor for testing!
        this.code = code;
    }

   // this is a constructor that includes the fields in our class diagram, expected to use this when reading
    // schedule courses saved to file back into memory
   public ScheduleItem(String code, String shortTitle, String longTitle, LocalTime beginTime, LocalTime endTime,
                       String meets, String building, String room, int enrollment, int capacity, int numCredits,
                       String professor, String department, String semester, ArrayList<ScheduleItem> labs,
                       ArrayList<ScheduleItem> prereqs) {
       this.code = code;
       this.shortTitle = shortTitle;
       this.longTitle = longTitle;
       this.beginTime = beginTime;
       this.endTime = endTime;
       this.meets = meets;
       this.building = building;
       this.room = room;
       this.enrollment = enrollment;
       this.capacity = capacity;
       this.numCredits = numCredits;
       this.professor = professor;
       this.department = department;
       this.semester = semester;
       this.labs = labs;
       this.prereqs = prereqs;
   }

   public ScheduleItem getConflictingCourse() {
        return conflictingScheduleItem;
   }

   public void setConflictingCourse(ScheduleItem scheduleItem) {
        this.conflictingScheduleItem = scheduleItem;
   }

   public boolean getConflicts(){
        return conflicts;
    }

    public void setConflicts(boolean conflicts){
        this.conflicts = conflicts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getMeets() {
        return meets;
    }

    public void setMeets(String meets) {
        this.meets = meets;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public ArrayList<ScheduleItem> getLabs() {
        return labs;
    }

    public void setLabs(ArrayList<ScheduleItem> labs) {
        this.labs = labs;
    }

    public ArrayList<ScheduleItem> getPrereqs() {
        return prereqs;
    }

    public void setPrereqs(ArrayList<ScheduleItem> prereqs) {
        this.prereqs = prereqs;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        Formatter formatter = new Formatter(value);
        formatter.format("%11s %18s %4s %5s - %5s %5s %4s %d/%d", code, shortTitle, meets, beginTime,
                endTime, building, room, enrollment, capacity);
        return value.toString().replace("null", "TBD");
    }
}
