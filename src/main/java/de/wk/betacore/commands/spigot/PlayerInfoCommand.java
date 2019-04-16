package de.wk.betacore.commands.spigot;


import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.datamanager.PlayerDataFactory;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONException;

public class PlayerInfoCommand implements CommandExecutor {
    Json data = FileManager.getPlayerData();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigManager cm = new ConfigManager();

        if (!(sender.hasPermission("betacore.playerinfo") && sender.hasPermission("betacore.*"))) {
            sender.sendMessage(Misc.getNOPERM());
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(Misc.getPREFIX() + "§7Benutung : §6/pi §7<§6Spieler§7>");
            return false;
        }

        if (Bukkit.getOfflinePlayer(args[0]) == null) {
            sender.sendMessage("§cDieser Spieler existiert nicht");
            return false;
        }

        String uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
        try {
            sender.sendMessage(RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getColor() + Bukkit.getOfflinePlayer(args[0]).getName() + "§7:");
            sender.sendMessage("§6Rank: §7" + RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getColor() + RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()));
            sender.sendMessage("§6Erster Join: §7" + data.getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".firstjoin"));
            sender.sendMessage("§6Letzer Join: §7" + data.getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".lastjoin"));
            sender.sendMessage("§6Banned: " + (PlayerDataFactory.isBanned(Bukkit.getOfflinePlayer(args[0]).getUniqueId()) ? "§aTrue" : "§cFalse"));
            sender.sendMessage("§6Muted: " + (data.getBoolean(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".muted") ? "§aTrue" : "§cFalse"));
            sender.sendMessage("§6Server-Fights: §7" + data.getInt(uuid + ".fights"));
            sender.sendMessage("§6WSRank: §7 " + data.getInt(uuid + ".wsrank"));

            if (data.getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".wsteam") != null) {
                sender.sendMessage("§6WS-Team: §7" + data.getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".wsteam"));
            }
            sender.sendMessage("§6UUID: §7" + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
        } catch (JSONException e) {
            sender.sendMessage("§cDieser Spieler war noch nie auf unserem Netzwerk.");
        }
        return false;
    }


}
