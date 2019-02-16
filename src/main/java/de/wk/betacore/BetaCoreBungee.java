package de.wk.betacore;

import net.md_5.bungee.api.plugin.Plugin;

public class BetaCoreBungee extends Plugin {


    private static BetaCoreBungee instance;

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {

    }
    //


    public static BetaCoreBungee getInstance() {
        return instance;
    }
}
