package de.wk.betacore.commands.spigot;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.data.Misc;
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

        if (!(sender.hasPermission("betacore.setspawn")) && (!(sender.hasPermission("betacore.*")))) {
            sender.sendMessage(Misc.NOPERM);
            return false;
        }

        Player player = (Player) sender;
        BetaCore.setSpawn(player.getLocation());
        player.sendMessage(Misc.PREFIX + "§7Du hast den Spawn erfolgreich gesetzt.");

        return false;
    }
}
