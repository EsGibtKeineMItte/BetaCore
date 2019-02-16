package de.wk.betacore.commands.spigot;

import de.wk.betacore.appearance.Info;
import de.wk.betacore.commands.spigot.manager.CommandInterface;
import de.wk.betacore.commands.spigot.manager.CommandManager;
import de.wk.betacore.listener.Spigot.JoinHandler;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandImplementer {

    public static void implementCommands() {
        ConfigManager cm = new ConfigManager();
        JoinHandler joinHandler = new JoinHandler();

        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "money";
            }

            @Override
            public String getInfo() {
                return "";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (!(sender.hasPermission("betacore.money"))) {
                    return;
                }
                cm.getPlayerData().reload();
                Info.sendInfo((Player) sender, "&aMoney > " + cm.getPlayerData().getInt(((Player) sender).getUniqueId().toString() + ".money"));
                joinHandler.update((Player) sender);
            }

            @Override
            public String[] getSubCommands() {
                String[] subs = new String[3];
                subs[0] = "money set";
                subs[1] = "money pay";
                subs[2] = "money clear";
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
                if (!(sender.hasPermission("betacore.money.set"))) {
                    return;
                }
                cm.getPlayerData().reload();
                int i = Integer.parseInt(args[1]);
                cm.getPlayerData().setInt(((Player) sender).getPlayer().getUniqueId().toString() + ".money", i);
                Info.sendInfo((Player) sender, "&aMoney set to > " + cm.getPlayerData().getInt(((Player) sender).getUniqueId().toString() + ".money"));
                joinHandler.update((Player) sender);
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
        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "money pay";
            }

            @Override
            public String getInfo() {
                return "/money pay <USER> <AMOUNT>";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (!(sender.hasPermission("betacore.money.pay"))) {
                    return;
                }
                if (args.length != 3) {
                    CommandManager.wrongUsage(sender);
                    return;
                }
                int i = Integer.parseInt(args[2]);
                cm.getPlayerData().reload();
                if (cm.getPlayerData().getInt(((Player) sender).getUniqueId().toString() + ".money") >= i) {
                    cm.getPlayerData().setInt(((Player) sender).getPlayer().getUniqueId().toString() + ".money", cm.getPlayerData().getInt(((Player) sender).getPlayer().getUniqueId().toString() + ".money") - i);
                    cm.getPlayerData().setInt(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString() + ".money", cm.getPlayerData().getInt(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString() + ".money") + i);
                } else {
                    Info.sendInfo((Player) sender, "&cNicht genügend Money!");
                }
                joinHandler.update((Player) sender);
                joinHandler.update((Player) Bukkit.getOfflinePlayer(args[1]));
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
        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "money clear";
            }

            @Override
            public String getInfo() {
                return "/money clear <USER>";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (!(sender.hasPermission("betacore.money.clear"))) {
                    return;
                }
                if (args.length != 2) {
                    CommandManager.wrongUsage(sender);
                    return;
                }
                cm.getPlayerData().reload();
                cm.getPlayerData().setInt(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString() + ".money", 0);
                Info.sendInfo((Player) sender, "&aCleared the Money from " + Bukkit.getOfflinePlayer(args[1]).getPlayer().getName());
                joinHandler.update((Player) Bukkit.getOfflinePlayer(args[1]));
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
