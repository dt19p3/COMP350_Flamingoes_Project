import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class Main {
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

    public static void storeAccount(String username, String salt, String hashpass) throws IOException, ParseException {
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
    }

    public static void login(String username, String password) throws Exception {
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
                    System.out.println("Login Successful!");
                else
                    System.out.println("Login Failed");
                break;
            }
        }
        if(!found) System.out.println("No Account Found");
    }

    public static void consoleVersion() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean done = false;

        while (!done){
            System.out.print("Please enter a command: ");
            String command = scan.nextLine();
            switch (command) {
                case "REGISTER" -> {
                    byte[] salt = generateSalt();
                    String saltString = Base64.getEncoder().encodeToString(salt);
                    System.out.print("Username: ");
                    String username = scan.nextLine();
                    System.out.print("Password: ");
                    String password = scan.nextLine();
                    String encryptedPassword = encrypt(password, salt);
                    storeAccount(username, saltString, encryptedPassword);
                }
                case "LOGIN" -> {
                    System.out.print("Username: ");
                    String username = scan.nextLine();
                    System.out.print("Password: ");
                    String password = scan.nextLine();
                    login(username, password);
                }
                case "EXIT" -> done = true;
                default -> System.out.println("Not a valid command!");
            }
        }


    }

    public static void main(String[] args) throws Exception {
        consoleVersion();
    }
}

