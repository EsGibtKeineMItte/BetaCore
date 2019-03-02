package de.wk.betacore.listener.Spigot;

import de.wk.betacore.util.ranksystem.PermissionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PermissionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PermissionManager pm = new PermissionManager();
        pm.setupPermissions(e.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        PermissionManager pm = new PermissionManager();
        pm.getPlayerPermissions().remove(e.getPlayer().getUniqueId());
    }
}
