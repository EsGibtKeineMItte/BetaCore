package de.wk.betacore.util.teamsystem;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.util.data.Misc;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.*;


public class TeamSystem {


    static Json teams = FileManager.getTeams();
    Json playerData = FileManager.getPlayerData();


    public boolean createTeam(final String teamName, final String kuerzel, final OfflinePlayer teamAdmin) {
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
        teams.set(teamName + ".teamrank", getActiveTeams().size());//PrivateFight *3 + wonEvents*5 + wonpublicfights
        teams.set(teamName + ".wonPublicFights", 0);
        teams.set(teamName + ".wonPrivateFights", 0);
        teams.set(teamName + ".wonEvents", 0);
        teams.set(teamName + ".elo", 0);
        teams.set(teamName + ".world", null);
        teams.set(teamName + ".teamws", null);
        teams.set(teamName + ".members", new ArrayList<String>());
        teams.set(teamName + ".invitations", new ArrayList<String>());

        ArrayList<String> activeTeams = getActiveTeams();

        activeTeams.add(teamName);

        teams.set("activeTeams", activeTeams);


        System.out.println(teamName + kuerzel + teamAdmin.getName());
        BetaCore.debug("Das Team " + teamName + " wurde erstellt.");
        return true;
    }


    public static int calculateElo(String teamName) {
        if (!(isActiveWarShipTeam(teamName))) {
            return 0;
        }
        int pub = teams.getInt(teamName + ".wonPublicFights");
        int priv = teams.getInt(teamName + ".wonPrivateFights");
        int events = teams.getInt(teamName + ".wonEvents");

        if (priv == 0) {
            return pub;
        }

        if (events == 0) {
            return pub * (priv * 3);
        }
        return pub * (priv * 3) * (events * 5);
    }


    public static Map<String, Integer> getTeamList() {
        HashMap<String, Integer> teamMap = new HashMap<>();
        for(String teamName: getActiveTeams()){
            teamMap.put(teamName, teams.getInt(teamName + ".elo"));
        }
        return sortByValue(teamMap);
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


    public  void joinTeam(String teamName, Player player) {
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

        player.sendMessage(Misc.PREFIX + "§aDu bist dem Team §6" + teamName + " §aerfolgreich beigetreten");

    }




    public static ArrayList<String> getActiveTeams() {

        return new ArrayList<>(teams.getStringList("activeTeams"));

    }


    public static void remove(String teamName) {
        ArrayList<String> activeTeams = getActiveTeams();
        activeTeams.remove(teamName);
        teams.set("activeTeams", activeTeams);
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
    public void invitePlayer(String teamName, OfflinePlayer player) {
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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}


