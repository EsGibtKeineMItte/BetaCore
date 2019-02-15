package de.wk.betacore;

import org.bukkit.plugin.java.JavaPlugin;

public final class BetaCore extends JavaPlugin {

    private static BetaCore instance;




    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static  BetaCore getInstance() {
        return instance;
    }
}
