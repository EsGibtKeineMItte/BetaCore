package de.wk.betacore.environment;

import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;
import lombok.Getter;
import lombok.Setter;

public class EnvironmentManager {


    @Getter
    @Setter
    private static boolean spigot, bungeecord, mysql, maintenance;


    private EnvironmentManager() {

    }



    public static String getPathToDataFolder() {
        if (bungeecord) {
            String pluginDataFolder = BetaCoreBungee.getInstance().getDataFolder().getAbsolutePath();
            String DataFolder = pluginDataFolder.replaceAll("BetaCore", "..");
            String path = DataFolder + "/../Data";
            return path;
        } else if (spigot) {
            String pluginDatafolder = BetaCore.getInstance().getDataFolder().getAbsolutePath();
            String path = pluginDatafolder + "/../../../Data";
            return path;
        }
        return null;
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

    public static int getPort(){
        return 0;
    }

    public static String getDataBase(){
        if(spigot){

        }else{

        }


        return null;
    }

    public static String getPassword(){

        if(spigot){

        }else{

        }

        return null;
    }

    public static String getUsername(){

        if(spigot){

        }else{

        }

        return null;
    }

    public static String getHost(){

        if(spigot){

        }else{

        }

        return null;
    }
}


