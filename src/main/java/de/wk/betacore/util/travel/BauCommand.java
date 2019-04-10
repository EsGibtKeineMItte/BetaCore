package de.wk.betacore.util.travel;

import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.data.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BauCommand implements CommandExecutor {

    FastTravelSystem fs = new FastTravelSystem();
    ConfigManager cm = new ConfigManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Misc.getNOTINCONSOLE());
            return false;
        }
        if(args.length != 0){
            sender.sendMessage("§7Benutung: §6/bau");
            return false;
        }

        Player player = (Player) sender;
        player.sendMessage("§aConnecting to Bau.");
        fs.connect(player, "Bau");

        return false;
    }
}