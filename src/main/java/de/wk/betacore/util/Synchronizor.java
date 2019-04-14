package de.wk.betacore.util;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;

public final class Synchronizor {
    static ConfigManager cm = new ConfigManager();
    static Json mysql = FileManager.getMysql();

    public static synchronized void synchronize() {
        if (Environment.isBungeecord()) {
            return;
        }
        if(mysql == null){
            BetaCore.debug("MYSQL ist null wtf?");
        }

//        mysql.set("useMySQL", cm.getGlobalConfig().getBoolean("UseMySQL"));
        mysql.set("MySQL.port", cm.getGlobalConfig().getString("MySQL.port"));
        mysql.set("MySQL.username", cm.getGlobalConfig().getString("MySQL.username"));
        mysql.set("MySQL.port", cm.getGlobalConfig().getString("MySQL.database"));
        mysql.set("MySQL.password", cm.getGlobalConfig().getString("MySQL.password"));
    }
}
