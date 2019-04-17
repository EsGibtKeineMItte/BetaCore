package de.wk.betacore.util.ranksystem;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class PermissionsListener implements Listener {


    private ArrayList<String> allowedCMDs = new ArrayList<>();

    /**
     * Klasse, um auf gewissen Welten Befehle zu blockieren
     *
     * @param allowedCMDS Commands, die jeder Spieler überall nutzen darf
     */

    public PermissionsListener(String... allowedCMDS) {

        this.allowedCMDs.addAll(Arrays.asList(allowedCMDS));

    }

    @EventHandler
    public void onCMD(PlayerCommandPreprocessEvent e) {

        String msg = e.getMessage().toLowerCase();
        ConfigManager cm = new ConfigManager();
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world") && (RankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER))
                && (!(allowedCMDs.contains(msg))) && (!cm.getConfig().getBoolean("useAsArena"))) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Misc.PREFIX + "§7Du kannst diesen Befehl hier nicht verwenden");
        } else {
           // e.getPlayer().sendMessage("Du darfst den Command " + msg + " auf der Welt " + e.getPlayer().getWorld().getName() + " verwenden.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getPlayer().getWorld().getName().equals("world") && (!(e.getPlayer().hasPermission("betacore.build"))) && (!(e.getPlayer().hasPermission("betacore.*")))){
            e.setCancelled(true);
            e.getPlayer().sendMessage(Misc.PREFIX + "§cDas darfst du hier nicht.");
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getMaterial() == Material.SIGN){
            BetaCore.debug("Auf sings darf  man drücken");
            return;
        }
        if(e.getPlayer().hasPermission("betacore.*") || e.getPlayer().hasPermission("betacore.build")){
            return;
        }
        e.getPlayer().sendMessage(Misc.PREFIX + "§cDas darfst du hier nicht.");
        e.setCancelled(true);

    }


}
