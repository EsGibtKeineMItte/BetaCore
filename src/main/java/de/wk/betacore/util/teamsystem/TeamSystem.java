package de.wk.betacore.util.teamsystem;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TeamSystem {


    Json cfg;

    static Json teams = FileManager.getTeams();
    Json playerData = FileManager.getPlayerData();


    public boolean createTeam(String teamName, String kuerzel, OfflinePlayer teamAdmin) {
        ConfigManager cm = new ConfigManager();
        LocalDate dateOfTeamCreation = LocalDate.now();

        if (teamExists(teamName)) {
            BetaCore.debug("Es wurde gerade versucht, ein Team zu erstellen, welches bereits existiert: " + teamName);
            return false;
        }

        if (teamAdmin == null) {
            throw new NullPointerException("Fehler 004: Der TeamAdmin ist nicht online/existiert nicht -> ist null");
        }
        playerData.set(teamAdmin.getUniqueId().toString() + ".wsteam", teamName);

        teams.set(teamName + ".admin", teamAdmin.getUniqueId().toString());
        teams.set(teamName + ".short", kuerzel);
        teams.set(teamName + ".dataOfCreation", dateOfTeamCreation.toString());
        teams.set(teamName + ".teamrank", -1);//PrivateFight *3 + wonEvents*5 + wonpublicfights
        teams.set(teamName + ".wonPublicFights", 0);
        teams.set(teamName + ".wonPrivateFights", 0);
        teams.set(teamName + ".wonEvents", 0);
        teams.set(teamName + ".world", null);
        teams.set(teamName + ".teamws", null);

        ArrayList<String> activeTeams = getActiveTeams();

        activeTeams.add(teamName);

        teams.set("activeTeams", activeTeams);


        System.out.println(teamName + kuerzel + teamAdmin.getName());
        BetaCore.debug("Das Team " + teamName + " wurde erstellt.");
        return true;
    }

    public void addTeamMember(String teamName, Player player) {
        if (!(teamExists(teamName))) {
            throw new NullPointerException("Das Team existiert nicht.");
        }
        if (player == null) {
            throw new NullPointerException("Der Spieler ist null");
        }
        playerData.set(player.getUniqueId() + ".wsteam", teamName);
        ArrayList<String> teamMembers = new ArrayList<String>(teams.getStringList(teamName + ".members"));
        teamMembers.add(player.getUniqueId().toString());
        teams.set(teamName + ".members", teamMembers);
        teamMembers.clear();

    }


    public List<String> getTeamMembers(String teamName) {

        return teams.getStringList(teamName + ".members");
    }

    public void removeTeammember(String teamName, Player player) {
        ArrayList<String> teamMembers = new ArrayList<>(teams.getStringList(teamName + ".members"));
        teamMembers.remove(player.getUniqueId().toString());
        teams.set(teamName + ".members", teamMembers);
        teamMembers.clear();
    }

    public void promoteUser(String Teamname, Player player) {


    }

    public String getTeamFromPlayer(Player player) {

        return playerData.getString(player.getUniqueId().toString() + ".wsteam");

    }


    public void joinTeam(String teamName, Player player) {
        if (!(teamExists(teamName))) {
            throw new NullPointerException("Es wurde versucht, einem Team beizutreten, welches nicht existiert.");
        }
        if (player == null) {
            throw new NullPointerException("Der Spieler, der versucht hat, dem Team " + teamName + " beizutreten, existiert nicht.");
        }
        ArrayList<String> invitations = new ArrayList<>(teams.getStringList(teamName + ".invitations")); //Kann die null sein?
        if (!(invitations.contains(player.getUniqueId().toString()))) {
            player.sendMessage("Du bist nicht in dieses Team eingeladen.");
            return;
        }
        invitations.remove(player.getUniqueId().toString());
        addTeamMember(teamName, player);

    }

    public static ArrayList<String> getActiveTeams() {

        return new ArrayList<>(teams.getStringList("activeTeams"));

    }


    public static boolean isActiveWarShipTeam(String teamName) {
        if (teamName == null) {
            return false;
        }
        return getActiveTeams().contains(teamName);
    }


    public boolean teamExists(String teamName) {
        return getActiveTeams().contains(teamName);
    }


    //Invitations
    public void invitePlayer(String teamName, Player player) {
        if (!(teamExists(teamName))) {
            throw new NullPointerException("Das Team " + teamName + " existiert nicht.");
        }
        ArrayList<String> invitations = new ArrayList<>(teams.getStringList(teamName + ".invitations"));
        invitations.add(player.getUniqueId().toString());

        teams.set(teamName + "invitations", invitations);

    }

    public List<String> getInvitations(String teamName) {

        return teams.getStringList(teamName + ".invitations");

    }
}


