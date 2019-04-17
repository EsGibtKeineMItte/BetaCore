package de.wk.betacore.commands.spigot;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.datamanager.PlayerDataFactory;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;


public class BanCommand {
    private final Json data = FileManager.getPlayerData();

    @Command(name = "ban", description = "Bannt einen Spieler", permission = "betacore.bann")
    public final void onBan(final CommandArgs args) {
        if (args.length() != 1) {
            args.getSender().sendMessage(Misc.PREFIX + "§7Benutzung: §6/ban §7<§6Spieler§7>");
            return;
        }
        if (Bukkit.getOfflinePlayer(args.getArgs(0)) == null) {
            args.getSender().sendMessage(Misc.PREFIX + "§cDieser Spieler existiert nicht.");
            return;
        }
        PlayerDataFactory.ban(Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId());
        if(Bukkit.getPlayer(args.getArgs(0)) != null){
            Bukkit.getPlayer(args.getArgs(0)).kickPlayer("Du wurdest gebannt");
        }
    }

}
