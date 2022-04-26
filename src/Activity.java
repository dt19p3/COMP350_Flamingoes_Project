import java.time.LocalTime;

public class Activity extends ScheduleItem {

    public Activity( String activityName, String longTitle, LocalTime beginTime,
                    LocalTime endTime, String meets, String building, String room,
                    int enrollment, int capacity, int numCredits) {
        super("", activityName, "", beginTime, endTime, meets, "", "", 0, 0, 0);
    }


}
