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
betacore.setupplayer
betacore.debug

betacore.setrank.

betacore.update


//Proxy:
betacore.servers
 */


/*
Fehler:
000: Hat einen Rang, der nicht existiert
001: Hat keinen Rang

002: Beim Restarten des Servers ist ein Fehler aufgetreten : Nullpointer Exception, es konnte keine passende Umgebung(SPIGOT oder Bungee gefunden werden.)
003: TeamAdmin existiert nicht.
 */

public class Misc {
    public final static String PREFIX = "§aBetaCore §7| ";
    public static final String S_PREFIX = "§aBetaCore §7|";
    public static final String NOPERM = "§cDu hast nicht die benötigten Berechtigungen, um diesen Befehl§c auszuführen";
    public static final String MUTED = "§cDu bist gemuted und kannst keine Nachrichten senden";
    public static final String HELP = "§7Benutze §6/help§7 , um Hilfe zu erhalten";
    public static final String NOTINCONSOLE = PREFIX + "Du kannst diesen Befehl nicht in der Konsole verwenden";
    public static final String CONSOLEPREFIX = "§8[§aBetaCore§8]§7 ";
    public static final String CODENAME = "Brass ";
    public static final String VERSION = "0.3";
    public static final String SCHEM_ORG_PREFIX = "§8[§bSchemOrg§8] §7";
    public static final String SCHEMLOADER_PREFIX = "§8[§3Schematic§8] §7";


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
