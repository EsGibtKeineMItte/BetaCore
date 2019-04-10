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







}