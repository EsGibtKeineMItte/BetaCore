package de.wk.betacore;

import de.wk.betacore.commands.bungee.PingCommand;
import de.wk.betacore.listener.Bungee.PingListenerB;
import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BetaCoreBungee extends Plugin {


    private static BetaCoreBungee instance;
    private boolean restart;

    public void regCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new PingCommand());
    }

    public void regListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PingListenerB());
    }

    @Override
    public void onEnable() {
        instance = this;
        regCommands();
        regListeners();
        ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {

            private int timer = 0;

            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Date d = new Date();
                String f = df.format(d);

                System.out.println("Es ist " + f  + " Uhr.");

                //TODO Switch?
                if (f.equalsIgnoreCase("23:00:00")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e60 Minuten §6neu!"));
                } else if (f.equalsIgnoreCase("23:30:00")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e30 Minuten §6neu!"));
                } else if (f.equalsIgnoreCase("23:45:00")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e15 Minuten §6neu!"));
                } else if (f.equalsIgnoreCase("23:50:00")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e10 Minuten §6neu!"));
                } else if (f.equalsIgnoreCase("23:55:00")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e5 Minuten §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:00")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §eeiner Minute §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:30")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e30 Sekunden §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:50")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e10 Sekunden §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:55")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e5 Sekunden §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:56")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e4 Sekunden §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:57")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e3 Sekunden §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:58")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e2 Sekunden §6neu!"));
                } else if (f.equalsIgnoreCase("23:59:59")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §eeiner Sekunde §6neu!"));
                } else if (f.equalsIgnoreCase("00:00:00")) {
                    ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet neu!"));
                    BetaCoreBungee.getInstance().getProxy().stop();
                    restart = true;
                }

            }
        }, 1, 1, TimeUnit.SECONDS);


    }

    @Override
    public void onDisable() {

        if(restart){
            new Thread(() -> {
                System.out.println("Restarting Proxy");
                try {
                    Process prc = new ProcessBuilder("./start.sh").start();
                } catch (IOException e) {
                    System.out.println("Could not restart the proxy. Is the restart.sh configurated");
                    e.printStackTrace();
                }
            }).start();

        }

    }
    //

    public static void log(String message){
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(Misc.getPREFIX() + message));
    }


    public static BetaCoreBungee getInstance() {
        return instance;
    }
}
