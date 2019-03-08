package de.wk.betacore.util;

import de.wk.betacore.BetaCore;
import de.wk.betacore.environment.Environment;
import io.bluecube.thunderbolt.Thunderbolt;
import io.bluecube.thunderbolt.exceptions.FileLoadException;
import io.bluecube.thunderbolt.io.ThunderFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class DataManager {
    ConfigManager cm = new ConfigManager();


    private static ThunderFile playerData;
    private static ThunderFile teams;

    public static void setup() throws IOException, FileLoadException {
        String pluginDatafolder = BetaCore.getInstance().getDataFolder().getAbsolutePath();
        String path = Objects.requireNonNull(Environment.getCurrent()).getPathToDataFolder();
        BetaCore.debug(path);

        playerData = Thunderbolt.load("playerdata", path);
        teams = Thunderbolt.load("teams", path);
    }

    public static ThunderFile getPlayerData() {
        if(playerData == null){
            try {
                setup();
                return teams;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileLoadException e) {
                e.printStackTrace();
            }
        }
        return playerData;
    }

    public static ThunderFile getTeams() {
        if(teams == null){
            try {
                setup();
                if(teams.getStringList("activeTeams") == null){
                    setupTeamFile();
                }
                return teams;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileLoadException e) {
                e.printStackTrace();
            }
        }
        return teams;
    }

    public static void setupTeamFile(){
        ArrayList<String> activeWarShipTeams = new ArrayList<>();
        getTeams().set("activeTeams", activeWarShipTeams);
    }

}
