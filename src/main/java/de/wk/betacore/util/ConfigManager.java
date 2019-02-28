package de.wk.betacore.util;


import de.wk.betacore.BetaCore;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;

import java.util.ArrayList;


public class ConfigManager {
    private Config config = new Config("config.yml", BetaCore.getInstance());
    private Config globalConfig = new Config("../../../Data/gconfig.yml", BetaCore.getInstance());
    private Config playerData = new Config("../../../Data/PlayerData.yml", BetaCore.getInstance());
    private Config teams = new Config("../../../Data/Teams.yml", BetaCore.getInstance());
    private Config permissions = new Config("../../../Data/Permissions.yml", BetaCore.getInstance());

    //
    public void setup() {
        ArrayList<String> activeTeams = new ArrayList<>();
        teams.setList("activeTeams", activeTeams);

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
        if (getGlobalConfig().getString("MySQL.username") == null) {
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

        if (getGlobalConfig().getString("MySQL.host").equals("") || getGlobalConfig().getString("MySQL.username").equals("")
                || getGlobalConfig().getInt("MySQL.port") == 0 || getGlobalConfig().getString("MySQL.password").equals("") && getGlobalConfig().getBoolean("useMySQL")) {
            Bukkit.getConsoleSender().sendMessage(Misc.getPREFIX() + "§1Die Verbindung zum MySQL Server ist nicht eingestellt");
            Bukkit.getScheduler().runTaskLater(BetaCore.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (getGlobalConfig().getString("MySQL.host").equals("") || getGlobalConfig().getString("MySQL.username").equals("")
                            || getGlobalConfig().getInt("MySQL.port") == 0 || getGlobalConfig().getString("MySQL.password").equals("") && getGlobalConfig().getBoolean("useMySQL")){
                        Bukkit.getConsoleSender().sendMessage(Misc.getPREFIX() + "Fahre das Plugin herrunter.");
                    }
                }
            },20L);







            Bukkit.getPluginManager().disablePlugin(BetaCore.getInstance());
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