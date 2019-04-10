package de.wk.betacore.util.ranksystem;

import de.wk.betacore.util.data.Misc;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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

        if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world") && (RankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) && (!(allowedCMDs.contains(msg)))) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Misc.PREFIX + "§7Du kannst diesen Befehl hier nicht verwenden");
        } else {
           // e.getPlayer().sendMessage("Du darfst den Command " + msg + " auf der Welt " + e.getPlayer().getWorld().getName() + " verwenden.");
        }
    }


}
