package de.wk.betacore.listener.Spigot;

import de.wk.betacore.appearance.Scoreboard;
import de.wk.betacore.appearance.Tablist;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinHandler implements Listener {
    ConfigManager cm = new ConfigManager();
    RankSystem rankSystem = new RankSystem();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        scoreboard(e);
        tablist(e);
    }

    private void scoreboard(PlayerJoinEvent e) {
        String[] st = new String[12];
        st[0] = "&6";
        st[1] = "&6> &7Money";
        st[2] = "&6> &e&7" + cm.getPlayerData().getInt(e.getPlayer().getUniqueId().toString() + ".money");
        st[3] = "&6> &7Rank";
        st[4] = "&6> " + rankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + rankSystem.getRank(e.getPlayer().getUniqueId()).getName();
        st[5] = "&7";
        st[6] = "&6> &7Joins";
        st[7] = "&6> &e(Joins)";
        st[8] = "&6> &7Play Time";
        st[9] = "&6> &e(PlayTime)";
        st[10] = "&8";
        st[11] = "&6TheWarking.de";
        Scoreboard.updateScoreboard("&aTheWarKing", st, e.getPlayer());
    }

    private void tablist(PlayerJoinEvent e) {
        Tablist.Tablist("&aTheWarKing(n)&7dein WarShip Server", "&7Viel Spaß auf dem Server(n)&e(Name)&7!", e.getPlayer());
    }

}
