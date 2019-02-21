package de.wk.betacore.util.teamsystem;

import de.butzlabben.world.config.MessageConfig;
import de.butzlabben.world.wrapper.SystemWorld;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.CheckForNull;

public class WorldSystemUtil {
    ConfigManager cm = new ConfigManager();
    TeamSystem teamSystem = new TeamSystem();
    private SystemWorld teamWorld;


    public void createWorldForTeam(String teamName) {
        if (cm.getTeams().getString(teamName + ".world") != null) {
            return;
        }
        cm.getTeams().setBoolean(teamName + ".hasWorld", true);
        teamWorld = SystemWorld.getSystemWorld(teamName + ".World");
    }
}
