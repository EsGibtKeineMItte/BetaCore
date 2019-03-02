package de.wk.betacore.util.misc;

public class StringUtils {
    public static String centerText(String text) {
        int maxWidth = 80,
                spaces = (int) Math.round((maxWidth-1.4*text.length())/2);
        return org.apache.commons.lang.StringUtils.repeat(" ", spaces)+text;
    }

}
