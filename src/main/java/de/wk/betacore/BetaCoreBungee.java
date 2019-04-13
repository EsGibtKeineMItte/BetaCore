package de.wk.betacore;

import de.leonhard.storage.Json;
import de.wk.betacore.commands.bungee.BungeePluginsReloadCommand;
import de.wk.betacore.commands.bungee.KickSystem;
import de.wk.betacore.commands.bungee.PingCommand;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.listener.Bungee.ConnectionListener;
import de.wk.betacore.listener.Bungee.PermissionListenerBungee;
import de.wk.betacore.listener.Bungee.PingListenerB;
import de.wk.betacore.util.ConnectionHolder;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.data.Misc;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;


public class BetaCoreBungee extends Plugin {


    private static BetaCoreBungee instance;
    private static Json mysql = FileManager.getMysql();
    @Getter
    private  ConnectionHolder connectionHolder;
    public boolean restart;

    private void regCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new PingCommand());
        this.getProxy().getPluginManager().registerCommand(this, new KickSystem());
        this.getProxy().getPluginManager().registerCommand(this, new BungeePluginsReloadCommand("grl"));
    }

    private void regListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PingListenerB());
        this.getProxy().getPluginManager().registerListener(this, new ConnectionListener());
        this.getProxy().getPluginManager().registerListener(this, new PermissionListenerBungee());
    }

    @Override
    public void onEnable() {
        log("§6[BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + "]");
        log("");
        Environment.setBungeecord(true);


        instance = this;

        log("§3Registering commands & listeners... ");
        regCommands();
        regListeners();
        log("§aDONE");

        if (mysql.getBoolean("useMySQL")) {
            log("§3Verbinde zum MySQL-Server");
            try {
                MySQL.openConnection();
                System.out.println("MySQL Connection erfolgreich.");

            } catch (SQLException x) {
                log("§4FAILED");
                System.out.println("");
                x.printStackTrace();
            }

            log("§3Setting up datafiles...");
            Json setting = FileManager.getSettings();

        }


        log("§3BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + "successfully enabled.");
    }

    @Override
    public void onDisable() {
        log("§3Successfully diabled BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + ".");
    }
    //

    public static void log(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(Misc.CONSOLEPREFIX + message));
    }

    public static void debug(String message) {

        //TODO wenn in config debug angestellt ist dann...
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(Misc.CONSOLEPREFIX + "[§eDEBUG]" + message));
    }

    public static BetaCoreBungee getInstance() {
        return instance;
    }

}
