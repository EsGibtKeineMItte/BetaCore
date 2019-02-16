package de.wk.betacore.commands.spigot;

import de.wk.betacore.commands.spigot.commandmanager.SubCommand;
import de.wk.betacore.util.data.misc;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetRankCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        RankSystem rankSystem = new RankSystem();
        if (args[1].equalsIgnoreCase("USER")) {
            if (player.hasPermission("betacore.setrank.user") || player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.USER);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }

        } else if (args[1].equalsIgnoreCase("PREMIUM")|| player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
            if (player.hasPermission("betacore.setrank.premium")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.PREMIUM);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }
        } else if (args[1].equalsIgnoreCase("YOU_TUBER")) {
            if (player.hasPermission("betacore.setrank.youtuber")|| player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.YOU_TUBER);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }
        } else if (args[1].equalsIgnoreCase("ARCHI")) {
            if (player.hasPermission("betacore.setrank.archi")|| player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.ARCHI);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }
        } else if (args[1].equalsIgnoreCase("Supp")) {
            if (player.hasPermission("betacore.setrank.supp")|| player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.SUPPORTER);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }
        } else if (args[1].equalsIgnoreCase("Mod")) {
            if (player.hasPermission("betacore.setrank.mod")|| player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.MOD);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }
        } else if (args[1].equalsIgnoreCase("Dev")) {
            if (player.hasPermission("betacore.setrank.dev")|| player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.DEV);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }
        } else if (args[1].equalsIgnoreCase("Admin")) {
            if (player.hasPermission("betacore.setrank.admin")|| player.hasPermission("betacore.setrank.*") ||player.hasPermission("betacore.*")) {
                rankSystem.setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.ADMIN);
                player.sendMessage(misc.getPREFIX() + "§aRang geändert.");
            }
        } else {
            player.sendMessage(misc.getPREFIX() + "§cDiesen Rang gibt es nicht.");
        }
    }
    @Override
    public String name() {
        return "setrank";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
