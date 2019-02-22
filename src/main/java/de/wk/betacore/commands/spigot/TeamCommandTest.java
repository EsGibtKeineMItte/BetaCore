package de.wk.betacore.commands.spigot;

import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.teamsystem.Team;
import de.wk.betacore.util.teamsystem.TeamSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TeamCommandTest implements CommandExecutor {
    TeamSystem teamSystem = new TeamSystem();

    /*
          0      1
    /team info <eigenes|anderes>
    /team join <Teamname>
    /team leave
    /team setadmin <Spielername>
    /team getworld
    /team challenge <teamname> -> Wenn angenommen wird zu einen privaten Arena gesendet.
           0          1
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {

        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {

            }

        }

        if (args.length == 2) {

        }


        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (teamSystem.getTeamFromPlayer(player) == null) {
                player.sendMessage(Misc.getPREFIX() + "Du bist in keinem WarShip Team");
                return false;
            }

            player.sendMessage(Misc.getPREFIX() + "§7Du bist im Team§6 " + teamSystem.getTeamFromPlayer(player));
        }

        if (args.length == 3) {
            //Team create abstract_artZ aaZ
            //      0        1          2
            Team team = new Team(args[1], args[2], player);
            player.sendMessage(Misc.getPREFIX() + "§7Du hast das Team §6" + args[1] + " §7mit dem Kürzel §6" + args[2] + "§7 erstellt.");

        }
        return false;
    }
}
