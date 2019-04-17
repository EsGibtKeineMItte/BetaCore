package de.wk.betacore.listener.Spigot;

import de.leonhard.lib.lib.Commons;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
        if (e.getEntity() instanceof Player) {
            ((Player) e.getEntity()).setHealth(20);
            ((Player) e.getEntity()).setFoodLevel(20);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)

    public void onDie(PlayerDeathEvent e) {
        final Player player = e.getEntity();


        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    ConfigManager cm = new ConfigManager();
                    player.spigot().respawn();
                    BetaCore.teleportSpawn(player);
                } catch (final Throwable t) {//Falsche Version, Craftbukkit bzw < 1.8
                    Commons.tell(player, "§cDu konntest nicht automatisch respawn werden");
                    if (!(t instanceof NoClassDefFoundError)) {//Wenn es nicht an der Version lag.
                        t.printStackTrace();
                    }
                }
            }
        }.runTaskLater(BetaCore.getInstance(), 7); //Delay
    }
}
