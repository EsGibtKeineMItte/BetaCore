package de.wk.betacore.listener.Spigot;

import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyListener implements Listener {
    ConfigManager cm = new ConfigManager();
    Json data = FileManager.getPlayerData();

    BossBar bossBar = Bukkit.createBossBar(cm.getConfig().getString("BossBarTitle"), BarColor.BLUE, BarStyle.SEGMENTED_20);



    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        bossBar.addPlayer(e.getPlayer());
    }

}
