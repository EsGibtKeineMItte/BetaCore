package de.wk.betacore.commands.spigot;

import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetSpawnCommand implements CommandExecutor {
    ConfigManager cm = new ConfigManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Misc.getNOTINCONSOLE());
            return false;
        }

        if(!(sender.hasPermission("betacore.setspawn")) && (!(sender.hasPermission("betacore.*")))){
            sender.sendMessage(Misc.NOPERM);
            return false;
        }

        Player player = (Player) sender;
        Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
        player.getWorld().setSpawnLocation(loc);
        Bukkit.getWorld("world").setSpawnLocation(loc);
        cm.getConfig().setLocation("Spawn", loc);
        player.sendMessage(Misc.Prefix + "§7Du hast den Spawn gesetzt.");
        return false;
    }


}
