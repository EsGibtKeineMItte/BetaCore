package de.wk.betacore.commands.spigot;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.wk.betacore.util.travel.FastTravelSystem;
import org.bukkit.entity.Player;

public class ServerCommand {


    @Command(name = "connect", description = "Command, um sich auf Server zu verbinden", aliases = {"s", "con"}, permission = "betacore.connect", inGameOnly = true)

    public void onConnect(CommandArgs args) {
        if (args.length() != 1) {
            args.getSender().sendMessage("§7Benutzung: §6/connect §7<§6Server§7>");
            return;
        }

        Player player = (Player) args.getSender();
        FastTravelSystem fs = new FastTravelSystem();
        fs.connect(player, args.getArgs(0));
    }
}
