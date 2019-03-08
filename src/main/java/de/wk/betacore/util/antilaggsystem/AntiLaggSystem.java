package de.wk.betacore.util.antilaggsystem;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class AntiLaggSystem {

    ConfigManager cm = new ConfigManager();

    int TICK_COUNT = 0;
    int removedEntities;
    long[] TICKS = new long[600];




    public void removeLaggs() {
        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e.getType() == EntityType.PRIMED_TNT || e.getType() == EntityType.FALLING_BLOCK || e.getType() == EntityType.ITEM_FRAME) {
                    e.remove();
                    removedEntities++;
                }
            }
        }
        Bukkit.broadcastMessage(Misc.PREFIX + "§7Aufgrund der aktuellen TPS-Zahlen wurden§6 " + removedEntities + " §7Entities entfernt.");
        Bukkit.getScheduler().scheduleSyncDelayedTask(BetaCore.getInstance(), new Runnable() {
            @Override
            public void run() {

                if (cm.getConfig().getBoolean("useAsBauServer") && Lag.getTPS() < 18) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stoplag");
                    Bukkit.broadcastMessage(Misc.PREFIX + "§7Aufgrund der aktuellen TPS Zahlen wurde Stoplag aktiviert!");
                }
            }
        }, 20 * 11);
    }

}
