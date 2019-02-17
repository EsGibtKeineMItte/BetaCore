package de.wk.betacore;

import de.wk.betacore.commands.bungee.PingCommand;
import de.wk.betacore.listener.Bungee.PingListenerB;
import net.md_5.bungee.api.plugin.Plugin;

public class BetaCoreBungee extends Plugin {


    private static BetaCoreBungee instance;

    public void regCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new PingCommand());
    }

    public void regListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PingListenerB());
    }

    @Override
    public void onEnable() {
        instance = this;
        regCommands();
        regListeners();
    }

    @Override
    public void onDisable() {

    }
    //


    public static BetaCoreBungee getInstance() {
        return instance;
    }
}
