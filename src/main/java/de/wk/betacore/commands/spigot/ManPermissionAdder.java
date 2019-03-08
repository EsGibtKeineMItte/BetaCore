package de.wk.betacore.commands.spigot;


import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
alphacore.setperm
 */

public class ManPermissionAdder implements CommandExecutor {
    ConfigManager cm = new ConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Noch kannst du diesen Befehl so nicht nutzen");
            return false;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§cDieser Spieler ist nicht online.");
            return false;
        }


        Player target = Bukkit.getPlayer(args[0]);
        Player player = (Player) sender;

        if (player.hasPermission("betacore.setperms") || player.hasPermission("betacore.*")) {
            PermissionManager.addPermission(args[1], target);
            player.sendMessage(Misc.getPREFIX() + "§7Du hast dem Spieler: §6" + target.getName() + "§7 die Permission: §6 " + args[1] + " §7 hinzugefügt.");
        } else {
            player.sendMessage(Misc.getNOPERM());
        }


        return false;
    }
}
