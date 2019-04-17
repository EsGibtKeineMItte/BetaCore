package de.wk.betacore.listener.Spigot;

import de.leonhard.lib.lib.Commons;
import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.appearance.ScoreboardUtils;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class LobbyListener implements Listener {
    ConfigManager cm = new ConfigManager();
    Json data = FileManager.getPlayerData();

    BossBar bossBar = Bukkit.createBossBar(cm.getConfig().getString("BossBarTitle"), BarColor.BLUE, BarStyle.SEGMENTED_20);



    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        bossBar.addPlayer(e.getPlayer());
    }

}
