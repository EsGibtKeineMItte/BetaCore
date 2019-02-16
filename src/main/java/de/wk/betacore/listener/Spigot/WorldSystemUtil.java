package de.wk.betacore.listener.Spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WorldSystemUtil implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {

        Player player = e.getPlayer();

        if (e.getMessage().toLowerCase().startsWith("/bau")) {
            e.setCancelled(true);
            String message = e.getMessage().replaceAll("/bau", "ws");
            player.performCommand(message);
        }
    }

}
