package de.wk.betacore.commands.spigot;

import de.wk.betacore.objects.WarPlayer;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/*
betacore.debug
betacore.setupplayer
 */

public class SetupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§7Benutzung: §6/setup §7<§6Spieler§7>");
            return false;
        }
        if(!(sender.hasPermission("betacore.setupplayer") && sender.hasPermission("betacore.debug") &&sender.hasPermission("betacore.*")))

        if (Bukkit.getOfflinePlayer(args[1]) == null) {
            sender.sendMessage("§cDieser Spieler existiert nicht.");
            return false;
        }
        WarPlayer.manuellsetup(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Bukkit.getOfflinePlayer(args[0]).getName());
        sender.sendMessage(Misc.PREFIX + "Du die Daten für den Spieler " +  Bukkit.getOfflinePlayer(args[0]).getName() + " erfolgreich gesetzt" );
        return false;
    }

}
