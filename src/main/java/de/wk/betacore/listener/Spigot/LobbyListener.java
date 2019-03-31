package de.wk.betacore.listener.Spigot;

import de.leonhard.storage.Json;
import de.wk.betacore.appearance.ScoreboardUtils;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class LobbyListener implements Listener {
    ConfigManager cm = new ConfigManager();
    Json data = FileManager.getPlayerData();

    BossBar bossBar = Bukkit.createBossBar(cm.getConfig().getString("BossBarTitle"), BarColor.BLUE, BarStyle.SEGMENTED_20);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {


        bossBar.addPlayer(e.getPlayer());
        scoreboard(e.getPlayer());
    }


    public void scoreboard(Player e) {

        ArrayList<String> sscore = new ArrayList<>();
        sscore.add("&6");
        sscore.add("&6> &7Money");
        sscore.add("&6> &e" + data.getInt(e.getPlayer().getUniqueId().toString() + ".money"));
        sscore.add("&6> &7Rank");
        sscore.add("&6> &e" + RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + RankSystem.getRank(e.getPlayer().getUniqueId()).getName());
        if (data.getInt(e.getPlayer().getUniqueId() + ".wsrank") < 501) {
            sscore.add("&7");
            sscore.add("&6> &7WSRank");
            sscore.add("&6> &e&l" + data.getInt(e.getPlayer().getUniqueId() + ".wsrank"));
        }
        if (data.getString(e.getPlayer().getUniqueId() + ".wsteam") != null) {
            sscore.add("&6> &7Team");
            sscore.add("&6> &e&l" + data.getString(e.getPlayer().getUniqueId() + ".team"));
        }
        sscore.add("&8");
        sscore.add("&6> &7Joins");
        sscore.add("&6> &e(Joins)");
        sscore.add("&6> &7Play Time");
        sscore.add("&6> &e(PlayTime)");
        sscore.add("&9");
        sscore.add("&6TheWarking.de");

        String[] st = new String[sscore.size()];
        int i = 0;
        for (String string : sscore) {
            st[i] = string;
            i++;
        }
        ScoreboardUtils.updateScoreboard("&aTheWarKing", st, e.getPlayer());
    }
}
