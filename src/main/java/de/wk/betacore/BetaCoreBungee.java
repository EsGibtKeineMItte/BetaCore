package de.wk.betacore;

import de.leonhard.storage.Json;
import de.wk.betacore.commands.bungee.*;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.listener.Bungee.*;
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
    private static Json mysql;
    @Getter
    private  ConnectionHolder connectionHolder;
    public boolean restart;

    private void regCommands() {

        this.getProxy().getPluginManager().registerCommand(this, new PingCommand());
        this.getProxy().getPluginManager().registerCommand(this, new KickSystem());
        this.getProxy().getPluginManager().registerCommand(this, new BungeeUpdate("b"));
        this.getProxy().getPluginManager().registerCommand(this, new ConnectCommand("connect", "betacore.servers", "con"));
        this.getProxy().getPluginManager().registerCommand(this, new BauCommand("bauserver", "betacore.servers","baumeee"));
        this.getProxy().getPluginManager().registerCommand(this, new LobbyCommand("l", "betacore.servers", "hub", "lobby"));
        this.getProxy().getPluginManager().registerCommand(this, new ArenaCommand("arena", "betacore.servers", "a"));
    }

    private void regListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PingListenerB());
        this.getProxy().getPluginManager().registerListener(this, new ConnectionListener());
        this.getProxy().getPluginManager().registerListener(this, new PermissionListenerBungee());
        this.getProxy().getPluginManager().registerListener(this, new JoinHandler());
        this.getProxy().getPluginManager().registerListener(this, new ChatListener());
    }

    @Override
    public void onEnable() {
        Environment.setBungeecord(true);


        log("§6[BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + "]");
        log("");
        Environment.setBungeecord(true);


        instance = this;

        log("§3Registering commands & listeners... ");
        regCommands();
        regListeners();
        log("§aDONE");

        mysql = new Json("mysql", "../Data");




        if (mysql.getBoolean("useMySQL")) {
            log("§3Verbinde zum MySQL-Server");
            Environment.setMysql(true);
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

        log("§7=====§eBUILDINFORMATIONEN§7======");


        System.out.println("");

        log("§eGlobal-Settings:");

        log("BetaCore-Version: " + Misc.VERSION);
        log("Code-Name: " + Misc.CODENAME);

        log("Plattform: " + (Environment.isSpigot() ? "§3Spigot" : "§3BungeeCord"));
        log("MySQL: " + (Environment.isMysql() ? "§atrue" : "§cfalse"));

        log("§eServer-Settings:");

        log("§eDependency's:");
        log("Brew: " + (Environment.isBrew() ? "§atrue" : "§cfalse"));
        log("WorldEdit: " + (Environment.isWorldedit() ? "§atrue" : "§cfalse"));
        log("§6Successfully enabled BetaCore" + Misc.CODENAME + "v." + Misc.VERSION + ".");

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
