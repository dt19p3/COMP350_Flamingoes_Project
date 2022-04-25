import java.util.ArrayList;
import java.util.Comparator;

public class ViewCalendarScheduleScreen {
//        public static void main(String[] args) {
//            Course class1 = new Course("COMP 314 A", "FOUN COMP SCI", "FOUNDATIONS OF COMPUTER SCIENCE", LocalTime.of(8,00,00), LocalTime.of(10,50,00),"MWF", "STEM", "326", 1, 1, 1, "", "", "", null, null);
//            Course class2 = new Course("MATH 162 D", "CALCULUS II", "CALCULUS II", LocalTime.of(12,00,00), LocalTime.of(12,50,00), "MWF", "HAL", "110", 1, 1, 1, "", "", "", null, null);
//            Course class3 = new Course("BUSA 211 B", "BUS COMP APP", "BUSINESS COMPUTER APPLICATIONS", LocalTime.of(14,00,00), LocalTime.of(14,50,00), "MWF", "HAL", "213", 1, 1, 1, "", "", "", null, null);
//            Course class4 = new Course("COMP 314 A", "PARALLEL COMP", "PARALLEL COMPUTING", LocalTime.of(8,00,00), LocalTime.of(12,15,00), "TR", "STEM", "326", 1, 1, 1, "", "", "", null, null);
//            Course class5 = new Course ("SCIC 203 A", "ATOMS-MOLECULES", "ATOMS, MOLECULES & MATERIAL WORLD", LocalTime.of(13,00,00), LocalTime.of(13,45,00), "TR", "STEM", "51", 1, 1, 1, "", "", "", null, null);
//            Course class6 = new Course ("SCIC 203 N L", "TEST TEST TEST", "LABORATORY", LocalTime.of(18,30,00), LocalTime.of(16,00,00), "W", "STEM", "255", 1, 1, 1, "", "", "", null, null);
//
//            ArrayList<Course> courses = new ArrayList<>();
//            courses.add(class1);
//            courses.add(class2);
//            courses.add(class3);
//            courses.add(class4);
//            courses.add(class5);
//            courses.add(class6);
//
//            printCalendar(courses);
//        }

        public static void printCalendar(ArrayList<ScheduleItem> cours) {
            cours.sort(Comparator.comparing((ScheduleItem a) -> a.beginTime));

            ArrayList<ScheduleItem> monday = new ArrayList<>();
            ArrayList<ScheduleItem> tuesday = new ArrayList<>();
            ArrayList<ScheduleItem> wednesday = new ArrayList<>();
            ArrayList<ScheduleItem> thursday = new ArrayList<>();
            ArrayList<ScheduleItem> friday = new ArrayList<>();

            for (int i = 0; i < cours.size(); i++){
                if (cours.get(i).meets.contains("M")){
                    monday.add(cours.get(i));
                }
                if (cours.get(i).meets.contains("T")){
                    tuesday.add(cours.get(i));
                }
                if (cours.get(i).meets.contains("W")){
                    wednesday.add(cours.get(i));
                }
                if (cours.get(i).meets.contains("R")){
                    thursday.add(cours.get(i));
                }
                if (cours.get(i).meets.contains("F")){
                    friday.add(cours.get(i));
                }
            }

            String mLongest = "";
            for (int i = 0; i < monday.size(); i++){
                if (monday.get(i).beginTime.getHour() == 18) {
                    if (("(00:00) " + monday.get(i).shortTitle).length() > getLongest(monday).length()){
                        mLongest = "(00:00) " + getLongest(monday);
                        break;
                    } else {
                        mLongest = getLongest(monday);
                    }
                }else {
                    mLongest = getLongest(monday);
                }
            }

            String tLongest = "";
            for (int i = 0; i < tuesday.size(); i++){
                if (tuesday.get(i).beginTime.getHour() == 10 || tuesday.get(i).beginTime.getHour() == 11 || tuesday.get(i).beginTime.getHour() == 14 || tuesday.get(i).beginTime.getHour() == 18) {
                    if (("(00:00) " + tuesday.get(i).shortTitle).length() > getLongest(tuesday).length()){
                        tLongest = "(00:00) " + getLongest(tuesday);
                        break;
                    } else {
                        tLongest = getLongest(tuesday);
                    }
                }else {
                    tLongest = getLongest(tuesday);
                }
            }

            String wLongest = "";
            for (int i = 0; i < wednesday.size(); i++){
                if (wednesday.get(i).beginTime.getHour() == 18) {
                    if (("(00:00) " + wednesday.get(i).shortTitle).length() > getLongest(wednesday).length()){
                        wLongest = "(00:00) " + getLongest(wednesday);
                        break;
                    } else {
                        wLongest = getLongest(wednesday);
                    }
                }else {
                    wLongest = getLongest(wednesday);
                }
            }

            String rLongest = "";
            for (int i = 0; i < thursday.size(); i++){
                if (thursday.get(i).beginTime.getHour() == 10 || thursday.get(i).beginTime.getHour() == 11 || thursday.get(i).beginTime.getHour() == 14 || thursday.get(i).beginTime.getHour() == 18) {
                    if (("(00:00) " + thursday.get(i).shortTitle).length() > getLongest(thursday).length()){
                        rLongest = "(00:00) " + getLongest(thursday);
                        break;
                    } else {
                        rLongest = getLongest(thursday);
                    }
                }else {
                    rLongest = getLongest(thursday);
                }
            }

            String fLongest = "";
            for (int i = 0; i < friday.size(); i++){
                if (friday.get(i).beginTime.getHour() == 18) {
                    if (("(00:00) " + friday.get(i).shortTitle).length() > getLongest(friday).length()){
                        fLongest = "(00:00) " + getLongest(friday);
                        break;
                    } else {
                        fLongest = getLongest(friday);
                    }
                }else {
                    fLongest = getLongest(friday);
                }
            }

            if (mLongest.length() < "monday".length()){
                mLongest = "monday";
            }
            if (tLongest.length() < "tuesday".length()){
                tLongest = "tuesday";
            }
            if (wLongest.length() < "wednesday".length()){
                wLongest = "wednesday";
            }
            if (rLongest.length() < "thursday".length()){
                rLongest = "thursday";
            }
            if (fLongest.length() < "friday".length()){
                fLongest = "friday";
            }

            StringBuilder timeSpacesBuilder = new StringBuilder();
            for (int i = 0; i < "12:00am".length(); i++){
                timeSpacesBuilder.append(" ");
            }
            String timeSpaces = timeSpacesBuilder.toString();

            int totalLength = timeSpaces.length() + mLongest.length() + tLongest.length() + wLongest.length() + rLongest.length() + fLongest.length();
            StringBuilder dashes = new StringBuilder();
            for (int i = 0; i < totalLength+7; i++){
                dashes.append("-");
            }

            ArrayList<String> minput = input(monday, mLongest);
            ArrayList<String> tinput = input(tuesday, tLongest);
            ArrayList<String> winput = input(wednesday, wLongest);
            ArrayList<String> rinput = input(thursday, rLongest);
            ArrayList<String> finput = input(friday, fLongest);

            System.out.printf(
                    "\t\t\t\t\t" + dashes + "\n"+
                            "\t\t\t\t\t" + "|" + timeSpaces
                            + "|MONDAY" + spaces(mLongest.length()-"monday".length()) +"|TUESDAY" + spaces(tLongest.length()-"tuesday".length()) + "|WEDNESDAY" + spaces(wLongest.length()-"wednesday".length()) + "|THURSDAY" + spaces(rLongest.length()-"thursday".length()) + "|FRIDAY" + spaces(fLongest.length()-"friday".length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|8:00am |" + "%1$s" + spaces(mLongest.length()-minput.get(0).length()) + "|" + "%10$s" + spaces(tLongest.length()-tinput.get(0).length() ) + "|" + "%19$s" + spaces(wLongest.length()-winput.get(0).length()) + "|" +"%28$s" + spaces(rLongest.length()-rinput.get(0).length()) + "|" + "%37$s" + spaces(fLongest.length()-finput.get(0).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|9:00am |" + "%2$s" + spaces(mLongest.length()-minput.get(1).length()) + "|" + "%11$s" + spaces(tLongest.length()-tinput.get(1).length()) + "|" + "%20$s" + spaces(wLongest.length()-winput.get(1).length()) + "|" +"%29$s" + spaces(rLongest.length()-rinput.get(1).length()) + "|" + "%38$s" + spaces(fLongest.length()-finput.get(1).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|10:00am|" + "%3$s"  + spaces(mLongest.length()-minput.get(2).length()) + "|" + "%12$s" + spaces(tLongest.length()-tinput.get(2).length()) + "|" + "%21$s" + spaces(wLongest.length()-winput.get(2).length()) + "|" +"%30$s" + spaces(rLongest.length()-rinput.get(2).length()) + "|" + "%39$s" + spaces(fLongest.length()-finput.get(2).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|11:00am|" + "%4$s" + spaces(mLongest.length()-minput.get(3).length()) + "|" + "%13$s" + spaces(tLongest.length()-tinput.get(3).length()) + "|" + "%22$s" + spaces(wLongest.length()-winput.get(3).length()) + "|" +"%31$s" + spaces(rLongest.length()-rinput.get(3).length()) + "|" + "%40$s" + spaces(fLongest.length()-finput.get(3).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|12:00pm|" + "%5$s" + spaces(mLongest.length()-minput.get(4).length()) + "|" + "%14$s" + spaces(tLongest.length()-tinput.get(4).length()) + "|" + "%23$s" + spaces(wLongest.length()-winput.get(4).length()) + "|" +"%32$s" + spaces(rLongest.length()-rinput.get(4).length()) + "|" + "%41$s" + spaces(fLongest.length()-finput.get(4).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|1:00pm |" + "%6$s" + spaces(mLongest.length()-minput.get(5).length()) + "|" + "%15$s" + spaces(tLongest.length()-tinput.get(5).length()) + "|" + "%24$s" + spaces(wLongest.length()-winput.get(5).length()) + "|" +"%33$s" + spaces(rLongest.length()-rinput.get(5).length()) + "|" + "%42$s" + spaces(fLongest.length()-finput.get(5).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|2:00pm |" + "%7$s" + spaces(mLongest.length()-minput.get(6).length()) + "|" + "%16$s" + spaces(tLongest.length()-tinput.get(6).length()) + "|" + "%25$s" + spaces(wLongest.length()-winput.get(6).length()) + "|" +"%34$s" + spaces(rLongest.length()-rinput.get(6).length()) + "|" + "%43$s" + spaces(fLongest.length()-finput.get(6).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|3:00pm |" + "%8$s" + spaces(mLongest.length()-minput.get(7).length()) + "|" + "%17$s" + spaces(tLongest.length()-tinput.get(7).length()) + "|" +"%26$s" + spaces(wLongest.length()-winput.get(7).length()) + "|" +"%35$s" + spaces(rLongest.length()-rinput.get(7).length()) + "|" + "%44$s" + spaces(fLongest.length()-finput.get(7).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n" +
                            "\t\t\t\t\t" + "|4:00pm |" + "%9$s" + spaces(mLongest.length()-minput.get(8).length()) + "|" + "%18$s" + spaces(tLongest.length()-tinput.get(8).length()) + "|" +"%27$s" + spaces(wLongest.length()-winput.get(8).length()) + "|" +"%36$s" + spaces(rLongest.length()-rinput.get(8).length()) + "|" + "%45$s" + spaces(fLongest.length()-finput.get(8).length()) + "|\n" +
                            "\t\t\t\t\t" + dashes + "\n",
                    minput.get(0), minput.get(1), minput.get(2), minput.get(3), minput.get(4), minput.get(5), minput.get(6), minput.get(7), minput.get(8),
                    tinput.get(0), tinput.get(1), tinput.get(2), tinput.get(3), tinput.get(4), tinput.get(5), tinput.get(6), tinput.get(7), tinput.get(8),
                    winput.get(0), winput.get(1), winput.get(2), winput.get(3), winput.get(4), winput.get(5), winput.get(6), winput.get(7), winput.get(8),
                    rinput.get(0), rinput.get(1), rinput.get(2), rinput.get(3), rinput.get(4), rinput.get(5), rinput.get(6), rinput.get(7), rinput.get(8),
                    finput.get(0), finput.get(1), finput.get(2), finput.get(3), finput.get(4), finput.get(5), finput.get(6), finput.get(7), finput.get(8)
            );

            if (!minput.get(9).startsWith(" ") || !tinput.get(9).startsWith(" ") || !winput.get(9).startsWith(" ") || !rinput.get(9).startsWith(" ") || !finput.get(9).startsWith(" ")){
                System.out.printf(
                        "\t\t\t\t\t" + "NIGHT CLASSES" + "\n" +
                                "\t\t\t\t\t" + dashes + "\n" +
                                "\t\t\t\t\t" + "|6:00pm |" + "%1$s" + spaces(mLongest.length()-minput.get(9).length()) + "|" + "%2$s" + spaces(tLongest.length()-tinput.get(9).length()) + "|" +"%3$s" + spaces(wLongest.length()-winput.get(9).length()) + "|" +"%4$s" + spaces(rLongest.length()-rinput.get(9).length()) + "|" + "%5$s" + spaces(fLongest.length()-finput.get(9).length()) + "|\n" +
                                "\t\t\t\t\t" + dashes + "\n" +
                                "\t\t\t\t\t" + "|7:00pm |" + "%6$s" + spaces(mLongest.length()-minput.get(10).length()) + "|" + "%7$s" + spaces(tLongest.length()-tinput.get(10).length()) + "|" +"%8$s" + spaces(wLongest.length()-winput.get(10).length()) + "|" +"%9$s" + spaces(rLongest.length()-rinput.get(10).length()) + "|" + "%10$s" + spaces(fLongest.length()-finput.get(10).length()) + "|\n" +
                                "\t\t\t\t\t" + dashes + "\n",
                        minput.get(9), tinput.get(9), winput.get(9), rinput.get(9), finput.get(9),
                        minput.get(10), tinput.get(10), winput.get(10), rinput.get(10), finput.get(10)
                );
            }
        }

        public static String getLongest(ArrayList<ScheduleItem> classesInDay){
            String longest = "";
            for (int i = 0; i < classesInDay.size(); i++){
                if(classesInDay.get(i).shortTitle.length() > longest.length()){
                    longest = classesInDay.get(i).shortTitle;
                }
            }
            return longest;
        }

        public static String spaces (int n){
            StringBuilder spaces = new StringBuilder();
            for (int i = 0; i < n; i++){
                spaces.append(" ");
            }
            return spaces.toString();
        }

        public static ArrayList<String> input (ArrayList<ScheduleItem> classesInDay, String longest){
            ArrayList<String> input = new ArrayList<>();

            String eightInput, nineInput, tenInput, elevenInput, twelveInput, oneInput, twoInput, threeInput, fourInput;
            String nightSix, nightSeven;

            int i = 0;
            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 8) {
                eightInput = classesInDay.get(i).shortTitle;
                i++;
            } else {
                eightInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 9){
                nineInput = classesInDay.get(i).shortTitle;
                i++;
            } else {
                nineInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 10){
                if(classesInDay.get(i).meets.contains("T") || classesInDay.get(i).meets.contains("R")){
                    tenInput = "(10:05) " + classesInDay.get(i).shortTitle;
                }else{
                    tenInput = classesInDay.get(i).shortTitle;
                }
                i++;
            } else {
                tenInput = spaces(longest.length()-"(10:05)".length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 11){
                if(classesInDay.get(i).meets.contains("T") || classesInDay.get(i).meets.contains("R")){
                    elevenInput = "(11:30) " + classesInDay.get(i).shortTitle;
                }else{
                    elevenInput = classesInDay.get(i).shortTitle;
                }
                i++;
            } else {
                elevenInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 12){
                twelveInput = classesInDay.get(i).shortTitle;
                i++;
            } else {
                twelveInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 13){
                oneInput = classesInDay.get(i).shortTitle;
                i++;
            } else {
                oneInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 14){
                if(classesInDay.get(i).meets.contains("T") || classesInDay.get(i).meets.contains("R")){
                    twoInput = "(02:30) " + classesInDay.get(i).shortTitle;
                }else{
                    twoInput = classesInDay.get(i).shortTitle;
                }
                i++;
            } else {
                twoInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 15){
                threeInput = classesInDay.get(i).shortTitle;
                i++;
            } else {
                threeInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 16){
                fourInput = classesInDay.get(i).shortTitle;
                i++;
            } else {
                fourInput = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 18){
                nightSix = "(06:30) " + classesInDay.get(i).shortTitle;
                i++;
            } else {
                nightSix = spaces(longest.length());
            }

            if (i < classesInDay.size() && classesInDay.get(i).beginTime.getHour() == 19){
                nightSeven = classesInDay.get(i).shortTitle;
                i++;
            } else {
                nightSeven = spaces(longest.length());
            }

            input.add(eightInput);
            input.add(nineInput);
            input.add(tenInput);
            input.add(elevenInput);
            input.add(twelveInput);
            input.add(oneInput);
            input.add(twoInput);
            input.add(threeInput);
            input.add(fourInput);
            input.add(nightSix);
            input.add(nightSeven);

            return input;
        }
    }
