package de.wk.betacore.listener.Spigot;


import de.wk.betacore.util.ConfigManager;
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

        if (cm.getPlayerData().getInt(e.getPlayer().getUniqueId() + ".wsrank") == 0) {
            cm.getPlayerData().setInt(e.getPlayer().getUniqueId() + ".wsrank", 900);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        LocalDate today = LocalDate.now();
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".firstjoin") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".firstjoin", today.toString());
            return;
        }
        cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".lastjoin", today.toString());
    }
}
