package de.wk.betacore.util.teamsystem;

import de.butzlabben.world.wrapper.SystemWorld;
import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;

public class WorldSystemUtil {

    Json teams = FileManager.getTeams();

    public boolean createWorldForTeam(String teamName) {
        if (teams.getString(teamName + ".world") != null) {
            return false;
        }
        SystemWorld teamWorld = SystemWorld.getSystemWorld(teamName + "World");
        return true;
    }


}
