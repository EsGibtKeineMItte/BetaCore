package de.wk.betacore.util.teamsystem;

import de.wk.betacore.util.Config;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class TeamSystem {
    public void createTeam(String teamName, String kuerzel, Player teamAdmin) {
        ConfigManager cm = new ConfigManager();


        if (!(cm.getTeams().getList(teamName + ".admin") == null)) {
            System.out.println("Das Team konnte nicht erstellt werden, da es bereits existiert");
            return;
        }
        ArrayList<Object> teamAdmins = new ArrayList<>();
        teamAdmins.add(teamAdmin.getUniqueId().toString());
        cm.getPlayerData().setString(teamAdmin.getUniqueId().toString() + ".wsteam", teamName);
        cm.getTeams().setList(teamName + "admins", teamAdmins);
        cm.getTeams().setString(teamName + ".short", kuerzel);
        cm.getTeams().setInt(teamName + ".teamrank", -1);//PrivateFight *3 + wonEvents*5 + wonpublicfights
        cm.getTeams().setInt(teamName + ".wonPrivateFights", 0);
        cm.getTeams().setInt(teamName + ".wonPublicFights", 0);
        cm.getTeams().setInt(teamName + ".wonEvents", 0);

        cm.getTeams().save();

    }

    public void addTeammember(String teamName, Player player) {
        ConfigManager cm = new ConfigManager();
        if (cm.getTeams().getList(teamName + ".admins") == null) {
            System.out.println("Dem Team konnten keine Member hinzugefügt werden, da es nicht existiert");
            return;
        }
        cm.getPlayerData().setString(player.getUniqueId().toString() + ".wsteam", teamName);
        ArrayList<String> teamMembers = new ArrayList<String>();
        teamMembers.add(player.getUniqueId().toString());
        cm.getTeams().setList(teamName + ".members", teamMembers);
    }

    public void removeTeammembe(String teamName, Player player) {
        ConfigManager cm = new ConfigManager();
        ArrayList<String> teamMembers = new ArrayList<>();

        for (Object members : cm.getTeams().getList(teamName + ".members")) {
            teamMembers.add(members.toString());
        }
        teamMembers.remove(player.getUniqueId().toString());
        cm.getTeams().setList(teamName + ".members", teamMembers);
        cm.getTeams().save();
    }

    public void promoteUser(String Teamname, Player player) {


    }

    public String getTeamFromPlayer(Player player) {
        ConfigManager cm = new ConfigManager();
        return cm.getPlayerData().getString(player.getUniqueId().toString() + ".team");
    }


}
