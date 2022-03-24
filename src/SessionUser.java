import java.util.ArrayList;

public class SessionUser {
    //...attributes etc. This is just for testing
    public ArrayList<Schedule> schedules;
    public Profile profile;
    public SessionUser(boolean isGuest){
        schedules = new ArrayList<>();
    }
    public void login(Profile p){
        //TODO make schedules equal to user's saved schedules CHRISTIAN
        this.profile = p;
    }
}
