package de.wk.betacore.appearance;

public class Color {

    /**
     * Convert between &a-f or &0-9 to a Color
     *
     * @param string Your String to Convert
     * @return Your converted String
     */

    public static String ConvertColor(String string) {
        string = string.replaceAll("&([a-f0-9])", "§$1");
        return string;
    }

    /**
     * Convert between &a-f or &0-9 to a Color
     *
     * @param string Your String Array to Convert
     * @return Your Converted String as an Array
     */

    public static String[] ConvertColor(String[] string) {
        for (int i = 0; i < string.length; i++) {
            string[i] = string[i].replaceAll("&([a-f0-9])", "§$1");
        }
        return string;
    }

    /**
     * Convert between &k-o or &? to Bold or Rainbow Text
     *
     * @param string Your String to Convert
     * @return Your converted String
     */

    public static String ConvertSpecial(String string) {
        if (string.contains("&?")) {
            string = ConvertColor(string);
            string = string.replaceAll("§[a-f0-9]", "");
            string = string.replaceAll("&([k-o])", "");
            string = string.replaceAll("&r", "");
            string = string.replaceAll("&\\?", "");
            string = ConvertRainbow(string);
        } else {
            string = string.replaceAll("&([k-o])", "§$1");
            string = string.replaceAll("&r", "§r");
        }
        return string;
    }

    /**
     * Convert between &k-o or &? to Bold or Rainbow Text
     *
     * @param string Your String Array to Convert
     * @return Your converted String Array
     */

    public static String[] ConvertSpecial(String[] string) {
        boolean isRainbow = false;

        for (int j = 0; j < string.length; j++) {
            if (string[j].contains("&?")) {
                isRainbow = true;
                break;
            }
        }

        Integer stringlenght = string.length;
        Integer stringIndex = 0;

        if (isRainbow == true) {
            String string2 = "";
            for (int i = 0; i < string.length; i++) {
                string[i] = ConvertColor(string[i]);
                string[i] = string[i].replaceAll("§([a-f0-9])", "");
                string[i] = string[i].replaceAll("&([k-o])", "");
                string[i] = string[i].replaceAll("&r", "");
                string[i] = string[i].replaceAll("&\\?", "");
                string2 += string[i];
                if (i < string.length) {
                    string2 += " ";
                }
            }
            string2 = ConvertRainbow(string2);

            char[] chars = string2.toCharArray();
            char[] space = " ".toCharArray();
            String string3 = "";

            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == space[0]) {
                    if (stringIndex < stringlenght) {
                        string[stringIndex] = string3;
                        stringIndex++;
                        string3 = "";
                    }
                } else {
                    string3 += chars[i];
                }
            }

        } else {
            for (int i = 0; i < string.length; i++) {
                string[i] = string[i].replaceAll("&([k-o])", "§$1");
                string[i] = string[i].replaceAll("&r", "§r");
            }
        }
        return string;
    }

    /**
     * Convert your Message to fancy Rainbow Messages
     *
     * @param string Your Message you want to Convert
     * @return Your Converted String
     */

    public static String ConvertRainbow(String string) {
        String[] Rainbow = new String[11];
        Rainbow[0] = "&4";
        Rainbow[1] = "&c";
        Rainbow[2] = "&6";
        Rainbow[3] = "&e";
        Rainbow[4] = "&2";
        Rainbow[5] = "&a";
        Rainbow[6] = "&b";
        Rainbow[7] = "&3";
        Rainbow[8] = "&9";
        Rainbow[9] = "&d";
        Rainbow[10] = "&5";

        char[] chars = string.toCharArray();

        string = "";
        int RBindex = 0;
        char[] space = " ".toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == space[0]) {
                string += chars[i];
            } else {
                string += Rainbow[RBindex];
                string += chars[i];
                RBindex++;
                if (RBindex == Rainbow.length) {
                    RBindex = 0;
                }
            }
        }
        return string;
    }

}
