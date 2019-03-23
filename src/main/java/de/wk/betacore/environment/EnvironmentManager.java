package de.wk.betacore.environment;

import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;

public class EnvironmentManager {


    private static boolean spigot, bungeecord, mysql, maintenance;



    private EnvironmentManager(){

    }


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

    public static void debug(String msg){
        if(bungeecord){
            BetaCoreBungee.debug(msg);
        }else{
            BetaCore.debug(msg);
        }
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

    public static boolean isMysql() {
        return mysql;
    }

    public static void setMysql(boolean mysql) {
        EnvironmentManager.mysql = mysql;
    }

    public static boolean isMaintenance() {
        return maintenance;
    }
}
