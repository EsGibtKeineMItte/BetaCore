package de.wk.betacore.listener.Spigot;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WorldSystemUtil implements Listener {
    ConfigManager cm = new ConfigManager();

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {
        if(!(cm.getConfig().getBoolean("useAsBauServer"))){
            return;
        }

        Player player = e.getPlayer();
        String msg = e.getMessage().toLowerCase();

        if (msg.startsWith("/bau")) {
            BetaCore.debug("Rep /bau /ws");
            e.setCancelled(true);
            String message = msg.replaceAll("/bau", "ws");
            player.performCommand(message);
        }

        if(msg.startsWith("//schematic")){
            e.setCancelled(true);
            String message = msg.replace("//schematic", "schem");
            player.performCommand(message);
        }

        if(msg.startsWith("//schem")){
            e.setCancelled(true);
            String message = msg.replace("//schem", "schem");
            BetaCore.debug(message);
            player.performCommand(message);
//            player.chat(message);
        }
    }

}
