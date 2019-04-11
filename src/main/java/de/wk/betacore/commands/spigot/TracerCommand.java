package de.wk.betacore.commands.spigot;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.wk.betacore.listener.Spigot.TNTTracer;
import de.wk.betacore.util.data.Misc;
import org.bukkit.entity.Player;


public class TracerCommand {


    @Command(name = "trace", description = "Command, um TNT zu tracen", inGameOnly = true)

    public void trace(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (args.length() != 0) {
            player.sendMessage(Misc.PREFIX + "§7Benutzung: §6/trace");
            return;
        }

        player.sendMessage(Misc.PREFIX + "§6/trace start §7Startet die Aufnahme.");
        player.sendMessage(Misc.PREFIX + "§6/trace stop §7Stoppt eine laufende Aufnahme.");
        player.sendMessage(Misc.PREFIX + "§6/trace clear §7Leert die aufgenommenen Traces & stoppt die Aufnahme.");
        player.sendMessage(Misc.PREFIX + "§6/trace show §7Zeigt die Trace.");
        player.sendMessage(Misc.PREFIX + "§6/trace unshow §7Entfernt die Trace.");

    }

    @Command(name = "trace.start", description = "Start tracer", inGameOnly = true)

    public void traceStart(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (args.length() != 0) {
            player.sendMessage(Misc.PREFIX + "§7Benutzung: §6/trace");
            return;
        }

        TNTTracer.addPlayerToTracer(player);

        player.sendMessage(Misc.PREFIX + "§aAufnahme begonnen.");


    }

    @Command(name = "trace.stop", description = "Stop&Clear tracer", inGameOnly = true)

    public void traceStop(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (args.length() != 0) {
            player.sendMessage(Misc.PREFIX + "§7Benutzung: §6/trace");
            return;
        }


        TNTTracer.removePlayerFromTracer(player);
        player.sendMessage(Misc.PREFIX + "§aAufnahme beendet.");


    }


    @Command(name = "trace.show", description = "Show traces", inGameOnly = true)

    public void traceShow(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (args.length() != 0) {
            player.sendMessage(Misc.PREFIX + "§7Benutzung: §6/trace");
            return;
        }
        if (!(TNTTracer.showTraces(player.getWorld(), player))) {
            player.sendMessage(Misc.PREFIX + "§cDu hast noch keine Traces aufgenommen.");
            return;
        }
        player.sendMessage(Misc.PREFIX + "§aZeige TNT-Traces.");
    }


    @Command(name = "trace.unshow", description = "Command, um TNT zu tracen", inGameOnly = true)

    public void traceUnshow(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (args.length() != 0) {
            player.sendMessage(Misc.PREFIX + "§7Benutzung: §6/trace");
            return;
        }

        TNTTracer.unShowTraces(player.getWorld(), player);
        TNTTracer.removePlayerFromTracer(player);
    }


    @Command(name = "trace.debug", description = "Debug traces", inGameOnly = true, permission = "betacore.debug")

    public void onDebug(CommandArgs args) {

        Player player = (Player) args.getSender();


        player.sendMessage("§7Positionen für Welten eingetragen: §6" + TNTTracer.getLocationHashMap().size());
        player.sendMessage("§7Positionen gesetzt: §6" + TNTTracer.getLocations().size());
        player.sendMessage("§7Welten gecheckt: §6" + TNTTracer.getCheckedWorlds().size());

        player.sendMessage("§7Aktuelle Welt gespeichert: " + (TNTTracer.getLocationHashMap().containsKey(player.getWorld()) ? "§aTrue" : "§cFalse"));
        player.sendMessage("§7Selbst als auf der toCheck-Liste: " + (TNTTracer.getCheckedWorlds().contains(player.getWorld()) ? "§aTrue" : "§cFalse"));


    }

    @Command(name = "trace.debug.list", description = "Debug traces", inGameOnly = true, permission = "betacore.debug.list")

    public void onShow(CommandArgs args) {

        Player player = (Player) args.getSender();

        try {
            player.sendMessage(TNTTracer.getLocationHashMap().get(player.getWorld()).toString());
        } catch (NullPointerException e) {
            player.sendMessage("§aDu bist nicht in der Liste.");
        }

    }

    @Command(name = "trace.debug.points", description = "Debug traces", inGameOnly = true, permission = "betacore.debug.points")

    public void onPoints(CommandArgs args) {


    }


}
