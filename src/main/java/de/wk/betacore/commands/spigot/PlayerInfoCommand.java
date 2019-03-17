package de.wk.betacore.commands.spigot;


import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.misc.StringUtils;
import de.wk.betacore.util.ranksystem.RankSystem;
import io.bluecube.thunderbolt.io.ThunderFile;
import io.bluecube.thunderbolt.org.json.JSONException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerInfoCommand implements CommandExecutor {
    ThunderFile data = FileManager.getPlayerData();

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

        String playerName = ChatColor.GRAY + Bukkit.getOfflinePlayer(args[0]).getName() + ":";
        String uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
        try {

            sender.sendMessage(RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getColor() + playerName + "§7:");

            sender.sendMessage("§6Rank: §7" + RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getColor() + RankSystem.getRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId()));
            sender.sendMessage("§6Erster Join: §7" + data.getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".firstjoin"));
            sender.sendMessage("§6Letzer Join: §7" + data.getString(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".lastjoin"));
            if (data.getBoolean(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".muted")) {
                sender.sendMessage("§6Muted: §aTRUE");

            } else {
                sender.sendMessage("§6Muted: §cFALSE");
            }

            if (data.getBoolean(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString() + ".banned")) {
                sender.sendMessage("§6Banned: §aTRUE");
            } else {
                sender.sendMessage("§6Muted: §cFALSE");
            }


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
