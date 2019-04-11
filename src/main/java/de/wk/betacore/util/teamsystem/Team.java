package de.wk.betacore.util.teamsystem;

import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.FileManager;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Team extends TeamSystem {

    Json teams = FileManager.getTeams();

    @Getter
    private String teamName, shortName, dateOfCreation, world;
    @Getter
    private int rank, wonPrivateFights, wonPublicFights, wonEvents, elo;
    @Getter
    private List<String> teamMembers;
    @Getter
    private UUID teamAdmin;



    public Team(String teamName, String shortName, Player teamAdmin) {
        createTeam(teamName, shortName, teamAdmin);
        this.teamName = teamName;
        this.shortName = shortName;
        this.teamAdmin = teamAdmin.getUniqueId();
        this.teamMembers = getTeamMembers(teamName);
        this.dateOfCreation = teams.getString(teamName + ".dateofCreation");
        this.rank = teams.getInt(teamName + ".teamrank");
        this.wonPrivateFights = teams.getInt(teamName + ".wonPrivateFights");
        this.wonPublicFights = teams.getInt(teamName + ".wonPublicFights");
        this.wonEvents = teams.getInt(teamName + ".wonEvents");
        this.world = teams.getString(teamName + ".world");
        this.teamMembers = teams.getStringList(teamName + ".members");

        calculateElo(teamName);

        this.elo = teams.getInt(teamName + ".elo");
    }

    public Team(String teamName) {
        if (!(teamExists(teamName))) {
            throw new NullPointerException("Hier wurde versucht, auf ein Team zu referenzieren, dass nicht existiert.");
        }
        this.teamName = teamName;
        this.shortName = teams.getString(teamName + ".short");
        this.dateOfCreation = teams.getString(teamName + ".dateofCreation");
        this.rank = teams.getInt(teamName + ".teamrank");
        this.wonPrivateFights = teams.getInt(teamName + ".wonPrivateFights");
        this.wonPublicFights = teams.getInt(teamName + ".wonPublicFights");
        this.wonEvents = teams.getInt(teamName + ".wonEvents");
        this.world = teams.getString(teamName + ".world");
        this.teamName = teamName;
        this.teamAdmin = UUID.fromString(teams.getString(teamName + ".admin"));
        this.teamMembers = teams.getStringList(teamName + ".members");

        calculateElo(teamName);

        this.elo = teams.getInt(teamName + ".elo");
    }



    public void setShortName(String shortName) {
        this.shortName = shortName;
        teams.set(teamName + ".short", shortName);
    }


    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        teams.set(teamName + ".dateOfCreation", dateOfCreation);

    }


    public void setWorld(String world) throws IOException {
        this.world = world;
        teams.set(teamName + ".world", world);
    }


    public void setRank(int rank) throws IOException {
        this.rank = rank;
        teams.set(teamName + ".rank", rank);
    }

    public void setWonPrivateFights(int wonPrivateFights) {
        this.wonPrivateFights = wonPrivateFights;
        teams.set(teamName + ".wonPrivateFights", wonPrivateFights);
    }


    public void setWonPublicFights(int wonPublicFights) throws IOException {
        this.wonPublicFights = wonPublicFights;
        teams.set(teamName + ".wonPublicFights", wonPublicFights);
    }


    public void setWonEvents(int wonEvents) throws IOException {
        this.wonEvents = wonEvents;
        teams.set(teamName + ".wonEvents", wonEvents);
    }


    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
        teams.set(teamName + ".members", teamMembers);
    }


    public void setTeamAdmin(UUID uuid) {
        this.teamAdmin = teamAdmin;
        teams.set(teamName + ".admin", teamAdmin.toString());
    }
}
