package de.wk.betacore.util.teamsystem;


import de.wk.betacore.util.ConfigManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class TeamSystem {
    ConfigManager cm = new ConfigManager();


    public void createTeam(String teamName, String kuerzel, Player teamAdmin) {
    if(!(cm.getTeams().getList(teamName) == null )){
        System.out.println("Das Team konnte nicht erstellt werden.");
    }

    }

    public void deleteTeam(String teamName) {

    }

    public void addTeammember(String Teamname, Player player) {

    }

    public void removeTeammembe(String Teamname, Player player) {

    }

    public void promoteUser(String Teamname, Player player) {

    }


}
