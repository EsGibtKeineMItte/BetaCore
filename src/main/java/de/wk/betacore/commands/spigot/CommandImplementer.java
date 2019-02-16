package de.wk.betacore.commands.spigot;

import de.wk.betacore.appearance.Info;
import de.wk.betacore.commands.spigot.manager.CommandInterface;
import de.wk.betacore.commands.spigot.manager.CommandManager;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandImplementer {

    public void implementCommands() {
        ConfigManager cm = new ConfigManager();
        CommandManager commandManager = new CommandManager();

        commandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "money";
            }

            @Override
            public String getInfo() {
                return null;
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                Info.sendInfo((Player) sender, "&aMoney > " + cm.getPlayerData().getInt(((Player) sender).getUniqueId().toString() + ".money"));
            }

            @Override
            public String[] getSubCommands() {
                return new String[0];
            }

            @Override
            public Boolean inConsole() {
                return false;
            }
        });
    }

}
