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

public class JoinHandler implements Listener {
    ConfigManager cm = new ConfigManager();
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

    public void scoreboard(Player e) {
        String[] st = new String[12];
        st[0] = "&6";
        st[1] = "&6> &7Money";
        st[2] = "&6> &e" + cm.getPlayerData().getInt(e.getPlayer().getUniqueId().toString() + ".money");
        st[3] = "&6> &7Rank";
        st[4] = "&6> " + rankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + rankSystem.getRank(e.getPlayer().getUniqueId()).getName();
        st[5] = "&7";
        st[6] = "&6> &7Joins";
        st[7] = "&6> &e(Joins)";
        st[8] = "&6> &7Play Time";
        st[9] = "&6> &e(PlayTime)";
        st[10] = "&8";
        st[11] = "&6TheWarking.de";
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
