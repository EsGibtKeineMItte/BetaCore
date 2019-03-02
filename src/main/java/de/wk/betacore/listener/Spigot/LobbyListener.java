package de.wk.betacore.listener.Spigot;

import de.wk.betacore.appearance.ActionBar;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyListener implements Listener {

    ConfigManager cm = new ConfigManager();

    BossBar bossBar = Bukkit.createBossBar(cm.getConfig().getString("BossBarTitle"), BarColor.BLUE, BarStyle.SEGMENTED_20);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (e.getPlayer().isOp()) {
            e.getPlayer().setGameMode(GameMode.CREATIVE);
        } else {
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
            e.getPlayer().setFoodLevel(20);
        }

        bossBar.addPlayer(e.getPlayer());
        e.getPlayer().teleport(cm.getConfig().getLocation("spawn"));
        ActionBar.sendActionBar(e.getPlayer(), cm.getConfig().getString("actionbarTitle"));

    }
        @EventHandler
        public void onDeath (PlayerDeathEvent e1){
            e1.setDeathMessage("");
            e1.setKeepInventory(true);
            e1.getEntity().teleport(cm.getConfig().getLocation("spawn"));
        }

    }
