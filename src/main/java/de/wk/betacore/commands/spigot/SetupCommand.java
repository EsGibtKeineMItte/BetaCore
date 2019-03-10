package de.wk.betacore.commands.spigot;

import de.wk.betacore.objects.WarPlayer;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Misc.NOPERM);
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("§7Benutzung: §6/setup §7<§6Spieler§7>");
            return false;
        }
        if (Bukkit.getOfflinePlayer(args[1]) == null) {
            sender.sendMessage("§cDieser Spieler existiert nicht.");
            return false;
        }
        WarPlayer.manuellsetup(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Bukkit.getOfflinePlayer(args[0]).getName());
        return false;
    }

}
