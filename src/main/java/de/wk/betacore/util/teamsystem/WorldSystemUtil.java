package de.wk.betacore.util.teamsystem;

import de.butzlabben.world.config.MessageConfig;
import de.butzlabben.world.wrapper.SystemWorld;
import de.wk.betacore.util.ConfigManager;

import javax.annotation.CheckForNull;

public class WorldSystemUtil {

    public void createWorldForTeam(String teamName) {
        ConfigManager cm = new ConfigManager();
        if (cm.getTeams().getString(teamName + ".world") != null) {
            return;
        }
        SystemWorld teamWorld = SystemWorld.getSystemWorld(teamName + "World");
    }


}
