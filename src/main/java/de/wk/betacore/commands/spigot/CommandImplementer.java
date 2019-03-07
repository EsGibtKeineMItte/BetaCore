package de.wk.betacore.commands.spigot;

import de.wk.betacore.appearance.Info;
import de.wk.betacore.commands.spigot.manager.CommandInterface;
import de.wk.betacore.commands.spigot.manager.CommandManager;
import de.wk.betacore.listener.Spigot.JoinHandler;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.moneysystem.MoneySystem;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
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
                if (!(sender.hasPermission("betacore.money")) && (!(sender.hasPermission("betacore.*")))) {
                    return;
                }

                if(!(sender instanceof Player)){
                    Misc.getNOTINCONSOLE();
                    return;
                }
                Player player = (Player) sender;
                cm.getPlayerData().reload();
                Info.sendInfo((Player) sender, "&7Du hast §6 " + MoneySystem.getMoney(player.getUniqueId()) + " §7 Coins.");
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
                if (!(sender.hasPermission("betacore.money.set") && sender.hasPermission("betacore.*"))) {
                    return;
                }
                cm.getPlayerData().reload();

                if(!(StringUtils.isNumeric(args[2]))){
                    sender.sendMessage("Du musst eine Ganzzahl als Betrag angeben.");
                    return;
                }
                int i = Integer.parseInt(args[2]);
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
                if (!(sender.hasPermission("betacore.money.pay") && sender.hasPermission("betacore.*"))) {
                    sender.sendMessage(Misc.NOPERM);
                    return;
                }
                if(!(sender instanceof Player)){
                    sender.sendMessage(Misc.getNOTINCONSOLE());
                    return;
                }
                Player player = (Player) sender;
                if (args.length != 3) {
                    CommandManager.wrongUsage(sender);
                    return;
                }
                int i = Integer.parseInt(args[2]);
                if (i < 1) {
                    Info.sendInfo((Player) sender, "&cDiese Anzahl ist nicht erlaubt");
                    return;
                }

                if (MoneySystem.getMoney(player.getUniqueId()) >= i) {
                    MoneySystem.decreaseMoney(player.getUniqueId(), i);
                    MoneySystem.increaseMoney(Bukkit.getOfflinePlayer(args[1]).getUniqueId(), i);
                } else {
                    Info.sendInfo((Player) sender, "§cDazu hast du nicht genügend Geld");
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
                MoneySystem.setMoney(Bukkit.getOfflinePlayer(args[1]).getUniqueId(), 0);
                Info.sendInfo((Player) sender, Misc.PREFIX + "&7Du hast das Geld von " + Bukkit.getOfflinePlayer(args[1]).getPlayer().getName());
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
                if (sender instanceof Player) {
                    Info.sendInfo((Player) sender, "&c/core help");
                } else {
                    System.out.println("/core help");
                }
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

                if(!(sender.hasPermission("betacore.setrank")) && (!(sender.hasPermission("betacore.*")))){
                    sender.sendMessage(Misc.NOPERM);
                    return;
                }

                    if (args.length != 3) {
                        CommandManager.wrongUsage(sender);
                        return;
                    }
                    int your = rankSystem.getRank(((Player) sender).getUniqueId()).getPriority();

                    ArrayList<String> priority = new ArrayList<>();
                    priority.add(Rank.ADMIN.getPriority() + "");
                    priority.add(Rank.DEV.getPriority() + "");
                    priority.add(Rank.MOD.getPriority() + "");
                    priority.add(Rank.SUPPORTER.getPriority() + "");
                    priority.add(Rank.BUILDER.getPriority() + "");
                    priority.add(Rank.YOU_TUBER.getPriority() + "");
                    priority.add(Rank.PREMIUM.getPriority() + "");
                    priority.add(Rank.USER.getPriority() + "");

                    Boolean isRank = true;
                    int their = 0;

                    if(!(sender.hasPermission("betacore.setrank"))&& (!(sender.hasPermission("betacore.*")))) {
                        sender.sendMessage(Misc.NOPERM);
                        return;
                    }
                    if(Bukkit.getOfflinePlayer(args[1]) == null){
                        sender.sendMessage("§cDieser Spieler existiert nicht");
                        return;
                    }

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                    if (your < their || your == 0 || sender.isOp()) {
                        String rank = args[2].toUpperCase();
                        switch (args[2].toLowerCase()){
                            case("admin"):
                                RankSystem.setRank(target.getUniqueId(), "ADMIN");
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                break;
                            case("dev"):
                                RankSystem.setRank(target.getUniqueId(), "DEV");
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                break;
                            case("mod"):
                                RankSystem.setRank(target.getUniqueId(), "MOD");
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                break;
                            case("supp"):
                                RankSystem.setRank(target.getUniqueId(), "SUPPORTER");
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                break;
                            case("builder"):
                                RankSystem.setRank(target.getUniqueId(), "BUILDER");
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                break;
                            case("yt"):
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                RankSystem.setRank(target.getUniqueId(), "YOU_TUBER");
                            case("premium"):
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                RankSystem.setRank(target.getUniqueId(), "PREMIUM");
                                break;
                            case("user"):
                                sender.sendMessage(Misc.PREFIX + "§aRang geändert");
                                RankSystem.setRank(target.getUniqueId(), "USER");
                                break;
                            default:
                                sender.sendMessage(Misc.PREFIX + "§7Dieser Rang existiert nicht.");
                                break;
                        }
                        if (Bukkit.getPlayer(args[1]) != null) {
                            joinHandler.update(Bukkit.getPlayer(args[1]));
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
                if (!(sender.hasPermission("betacore.core.help") && sender.hasPermission("betacore.*"))) {
                    sender.sendMessage(Misc.NOPERM);
                    return;
                }
                Info.sendInfo((Player) sender, "&6/core help &7Zeigt diese Nachricht an");
                Info.sendInfo((Player) sender, "&6/core setrank <User> <Rank>&7Setzte den Rank eines Users");
                Info.sendInfo((Player) sender, "&6/core info §7Zeigt Informationen über den Core an");
                Info.sendInfo((Player) sender, "&6/core reload §7Reloadet den Core &alle Configfiles");
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
                return "/gm [GameMode] <Player>";
            }

            @Override
            public void run(CommandSender sender, String[] args) {
                if (args.length == 1) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Nicht in der Console ausführbar");
                        return;
                    }
                    if (!(sender.hasPermission("betacore.gm.self")) && (!(sender.hasPermission("betacore.*")))) {
                        Info.sendInfo((Player) sender, Misc.NOPERM);
                        return;
                    }
                    if (!(StringUtils.isNumeric(args[0]))) {
                        Info.sendInfo((Player) sender, "§7Dieser GameMode existiert nicht.");
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
                    if (!(sender.hasPermission("betacore.gm.other")) && (!(sender.hasPermission("betacore.*")))) {
                        Info.sendInfo((Player) sender, Misc.getNOPERM());
                        return;
                    }
                    if (!(StringUtils.isNumeric(args[0]))) {
                        Info.sendInfo((Player) sender, "§7Dieser GameMode existiert nicht.");
                        return;
                    }
                    int i = Integer.parseInt(args[0]);
                    if (i > -1 && i <= 4) {
                        if (Bukkit.getPlayer(args[1]) == null) {
                            Info.sendInfo((Player) sender, "§7Dieser Spieler ist nicht online.");
                            return;
                        }
                        if (i == 0) {
                            (Bukkit.getPlayer(args[1]).getPlayer()).setGameMode(GameMode.SURVIVAL);
                        } else if (i == 1) {
                            (Bukkit.getPlayer(args[1]).getPlayer()).setGameMode(GameMode.CREATIVE);
                        } else if (i == 2) {
                            (Bukkit.getPlayer(args[1]).getPlayer()).setGameMode(GameMode.ADVENTURE);
                        } else if (i == 3) {
                            (Bukkit.getPlayer(args[1]).getPlayer()).setGameMode(GameMode.SPECTATOR);
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
