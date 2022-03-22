import java.util.EnumSet;

/**
 * Represents all search filters/queries passed in by user
 */
public class SearchParameter {

    //region Member Variables

    /**
     * Flags for various search filters
     */
    public enum Flag {
        CODE,
        NAME,
        DATE,
        START_TIME,
        END_TIME
    }
    private EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);
    private String value;

    //endregion

    /**
     * Used in Search class to specify what user is searching for
     * @param byCourseCode Determines if user is searching by course code
     * @param byStartTime Determines if user is searching by start time
     * @param byEndTime Determines if user is searching by end time
     * @param byDate Determines if user is searching by date
     * @param byName Determines if user is searching by course name
     * @param value Specifies values entered by user, each value should be new line delimited
     */
    public SearchParameter(boolean byCourseCode, boolean byStartTime, boolean byEndTime, boolean byDate,
                           boolean byName, String value) {
        if(byCourseCode) flags.add(Flag.CODE);
        if(byStartTime) flags.add(Flag.START_TIME);
        if(byEndTime) flags.add(Flag.END_TIME);
        if(byName) flags.add(Flag.NAME);
        if(byDate) flags.add(Flag.DATE);
        this.value = value;
    }

    public EnumSet<Flag> getFlags() {
        return flags;
    }

    public String getValue() {
        return value;
    }
}