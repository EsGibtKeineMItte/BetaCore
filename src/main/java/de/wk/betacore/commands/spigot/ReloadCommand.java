package de.wk.betacore.commands.spigot;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.listener.Spigot.JoinHandler;
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
            sender.sendMessage("§7Benutzung: §6/rl");
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

            BetaCore.teleportSpawn(player);
        }
        for (Player e : Bukkit.getOnlinePlayers()) {
            JoinHandler joinHandler = new JoinHandler();
            joinHandler.update(e);
        }
        sender.sendMessage("§aReload complete");
        return false;
    }
}
