package de.wk.betacore.commands.spigot;


import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.antilaggsystem.AntiLaggSystem;
import de.wk.betacore.util.data.Misc;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//betacore.clearlagg

public class LagCommand implements CommandExecutor {
    ConfigManager cm = new ConfigManager();
    AntiLaggSystem as = new AntiLaggSystem();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("betacore.clearlagg") && sender.hasPermission("betacore.*"))) {
            sender.sendMessage(Misc.NOPERM);
            return false;
        }


        if (args.length == 0) {
            Bukkit.dispatchCommand(sender, "lagg info");
            StringBuilder sb = new StringBuilder(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: ");
            for (double tps : MinecraftServer.getServer().recentTps) {
                sb.append((tps));
                sb.append(", ");
            }
            sender.sendMessage(sb.substring(0, sb.length() - 2));
            return true;
        }
        if (args.length == 1) {
            if (args[1].equalsIgnoreCase("clear")) {
                as.removeLaggs();
                Bukkit.dispatchCommand(sender, "lagg unloadChunks");
                Bukkit.dispatchCommand(sender, "lagg clear");
            }else {

            }
        } else {
            sender.sendMessage("§7Benutung: §6/lagg §7<§6info|clear§7>");
        }
        return false;
    }
}