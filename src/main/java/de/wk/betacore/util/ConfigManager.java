package de.wk.betacore.util;


import de.ops.alphacore.main.AlphaCoreS;
import de.wk.betacore.BetaCore;

import java.util.UUID;


public class ConfigManager {
    private Config config = new Config("config.yml", BetaCore.getInstance());
    private Config globalConfig = new Config("../../../Data/gconfig.yml", BetaCore.getInstance());
    private Config playerData = new Config("../../../Data/PlayerData.yml", BetaCore.getInstance());
    private Config teams = new Config("../../../Data/Teams.yml", BetaCore.getInstance());
    private Config permissions = new Config("../../../Data/Permissions.yml", BetaCore.getInstance());


    //
    public void setup() {

        if (!config.getBoolean("useGlobalConfig")) {
            config.setBoolean("useGlobalConfig", true);
        }
        if (!config.getBoolean("useDefaultChatFilter")) {
            config.setBoolean("useDefaultChatFilter", false);
        }
        if (!config.getBoolean("useAsBauServer")) {
            config.setBoolean("useAsBauServer", false);
        }
        if (!config.getBoolean("useAsArena")) {
            config.setBoolean("useAsArena", false);
        }

        if (!config.getBoolean("useAsLobby")) {
            config.setBoolean("useAsLobby", false);
        }
        if (!config.getBoolean("useAntiLaggSystem")) {
            config.setBoolean("useAntiLaggSystem", true);
        }
    }

    public String getMOTD(){
        if(config.getBoolean("use"))
    }





}