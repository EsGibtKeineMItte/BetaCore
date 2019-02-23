package de.wk.betacore.commands.spigot;


import de.wk.betacore.util.Config;
import de.wk.betacore.util.ConfigManager;
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
        Player player = (Player) sender;
        if (player.hasPermission("alphacore.setperms") || player.hasPermission("alphacore.*")) {
          Player target = Bukkit.getPlayer(args[0]);
          if(target != null){
              PermissionManager.addPermission(args[1], target);
              player.sendMessage(Misc.getPREFIX() + "§7Du hast dem Spieler: §6" + target.getName() + "§7 die Permission: §6 " + args[1] + " §7 hinzugefügt.");
          }else{
              player.sendMessage(Misc.getPREFIX() + "§cDieser Spieler nicht online");
          }
        } else {
            player.sendMessage(Misc.getNOPERM());
        }


        return false;
    }
}
