package de.wk.betacore.commands.spigot;

import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    ConfigManager cm = new ConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length != 0) {
            sender.sendMessage("§7Benutzung: §6/reload");
            return false;
        }

        if (!(sender.hasPermission("betacore.reload")) && sender.hasPermission("betacore.*")) {
            sender.sendMessage(Misc.NOPERM);
            return false;
        }

        sender.sendMessage("§aReloading Server");
        Bukkit.getServer().reload();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setGameMode(GameMode.CREATIVE);
            if (cm.getConfig().getLocation("Spawn") != null) {
                player.teleport(cm.getConfig().getLocation("Spawn"));
            }
        }
        sender.sendMessage("§aReload complete");
        return false;
    }
}
