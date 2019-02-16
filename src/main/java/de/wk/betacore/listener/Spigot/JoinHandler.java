package de.wk.betacore.listener.Spigot;

import de.wk.betacore.appearance.Scoreboard;
import de.wk.betacore.appearance.Tablist;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinHandler implements Listener {
    ConfigManager cm = new ConfigManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        scoreboard(e);
        tablist(e);
    }

    private void scoreboard(PlayerJoinEvent e) {
        String[] st = new String[10];
        st[0] = "&6";
        st[1] = "&6> &7Joins";
        st[2] = "&6> &7(Joins)";
        st[3] = "&6> &7Play Time";
        st[4] = "&6> &7(PlayTime)";
        st[5] = "&7";
        st[6] = "&6> &7Money";
        st[7] = "&6> &7" + cm.getPlayerData().getInt(e.getPlayer().getUniqueId().toString() + ".money");
        st[8] = "&8";
        st[9] = "&6TheWarking.de";
        Scoreboard.updateScoreboard("&aTheWarKing", st, e.getPlayer());
    }

    private void tablist(PlayerJoinEvent e) {
        Tablist.Tablist("&aTheWarKing(n)&7dein WarShip Server", "&7Viel Spaß auf dem Server(n)&e(Name)&7!", e.getPlayer());
    }

}
