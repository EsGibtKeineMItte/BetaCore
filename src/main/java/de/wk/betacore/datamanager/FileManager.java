package de.wk.betacore.datamanager;


import de.leonhard.storage.Json;
import de.wk.betacore.environment.EnvironmentManager;

import java.io.IOException;


public class FileManager {

    private static Json playerData;
    private static Json teams;
    private static Json settings;
    private static Json bungeePerms;
    private static Json mysql;

    private FileManager() {

    }


    public static void setup() throws IOException {
        String path = EnvironmentManager.getPathToDataFolder();
        playerData = new Json("playerdata", path);
        teams = new Json("teams", path);
        settings = new Json("settings", path);
        bungeePerms = new Json("bungeeperms", path);
    }


    private static Json getJson(Json json) {
        if (json == null) {
            try {
                setup();
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public static Json getPlayerData() {
        return getJson(playerData);

    }

    public static Json getTeams() {
        return getJson(teams);
    }

    public static Json getSettings() {
        return getJson(settings);
    }

    public static Json getBungeePerms() {
        return getJson(bungeePerms);
    }

    public static Json getMysql(){
        return getJson(mysql);
    }
}