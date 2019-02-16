package de.wk.betacore.commands.spigot.manager;

import org.bukkit.command.CommandSender;

public interface CommandInterface {

    String getName();

    String getInfo();

    void run(CommandSender sender, String[] args);

    String[] getSubCommands();

    Boolean inConsole();

}
