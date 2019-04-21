package de.wk.betacore.environment;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.util.ConnectionHolder;
import lombok.Getter;
import lombok.Setter;

public class Environment {


    @Getter
    @Setter
    private static boolean spigot, bungeecord, mysql, maintenance,brew, worldedit;


    private Environment() {

    }

    public static synchronized Environment getCurrent(){
        return new Environment();
    }


    public static String getPathToDataFolder() {
        if (bungeecord) {
            String pluginDataFolder = BetaCoreBungee.getInstance().getDataFolder().getAbsolutePath();
            String DataFolder = pluginDataFolder.replaceAll("BetaCore", "..");
            String path = DataFolder + "/../Data";
            return path;
        } else {
            String pluginDatafolder = BetaCore.getInstance().getDataFolder().getAbsolutePath();
            String path = pluginDatafolder + "/../../../Data";
            return path;
        }

    }


    public static void debug(final String msg) {
        if (bungeecord) {
            BetaCoreBungee.debug(msg);
        } else {
            BetaCore.debug(msg);
        }
    }

    public static <T> T getInstance() {
        if (spigot) {
            return (T) BetaCore.getInstance();
        } else {
            return (T) BetaCoreBungee.getInstance();
        }
    }

    public  ConnectionHolder getConnectionHolder() {
        if (bungeecord) {
            return BetaCoreBungee.getInstance().getConnectionHolder();
        } else {
            if(BetaCore.getInstance().getConnectionHolder() == null){
                BetaCore.debug("Connectionholder lebt nicht.");
            }
            return BetaCore.getInstance().getConnectionHolder();
        }
    }

    public static int getMySqlPort() {
        if (spigot) {
            ConfigManager cm = new ConfigManager();
            return cm.getGlobalConfig().getInt("MySQL.port");
        } else {
            Json data = FileManager.getMysql();
            return data.getInt("MySQL.host");
        }
    }

    public static String getMySqlHost() {
        if (spigot) {
            ConfigManager cm = new ConfigManager();
            return cm.getGlobalConfig().getString("MySQL.host");
        } else {
            Json data = FileManager.getMysql();
            return data.getString("MySQL.host");
        }
    }

    public static String getMySqlDatabase() {
        if (spigot) {
            ConfigManager cm = new ConfigManager();
            return cm.getGlobalConfig().getString("MySQL.database");

        } else {
            Json data = FileManager.getMysql();
            return data.getString("MySQL.database");
        }
    }

    public static String getMySqlPassword() {
        if (spigot) {
            ConfigManager cm = new ConfigManager();
            return cm.getGlobalConfig().getString("MySQL.password");
        } else {
            Json data = FileManager.getMysql();
            return data.getString("MySQL.password");
        }
    }

    public static String getMySqlUsername() {
        if (spigot) {
            ConfigManager cm = new ConfigManager();
            return cm.getGlobalConfig().getString("MySQL.username");
        } else {
            Json data = FileManager.getMysql();
            return data.getString("MySQL.username");
        }
    }
}


