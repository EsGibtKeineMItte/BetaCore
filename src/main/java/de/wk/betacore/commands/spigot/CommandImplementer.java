package de.wk.betacore.commands.spigot;

import de.wk.betacore.appearance.Info;
import de.wk.betacore.commands.spigot.manager.CommandInterface;
import de.wk.betacore.commands.spigot.manager.CommandManager;
import de.wk.betacore.listener.Spigot.JoinHandler;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
                cm.getConfig().save();
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
                cm.getConfig().save();
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
                cm.getConfig().save();
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
                cm.getConfig().save();
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
                return "";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (!(sender.hasPermission("betacore.core"))) {
                    return;
                }
                Info.sendInfo((Player) sender, "&c/core help");
            }

            @Override
            public String[] getSubCommands() {
                String[] subs = new String[4];
                subs[0] = "core setrank";
                subs[1] = "core info";
                subs[2] = "core reload";
                subs[3] = "core help";
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
                if (sender.hasPermission("betacore.core.setrank")) {
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
                    priority.add(Rank.ADMIN.getPriority() + "");
                    priority.add(Rank.DEV.getPriority() + "");
                    priority.add(Rank.MOD.getPriority() + "");
                    priority.add(Rank.SUPPORTER.getPriority() + "");
                    priority.add(Rank.ARCHI.getPriority() + "");
                    priority.add(Rank.YOU_TUBER.getPriority() + "");
                    priority.add(Rank.PREMIUM.getPriority() + "");
                    priority.add(Rank.USER.getPriority() + "");

                    Boolean isRank = true;
                    int their = 0;
                    for (String name : ranks) {
                        if (isRank && name.equals(args[2].toUpperCase())) {
                            isRank = false;
                            their = Integer.parseInt(priority.get(ranks.indexOf(name)));
                        }
                    }
                    if (isRank) {
                        Info.sendInfo((Player) sender, "Rank " + args[2].toUpperCase() + " does not exist");
                        return;
                    }
                    if (your < their || your == 0 || !(sender instanceof Player) || sender.isOp()) {
                        String rank = args[2].toUpperCase();
                        cm.getPlayerData().setString(Bukkit.getOfflinePlayer(args[1]).getUniqueId() + ".rank", rank);
                        Info.sendInfo((Player) sender, "&eRank geändert zu " + rank);
                        joinHandler.update((Player) Bukkit.getOfflinePlayer(args[1]));
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
        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "core help";
            }

            @Override
            public String getInfo() {
                return "";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (!(sender.hasPermission("betacore.core.help"))) {
                    return;
                }
                Info.sendInfo((Player) sender, "&7/core help");
                Info.sendInfo((Player) sender, "&7Zeigt diese Nachricht an");
                Info.sendInfo((Player) sender, "&7/core setrank [User] [Rank]");
                Info.sendInfo((Player) sender, "&7Setzte den Rank eines Users");
                Info.sendInfo((Player) sender, "&7/core info");
                Info.sendInfo((Player) sender, "&7Zeigt Informationen über den Server an");
                Info.sendInfo((Player) sender, "&7/core reload");
                Info.sendInfo((Player) sender, "&7Reloade alle Config files");
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
                return "core reload";
            }

            @Override
            public String getInfo() {
                return "";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (!(sender.hasPermission("betacore.core.reload"))) {
                    return;
                }
                for (Player e : Bukkit.getOnlinePlayers()) {
                    joinHandler.update(e);
                }
                // TODO: hier muss noch das ganze reload zeug hin!
                Info.sendInfo((Player) sender, "&eCore System reloaded");
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
        CommandManager.addCommand(new CommandInterface() {
            @Override
            public String getName() {
                return "core info";
            }

            @Override
            public String getInfo() {
                return "";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (!(sender.hasPermission("betacore.core.info"))) {
                    return;
                }
                // TODO: Hier muss noch alles an Info hin
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
                return "gm";
            }

            @Override
            public String getInfo() {
                return "/gm [GameMode]";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Nicht in der Console ausführbar");
                        return;
                    }
                    if (!(sender.hasPermission("betacore.gm.self"))) {
                        Info.sendInfo((Player) sender, "&cDu hast keine Rechte dazu!");
                        return;
                    }
                    int i = Integer.parseInt(args[0]);
                    if (i > -1 && i <= 4) {
                        if (i == 0) {
                            ((Player) sender).setGameMode(GameMode.SURVIVAL);
                        } else if (i == 1) {
                            ((Player) sender).setGameMode(GameMode.CREATIVE);
                        } else if (i == 2) {
                            ((Player) sender).setGameMode(GameMode.ADVENTURE);
                        } else if (i == 3) {
                            ((Player) sender).setGameMode(GameMode.SPECTATOR);
                        }
                        Info.sendInfo((Player) sender, "&aGamemode updated zu " + ((Player) sender).getGameMode().toString());
                    } else {
                        Info.sendInfo((Player) sender, "&cUnbekannter Gamemode!");
                    }
                }
                if (args.length == 2) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Nicht in der Console ausführbar");
                        return;
                    }
                    if (!(sender.hasPermission("betacore.gm.other"))) {
                        Info.sendInfo((Player) sender, Misc.getNOPERM());
                        return;
                    }
                    int i = Integer.parseInt(args[1]);
                    if (i > -1 && i <= 4) {
                        if (Bukkit.getPlayer(args[0]) == null) {
                            Info.sendInfo((Player) sender, "§7Dieser Spieler ist nicht online.");
                            return;
                        }
                        if (i == 0) {
                            (Bukkit.getPlayer(args[0]).getPlayer()).setGameMode(GameMode.SURVIVAL);
                        } else if (i == 1) {
                            (Bukkit.getPlayer(args[0]).getPlayer()).setGameMode(GameMode.CREATIVE);
                        } else if (i == 2) {
                            (Bukkit.getPlayer(args[0]).getPlayer()).setGameMode(GameMode.ADVENTURE);
                        } else if (i == 3) {
                            (Bukkit.getPlayer(args[0]).getPlayer()).setGameMode(GameMode.SPECTATOR);
                        }
                        Info.sendInfo((Player) sender, "&aGamemode updated zu " + ((Player) sender).getGameMode().toString());
                    } else {
                        Info.sendInfo((Player) sender, "&cUnbekannter Gamemode!");
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
