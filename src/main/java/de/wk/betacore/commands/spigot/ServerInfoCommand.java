package de.wk.betacore.commands.spigot;

import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.misc.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.management.ManagementFactory;

public class ServerInfoCommand implements CommandExecutor {
    ConfigManager cm = new ConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        long usage = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory();
        String server = ChatColor.GRAY + Bukkit.getServer().getServerName();

        sender.sendMessage(StringUtils.centerText(server));
        sender.sendMessage("");

        if (cm.getConfig().getBoolean("useAsBauServer")) {
            sender.sendMessage("§8Type: Bau");

        } else if (cm.getConfig().getBoolean("useAsArena")) {
            sender.sendMessage("§8Type: Arena");

        } else if (cm.getConfig().getBoolean("useAsLobby")) {
            sender.sendMessage("§8Type: Lobby");
        } else {
            sender.sendMessage("§8Type: Special");
        }
        sender.sendMessage("§8Arbeitsspeicher: §6" + humanReadableByteCount(usage, true) + " / " + humanReadableByteCount(Runtime.getRuntime().maxMemory(), true) + " / " + humanReadableByteCount(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax(), true));
        sender.sendMessage("§8Spieler: §6" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
        return false;
    }

    public String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
