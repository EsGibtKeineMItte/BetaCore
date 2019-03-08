package de.wk.betacore.environment;

import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public enum Environment {

    BUNGEECORD, SPIGOT;


    public static Environment getCurrent() {
        try {
            return ProxyServer.getInstance() != null ? BUNGEECORD : SPIGOT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void restartServer() {
        if (this == SPIGOT) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.kickPlayer("Server restart. Wir sind gleich wieder da:)");
            }
            Bukkit.spigot().restart();
        } else if (this == BUNGEECORD) {
            BetaCoreBungee.getInstance().getProxy().stop("Wir sind gleich wieder da:)");
            BetaCoreBungee.getInstance().restart = true;
        }
    }


    public void restartDaily() {
        if(this == SPIGOT){
            Bukkit.getScheduler().scheduleSyncRepeatingTask(BetaCore.getInstance(), () ->{
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Date d = new Date();
                String f = df.format(d);
                switch (f) {
                    case ("23:00:00"):
                        BetaCore.log("§6Das Netzwerk startet in §e60  Minuten §6neu!");
                    case ("23:30:00"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:45:00"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:50:00"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:55:00"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:59:00"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                    case ("23:59:50"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:55"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:56"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:57"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:58"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("23:59:59"):
                        BetaCore.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                    case ("00:00:00"):
                        restartServer();
                }
            }, 0L, 20);
        }else if(this == BUNGEECORD){
            ProxyServer.getInstance().getScheduler().schedule(BetaCoreBungee.getInstance(), new Runnable() {

                private int timer = 0;

                @Override
                public void run() {
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                    Date d = new Date();
                    String f = df.format(d);
                    switch (f) {
                        case ("23:00:00"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e60 Minuten §6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e60  Minuten §6neu!");
                        case ("23:30:00"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                        case ("23:45:00"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                        case ("23:50:00"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                        case ("23:55:00"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                        case ("23:59:00"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Minuten §6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Minuten §6neu!");
                        case ("23:59:50"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                        case ("23:59:55"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                        case ("23:59:56"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                        case ("23:59:57"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                        case ("23:59:58"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                        case ("23:59:59"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet in §e Sekunden§6neu!"));
                            BetaCoreBungee.log("§6Das Netzwerk startet in §e  Sekunden §6neu!");
                        case ("00:00:00"):
                            ProxyServer.getInstance().broadcast(new TextComponent("§6Das Netzwerk startet neu!"));
                            BetaCoreBungee.getInstance().getProxy().stop(Misc.PREFIX + "§cDas Netzwerk startet neu. Wir sind gleich wieder da:)");
                            BetaCoreBungee.getInstance().restart = true;
                    }
                }
            }, 1, 1, TimeUnit.SECONDS);

        }
    }
}