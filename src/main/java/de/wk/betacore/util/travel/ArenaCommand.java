package de.wk.betacore.util.travel;


import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.misc;
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
            sender.sendMessage(misc.getNOTINCONSOLE());
            return false;
        }
        if (!(args.length == 1)) {
            sender.sendMessage(misc.getPREFIX() + "§7Benutzung: §6/arena <1|2>");
            return false;
        }
        Player player = (Player) sender;
        if (args[0].equals("1")) {
            player.sendMessage("§aDu wirst zur Arena§7-§a1 verbunden.");
            fs.connect(player, cm.getGlobalConfig().getString("LinkToArena-1")); //LINK ZU ARENA 1 ANGEBEN
        } else if (args[0].equals("2")) {
            fs.connect(player, cm.getGlobalConfig().getString("LinkToArena-2"));
            player.sendMessage("§aDu wirst zur Arena§7-§a2 verbunden.");
        } else {
            player.sendMessage(misc.getPREFIX() + "§cDiese Arena existiert nicht.");
        }
        return false;
    }
}
