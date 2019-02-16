package de.wk.betacore.listener.Spigot;

import de.wk.betacore.appearance.Color;
import de.wk.betacore.appearance.Scoreboard;
import de.wk.betacore.appearance.Tablist;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class JoinHandler implements Listener {
    RankSystem rankSystem = new RankSystem();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        scoreboard(e.getPlayer());
        tablist(e.getPlayer());
        playerTablist(e.getPlayer());
        if (rankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) {
            e.setJoinMessage("");
        } else {
            e.setJoinMessage(Color.ConvertColor(rankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + rankSystem.getRank(e.getPlayer().getUniqueId()).getName() + " " + e.getPlayer().getName() + " &eist beigetreten"));
        }
    }

    public void update(Player e) {
        scoreboard(e);
        tablist(e);
        playerTablist(e);
    }

    public void scoreboard(Player e) {
        ConfigManager cm = new ConfigManager();

        ArrayList<String> sscore = new ArrayList<>();
        sscore.add("&6");
        sscore.add("&6> &7Money");
        sscore.add("&6> &e" + cm.getPlayerData().getInt(e.getPlayer().getUniqueId().toString() + ".money"));
        sscore.add("&6> &7Rank");
        sscore.add("&6> &e" + rankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + rankSystem.getRank(e.getPlayer().getUniqueId()).getName());
        if (cm.getPlayerData().getInt(e.getPlayer().getUniqueId() + ".wsrank") < 501) {
            sscore.add("&7");
            sscore.add("&6> &7WSRank");
            sscore.add("&6> &e&l" + cm.getPlayerData().getInt(e.getPlayer().getUniqueId() + ".wsrank"));
        }
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId() + ".team").length() != 0) {
            sscore.add("&6> &7Team");
            sscore.add("&6> &e&l" + cm.getPlayerData().getString(e.getPlayer().getUniqueId() + ".team"));
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

        Scoreboard.updateScoreboard("", new String[0], e.getPlayer());
        Scoreboard.updateScoreboard("&aTheWarKing", st, e.getPlayer());
    }

    public void tablist(Player e) {
        Tablist.Tablist("&aTheWarKing(n)&7dein WarShip Server", "&7Viel Spaß auf dem Server(n)&e(Name)&7!", e.getPlayer());
    }

    public void playerTablist(Player e) {
        e.getPlayer().setDisplayName(e.getPlayer().getName());
        if (rankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) {
            e.getPlayer().setPlayerListName(Color.ConvertColor(rankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + e.getPlayer().getName()));
        } else {
            e.getPlayer().setPlayerListName(Color.ConvertColor(rankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + rankSystem.getRank(e.getPlayer().getUniqueId()).getName() + " " + e.getPlayer().getName()));
        }
    }

}
