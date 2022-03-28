import java.time.LocalDate;
public class Profile {
    //attributes etc. . . . this is just for testing
    public String username;
    public String password;
    public String major;
    public short gradYear;
    public Profile(String username, String password){
        this.username = username;
        this.password = password;
        this.major = "Undeclared";
        this.gradYear = 0;
        //TODO shouldn't be public or store actual password
    }
}
