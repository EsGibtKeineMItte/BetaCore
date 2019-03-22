package de.wk.betacore;

import de.wk.betacore.commands.bungee.BungeePluginsReloadCommand;
import de.wk.betacore.commands.bungee.KickSystem;
import de.wk.betacore.commands.bungee.PingCommand;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.EnvironmentManager;
import de.wk.betacore.listener.Bungee.PingListenerB;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.teamsystem.TeamSystem;
import io.bluecube.thunderbolt.exceptions.FileLoadException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

public class BetaCoreBungee extends Plugin {
    TeamSystem ts = new TeamSystem();


    private static BetaCoreBungee instance;
    public boolean restart;

    public void regCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new PingCommand());
        this.getProxy().getPluginManager().registerCommand(this, new KickSystem());
        this.getProxy().getPluginManager().registerCommand(this, new BungeePluginsReloadCommand("grl", "betacore.reload.bungee", "brl", "reload bungee" , "rl bungee", "rl b"));
    }

    public void regListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PingListenerB());
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
        try {
            FileManager.setup();
            FileManager.getPlayerData().save();
        } catch (IOException | FileLoadException e) {
            e.printStackTrace();
        }
        log("§aDone");




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
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(Misc.CONSOLEPREFIX + "[§eDEBUG]" + message));

    }

    public static BetaCoreBungee getInstance() {
        return instance;
    }

}
