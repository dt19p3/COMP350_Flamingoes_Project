import java.util.ArrayList;

public class SessionUser {
    //...attributes etc. This is just for testing
    public ArrayList<Schedule> schedules;
    public ArrayList<ScheduleItem> recentlyAdded;
    public Profile profile;
    public boolean isGuest, accountExists;
    public SessionUser(boolean isGuest){
        this.isGuest = isGuest;
        profile = new Profile("","");
        schedules = new ArrayList<>();
        recentlyAdded = new ArrayList<>();
    }
    public void login(Profile p) throws Exception {
        this.profile = p;
        Store s = new Store(profile.username, profile.password);
        accountExists = s.checkForUser(profile.username);
        if(accountExists) {
            if (s.login(s.username, s.password)) {
                profile.gradYear = (short) s.getGradYear(profile.username);
                profile.major = s.getMajor(profile.username);
                this.schedules = s.getSchedules(s.username);
            }
            if (this.schedules == null) {
                this.schedules = new ArrayList<>();
            }
        }
        else System.out.println("No account found");
    }

    public void register(String username, String password) throws Exception {
        Store s = new Store(username, password);
        s.register(username, password);
        this.profile = new Profile(s.username, s.password);


    }
}
