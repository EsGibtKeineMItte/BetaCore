package de.wk.betacore.util.teamsystem;

import de.butzlabben.world.wrapper.SystemWorld;
import de.wk.betacore.util.Config;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.ArrayList;

public class Team extends TeamSystem{
    /*
    arrayliste für alle teams: -> so kann man dann auch Teams löschen
     */


    ConfigManager cm = new ConfigManager();

    String teamName;
    String shortName;
    Player teamAdmin;

    /*
    Spieler zu welt des Teams teleportieren.

     */

    public Team(String teamName, String shortName, Player teamAdmin){
        this.teamName = teamName;
        this.shortName = shortName;
        this.teamAdmin = teamAdmin;
        createTeam(teamName, shortName, teamAdmin);
    }

    public void loadTeamWorld(Player p){
        getWorld().load(p);
    }
    public SystemWorld getWorld(){
        SystemWorld systemWorld = SystemWorld.getSystemWorld(teamName + ".World");
        if(systemWorld == null){
            System.out.println("Dieser Spieler ist in keinem WS Team.");
            return null;
        }

        return systemWorld;
    }

    public boolean hasWorld(String teamName){
        if(cm.getTeams().getBoolean(teamName + ".hasWorld")){
            return true;
        }
        return false;
    }
}
