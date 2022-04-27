import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SimilarityMap {
    public HashMap<String, HashMap<String, Integer>> map;
    public String src;

    public SimilarityMap(String filename){
        map = new HashMap<>();
        src = filename;
        File f = new File(filename);
        try {
            Scanner lineReader = new Scanner(f);
            while(lineReader.hasNextLine()){
                Scanner lineParser = new Scanner(lineReader.nextLine());
                lineParser.useDelimiter("\\.");

                String key = lineParser.next();
//                System.out.println(key);

                HashMap<String,Integer> subMap = map.getOrDefault(key,new HashMap<>());

                String subKey = lineParser.next();
//                System.out.println(subKey);


                String count = lineParser.next();
//                System.out.println(count);
                Integer subValue = Integer.valueOf(count);
//              Integer subValue = Integer.valueOf(lineParser.next());
                subMap.put(subKey,subValue);
                map.put(key, subMap);
                lineParser.close();
            }
            lineReader.close();


        } catch (FileNotFoundException e){
            System.out.println("Couldn't find a file :/");
        }


    }

    public void processShedule(Schedule sched){
        for(ScheduleItem s_key : sched.cours){
            for(ScheduleItem s_subKey : sched.cours){
                String key = s_key.code;
                String subKey = s_subKey.code;
                if(key.equalsIgnoreCase(subKey)) continue;
                Integer concurrence = map.getOrDefault(key,new HashMap<>()).getOrDefault(subKey,0);
                HashMap<String,Integer> subMap = map.getOrDefault(key,new HashMap<>());
                subMap.put(subKey,concurrence+1);
                map.put(key,subMap);
            }
        }
    }

    public String getSimilarCoursesStrings(Schedule sched){
        HashMap<String, Integer> merged = new HashMap<>();
        for(ScheduleItem s : sched.cours){
            for(String key : map.getOrDefault(s.code,new HashMap<>()).keySet()) {
                if (merged.containsKey(key)) {
                    merged.put(key, merged.get(key)+map.get(s.code).get(key));
                } else {
                    merged.put(key, map.get(s.code).get(key));
                }
            }
        }
        int[] max_vals = {-1,-1,-1,-1,-1};
        String[] max_names = {"","","","",""};

        for(String key : merged.keySet()){
            int maxdex = 4;
            int curval = merged.get(key);
            while(maxdex >= 0 && curval > max_vals[maxdex]){
                max_vals[maxdex] = curval;
                max_names[maxdex] = key;
                maxdex--;
            }
        }
        StringBuilder out = new StringBuilder();
        out.append("You might want to check out these courses:\n");
        String prevCode = "";
        for(int i = 0; i < max_vals.length; i++){
            if(!max_names[i].equalsIgnoreCase(prevCode)){
                out.append("-");
                out.append(max_names[i]);
                out.append("\n");
            }
            prevCode = max_names[i];
        }
        return out.toString();
    }

    public void flush(){
        File f = new File(src);
        try {
            PrintWriter pw = new PrintWriter(f);
            for(String key : map.keySet()){
                for(String subKey  : map.get(key).keySet()){
                    pw.println(key + "." + subKey + "." + map.get(key).get(subKey));
                }
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println("Couldn't find a file :/");
        }
    }
}
