package de.wk.betacore.util.teamsystem;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team extends TeamSystem {
    public String teamName, shortName;
    public List<String> teamMembers = new ArrayList<>();
    public Player teamAdmin;


    public Team(String teamName, String shortName, Player teamAdmin) {
        createTeam(teamName, shortName, teamAdmin);
        this.teamName = teamName;
        this.shortName = shortName;
        this.teamAdmin = teamAdmin;
        this.teamMembers = getTeamMembers(teamName);
    }

    public Team(String teamName){

        /*
         cm.getPlayerData().setString(teamAdmin.getUniqueId().toString() + ".wsteam", teamName);
        cm.getPlayerData().save();
        cm.getTeams().setList(teamName + ".admins", teamAdmins);
        cm.getTeams().setString(teamName + ".short", kuerzel);
        cm.getTeams().setString(teamName + ".dateOfCreation", dateOfTeamCreation.toString());
        cm.getTeams().setInt(teamName + ".teamrank", -1);//PrivateFight *3 + wonEvents*5 + wonpublicfights
        cm.getTeams().setInt(teamName + ".wonPrivateFights", 0);
        cm.getTeams().setInt(teamName + ".wonPublicFights", 0);
        cm.getTeams().setInt(teamName + ".wonEvents", 0);
        cm.getTeams().setString(teamName + ".world", null);
        cm.getTeams().save();
         */


     this.teamName = teamName;

    }

}
