package de.wk.betacore.util.travel;


import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.data.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ArenaCommand implements CommandExecutor {
    FastTravelSystem fs = new FastTravelSystem();
    ConfigManager cm = new ConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Misc.getNOTINCONSOLE());
            return false;
        }
        if (!(args.length == 1)) {
            sender.sendMessage(Misc.getPREFIX() + "§7Benutzung: §6/arena <1|2>");
            return false;
        }
        Player player = (Player) sender;
        if (args[0].equals("1")) {
            player.sendMessage("§aDu wirst zur Arena§7-§a1 verbunden.");
            player.performCommand("/server Arena-1");
        } else if (args[0].equals("2")) {
            player.sendMessage("§aDu wirst zur Arena§7-§a2 verbunden.");
            player.performCommand("/server Arena-2");
        } else {
            player.sendMessage(Misc.getPREFIX() + "§cDiese Arena existiert nicht.");
        }
        return false;
    }
}
