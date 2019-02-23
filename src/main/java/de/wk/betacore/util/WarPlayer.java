package de.wk.betacore.util;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class WarPlayer {
    static ConfigManager cm = new ConfigManager();

    public WarPlayer(UUID uuid) {

    }


    public static WarPlayer getWarPlayer(Player player) {
        if (cm.getPlayerData().getString(player.getUniqueId().toString() + ".rank") == null) {
            cm.getPlayerData().setString(player.getUniqueId().toString() + ".rank", "USER");
        }
        if (cm.getPlayerData().getString(player.getUniqueId().toString() + ".money") == null) {
            cm.getPlayerData().setInt(player.getUniqueId().toString() + ".money", 0);
        }
        //Name
        if (cm.getPlayerData().getString(player.getUniqueId().toString() + ".name") == null) {
            cm.getPlayerData().setString(player.getUniqueId().toString() + ".name", player.getName());
        }
        if (!(cm.getPlayerData().getBoolean(player.getUniqueId().toString() + ".muted"))) {
            cm.getPlayerData().setBoolean(player.getUniqueId().toString() + ".muted", false);
        }


        if (cm.getPlayerData().getInt(player.getUniqueId() + ".wsrank") == 0) {
            cm.getPlayerData().setInt(player.getUniqueId() + ".wsrank", 900);
        }
        return new WarPlayer(player.getUniqueId());
    }

    public void setWarShipTeam() {

    }

    public String getWarShipTeam() {
        return null;
    }


}
