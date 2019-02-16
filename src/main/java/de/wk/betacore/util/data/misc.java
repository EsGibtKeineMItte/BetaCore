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


public class misc {
    private static String PREFIX = "§aBetaCore §7|";
    private static String NOPERM = "§cDu hast nicht die benötigten Berechtigungen, um diesen Befehl auszuführen";
    private static String MUTED = "§cDu bist gemuted und kannst keine Nachrichten senden";


    public static String getPREFIX() {
        return PREFIX;
    }
    public static String getNOPERM() {
        return NOPERM;
    }
    public static String getMUTED() {
        return MUTED;
    }
}
