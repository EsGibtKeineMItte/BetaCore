package de.wk.betacore.util.teamsystem;

import de.butzlabben.world.config.MessageConfig;
import de.butzlabben.world.wrapper.SystemWorld;
import de.wk.betacore.util.Config;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TeamSystem {
    ConfigManager cm = new ConfigManager();
    public void createTeam(String teamName, String kuerzel, Player teamAdmin) {
        ConfigManager cm = new ConfigManager();
        LocalDate dateOfTeamCreation = LocalDate.now();
        if (!(cm.getTeams().getList(teamName + ".admin") == null)) {
            System.out.println("Das Team konnte nicht erstellt werden, da es bereits existiert");
            return;
        }
        ArrayList<Object> teamAdmins = new ArrayList<>();
        teamAdmins.add(teamAdmin.getUniqueId().toString());
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

        teamAdmins.clear();
    }

    public void addTeammember(String teamName, Player player) {
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

    public void removeTeammember(String teamName, Player player) {
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
        if(cm.getTeams().getString(teamName) == null){
            System.out.println("Dieses Team existiert nicht");
            return;
        }
        ArrayList<String> invitations = new ArrayList<>();
        invitations.add(player.getUniqueId().toString());
        cm.getTeams().setList(teamName + ".invations", invitations);
        invitations.clear();
    }

    public boolean joinTeam(String teamName, Player player){
        ArrayList<String> invs = new ArrayList<>();
        if(!(cm.getTeams().getList(teamName + ".invations").contains(player.getUniqueId().toString()))){
            return false;
        }
        for(Object invitations: cm.getTeams().getList(teamName + ".invitations")){
            invs.add(invitations.toString());
        }
        cm.getTeams().setList(teamName + ".invitations", invs);
        addTeammember(teamName, player);
        return true;
    }
}
