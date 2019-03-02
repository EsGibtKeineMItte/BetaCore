package de.wk.betacore.commands.spigot;


import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.antilaggsystem.AntiLaggSystem;
import de.wk.betacore.util.data.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//alphacore.clearlagg

public class LagCommand implements CommandExecutor {
    ConfigManager cm = new ConfigManager();
    AntiLaggSystem as = new AntiLaggSystem();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("alphacore.clearlagg")) {
            sender.sendMessage(Misc.NOPERM);
            return false;
        }
        as.removeLaggs();
        sender.sendMessage(Misc.Prefix + "§7Die Laggs müssten jetzt reduziert worden sein.");
        return true;
    }
}