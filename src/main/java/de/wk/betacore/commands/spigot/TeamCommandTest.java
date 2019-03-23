package de.wk.betacore.commands.spigot;

import de.wk.betacore.objects.WarPlayer;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
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
    ThunderFile teams = FileManager.getTeams();
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
    /team getworld 1
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
        WarPlayer wp = new WarPlayer(player.getUniqueId(), player.getName());


        if (args.length == 0) {
            StringUtils.centerText("§dTeamSystem - BetaCore");
            sender.sendMessage("§6/team info §7<§6Teamname§7> §7Zeigt die Informationen über dein oder ein anderes WarShip-Team an.");
            sender.sendMessage("§6/team list §7Zeigt alle aktiven WarShip-Teams an.");
            sender.sendMessage("§6/team join §7<§6Teamname§7> §7Trete einem Team bei.");
            sender.sendMessage("§6/team invite §7<§6Spieler§7> §7Läd einen Spieler in dein WarShip-Team ein");
            sender.sendMessage("§6/team leave §7Verlässt ein WarShip-Team");
            sender.sendMessage("§6/team gs §7Teleportiert dich zu deinem Teamgrundstück.");
            sender.sendMessage("§6/team getworld §7Erstellt deinem WarShip-Team ein Teamgrundstück");
            /*
            help
            info
            list
            gs
            getworld
            join
            invite
            leave
             */


        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {
                if (TeamSystem.isActiveWarShipTeam(wp.getTeamName())) {
                    player.sendMessage("§7Team:" + wp.getTeamName());
                    player.sendMessage("§7Admin:" + wp.getTeam(player.getUniqueId()).getTeamAdmin().getName());
                    player.sendMessage("§7");
                    return false;
                    //INFOS
                } else {
                    player.sendMessage(Misc.PREFIX + "§7Du bist in keinem WarShip-Team.");
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!(sender.hasPermission("betacore.teamlist") || sender.hasPermission("betacore.*"))) {
                    sender.sendMessage(Misc.NOPERM);
                    return false;
                }
                sender.sendMessage("§7Aktive WarShip Teams:");
                for (String wst : TeamSystem.getActiveTeams()) {
                    String index = String.valueOf(TeamSystem.getActiveTeams().indexOf(wst) + 1);
                    sender.sendMessage("§6" + index + "§7: " + wst);
                }
            } else if (args[0].equalsIgnoreCase("gs")) {
                if (TeamSystem.isActiveWarShipTeam(wp.getTeamName())) {
                    sender.sendMessage("Diese Funktion kommt bald.");
                    return false;
                }

            } else if (args[0].equalsIgnoreCase("leave")) {
                player.sendMessage("Diese Funktion kommt bald");
                return false;
            } else if (args[0].equalsIgnoreCase("getworld")) {
                player.sendMessage("Dieser Command kommt noch.");
            } else {
                sender.sendMessage("§7Nutze §6/team§7, um Hilfe zu erhalten");
            }

        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("join")) {
                if (TeamSystem.isActiveWarShipTeam(wp.getTeamName())) {
                    player.sendMessage("§7Du bist bereits in einem WarShip Team.");
                    return false;
                }
                if (!(TeamSystem.isActiveWarShipTeam(args[1]))) {
                    player.sendMessage("§cDieses WarShip Team existiert nicht.");
                    return false;
                }
                TeamSystem ts = new TeamSystem();
                ts.joinTeam(args[1], player);
                player.sendMessage("§7Du bist dem Team§6 " + args[1] + " §7 beigetreten.");
            } else if (args[0].equalsIgnoreCase("challenge")) {
                player.sendMessage("Dieser Command kommt noch.");
            } else {
                player.sendMessage("§7Nutze §6/team§7, um Hilfe zu erhalten");
            }
        }


        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("create")) {
                Team team = new Team(args[1], args[2], player);
                player.sendMessage(Misc.getPREFIX() + "§7Du hast das Team §6" + args[1] + " §7mit dem Kürzel §6" + args[2] + "§7 erstellt.");
                return false;
            } else {
                player.sendMessage("§6/team für alle TeamCommands.");
            }
        }
        return false;
    }
}
