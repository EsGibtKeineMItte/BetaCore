package de.wk.betacore.datamanager;


import de.leonhard.storage.Json;
import de.wk.betacore.environment.Environment;

import java.io.IOException;


public class FileManager {

    private static Json playerData;
    private static Json teams;
    private static Json settings;
    private static Json bungeePerms;
    private static Json mysql;
    private static Json schematics;
    private static Json spawn;

    private FileManager() {

    }


    public static void setup() throws IOException {
        String path = Environment.getPathToDataFolder();
        playerData = new Json("playerdata", path);
        teams = new Json("teams", path);
        settings = new Json("settings", path);
        bungeePerms = new Json("bungeeperms", path);
        mysql = new Json("mysql", path);
        spawn = new Json("spawn", "plugins/BetaCore");
        Environment.debug("Path" + path);

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

    public static Json getMysql() {
        return getJson(mysql);
    }

    public static Json getSchematics() {
        return getJson(schematics);
    }

    public static Json getSpawn() {
        return getJson(spawn);
    }
}