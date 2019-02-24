package de.wk.betacore.util;

import de.butzlabben.world.gui.GuiCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class WarPlayer {
    static ConfigManager cm = new ConfigManager();

    public WarPlayer(UUID uuid) {
        if (cm.getPlayerData().getString(uuid.toString() + ".name") == null) {
            getWarPlayer(uuid);
        }
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

    public static WarPlayer getWarPlayer(UUID uuid) {
        if (cm.getPlayerData().getString(uuid.toString() + ".rank") == null) {
            cm.getPlayerData().setString(uuid.toString() + ".rank", "USER");
        }
        if (cm.getPlayerData().getString(uuid.toString() + ".money") == null) {
            cm.getPlayerData().setInt(uuid.toString() + ".money", 0);
        }
        //Name
        if (cm.getPlayerData().getString(uuid.toString() + ".name") == null || Bukkit.getOfflinePlayer(uuid) != null) {
            cm.getPlayerData().setString(uuid.toString() + ".name", Bukkit.getOfflinePlayer(uuid).getName());
        }
        if (!(cm.getPlayerData().getBoolean(uuid.toString() + ".muted"))) {
            cm.getPlayerData().setBoolean(uuid.toString() + ".muted", false);
        }
        if (cm.getPlayerData().getInt(uuid + ".wsrank") == 0) {
            cm.getPlayerData().setInt(uuid + ".wsrank", 900);
        }
        return new WarPlayer(uuid);
    }

    public void setWarShipTeam() {

    }

    public String getWarShipTeam() {
        return null;
    }


}
