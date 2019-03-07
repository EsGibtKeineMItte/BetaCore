package de.wk.betacore.util.teamsystem;

import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.DataManager;
import io.bluecube.thunderbolt.io.ThunderFile;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TeamSystem {
    ThunderFile cfg;

    ThunderFile teams = DataManager.getTeams();
    ThunderFile playerData = DataManager.getPlayerData();


    public void createTeam(String teamName, String kuerzel, Player teamAdmin) {
        ConfigManager cm = new ConfigManager();
        LocalDate dateOfTeamCreation = LocalDate.now();

        if (teamExists(teamName)) {
            BetaCore.debug("Es wurde gerade versucht, ein Team zu erstellen, welches bereits existiert: " + teamName);
            return;
        }

        if (teamAdmin == null) {
            throw new NullPointerException("Fehler 004: Der TeamAdmin ist nicht online/existiert nicht -> ist null");
        }

        playerData.set(teamName + ".wsteam", teamName);

        teams.set(teamName + ".admin", teamAdmin.getUniqueId().toString());
        teams.set(teamName + ".short", kuerzel);
        teams.set(teamName + ".dataOfCreation", dateOfTeamCreation.toString());
        teams.set(teamName + ".teamrank", -1);//PrivateFight *3 + wonEvents*5 + wonpublicfights
        teams.set(teamName + "wonPublicFights", 0);
        teams.set(teamName + ".wonPrivateFights", 0);
        teams.set(teamName + ".wonEvents", 0);
        teams.set(teamName + ".world", null);
        teams.set(teamName + ".teamws", null);

        ArrayList<String> activeTeams = getActiveTeams();

        activeTeams.add(teamName);

        teams.set("activeTeams", teamName);

        try {
            playerData.save();
            teams.save();
        } catch (IOException e) {
            BetaCore.debug("Es ist ein Fehler beim kreieren des Teams " + teamName + " ausgetreten.");
            e.printStackTrace();
        }

    }
    /*
    DataSetter -> Teams erstellen.
     */

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
        try {
            teams.save();
            playerData.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getTeamMembers(String teamName) {
        return teams.getStringList(teamName + ".members");
    }

    public void removeTeammember(String teamName, Player player) {
        ArrayList<String> teamMembers = new ArrayList<>(teams.getStringList(teamName + ".members"));
        teamMembers.remove(player.getUniqueId().toString());
        teams.set(teamName + ".members", teamMembers);
        teamMembers.clear();

        try {
            teams.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void promoteUser(String Teamname, Player player) {


    }

    public String getTeamFromPlayer(Player player) {
        return playerData.getString(player.getUniqueId().toString() + ".wsteam");
    }

    public void invitePlayer(String teamName, Player player) {
     if(!(teamExists(teamName))){
         throw new NullPointerException("Das Team " + teamName + " existiert nicht.");
     }
     ArrayList<String> invitations = new ArrayList<>(teams.getStringList(teamName + ".invitations"));
     invitations.add(player.getUniqueId().toString());

     teams.set(teamName + "invitations", invitations);

        try {
            teams.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void joinTeam(String teamName, Player player) {
        if (!(teamExists(teamName))) {
            throw new NullPointerException("Es wurde versucht, einem Team beizutreten, welches nicht existiert.");
        }
        if (player == null) {
            throw new NullPointerException("Der Spieler, der versucht hat, dem Team " + teamName + " beizutreten, existiert nicht.");
        }
        ArrayList<String> invitations = new ArrayList<>(teams.getStringList(teamName + ".invitations")); //Kann die null sein?
        invitations.remove(player.getUniqueId().toString());
        //addTeamMember(teamName, player)

    }


    public ArrayList<String> getActiveTeams() {
        ArrayList<String> activeTeams = new ArrayList<>(teams.getStringList("activeTeams"));
        return activeTeams;
    }

    public boolean teamExists(String teamName) {
        return teams.getString(teamName + ".admin") == null || (!getActiveTeams().contains(teamName));
    }


    /*
       public void createTeam(String teamName, String kuerzel, Player teamAdmin) {
        ConfigManager cm = new ConfigManager();
        LocalDate dateOfTeamCreation = LocalDate.now();

        if (teamExists(teamName)) {
            if (teamAdmin == null) {
                BetaCore.debug("Hier wurde gerade versucht ein Team zu erstellen, dass es schon gibt, von einem Spieler, der nicht existiert?!");
                return;
            }
            teamAdmin.sendMessage(Misc.PREFIX + "§7Dieses Team existiert bereits.");
            return;
        }

        cm.getPlayerData().setString(teamAdmin.getUniqueId().toString() + ".wsteam", teamName);
        cm.getPlayerData().save();
        cm.getTeams().setString(teamName + ".admin", teamAdmin.getUniqueId().toString());
        cm.getTeams().setString(teamName + ".short", kuerzel);
        cm.getTeams().setString(teamName + ".dateOfCreation", dateOfTeamCreation.toString());
        cm.getTeams().setInt(teamName + ".teamrank", -1);//PrivateFight *3 + wonEvents*5 + wonpublicfights
        cm.getTeams().setInt(teamName + ".wonPrivateFights", 0);
        cm.getTeams().setInt(teamName + ".wonPublicFights", 0);
        cm.getTeams().setInt(teamName + ".wonEvents", 0);
        cm.getTeams().setString(teamName + ".world", null);
        cm.getTeams().setString(teamName + ".teamws", null);
        cm.getTeams().save();

        getActiveTeams().add(teamName);

    }

    public void addTeamMember(String teamName, Player player) {
        setup();
        if (cm.getTeams().getList(teamName + ".admins") == null) {
            System.out.println("Dem Team konnten keine Member hinzugefügt werden, da es nicht existiert");
            return;
        }
        cm.getPlayerData().setString(player.getUniqueId().toString() + ".wsteam", teamName);
        ArrayList<String> teamMembers = new ArrayList<String>();
        teamMembers.add(player.getUniqueId().toString());
        cm.getTeams().setList(teamName + ".members", teamMembers);
        teamMembers.clear();
    }

    public ArrayList<String> getTeamMembers(String teamName) {
        setup();
        ArrayList<String> teamMembers = new ArrayList<String>();

        for (Object obj : cm.getTeams().getList(teamName + ".members")) {
            teamMembers.add(obj.toString());
        }
        return teamMembers;
    }

    public void removeTeammember(String teamName, Player player) {
        setup();
        ArrayList<String> teamMembers = new ArrayList<>();

        for (Object members : cm.getTeams().getList(teamName + ".members")) {
            teamMembers.add(members.toString());
        }
        teamMembers.remove(player.getUniqueId().toString());
        cm.getTeams().setList(teamName + ".members", teamMembers);
        cm.getTeams().save();
        teamMembers.clear();
    }

    public void promoteUser(String Teamname, Player player) {


    }

    public String getTeamFromPlayer(Player player) {
        return cm.getPlayerData().getString(player.getUniqueId().toString() + ".wsteam");
    }

    public void invitePlayer(String teamName, Player player) {
        if (cm.getTeams().getString(teamName) == null) {
            System.out.println("Dieses Team existiert nicht");
            return;
        }
        ArrayList<String> invitations = new ArrayList<>();
        invitations.add(player.getUniqueId().toString());
        cm.getTeams().setList(teamName + ".invations", invitations);
        invitations.clear();
    }

    public boolean joinTeam(String teamName, Player player) {
        ArrayList<String> invs = new ArrayList<>();
        if (!(cm.getTeams().getList(teamName + ".invations").contains(player.getUniqueId().toString()))) {
            return false;
        }
        for (Object invitations : cm.getTeams().getList(teamName + ".invitations")) {
            invs.add(invitations.toString());
        }
        cm.getTeams().setList(teamName + ".invitations", invs);
        addTeamMember(teamName, player);
        return true;
    }

    public ArrayList<String> getActiveTeams() {
        ArrayList<String> activeTeams = new ArrayList<>();
        for (Object obj : cm.getTeams().getList("activeTeams")) {
            activeTeams.add(obj.toString());
        }
        cm.getTeams().setList("activeTeams", activeTeams);
        return activeTeams;
    }

     */
}
