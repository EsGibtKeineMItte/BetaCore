package de.wk.betacore.environment;

import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;

public class EnvironmentManager {


    private static boolean spigot;
    private static boolean bungeecord;

    public static String getPathToDataFolder() {
        if (bungeecord) {
            String pluginDataFolder = BetaCoreBungee.getInstance().getDataFolder().getAbsolutePath();
            String DataFolder = pluginDataFolder.replaceAll("BetaCore", "..");
            String path = DataFolder + "/../Data";
            BetaCoreBungee.debug("Path to datafolder " + path);
            return path;
        } else if (spigot) {
            String pluginDatafolder = BetaCore.getInstance().getDataFolder().getAbsolutePath();
            String path = pluginDatafolder + "/../../../Data";
            return path;
        }
        return null;
    }

    public static boolean isSpigot() {
        return spigot;
    }

    public static boolean isBungeecord() {
        return bungeecord;
    }

    public static void setSpigot(boolean spigot) {
        EnvironmentManager.spigot = spigot;
    }

    public static void setBungeecord(boolean bungeecord) {
        EnvironmentManager.bungeecord = bungeecord;
    }
}
