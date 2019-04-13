package de.wk.betacore.datamanager;

import de.wk.betacore.BetaCore;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.data.Misc;
import lombok.Getter;
import org.bukkit.Bukkit;


public class ConfigManager {
    public Config getConfig() {
        return config;
    }

    public Config getGlobalConfig() {
        return globalConfig;
    }

    public Config getPermissions() {
        return permissions;
    }

    @Getter
    private Config config = new Config("config.yml", BetaCore.getInstance());
    @Getter
    private Config globalConfig = new Config("../../../Data/gconfig.yml", BetaCore.getInstance());
   @Getter
    private Config permissions = new Config("../../../Data/Permissions.yml", BetaCore.getInstance());


    //
    public void setup() {

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


        config.setString("BossBarTitle", "§6Willkommen auf §eWarKing");
        config.setString("actionbarTitle", "§6/bau §7um auf den Bauserver zu kommen.");


        config.save();


        if (!globalConfig.getBoolean("UseMySQL")) {
            globalConfig.setBoolean("UseMySQL", false);
        }
        if (globalConfig.getString("LinkToArena-1") == null) {
            globalConfig.setString("LinkToArena-1", "Arena-1");
        }
        if (globalConfig.getString("LinkToArena-2") == null) {
            globalConfig.setString("LinkToArena-2", "Arena-2");
        }
        if (globalConfig.getString("LinkToBau") == null) {
            globalConfig.setString("LinkToBau", "Bau");
        }
        if (globalConfig.getString("LinkToLobby") == null) {
            globalConfig.setString("LinkToLobby", "Lobby-1");
        }
    }


    public void setupMySQL() { //Eigene MYSQL Config?
        if (!(globalConfig.getBoolean("UseMySQL"))) {
            Environment.setMysql(false);
            return;
        }
        globalConfig.setBoolean("UseMySQL", true);


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
                            || getGlobalConfig().getInt("MySQL.port") == 0 || getGlobalConfig().getString("MySQL.password").equals("") && getGlobalConfig().getBoolean("useMySQL")) {
                        Bukkit.getConsoleSender().sendMessage(Misc.getPREFIX() + "Fahre das Plugin herrunter.");
                    }
                }
            }, 20L);


            Bukkit.getPluginManager().disablePlugin(BetaCore.getInstance());
        }
    }


}