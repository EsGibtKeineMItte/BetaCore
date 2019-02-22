package de.wk.betacore.commands.spigot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("betacore.cc")){
                if(args.length == 0){
                    p.sendMessage("§bCustom Command §l| §c Syntax: /cc help");
                }if (args.length == 1){
                    if(args[0].equalsIgnoreCase("help")){
                        p.sendMessage("§b--==§lCustom Command§b==--");
                        p.sendMessage("§c/cc §ehelp §7| §aShows this Message");
                        p.sendMessage("§c/cc §ecreate §7| §aCreate a new Command");
                        p.sendMessage("§c/cc §edelete [name] §7| §aDelete a Command");
                    }


                }
            }
        }
        return true;
    }
}
