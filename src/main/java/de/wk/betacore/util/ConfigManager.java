package de.wk.betacore.util;


import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ranksystem.Rank;
import org.apache.commons.lang3.EnumUtils;

import java.util.UUID;


public class ConfigManager {
    private Config config = new Config("config.yml", BetaCore.getInstance());
    private Config globalConfig = new Config("../../../Data/gconfig.yml", BetaCore.getInstance());
    private Config playerData = new Config("../../../Data/PlayerData.yml", BetaCore.getInstance());
    private Config teams = new Config("../../../Data/Teams.yml", BetaCore.getInstance());
    private Config permissions = new Config("../../../Data/Permissions.yml", BetaCore.getInstance());

    //
    public void setup() {

        playerData.setHeader("PlayerData");
        playerData.save();

        if (!config.getBoolean("useGlobalConfig")) {
            config.setBoolean("useGlobalConfig", true);
        }
        if (!config.getBoolean("useDefaultChatFilter")) {
            config.setBoolean("useDefaultChatFilter", false);
        }
        if (!config.getBoolean("useAsBauServer")) {
            config.setBoolean("useAsBauServer", false);
        }
        if (!config.getBoolean("useAsArena")) {
            config.setBoolean("useAsArena", false);
        }

        if (!config.getBoolean("useAsLobby")) {
            config.setBoolean("useAsLobby", false);
        }
        if (!config.getBoolean("useAntiLaggSystem")) {
            config.setBoolean("useAntiLaggSystem", true);
        }
        config.save();

        globalConfig.setString("LinkToArena-1", "Arena-1");
        globalConfig.setString("LinkToArena-2", "Arena-2");
        globalConfig.setString("LinkToBau", "Bau");
        globalConfig.setString("LinkToLobby", "Lobby-1");
    }

    public void setupMySQL() {
        if (getGlobalConfig().getString("MySQL.host") == null) {
            getGlobalConfig().setString("MySQL.host", "");
        }
        if(getGlobalConfig().getString("MySQL.username") == null){
            getGlobalConfig().setString("MySQL.username", "");
        }

        if (getGlobalConfig().getInt("MySQL.port") == 0) {
            getGlobalConfig().setInt("MySQL.port", 3306);
        }
        if (getGlobalConfig().getString("MySQL.database") == null) {
            getGlobalConfig().setString("MySQL.database", "");
        }

        if (getGlobalConfig().getString("MySQL.password") == null) {
            getGlobalConfig().setString("MySQL.password", "");
        }
    }


    public Config getConfig() {
        return config;
    }

    public Config getGlobalConfig() {
        return globalConfig;
    }

    public Config getPlayerData() {
        return playerData;
    }

    public Config getTeams() {
        return teams;
    }

    public Config getPermissions() {
        return permissions;
    }


}