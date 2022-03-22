import java.time.LocalTime;
import java.util.Formatter;

public class Course {
    String code;
    String shortTitle;
    String longTitle;
    LocalTime beginTime;
    LocalTime endTime;
    String meets;
    String building;
    String room;
    int enrollment;
    int capacity;

    public Course (String code,  String shortTitle, String longTitle, LocalTime beginTime, LocalTime endTime,
                   String meets, String building, String room, int enrollment, int capacity){
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
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        Formatter formatter = new Formatter(value);
        formatter.format("%11s %18s %4s %5s - %5s %5s %4s %d/%d\n", code, shortTitle, meets, beginTime,
                endTime, building, room, enrollment, capacity);
        return value.toString().replace("null", "TBD");
    }
}
