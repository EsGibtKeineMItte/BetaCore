package de.wk.betacore;

import de.wk.betacore.util.misc.CommandRemover;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetaCore extends JavaPlugin {

    private static BetaCore instance;
    private void removeCommands() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            try {
                CommandRemover.removeAll("tell", "libsdisguises:", "turnier", "bukkit:pl",
                        "bukkit:plugins", "ver", "bukkit:ver", "bukkit:seed", "bukkit:msg", "bukkit:w",
                        "bukkit:tell", "bukkit:list", "version", "bukkit:version", "?", "bukkit:?", "help", "me",
                        "bukkit:help", "minecraft:help", "about", "bukkit:about", "icanhasbukkit", "me", "msg",
                        "bukkit:kill", "bukkit:me", "plugins", "minecraft:me", "eval", "evaluate", "solve", "calc",
                        "calculate", "/eval", "/evaluate", "/solve", "/calc", "/calculate", "w", "minecraft:w", "list", "minecraft:list");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 30);
    }



    @Override
    public void onEnable() {
        instance = this;
        removeCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static  BetaCore getInstance() {
        return instance;
    }
}
