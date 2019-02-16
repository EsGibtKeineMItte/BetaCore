package de.wk.betacore.listener.Spigot;

import de.wk.betacore.appearance.Scoreboard;
import de.wk.betacore.appearance.Tablist;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.LocalDate;

public class DataSetter implements Listener {
    ConfigManager cm = new ConfigManager();

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        //Rank
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".rank") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".rank", "USER");
        }
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".money") == null) {
            cm.getPlayerData().setInt(e.getPlayer().getUniqueId().toString() + ".money", 0);
        }
        //Name
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".name") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".name", e.getPlayer().getName());
        }
        if (!(cm.getPlayerData().getBoolean(e.getPlayer().getUniqueId().toString() + ".muted"))) {
            cm.getPlayerData().setBoolean(e.getPlayer().getUniqueId().toString() + "muted", false);
        }

        if (cm.getPlayerData().getInt(e.getPlayer().getUniqueId() + ".wsrank") == 0) {
            cm.getPlayerData().setInt(e.getPlayer().getUniqueId() + ".wsrank", 900);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        LocalDate today = LocalDate.now();
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".firstjoin") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".firstjoin", today.toString());
            return;
        }
        cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".lastjoin", today.toString());
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
        st[9] = "&6> TheWarking.de";
        Scoreboard.updateScoreboard(e.getPlayer().getServer().getServerName(), st, e.getPlayer());
    }

    private void tablist(PlayerJoinEvent e) {
        Tablist.Tablist("(Server)(n)dein WarShip Server", "Viel Spaß auf dem Server(n)(Name)!", e.getPlayer());
    }
}
