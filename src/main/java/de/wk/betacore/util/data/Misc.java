package de.wk.betacore.util.data;

/*
Permissions:

beta.core.*

betacore.gm.self
betacore.gm.other
betacore.money
betacore.money.set
betacore.money.pay
betacore.money.clear
betacore.core
betacore.clearlagg

betacore.setrank.
 */


/*
Fehler:
000: Hat einen Rang, der nicht existiert
001: Hat keinen Rang
 */

public class Misc {
    public final static String PREFIX = "§aBetaCore §7| ";
    public static final String S_PREFIX = "§aBetaCore §7|";
    public static final String NOPERM = "§cDu hast nicht die benötigten Berechtigungen, um diesen Befehl auszuführen";
    public static final String MUTED = "§cDu bist gemuted und kannst keine Nachrichten senden";
    public static final String HELP = "§7Benutze §6/help§7 , um Hilfe zu erhalten";
    public static final String NOTINCONSOLE = PREFIX + "Du kannst diesen Befehl nicht in der Konsole verwenden";
    public static final String CONSOLEPREFIX = "§8[§aBetaCore§8]§7 ";
    public static final String CODENAME = "Brass ";
    public static final String VERSION = "0.3 ";


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
