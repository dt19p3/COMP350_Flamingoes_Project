import java.util.ArrayList;

public class SessionUser {
    //...attributes etc. This is just for testing
    public ArrayList<Schedule> schedules;
    public Profile profile;
    public boolean isGuest;
    public SessionUser(boolean isGuest){
        this.isGuest = isGuest;
        profile = new Profile("","");
        schedules = new ArrayList<>();
    }
    public void login(Profile p){
        //TODO make schedules equal to user's saved schedules CHRISTIAN
        this.profile = p;
    }
}
