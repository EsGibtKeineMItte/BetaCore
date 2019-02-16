package de.wk.betacore.commands.spigot;

import de.wk.betacore.commands.spigot.commandmanager.SubCommand;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetupCommand extends SubCommand {
    ConfigManager cm = new ConfigManager();

    RankSystem rankSystem = new RankSystem();

    @Override
    public void onCommand(Player player, String[] args) {
        if (!(player.hasPermission("alphacore.setup"))) {
            player.sendMessage(Misc.getPREFIX() + Misc.getNOPERM());
            return;
        }

        if (args.length != 1) {
            player.sendMessage(Misc.getPREFIX() + "§7Benutzung: §6/setup");
            return;
        }
        Bukkit.broadcastMessage(Misc.getPREFIX() + "§7Setze alle Ränge neu.");

        cm.getPlayerData().save();
        cm.getGlobalConfig().reload();

        for (Player p : Bukkit.getOnlinePlayers()) {
            rankSystem.setRank(p.getUniqueId(), rankSystem.getRank(p.getUniqueId()));
        }

        cm.getConfig().save();
        cm.getConfig().reload();


        return;
    }


    @Override
    public String name() {
        return "setup";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
