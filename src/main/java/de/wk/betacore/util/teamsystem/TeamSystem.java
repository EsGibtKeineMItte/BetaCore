package de.wk.betacore.util.teamsystem;


import de.wk.betacore.util.ConfigManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class TeamSystem {
    ConfigManager cm = new ConfigManager();



    public void createTeam(String teamName, Player teamAdmin){
        List<String> teamAdmins = new ArrayList<String>();
        teamAdmins.add(teamAdmin.getUniqueId().toString());




    }

    public void deleteTeam(String teamName){

    }

    public void addTeammember(String Teamname, Player player){

    }

    public void removeTeammembe(String Teamname, Player player){

    }

    public void promoteUser(String Teamname, Player player){

    }


}
