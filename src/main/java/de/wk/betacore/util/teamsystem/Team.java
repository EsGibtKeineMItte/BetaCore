package de.wk.betacore.util.teamsystem;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.DataManager;
import io.bluecube.thunderbolt.io.ThunderFile;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Getter
public class Team extends TeamSystem {

    ThunderFile teams = DataManager.getTeams();

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
        this.shortName = teams.getString(teamName + ".short");
        this.dateOfCreation = teams.getString(teamName + ".dateofCreation");
        this.rank = teams.getInt(teamName + ".rank");
        this.wonPrivateFights = teams.getInt(teamName + ".wonPrivateFights");
        this.wonPublicFights = teams.getInt(teamName + ".wonPublicFights");
        this.wonEvents = teams.getInt(teamName + ".wonEvents");
        this.world = teams.getString(teamName + ".world");
        this.teamName = teamName;
    }


    public void setShortName(String shortName) {
        this.shortName = shortName;
        teams.set(teamName + ".short", shortName);
        try {
            teams.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        teams.set(teamName + ".dateOfCreation", dateOfCreation);
        try {
            teams.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setWorld(String world) throws IOException {
        this.world = world;
        teams.set(teamName + ".world", world);

        teams.save();

    }


    public void setRank(int rank) throws IOException {
        this.rank = rank;
        teams.set(teamName + ".rank", rank);

        teams.save();
    }

    public void setWonPrivateFights(int wonPrivateFights) {
        this.wonPrivateFights = wonPrivateFights;
        teams.set(teamName + ".wonPrivateFights", wonPrivateFights);
        try {
            teams.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setWonPublicFights(int wonPublicFights) throws IOException {
        this.wonPublicFights = wonPublicFights;
        teams.set(teamName + ".wonPublicFights", wonPublicFights);
        teams.save();
    }


    public void setWonEvents(int wonEvents) throws IOException {
        this.wonEvents = wonEvents;
        teams.set(teamName + ".wonEvents", wonEvents);
        teams.save();
    }


    public void setTeamMembers(List<String> teamMembers) throws IOException {
        this.teamMembers = teamMembers;
        teams.set(teamName + ".members", teamMembers);
        teams.save();
    }


    public void setTeamAdmin(Player teamAdmin) throws IOException {
        this.teamAdmin = teamAdmin;
        teams.set(teamName + ".admin", teamAdmin.getUniqueId().toString());
        teams.save();
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
