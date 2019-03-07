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
        wp.manuellsetup();


        LocalDate today = LocalDate.now();
    }
}
