package de.wk.betacore.util.teamsystem;

import de.wk.betacore.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class TeamSystem {


    public void createTeam(String teamName, String kuerzel, Player teamAdmin) {
        ConfigManager cm = new ConfigManager();
    if(!(cm.getTeams().getList(teamName) == null )){
        System.out.println("Das Team konnte nicht erstellt werden, da es bereits existiert");
        return;
    }
    ArrayList<Object> teamAdmins = new ArrayList<>();
    teamAdmins.add(teamAdmin.getUniqueId().toString());
    cm.getTeams().setList(teamName + "admins", teamAdmins);
    cm.getTeams().setInt(teamName + ".wsrank", -1);

    cm.getTeams().save();

    }

    public void addTeammember(String teamName, Player player) {
        ConfigManager cm = new ConfigManager();
      if(cm.getTeams().getList(teamName + ".admins") == null){
          System.out.println("Dem Team konnten keine Member hinzugefügt werden, da es nicht existiert");
          return;
      }
      ArrayList<String> teamMembers = new ArrayList<String>();
      teamMembers.add(player.getUniqueId().toString());
      cm.getTeams().setList(teamName + ".members", teamMembers);
    }

    public void removeTeammembe(String Teamname, Player player) {

    }

    public void promoteUser(String Teamname, Player player) {

    }


}
