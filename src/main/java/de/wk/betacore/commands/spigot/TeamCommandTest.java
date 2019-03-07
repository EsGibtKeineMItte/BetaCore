package de.wk.betacore.commands.spigot;

import de.wk.betacore.objects.WarPlayer;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.DataManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.misc.StringUtils;
import de.wk.betacore.util.teamsystem.Team;
import de.wk.betacore.util.teamsystem.TeamSystem;
import io.bluecube.thunderbolt.io.ThunderFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TeamCommandTest implements CommandExecutor {
    TeamSystem teamSystem = new TeamSystem();
    ThunderFile teams = DataManager.getTeams();
/*
betacore.teamlist
 */


    /*
          0      1
    /team info <eigenes|anderes> 2
    /team join <Teamname> 2
    /team create Teamname shortname 3
    /team leave 1
    /team setadmin <Spielername> 2
    /team getworld 2
    /team challenge <teamname> -> Wenn angenommen wird zu einen privaten Arena gesendet. 2
    /team buy gs
    /team gs
           0          1
           3
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigManager cm = new ConfigManager();
        if (!(sender instanceof Player)) {
            sender.sendMessage(Misc.getNOTINCONSOLE());
            return false;
        }
        Player player = (Player) sender;
        WarPlayer wp = new WarPlayer(player.getUniqueId());


        if (args.length == 0) {
            StringUtils.centerText("§dTeamSystem - BetaCore");
            sender.sendMessage("§6/team §7Zeigt diese Nachricht an.");


        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {
                if (TeamSystem.isActiveWarShipTeam(wp.getTeam())) {
                    player.sendMessage(Misc.PREFIX + "§7Du bist im WarShip-Team §6 " + wp.getTeam() + "§7.");
                } else {
                    player.sendMessage(Misc.PREFIX + "§7Du bist in keinem WarShip-Team.");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!(sender.hasPermission("betacore.teamlist") || sender.hasPermission("betacore.*"))) {
                    sender.sendMessage(Misc.NOPERM);
                    return false;
                }
                sender.sendMessage(StringUtils.centerText("Aktive WarShip Teams:"));
                for (String wst : TeamSystem.getActiveTeams()) {
                    sender.sendMessage(wst);
                }
            } else if (args[0].equalsIgnoreCase("gs")) {
                if (TeamSystem.isActiveWarShipTeam(wp.getTeam())) {
                    sender.sendMessage("Diese Funktion kommt bald.");
                }

            } else {
                sender.sendMessage("§7Nutze §6/team§7, um Hilfe zu erhalten");
            }

        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("join")) {
                if (TeamSystem.isActiveWarShipTeam(wp.getTeam())) {
                    sender.sendMessage("§7Du bist bereits in einem WarShip Team.");
                    return false;
                }
                TeamSystem ts = new TeamSystem();

                sender.sendMessage("§7Du bist dem Team§6 " + args[1] + " §7 beigetreten.");
            }

        }


        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) {
                //Team create abstract_artZ aaZ
                //      0        1          2
                Team team = new Team(args[1], args[2], player);
                player.sendMessage(Misc.getPREFIX() + "§7Du hast das Team §6" + args[1] + " §7mit dem Kürzel §6" + args[2] + "§7 erstellt.");
            }
        }
        return false;
    }
}
