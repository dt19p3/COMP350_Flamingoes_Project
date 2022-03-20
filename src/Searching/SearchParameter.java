package Searching;

import java.util.EnumSet;

public class SearchParameter {
    public enum Flag {
        CODE,
        NAME,
        DATETIME
    }
    private EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);
    private String value;

    public SearchParameter(boolean byCourseCode, boolean byName, boolean byDateTime, String value) {
        if(byCourseCode) flags.add(Flag.CODE);
        if(byName) flags.add(Flag.NAME);
        if(byDateTime) flags.add(Flag.DATETIME);
        this.value = value;
    }

    public EnumSet<Flag> getFlags() {
        return flags;
    }

    public String getValue() {
        return value;
    }
}
