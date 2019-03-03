package de.wk.betacore.commands.spigot;

import de.wk.betacore.commands.spigot.manager.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.omg.CORBA.Environment;

public class Core implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandManager.executeCommand(sender, "core", args);
        return true;
    }

}
