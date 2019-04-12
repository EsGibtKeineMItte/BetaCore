package de.wk.betacore.commands.spigot;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.wk.betacore.BetaCore;
import de.wk.betacore.objects.WarPlayer;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.misc.StringUtils;
import de.wk.betacore.util.teamsystem.Team;
import de.wk.betacore.util.teamsystem.TeamSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamCommand {


    @Command(name = "team", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst"})
    public void onTeam(CommandArgs args) {

        CommandSender sender = args.getSender();

        StringUtils.centerText("§dTeamSystem - BetaCore");
        sender.sendMessage(Misc.PREFIX + "§6/team info §7<§6Teamname§7> §7Zeigt die Informationen über dein oder ein anderes WarShip-Team an.");
        sender.sendMessage(Misc.PREFIX + "§6/team list §7Zeigt alle aktiven WarShip-Teams an.");
        sender.sendMessage(Misc.PREFIX + "§6/team join §7<§6Teamname§7> §7Trete einem Team bei.");
        sender.sendMessage(Misc.PREFIX + "§6/team invite §7<§6Spieler§7> §7Läd einen Spieler in dein WarShip-Team ein");
        sender.sendMessage(Misc.PREFIX + "§6/team leave §7Verlässt ein WarShip-Team");
        sender.sendMessage(Misc.PREFIX + "§6/team gs §7Teleportiert dich zu deinem Teamgrundstück.");
        sender.sendMessage(Misc.PREFIX + "§6/team getworld §7Erstellt deinem WarShip-Team ein Teamgrundstück");
    }

    @Command(name = "team.info", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.info"}, inGameOnly = true)

    public void onInfo(CommandArgs args) {
        Player player = (Player) args.getSender();
        WarPlayer wp = new WarPlayer(player.getUniqueId(), player.getName());
        if (args.getArgs().length == 0) {
            if (!(args.getSender() instanceof Player)) {
                args.getSender().sendMessage(Misc.NOTINCONSOLE);
                return;
            }

            if (wp.isInWarShipTeam()) {
                player.sendMessage("§6Team: §7" + wp.getTeamName());
                player.sendMessage("§6Kürzel: §7" + wp.getTeam().getShortName());
                player.sendMessage("§6Admin: §7" + Bukkit.getOfflinePlayer(wp.getTeam().getTeamAdmin()).getName());
                player.sendMessage("§6Elo: §7" + wp.getTeam().getElo());
                player.sendMessage("§6Gewonnene:\n-öffentliche Kämpfe: §7" + wp.getTeam().getWonPublicFights());
                player.sendMessage("§6-private Kämpfe: §7" + wp.getTeam().getWonPublicFights());
                player.sendMessage("§6-Events: §7" + wp.getTeam().getWonPrivateFights());
                //INFOS
            } else {
                player.sendMessage(Misc.PREFIX + "§7Du bist in keinem WarShip-Team.");
            }
        } else if (args.getArgs().length == 1) {
            if (!(TeamSystem.isActiveWarShipTeam(args.getArgs(0)))) { args.getSender().sendMessage(Misc.PREFIX + "§cDieses Team existiert nicht");
                return;
            }
            Team checked = new Team(args.getArgs(0));
            player.sendMessage("§6Team: §7" + checked.getTeamName());
            player.sendMessage("§6Kürzel: §7" + checked.getShortName());
            player.sendMessage("§6Admin: §7" + Bukkit.getOfflinePlayer(checked.getTeamAdmin()).getName());
            player.sendMessage("§6Member: §7 ");
            player.sendMessage("§6Elo: §7" + checked.getElo());
            player.sendMessage("§6Gewonnene:\n-öffentliche Kämpfe: §7" + checked.getWonPublicFights());
            player.sendMessage("§6-private Kämpfe: §7" + checked.getWonPublicFights());
            player.sendMessage("§6-Events: §7" + checked.getWonPrivateFights());

        }
    }



    @Command(name = "team.list", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.list"})
    public void onList(CommandArgs args) {
        args.getSender().sendMessage("§7Aktive WarShip Teams:");
        int i = 1;
        for(String key: TeamSystem.getTeamList().keySet()){
            args.getSender().sendMessage(Misc.PREFIX + ChatColor.GOLD + i + ": " + key  + " §7- " + TeamSystem.getTeamList().get(key));
            i++;
        }
    }

    @Command(name = "team.leave", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.leave"})

    public void leave(CommandArgs args){
        if(!(args.getSender() instanceof Player)){
            args.getSender().sendMessage(Misc.NOTINCONSOLE);
            return;
        }
        Player player = (Player) args.getSender();
        WarPlayer wp = new WarPlayer(player.getUniqueId(), player.getName());

        if(!(wp.isInWarShipTeam())){
            player.sendMessage(Misc.PREFIX + "§cDu bist in keinem WarShipTeam, welches du verlassen könntest");
            return;
        }

        if(wp.getTeam().getTeamAdmin().toString().equals(wp.UUID)){
            player.sendMessage(Misc.PREFIX + "§7Du bist der Admin des Teams und kannst es nicht verlassen.\n§7Nutze §6/team delete§7 um dein Team zu löschen.");
            return;
        }

        wp.getTeam();
        TeamSystem.remove(wp.getTeamName());
    }


    @Command(name = "team.join", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.join"}, inGameOnly = true)
    public void onJoin(CommandArgs args){
        if(args.getArgs().length != 1){
            args.getSender().sendMessage(Misc.PREFIX + "§7Benutzung: §6/team join §7<§6Teamname§7>");
            return;
        }
        Player player = (Player) args.getSender();
        WarPlayer wp = new WarPlayer(player.getUniqueId(), player.getName());

        Team team = new Team(args.getArgs(0));

        team.joinTeam(args.getArgs(0), player);



    }

    @Command(name = "team.challenge", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.challenge"}, inGameOnly = true)
    public void onChallenge(CommandArgs args){

        if(args.getArgs().length != 1){
            args.getSender().sendMessage(Misc.PREFIX + "§7Benutzung: §6/team challenge §7<Teamname§7>");
            return;
        }if(!(TeamSystem.isActiveWarShipTeam(args.getArgs(0)))){
            args.getSender().sendMessage(Misc.PREFIX + "§7Dieses Team existiert nicht.");
            return;
        }

        args.getSender().sendMessage(Misc.PREFIX + "Dieser Befehl wird in kürze hinzugefügt.");

    }


    @Command(name = "team.create", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.create"}, inGameOnly = true)
    public void onCreate(CommandArgs args){
        Player player = (Player) args.getSender();
        WarPlayer wp = new WarPlayer(player.getUniqueId(), player.getName());

        if(args.getArgs().length != 2){
            player.sendMessage(Misc.PREFIX + "§7Benutzung: §6/team create §7<§6Teamname§7> §7<§6Kürzel§7>");
            return;
        }

        if(wp.isInWarShipTeam()){
            player.sendMessage(Misc.PREFIX + "§7Du bist bereits in einem WarShip-Team verlasse dieses, bevor du ein neues gründest.");
            return;
        }

        Team team = new Team(args.getArgs(0), args.getArgs(1), player);
        args.getSender().sendMessage((Misc.getPREFIX() + "§7Du hast das Team §6" + args.getArgs(0) + "§7#§2" + args.getArgs(1)) + " erstellt.");

    }

    @Command(name = "team.delete", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.delete"}, inGameOnly = true)

    public void onDelete(CommandArgs args){
        Player player = (Player) args.getSender();
        WarPlayer wp = new WarPlayer(player.getUniqueId(), player.getName());

        if(!(wp.isInWarShipTeam())){
            player.sendMessage(Misc.PREFIX + "§cDu bist in keinem Team, welches du verlassen löschen könntest.");
        }

        if(!(wp.getTeam().getTeamAdmin().toString().equals(wp.UUID))){
            player.sendMessage(Misc.PREFIX + "§7Nur der Teamadmin kann ein Team löschen.");
            return;
        }

        TeamSystem.remove(wp.getTeamName());

    }

    @Command(name = "team.invite", description = "Command, um Teams zu erstellen und zu verwalten", aliases = {"wst.invite"}, inGameOnly = true)

    public void onInvite(CommandArgs args){
        Player player = (Player) args.getSender();

        if(args.getArgs().length != 1){
            player.sendMessage(Misc.PREFIX + "§6Benutzung: §6/team invite §7<§6Spieler§7>");
            return;
        }
        WarPlayer wp = new WarPlayer(player.getUniqueId(), player.getName());
        if(!(wp.getTeam().getTeamAdmin().equals(wp.uuid))){
            player.sendMessage(Misc.PREFIX + "§cDu bist nicht der Admin des Teams und kannst auch keine Spieler einladen");
            return;
        }

        if(Bukkit.getOfflinePlayer(args.getArgs(0)) == null){
            player.sendMessage(Misc.PREFIX + "§cDieser Spieler existiert nicht.");
            return;
        }

        wp.getTeam().invitePlayer(wp.getTeamName(), Bukkit.getOfflinePlayer(args.getArgs(0))); //TODO Bessere Funktion zu verfügung stellen, die diesen Parameter nicht braucht! -> Neue Funktion, die diese nutzt in Teamklasse.

        player.sendMessage(Misc.PREFIX + "§aDu hast den Spieler erfolgreich eingeladen.");

    }







}
