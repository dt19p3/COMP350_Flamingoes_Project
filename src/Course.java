import java.time.LocalTime;

public class Course {
    String code;
    String shortTitle;
    String longTitle;
    LocalTime beginTime;
    LocalTime endTime;
    String meets;
    String building;
    String room;

    public Course (String code,  String shortTitle, String longTitle, LocalTime beginTime, LocalTime endTime,
                   String meets, String building, String room){
        this.code = code;
        this.shortTitle = shortTitle;
        this.longTitle = longTitle;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.meets = meets;
        this.building = building;
        this.room = room;
    }
}
