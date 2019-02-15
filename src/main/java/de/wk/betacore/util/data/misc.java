package de.wk.betacore.util.data;

/*
Permissions:
betacore.gm.self
betacore.gm.other
 */


public class misc {
    private static String PREFIX = "§aBetaCore §7|";
    private static String NOPERM = "§cDu hast nicht die benötigten Berechtigungen, um diesen Befehl auszuführen";


    public static String getPREFIX() {
        return PREFIX;
    }

    public static String getNOPERM() {
        return NOPERM;
    }
}
