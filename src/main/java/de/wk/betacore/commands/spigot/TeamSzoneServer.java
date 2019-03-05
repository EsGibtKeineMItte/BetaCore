package de.wk.betacore.commands.spigot;

import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.travel.FastTravelSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamSzoneServer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Misc.NOTINCONSOLE);
            return false;
        }
        Player player = (Player) sender;

        if (!(sender.hasPermission("betacore.dev") && sender.hasPermission("betacore.*"))) {
            sender.sendMessage(Misc.NOPERM);
            return false;
        }
        FastTravelSystem fs = new FastTravelSystem();
        fs.connect(player, "DevServer");//ChangeMe


        return false;
    }


    /*
    Use this to connect a user to a server which is only for staff
     */
}
