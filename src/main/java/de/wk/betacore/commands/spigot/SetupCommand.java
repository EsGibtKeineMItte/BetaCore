package de.wk.betacore.commands.spigot;

import de.wk.betacore.datamanager.PlayerDataFactory;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.teamsystem.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/*
betacore.debug
betacore.setupplayer
 */

public class SetupCommand implements CommandExecutor, PlayerDataFactory{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§7Benutzung: §6/setup §7<§6Spieler§7>");
            return false;
        }
        if(!(sender.hasPermission("betacore.setupplayer") && sender.hasPermission("betacore.debug") &&sender.hasPermission("betacore.*")))

        if (Bukkit.getOfflinePlayer(args[1]) == null) {
            sender.sendMessage("§cDieser Spieler existiert nicht.");
            return false;
        }
        setupPlayer(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Bukkit.getOfflinePlayer(args[0]).getName());
        sender.sendMessage(Misc.PREFIX + "Du die Daten für den Spieler " +  Bukkit.getOfflinePlayer(args[0]).getName() + " erfolgreich gesetzt" );
        return false;
    }

    @Override
    public int getWsRank(UUID uuid) {
        return 0;
    }

    @Override
    public int getMoney(UUID uuid) {
        return 0;
    }

    @Override
    public int getFights(UUID uuid) {
        return 0;
    }

    @Override
    public Rank getRank(UUID uuid) {
        return null;
    }

    @Override
    public String getFirstJoin(UUID uuid) {
        return null;
    }

    @Override
    public String getLastJoin(UUID uuid) {
        return null;
    }

    @Override
    public String getName(UUID uuid) {
        return null;
    }

    @Override
    public Team getTeam(UUID uuid) {
        return null;
    }

    @Override
    public boolean isBanned(UUID uuid) {
        return false;
    }

    @Override
    public boolean isMuted(UUID uuid) {
        return false;
    }

    @Override
    public void unmute(UUID uuid) {

    }

    @Override
    public void unban(UUID uuid) {

    }
}
