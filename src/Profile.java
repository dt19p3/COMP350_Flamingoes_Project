import org.json.simple.parser.ParseException;

import java.io.IOException;
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

    public void storeGradYear(short gradYear) throws IOException, ParseException {
        Store s = new Store(username, password);
        s.setGradYear(username, gradYear);
    }

    public void storeMajor (String major)throws IOException, ParseException {
        Store s = new Store(username, password);
        s.setMajor(username, major);
    }
}
