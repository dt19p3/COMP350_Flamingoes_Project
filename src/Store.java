import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class Store {
    public String username;
    public String password;
    public ArrayList<Schedule> schedules;
    public Store(String username, String password){
        this.username = username;
        this.password = password;
    }

    public byte[] generateSalt() throws Exception{
        try {
            SecureRandom rand = new SecureRandom();
            byte[] salt = new byte[16];
            rand.nextBytes(salt);
            return salt;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public String encrypt(final String base, byte[] salt)throws Exception{
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

    public void storeAccount(String username, String salt, String hashpass) throws IOException, ParseException {
        //Reads JSON File and copies it to users
        Object o = new JSONParser().parse(new FileReader("accounts.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray original = new JSONArray(users.get("Users").toString());

        //stores salt and hashed password details
        JSONObject accountDetails = new JSONObject();
        accountDetails.put("Salt", salt);
        accountDetails.put("Hashed Password", hashpass);

        //makes account object with details
        JSONObject account = new JSONObject();
        account.put(username, accountDetails);

        //adds the user to the already existing file
        original.put(account);

        //puts the updated  final user list in the json file
        JSONObject updated = new JSONObject();
        updated.put("Users", original);

        //writes to JSON File
        try{
            FileWriter file = new FileWriter("accounts.json");
            file.write(updated.toString());
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        addUserDetails(username);
        addInitialSchedules(username);
    }

    public boolean login(String username, String password) throws Exception {
        //Reads json file
        Object o = new JSONParser().parse(new FileReader("accounts.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray arr = new JSONArray(users.get("Users").toString());

        boolean found = false;
        for(int i = 0; i<arr.length(); i++){
            JSONObject checkAcc = (JSONObject) new JSONParser().parse(arr.getJSONObject(i).toString());
            if(checkAcc.containsKey(username)){
                found = true;
                JSONObject accDet = (JSONObject) checkAcc.get(username);
                String s =accDet.get("Salt").toString();
                byte[] sa = Base64.getDecoder().decode(s);
                if(accDet.get("Hashed Password").equals(encrypt(password, sa)))
                    //System.out.println("Login Successful!");
                    return true;
                else return false;
            }
        }
        return found;
    }




    public void addUserDetails(String username) throws IOException, ParseException {
        Object o = new JSONParser().parse(new FileReader("accountDetails.json"));
        JSONObject users = (JSONObject) o;

        JSONArray original = new JSONArray(users.get("Users").toString());

        JSONObject details = new JSONObject();
        details.put("Major", "Undeclared");
        details.put("Graduation Year", 0000);

        JSONObject account = new JSONObject();
        account.put(username, details);

        original.put(account);

        JSONObject updated = new JSONObject();
        updated.put("Users", original);

        try{
            FileWriter file = new FileWriter("accountDetails.json");
            file.write(updated.toString());
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addInitialSchedules(String username) throws IOException, ParseException{
        Object o = new JSONParser().parse(new FileReader("accountSchedules.json"));
        JSONObject users = (JSONObject) o;

        JSONArray original = new JSONArray(users.get("Users").toString());

        JSONObject schedules = new JSONObject();
        schedules.put("Schedules",null);

        JSONObject account = new JSONObject();
        account.put(username, schedules);

        original.put(account);

        JSONObject updated = new JSONObject();
        updated.put("Users", original);
        try{
            FileWriter file = new FileWriter("accountSchedules.json");
            file.write(updated.toString());
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public int getGradYear(String username) throws IOException, ParseException {
        int gradYear = 0;
        Object o = new JSONParser().parse(new FileReader("accountDetails.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray arr = new JSONArray(users.get("Users").toString());

        for(int i = 0; i<arr.length(); i++){
            JSONObject checkAcc = (JSONObject) new JSONParser().parse(arr.getJSONObject(i).toString());
            if(checkAcc.containsKey(username)){
                JSONObject accDet = (JSONObject) checkAcc.get(username);
                return Integer.parseInt(accDet.get("Graduation Year").toString());
            }
        }
        return gradYear;
    }

    public void setGradYear(String username, int gradYear)throws IOException, ParseException{
        Object o = new JSONParser().parse(new FileReader("accountDetails.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray arr = new JSONArray(users.get("Users").toString());
        for(int i = 0; i<arr.length(); i++){
            JSONObject checkAcc = (JSONObject) new JSONParser().parse(arr.getJSONObject(i).toString());
            if(checkAcc.containsKey(username)){
                JSONObject accDet = (JSONObject) checkAcc.get(username);
                accDet.replace("Graduation Year", gradYear);

                JSONObject account = new JSONObject();
                account.put(username, accDet);

                arr.remove(i);
                arr.put(account);

                JSONObject updated = new JSONObject();
                updated.put("Users", arr);
                try{
                    FileWriter file = new FileWriter("accountDetails.json");
                    file.write(updated.toString());
                    file.flush();
                    file.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public String getMajor(String username) throws IOException, ParseException {
        String major = "";
        Object o = new JSONParser().parse(new FileReader("accountDetails.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray arr = new JSONArray(users.get("Users").toString());

        for(int i = 0; i<arr.length(); i++){
            JSONObject checkAcc = (JSONObject) new JSONParser().parse(arr.getJSONObject(i).toString());
            if(checkAcc.containsKey(username)){
                JSONObject accDet = (JSONObject) checkAcc.get(username);
                major = accDet.get("Major").toString();
                break;
            }
        }
        return major;
    }

    public void setMajor(String username, String major)throws IOException, ParseException{
        Object o = new JSONParser().parse(new FileReader("accountDetails.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray arr = new JSONArray(users.get("Users").toString());
        for(int i = 0; i<arr.length(); i++){
            JSONObject checkAcc = (JSONObject) new JSONParser().parse(arr.getJSONObject(i).toString());
            if(checkAcc.containsKey(username)){
                JSONObject accDet = (JSONObject) checkAcc.get(username);
                accDet.replace("Major", major);

                JSONObject account = new JSONObject();
                account.put(username, accDet);

                arr.remove(i);
                arr.put(account);

                JSONObject updated = new JSONObject();
                updated.put("Users", arr);
                try{
                    FileWriter file = new FileWriter("accountDetails.json");
                    file.write(updated.toString());
                    file.flush();
                    file.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void storeSchedules(String username, ArrayList<Schedule> schedules)throws IOException, ParseException{
        Object o = new JSONParser().parse(new FileReader("accountSchedules.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray arr = new JSONArray(users.get("Users").toString());
        for(int i = 0; i<arr.length(); i++){
            JSONObject checkAcc = (JSONObject) new JSONParser().parse(arr.getJSONObject(i).toString());
            if(checkAcc.containsKey(username)){
                JSONObject accDet = (JSONObject) checkAcc.get(username);

                accDet.replace("Schedules", schedules);

                JSONObject account = new JSONObject();
                account.put(username, accDet);
                arr.remove(i);
                arr.put(account);

                JSONObject updated = new JSONObject();
                updated.put("Users", arr);
                try{
                    FileWriter file = new FileWriter("accountSchedules.json");
                    file.write(updated.toString());
                    file.flush();
                    file.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    public ArrayList<Schedule> getSchedules(String username) throws ParseException, IOException {
        ArrayList<Schedule> schedules = null;
        Object o = new JSONParser().parse(new FileReader("accountSchedules.json"));
        JSONObject users = (JSONObject) o;

        //copies the users into a JSONArray
        JSONArray arr = new JSONArray(users.get("Users").toString());

        for(int i = 0; i<arr.length(); i++) {
            JSONObject checkAcc = (JSONObject) new JSONParser().parse(arr.getJSONObject(i).toString());
            if (checkAcc.containsKey(username)) {
                JSONObject accDet = (JSONObject) checkAcc.get(username);
                this.schedules = (ArrayList<Schedule>) accDet.get("Schedules");
                return (ArrayList<Schedule>) accDet.get("Schedules");
            }
        }
        return schedules;
    }

//    public static void consoleVersion() throws Exception {
//        Scanner scan = new Scanner(System.in);
//        boolean done = false;
//
//        while (!done){
//            System.out.print("Please enter a command: ");
//            String command = scan.nextLine();
//            switch (command) {
//                case "REGISTER" -> {
//                    byte[] salt = generateSalt();
//                    String saltString = Base64.getEncoder().encodeToString(salt);
//                    System.out.print("Username: ");
//                    String username = scan.nextLine();
//                    System.out.print("Password: ");
//                    String password = scan.nextLine();
//                    storeAccount(username, saltString, encrypt(password, salt));
//                }
//                case "LOGIN" -> {
//                    System.out.print("Username: ");
//                    String username = scan.nextLine();
//                    System.out.print("Password: ");
//                    String password = scan.nextLine();
//                    login(username, password);
//                }
//                case "EXIT" -> done = true;
//                default -> System.out.println("Not a valid command!");
//            }
//        }
//
//
//    }

//    public static void main(String[] args) throws Exception {
//        consoleVersion();
//    }
}

