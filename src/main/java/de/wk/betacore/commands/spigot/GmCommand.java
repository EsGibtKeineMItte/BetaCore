package de.wk.betacore.commands.spigot;


import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.misc;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
betacore.gm.self
betacore.gm.other
 */

public class GmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigManager cm = new ConfigManager();

        if (!(sender instanceof Player)) {
            System.out.println("Du kannst diesen Befehl nicht in der Konsole verwenden");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            if (player.hasPermission("betacore.gm.self") || player.hasPermission("betacore.*")) {
                if (!(StringUtils.isNumeric(args[0]))) {
                    player.sendMessage(misc.getPREFIX() + "§cDiesen GameMode gibt es nicht.");
                    return true;
                }
                int action = Integer.parseInt(args[0]);
                switch (action) {
                    case 0:
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(misc.getPREFIX() + "§aDu bist nun im §6Survival-Modus");
                        break;
                    case 1:
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(misc.getPREFIX() + "§aDu bist nun im §6Kreativ-Modus");
                        break;
                    case 2:
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(misc.getPREFIX() +"§aDu bist num im §6Adventur-Modus");
                        break;
                    case 3:
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(misc.getPREFIX() + "§aDu bist nun im §6Spectator-Modus");
                        break;

                    default:
                        player.sendMessage(misc.getPREFIX() +" : §6/gamemode <0/1/2/3> [Spieler]");
                        break;
                }
            } else {
                player.sendMessage("§cDu hast nicht die benötigten Rechte, um diesen Befehl auszuführen");
            }
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (player.hasPermission("betacore.gm.other") || player.hasPermission("betacore.*")) {
                if (target != null) {
                    int action = 0;
                    try {
                        action = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(misc.getPREFIX() + "§cDiesen GameMode gibt es nicht.");
                    }

                    switch (action) {
                        case 0:
                            target.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(misc.getPREFIX() + "§aDu hast GameMode des Spielers geändert");
                            target.sendMessage(misc.getPREFIX() + "§aDu bist nun im §6Survival-Modus");
                            break;
                        case 1:
                            target.setGameMode(GameMode.CREATIVE);
                            player.sendMessage(misc.getPREFIX() + "§aDu hast GameMode des Spielers geändert");
                            target.sendMessage(misc.getPREFIX() + "§aDu bist nun im §6Kreativ-Modus");
                            break;
                        case 2:
                            target.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage(misc.getPREFIX() + "§aDu hast GameMode des Spielers geändert");
                            target.sendMessage(misc.getPREFIX() +"§aDu bist num im §6Adventur-Modus");
                            break;
                        case 3:
                            target.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage(misc.getPREFIX() + "§aDu hast GameMode des Spielers geändert");
                            target.sendMessage(misc.getPREFIX() + "§aDu bist nun im §6Spectator-Modus");
                            break;

                        default:
                            player.sendMessage(misc.getPREFIX() + "Benutzung: : §6/gamemode <0/1/2/3> [Spieler]");
                            break;
                    }
                }
            } else {
                player.sendMessage(misc.getNOPERM());
            }
        } else {
            player.sendMessage(misc.getPREFIX() + "§7Benutzung: §6/gamemode §7<0/1/2/3§7> [Spieler]");
        }
        return false;
    }
}
