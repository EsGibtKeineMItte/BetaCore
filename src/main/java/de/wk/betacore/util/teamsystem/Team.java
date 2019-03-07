package de.wk.betacore.util.teamsystem;

import de.wk.betacore.BetaCore;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team extends TeamSystem {

    private String teamName, shortName, dateOfCreation, world;
    private int rank, wonPrivateFights, wonPublicFights, wonEvents;
    private List<String> teamMembers = new ArrayList<>();
    private Player teamAdmin;

    public Team(String teamName, String shortName, Player teamAdmin) {


        createTeam(teamName, shortName, teamAdmin);
        this.teamName = teamName;
        this.shortName = shortName;
        this.teamAdmin = teamAdmin;
        this.teamMembers = getTeamMembers(teamName);
    }

    public Team(String teamName) {
        if (!(teamExists(teamName))) {
            BetaCore.debug("Hier wurde versucht, auf ein Team zu referenzieren, dass nicht existiert.");
            throw new NullPointerException();
        }
        this.teamName = teamName;
        this.shortName = cm.getTeams().getString(teamName + ".short");
        this.dateOfCreation = cm.getTeams().getString(teamName + ".dateofCreation");
        this.rank = cm.getTeams().getInt(teamName + ".rank");
        this.wonPrivateFights = cm.getTeams().getInt(teamName + ".wonPrivateFights");
        this.wonPublicFights = cm.getTeams().getInt(teamName + ".wonPublicFights");
        this.wonEvents = cm.getTeams().getInt(teamName + ".wonEvents");
        this.world = cm.getTeams().getString(teamName + ".world");
        this.teamName = teamName;
    }



    public void setShortName(String shortName) {
        this.shortName = shortName;
        cm.getTeams().setString(teamName + ".short", shortName);
    }


    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        cm.getTeams().setString(teamName + ".dateOfCreation", dateOfCreation);
    }


    public void setWorld(String world) {
        this.world = world;
        cm.getTeams().setString(teamName + ".world", world);
    }


    public void setRank(int rank) {
        this.rank = rank;
        cm.getTeams().setInt(teamName + ".rank", rank);
    }

    public void setWonPrivateFights(int wonPrivateFights) {
        this.wonPrivateFights = wonPrivateFights;
        cm.getTeams().setInt(teamName + ".wonPrivateFights", wonPrivateFights);
    }


    public void setWonPublicFights(int wonPublicFights) {
        this.wonPublicFights = wonPublicFights;
        cm.getTeams().setInt(teamName + ".wonPublicFights", wonPublicFights);
    }


    public void setWonEvents(int wonEvents) {
        this.wonEvents = wonEvents;
        cm.getTeams().setInt(teamName + ".wonEvents", wonEvents);
    }


    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
        cm.getTeams().setList(teamName + ".members", teamMembers);
    }


    public void setTeamAdmin(Player teamAdmin) {
        this.teamAdmin = teamAdmin;
        cm.getTeams().setString(teamName + ".admin", teamAdmin.getUniqueId().toString());
    }




    public String getTeamName() {
        return teamName;
    }

    public String getShortName() {
        return shortName;
    }


    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getWorld() {
        return world;
    }

    public int getRank() {
        return rank;
    }

    public int getWonPublicFights() {
        return wonPublicFights;
    }

    public int getWonPrivateFights() {
        return wonPrivateFights;
    }

    public int getWonEvents() {
        return wonEvents;
    }

    public Player getTeamAdmin() {
        return teamAdmin;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }






/*

 */




}
