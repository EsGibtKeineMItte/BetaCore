package de.wk.betacore.datamanager;


import de.wk.betacore.environment.EnvironmentManager;
import io.bluecube.thunderbolt.Thunderbolt;
import io.bluecube.thunderbolt.exceptions.FileLoadException;
import io.bluecube.thunderbolt.io.ThunderFile;
import java.io.IOException;
import java.util.ArrayList;



public class
FileManager {
    private static ThunderFile playerData;
    private static ThunderFile teams;
    ConfigManager cm = new ConfigManager();

    private FileManager(){

    }





    public static void setup() throws IOException, FileLoadException {
        String path = EnvironmentManager.getPathToDataFolder();
        playerData = Thunderbolt.load("playerdata", path);
        teams = Thunderbolt.load("teams", path);
    }

    public static ThunderFile getPlayerData() {
        if (playerData == null) {
            try {
                setup();
                return teams;
            } catch (IOException | FileLoadException e) {
                e.printStackTrace();
            }
        }
        return playerData;
    }

    public static ThunderFile getTeams() {
        if (teams == null) {
            try {
                setup();
                if (teams.getStringList("activeTeams") == null) {
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

    public static void setupTeamFile() {
        ArrayList<String> activeWarShipTeams = new ArrayList<>();
        getTeams().set("activeTeams", activeWarShipTeams);
    }

    public static void unloadFiles(){
        Thunderbolt.unload("playerdata");
        Thunderbolt.unload("teams");
    }

}
