package de.wk.betacore.listener.Spigot;


import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.LocalDate;

public class DataSetter implements Listener {
    ConfigManager cm = new ConfigManager();

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        //Rank
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".rank") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".rank", "USER");
        }
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".money") == null) {
            cm.getPlayerData().setInt(e.getPlayer().getUniqueId().toString() + ".money", 0);
        }
        //Name
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".name") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".name", e.getPlayer().getName());
        }
        if (!(cm.getPlayerData().getBoolean(e.getPlayer().getUniqueId().toString() + ".muted"))) {
            cm.getPlayerData().setBoolean(e.getPlayer().getUniqueId().toString() + ".muted", false);
        }
        //Team
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".team") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".team", "");
        }

        if (cm.getPlayerData().getInt(e.getPlayer().getUniqueId() + ".wsrank") == 0) {
            cm.getPlayerData().setInt(e.getPlayer().getUniqueId() + ".wsrank", 900);
        }
        cm.getPlayerData().save();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        LocalDate today = LocalDate.now();

        RankSystem rankSystem = new RankSystem();
        System.out.println("Debug Message: Rang ist:" + rankSystem.getRank(e.getPlayer().getUniqueId()));
        cm.getPlayerData().reload();
        cm.getPlayerData().save();
        // cm.getPlayerData().setString(uuid.toString() + ".rank", rank.name().toUpperCase());
        //
        System.out.println("Debug Message: Rang ist:" + cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".rank").toUpperCase());
        cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".rank", cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".rank").toUpperCase());
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".firstjoin") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".firstjoin", today.toString());
            return;
        }
        cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".lastjoin", today.toString());
        cm.getPlayerData().save();
    }
}
