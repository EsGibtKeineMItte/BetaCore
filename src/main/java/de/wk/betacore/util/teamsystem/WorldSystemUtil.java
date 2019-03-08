package de.wk.betacore.util.teamsystem;

import de.butzlabben.world.wrapper.SystemWorld;
import de.wk.betacore.datamanager.ConfigManager;
public class WorldSystemUtil {

    public boolean createWorldForTeam(String teamName) {
        ConfigManager cm = new ConfigManager();
        if (cm.getTeams().getString(teamName + ".world") != null) {
            return false;
        }
        SystemWorld teamWorld = SystemWorld.getSystemWorld(teamName + "World");
        return true;
    }


}
