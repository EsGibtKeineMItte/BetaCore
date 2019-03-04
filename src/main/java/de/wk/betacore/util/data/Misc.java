package de.wk.betacore.util.data;

/*
Permissions:

beta.core.*

betacore.gm.self
betacore.gm.other


betacore.setrank.*
betacore.setrank.user
betacore.setrank.premium
betacore.setrank.youtuber
betacore.setrank.archi
betacore.setrank.supp
betacore.setrank.mod
betacore.setrank.dev
betacore.setrank.admin

 */


/*
Fehler:
000: Hat einen Rang, der nicht existiert
001: Hat keinen Rang
 */

public class Misc {
    private static String PREFIX = "§aBetaCore §7| ";
    public static String Prefix = "§aBetaCore §7| ";

    public static String NOPERM = "§cDu hast nicht die benötigten Berechtigungen, um diesen Befehl auszuführen";
    public static String MUTED = "§cDu bist gemuted und kannst keine Nachrichten senden";
    public static String HELP = "§7Benutze §6/help§7 , um Hilfe zu erhalten";
    public static String NOTINCONSOLE = PREFIX + "Du kannst diesen Befehl nicht in der Konsole verwenden";
    public static String CONSOLEPREFIX = "§8[§aBetaCore§8]§7 ";
    public static String CODENAME = "Brass ";
    public static String VERSION = "0.3 ";


    public static String getPREFIX() {
        return PREFIX;
    }
    public static String getNOPERM() {
        return NOPERM;
    }
    public static String getMUTED() {
        return MUTED;
    }
    public static String getNOTINCONSOLE() {
        return NOTINCONSOLE;
    }
    public static String getHELP() {
        return HELP;
    }

}
