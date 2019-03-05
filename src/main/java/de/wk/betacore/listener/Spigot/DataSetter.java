package de.wk.betacore.listener.Spigot;


import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.objects.WarPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.PreparedStatement;
import java.time.LocalDate;

public class DataSetter implements Listener {
    ConfigManager cm = new ConfigManager();

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {

        Player player = e.getPlayer();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        WarPlayer wp = new WarPlayer(player.getUniqueId());
        wp.syncWithConfigs();


        LocalDate today = LocalDate.now();
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".firstjoin") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".firstjoin", today.toString());
            return;
        }
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".name") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + "name", e.getPlayer().getName());
        }
        cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".lastjoin", today.toString());
    }
}
