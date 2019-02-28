package de.wk.betacore.commands.spigot;

import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/*
betacore.seeplugins
 */

public class PluginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender.hasPermission("betacore.seeplugins")) && (!(sender.hasPermission("betacore.*")))){
            sender.sendMessage(Misc.getNOPERM());
            return false;
        }

        sender.sendMessage(Misc.getPREFIX() +  "§6Installierte Plugins (§e"+ Bukkit.getPluginManager().getPlugins().length+"§6):");
        for(Plugin pl : Bukkit.getPluginManager().getPlugins()) {
            sender.sendMessage((pl.isEnabled() ? "§a" : "§c")+pl.getName()+" §7by §6"+pl.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
        }

        return false;
    }
}
