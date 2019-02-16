package de.wk.betacore;

import de.wk.betacore.commands.spigot.commandmanager.CommandManager;
import de.wk.betacore.listener.Spigot.DataSetter;
import de.wk.betacore.listener.Spigot.JoinHandler;
import de.wk.betacore.listener.Spigot.MessageSend;
import de.wk.betacore.listener.Spigot.WorldSystemUtil;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.misc;
import de.wk.betacore.util.misc.CommandRemover;
import de.wk.betacore.util.travel.ArenaCommand;
import de.wk.betacore.util.travel.BauCommand;
import de.wk.betacore.util.travel.FastTravelSystem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetaCore extends JavaPlugin {


      /*
    <AlphaCore a core plugin for minecraft server.>
    Copyright (C) <2018>  <linksKeineMitte, YoyoNow, Chaoschaot>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
            (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
   */

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


    public void regCommands(){
    }

    public void regListeners(){
        Bukkit.getPluginManager().registerEvents(new DataSetter(), this);
        Bukkit.getPluginManager().registerEvents(new MessageSend(), this);
        Bukkit.getPluginManager().registerEvents(new JoinHandler(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new FastTravelSystem());
    }


    @Override
    public void onEnable() {
        instance = this;
        ConfigManager cm = new ConfigManager();
        CommandManager commandManager = new CommandManager();
        commandManager.setup();
        // EVENTS so just the talk walk and so on!
        // COMMANDS so the commands and so on

        regCommands();
        regListeners();


        removeCommands();
        cm.setup();

        if (!cm.getConfig().getBoolean("useAsBauServer")) {
            getCommand("bau").setExecutor(new BauCommand());
        } else {
            this.getServer().getPluginManager().registerEvents(new WorldSystemUtil(), this);
        }
        if (!cm.getConfig().getBoolean("useAsArena")) {
            getCommand("arena").setExecutor(new ArenaCommand());
            //   getCommand("a").setExecutor(new ArenaCommand());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static BetaCore getInstance() {
        return instance;
    }
}
