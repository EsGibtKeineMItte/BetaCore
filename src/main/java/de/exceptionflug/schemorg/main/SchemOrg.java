package de.exceptionflug.schemorg.main;

import de.exceptionflug.schemloader.main.SchemLoader;
import de.exceptionflug.schemorg.util.SchematicPaster;
import de.wk.betacore.BetaCore;
import de.wk.betacore.util.data.Misc;
import net.thecobix.brew.main.Brew;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SchemOrg implements Listener {

    public static final String S_PREFIX = SchemLoader.prefix;
    public static ArrayList<CheckSchem> toCheck = new ArrayList<>();


    public static Config conf;


    public static CheckSchem getSchem(String ownaz, String name) {
        BetaCore.debug("" + toCheck.size());
        for (CheckSchem cs : toCheck) {
            BetaCore.debug("CS Owner: " + cs.getOwner() + " CS Name: " + cs.getName() + " Searched Owner: " + ownaz + " Searched name: " + name);
            if (cs.getOwner().equals(ownaz) && cs.getName().equalsIgnoreCase(name)) {
                return cs;
            }
        }
        BetaCore.debug("Es konnte keine Schematic gefunden werden");
        return null;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("schemorg.list")) {
            if (toCheck.size() > 0) {
                if (toCheck.size() == 1) {
                    p.sendMessage(S_PREFIX + "Es gibt §6eine §7Schematic zu prüfen.");
                } else {
                    p.sendMessage(S_PREFIX + "Es gibt §6" + toCheck.size() + " §7Schematics zu prüfen.");
                }
            }
        }
    }

    public static class NotifierRunnable implements Runnable {

        @Override
        public void run() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("schemorg.list")) {
                    if (toCheck.size() > 0) {
                        if (toCheck.size() == 1) {
                            p.sendMessage(S_PREFIX + "Es gibt §6eine §7Schematic zu prüfen.");
                        } else {
                            p.sendMessage(S_PREFIX + "Es gibt §6" + toCheck.size() + " §7Schematics zu prüfen.");
                        }
                    }
                }
            }
        }

    }

    public static class FileCheckerRunnable implements Runnable {

        @Override
        public void run() {
            toCheck.clear();
            File dirall = new File(SchematicPaster.SCHEM_DIR);
            File dir = new File(dirall.getAbsolutePath() + "/tocheck");

            File[] content = dir.listFiles();

            if (content == null) {
                return;
            }

            for (File f : content) {
                try {
                    if (Brew.schematicUtil.isSchematic(f)) {
                        // typ;uuid;name.schematic
                        String[] comps = f.getName().split(";");
                        if (comps.length == 3) {
                            String type = comps[0];
                            String owner = comps[1];
                            String name = comps[2];
                            toCheck.add(new CheckSchem(f, type, name, owner));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Err: " + e.getMessage() + " @ " + e);
                }
            }
        }

    }

}
