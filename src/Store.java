import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

//@SuppressWarnings("ALL")
public class Store {
    //Constructor
    public String username;
    public String password;
    public ArrayList<Schedule> schedules;
    public Store(String username, String password){
        this.username = username;
        this.password = password;
    }


    private Connection connect() {
        String url = "jdbc:sqlite:Database\\Passwords.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:Database\\" + fileName;

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createUserTable(){
        String url = "jdbc:sqlite:COMP350_Flamingoes_Project\\Database\\passwords.db";

        String sql = """
            CREATE TABLE IF NOT EXISTS users (
             user text PRIMARY KEY,
             salt text,
             hashedPassword text,
             gradYear integer,
             major text
            );""";
        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public static byte[] generateSalt() throws Exception{
        try {
            SecureRandom rand = new SecureRandom();
            byte[] salt = new byte[16];
            rand.nextBytes(salt);
            return salt;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public static String encrypt(final String base, byte[] salt)throws Exception{
        try{
            final MessageDigest hashFunction = MessageDigest.getInstance("SHA-256");
            hashFunction.update(salt);
            final byte[] password = hashFunction.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hashedPassword = new StringBuilder();
            for (byte b : password) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hashedPassword.append('0');
                hashedPassword.append(hex);
            }
            return hashedPassword.toString();
        }catch(Exception ex){
            throw new Exception(ex);
        }
    }

    public void register(String username, String password) throws Exception {
        byte[] salt = generateSalt();
        String saltString = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encrypt(password, salt);

        String sql = "INSERT INTO users(user, salt, hashedPassword, gradYear, major) VALUES(?,?,?,?,?)";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, saltString);
            pstmt.setString(3,encryptedPassword);
            pstmt.setInt(4, 0);
            pstmt.setString(5, "Undeclared");
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean checkForUser(String username){
        boolean found = false;
        String sql = "SELECT * FROM users WHERE user = ?";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs    = pstmt.executeQuery();

            if(rs.next()) found = true;
            conn.close();

        }catch(SQLException e){
            System.out.println("fail");
            return false;
        }
        return found;

    }

    public boolean login(String username, String password){
        boolean loggedIn = false;
        if(checkForUser(username)){
            String sql = "SELECT * FROM users WHERE user = ?";
            try{
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                ResultSet rs    = pstmt.executeQuery();

                String userSalt = rs.getString("salt");
                byte[] salt = Base64.getDecoder().decode(userSalt);

                loggedIn = rs.getString("hashedPassword").equals(encrypt(password, salt));
                conn.close();
            }catch (SQLException e){
                System.out.println("uh oh");
                //System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("uh oh2");
                //e.printStackTrace();
            }
        }
        else{
            System.out.println("No User found");
            return false;
        }
        return loggedIn;
    }

    public void setGradYear(String username, int gradYear){
        String sql = "UPDATE users SET gradYear = ? WHERE user = ?";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gradYear);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getGradYear(String username){
        int year = 0;
        String sql = "SELECT * FROM users WHERE user = ?";

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs    = pstmt.executeQuery();

            year = rs.getInt("gradYear");
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return year;
    }

    public void setMajor(String username, String major){
        String sql = "UPDATE users SET major = ? WHERE user = ?";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, major);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMajor(String username){
        String major = null;
        String sql = "SELECT * FROM users WHERE user = ?";

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs    = pstmt.executeQuery();

            major = rs.getString("major");
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return major;
    }

    public static void addSchedule(String username, String schedName, Schedule schedule){
        String url = "jdbc:sqlite:Database\\Passwords.db";
//        String sql = "INSERT INTO schedules(user, scheduleName, schedule) VALUES(?,?,?)";
        String sql = "INSERT INTO schedules(user, scheduleName, code, shortTitle, longTitle, beginTime, endTime," +
                " meets, building, room, enrollment, capacity, numCredits, professor, department, semester) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ArrayList<ScheduleItem> c = schedule.cours;

        for(ScheduleItem s: c) {
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, schedName);
                pstmt.setString(3, s.code);
                pstmt.setString(4, s.shortTitle);
                pstmt.setString(5, s.longTitle);
                pstmt.setTime(6, Time.valueOf(s.beginTime));
                pstmt.setTime(7, Time.valueOf(s.endTime));
                pstmt.setString(8, s.meets);
                pstmt.setString(9, s.building);
                pstmt.setString(10, s.room);
                pstmt.setInt(11, s.enrollment);
                pstmt.setInt(12,s.capacity);
                pstmt.setInt(13,s.numCredits);
                pstmt.setString(14, s.professor);
                pstmt.setString(15, s.department);
                pstmt.setString(16, s.semester);
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Schedule> getSchedules(String username){
        String url = "jdbc:sqlite:Database\\Passwords.db";
        String sql = "SELECT * FROM schedules WHERE user = ?";
        ArrayList<Schedule> userSched = new ArrayList<>();
        ArrayList<String> schedNames = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs    = pstmt.executeQuery();

            while(rs.next()){
                if(!schedNames.contains(rs.getString(3))){
                    schedNames.add(rs.getString(3));
                }
            }

            rs.close();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try {
            for(String schedN: schedNames) {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                Schedule sched = new Schedule();
                while(rs.next()) {
                    if (rs.getString(3).equals(schedN)) {
                        ScheduleItem c = new ScheduleItem(
                                rs.getString(4), rs.getString(5), rs.getString(6),
                                rs.getTime(7).toLocalTime(), rs.getTime(8).toLocalTime(),
                                rs.getString(9), rs.getString(10), rs.getString(11),
                                rs.getInt(12), rs.getInt(13), rs.getInt(14)
                        );
                        sched.addCourse(c);
                    }
                }
                sched.setName(schedN);
                userSched.add(sched);
                rs.close();
                conn.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return userSched;
    }

    public static void removeScheduleFromDatabase(String username, String schedName){
        String url = "jdbc:sqlite:Database\\Passwords.db";
        String sql = "DELETE FROM schedules WHERE user = ? AND scheduleName = ?";

        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2,schedName);
            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }



//    public static void main(String[] args) throws Exception {
//        createNewDatabase("Passwords");
//
//        Store app = new Store("user", "password");
//        app.register("user", "password");
//        System.out.println(app.checkForUser("user9"));
//        app.login("user3", "password2");
//        app.setGradYear("user", 2023);
//        app.setMajor("user", "COMM");
//        app.getGradYear("user");
//        System.out.println(app.getMajor("user"));
//    }

}

