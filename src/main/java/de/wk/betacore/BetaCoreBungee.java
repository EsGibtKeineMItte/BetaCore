package de.wk.betacore;

import de.wk.betacore.commands.bungee.PingCommand;
import de.wk.betacore.datamanager.DataManager;
import de.wk.betacore.environment.EnvironmentManager;
import de.wk.betacore.listener.Bungee.PingListenerB;
import de.wk.betacore.util.data.Misc;
import io.bluecube.thunderbolt.exceptions.FileLoadException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BetaCoreBungee extends Plugin {


    private static BetaCoreBungee instance;
    public boolean restart;

    public void regCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new PingCommand());
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
            DataManager.setup();
            DataManager.getPlayerData().save();
        } catch (IOException | FileLoadException e) {
            e.printStackTrace();
        }
        log("§aDone");


        ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {

            private int timer = 0;

            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Date d = new Date();
                String f = df.format(d);
                switch (f) {
                    case ("23:00:00"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e60 Minuten §6neu!"));
                        log("§6Das Netzwerk startet in §e60  Minuten §6neu!");
                    case ("23:30:00"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                        log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:45:00"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                        log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:50:00"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                        log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:55:00"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                        log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:59:00"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                        log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:59:50"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                        log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:55"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                        log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:56"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                        log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:57"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                        log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:58"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                        log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:59"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                        log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("00:00:00"):
                        ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet neu!"));
                        BetaCoreBungee.getInstance().getProxy().stop(Misc.PREFIX + "§cDas Netzwerk startet neu. Wir sind gleich wieder da:)");
                        restart = true;

                }
            }
        }, 1, 1, TimeUnit.SECONDS);

        log("§3BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + "successfully enabled.");
    }

    @Override
    public void onDisable() {
        log("§3Successfully diabled BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + ".");

        if (restart) {
            new Thread(() -> {
                log("Restarting Proxy");
                try {
                    Process prc = new ProcessBuilder("./start.sh").start();
                } catch (IOException e) {
                    log("Could not restart the proxy. Is the start.sh configurated?!");
                    e.printStackTrace();
                }
            }).start();

        }

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
