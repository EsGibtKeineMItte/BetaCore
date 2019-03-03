package de.wk.betacore.commands.spigot;


import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.misc.StringUtils;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigManager cm = new ConfigManager();

        if (!(sender.hasPermission("betacore.playerinfo") || (!(sender.hasPermission("betacore.*"))))) {
            sender.sendMessage(Misc.getNOPERM());
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(Misc.getPREFIX() + "§7Benutung : §6/pi §7<§6Spieler§7>");
            return false;
        }

        if(Bukkit.getOfflinePlayer(args[0]) == null){
            sender.sendMessage("§cDieser Spieler existiert nicht");
            return false;
        }

        String playerName = ChatColor.GRAY +  Bukkit.getOfflinePlayer(args[0]).getName() + ":";

        sender.sendMessage(playerName);
        sender.sendMessage("");

        sender.sendMessage("§6UUID: §7" + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
        sender.sendMessage("§6Rank: §7" + RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getColor() + RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()));
        if (cm.getPlayerData().getBoolean(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".muted")) {
            sender.sendMessage("§6Muted: §aTRUE");
        } else {
            sender.sendMessage("§6Muted: §cFALSE");
        }
        sender.sendMessage("§6First Join: §7" + cm.getPlayerData().getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".firstjoin"));
        sender.sendMessage("§6Last Join: §7" + cm.getPlayerData().getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".lastjoin"));
        if (cm.getPlayerData().getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".wsteam") != null) {
            sender.sendMessage("§6WS-Team: §7 " + cm.getPlayerData().getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".wsteam"));
        }
        return false;
    }
}
