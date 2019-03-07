package de.wk.betacore.util;

import de.wk.betacore.BetaCore;
import io.bluecube.thunderbolt.Thunderbolt;
import io.bluecube.thunderbolt.exceptions.FileLoadException;
import io.bluecube.thunderbolt.io.ThunderFile;
import java.io.IOException;


public class DataManager {
    ConfigManager cm = new ConfigManager();


    private static ThunderFile playerData;
    private static ThunderFile teams;

    public static void setup() throws IOException, FileLoadException {
        String pluginDatafolder = BetaCore.getInstance().getDataFolder().getAbsolutePath();
        String path = pluginDatafolder + "../../../Data";
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
                return teams;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileLoadException e) {
                e.printStackTrace();
            }
        }
        return teams;
    }

}
