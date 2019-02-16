package de.wk.betacore.commands.spigot;

import de.wk.betacore.appearance.Info;
import de.wk.betacore.commands.spigot.manager.CommandInterface;
import de.wk.betacore.commands.spigot.manager.CommandManager;
import de.wk.betacore.listener.Spigot.JoinHandler;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandImplementer {

    public static void implementCommands() {
        ConfigManager cm = new ConfigManager();
        JoinHandler joinHandler = new JoinHandler();
        RankSystem rankSystem = new RankSystem();

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
                if (i < 1) {
                    Info.sendInfo((Player) sender, "&cDiese Anzahl ist nicht erlaubt");
                    return;
                }
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

        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "core";
            }

            @Override
            public String getInfo() {
                return "/core help";
            }

            @Override
            public void run(CommandSender sender, String[] args) { }

            @Override
            public String[] getSubCommands() {
                String[] subs = new String[5];
                subs[0] = "core setrank";
                subs[1] = "core setup";
                subs[2] = "core reload";
                subs[3] = "core help";
                subs[4] = "core info";
                return subs;
            }

            @Override
            public Boolean inConsole() {
                return true;
            }
        });
        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "core setrank";
            }

            @Override
            public String getInfo() {
                return "";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (sender.hasPermission("betacore.setrank")) {
                    if (args.length != 3) {
                        CommandManager.wrongUsage(sender);
                        return;
                    }
                    int your = rankSystem.getRank(((Player) sender).getUniqueId()).getPriority();
                    ArrayList<String> ranks = new ArrayList<>();
                    ranks.add(Rank.ADMIN.getName());
                    ranks.add(Rank.DEV.getName());
                    ranks.add(Rank.MOD.getName());
                    ranks.add(Rank.SUPPORTER.getName());
                    ranks.add(Rank.ARCHI.getName());
                    ranks.add(Rank.YOU_TUBER.getName());
                    ranks.add(Rank.PREMIUM.getName());
                    ranks.add(Rank.USER.getName());

                    ArrayList<String> priority = new ArrayList<>();
                    ranks.add(Rank.ADMIN.getPriority() + "");
                    ranks.add(Rank.DEV.getPriority() + "");
                    ranks.add(Rank.MOD.getPriority() + "");
                    ranks.add(Rank.SUPPORTER.getPriority() + "");
                    ranks.add(Rank.ARCHI.getPriority() + "");
                    ranks.add(Rank.YOU_TUBER.getPriority() + "");
                    ranks.add(Rank.PREMIUM.getPriority() + "");
                    ranks.add(Rank.USER.getPriority() + "");

                    Boolean isRank = true;
                    int their = 0;
                    for (String name : ranks) {
                        if (isRank && name.equals(args[2].toUpperCase())) {
                            isRank = false;
                            their = Integer.parseInt(priority.get(ranks.indexOf(name)));
                        }
                    }
                    if (isRank) {
                        Info.sendInfo((Player) sender, "Ranks does not exist");
                        return;
                    }
                    if (your < their || your == 0 || !(sender instanceof Player)) {
                        String rank = args[2].toUpperCase();
                        cm.getPlayerData().setString(((Player) sender).getUniqueId().toString() + ".rank", rank);
                        Info.sendInfo((Player) sender, "Rank geändert zu " + rank);
                    }
                }
            }

            @Override
            public String[] getSubCommands() {
                return new String[0];
            }

            @Override
            public Boolean inConsole() {
                return true;
            }
        });
    }

}
