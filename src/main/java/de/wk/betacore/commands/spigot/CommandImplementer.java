package de.wk.betacore.commands.spigot;

import de.wk.betacore.appearance.Info;
import de.wk.betacore.commands.spigot.manager.CommandInterface;
import de.wk.betacore.commands.spigot.manager.CommandManager;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandImplementer {

    public static void implementCommands() {
        ConfigManager cm = new ConfigManager();

        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "money";
            }

            @Override
            public String getInfo() {
                return "/money [SET/PAY]";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                Info.sendInfo((Player) sender, "&aMoney > " + cm.getPlayerData().getInt(((Player) sender).getUniqueId().toString() + ".money"));
            }

            @Override
            public String[] getSubCommands() {
                String[] subs = new String[2];
                subs[0] = "set";
                subs[1] = "pay";
                return subs;
            }

            @Override
            public Boolean inConsole() {
                return false;
            }
        });
        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "money set";
            }

            @Override
            public String getInfo() {
                return "";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                int i = Integer.parseInt(args[1]);
                cm.getPlayerData().setInt(((Player) sender).getPlayer().getUniqueId().toString() + ".money", i);
                Info.sendInfo((Player) sender, "&aMoney set to > " + cm.getPlayerData().getInt(((Player) sender).getUniqueId().toString() + ".money"));
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
