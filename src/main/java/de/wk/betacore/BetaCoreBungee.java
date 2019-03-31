package de.wk.betacore;

import de.wk.betacore.commands.bungee.BungeePluginsReloadCommand;
import de.wk.betacore.commands.bungee.KickSystem;
import de.wk.betacore.commands.bungee.PingCommand;
import de.wk.betacore.environment.EnvironmentManager;
import de.wk.betacore.listener.Bungee.ConnectionListener;
import de.wk.betacore.listener.Bungee.PermissionListenerBungee;
import de.wk.betacore.listener.Bungee.PingListenerB;
import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;


public class BetaCoreBungee extends Plugin {


    private static BetaCoreBungee instance;
    public boolean restart;

    public void regCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new PingCommand());
        this.getProxy().getPluginManager().registerCommand(this, new KickSystem());
        this.getProxy().getPluginManager().registerCommand(this, new BungeePluginsReloadCommand("grl_"));
    }

    public void regListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PingListenerB());
        this.getProxy().getPluginManager().registerListener(this, new ConnectionListener());
        this.getProxy().getPluginManager().registerListener(this, new PermissionListenerBungee());
    }

    @Override
    public void onEnable() {
        log("§3Enabling BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + "...");
        log("");
        EnvironmentManager.setBungeecord(true);


        instance = this;

        log("§3Registering commands & listeners... ");
        regCommands();
        regListeners();
        log("§aDONE");

        log("§3Setting up datafiles...");





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
