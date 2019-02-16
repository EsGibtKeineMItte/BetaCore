package de.wk.betacore.util.travel;

import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    FastTravelSystem fs = new FastTravelSystem();
    ConfigManager cm = new ConfigManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(misc.getNOTINCONSOLE());
            return false;
        }
        Player player = (Player) sender;
        fs.connect(player, cm.getGlobalConfig().getString("LinkToLobby"));

        return false;
    }
}
