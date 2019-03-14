package de.wk.betacore;

import de.wk.betacore.commands.spigot.*;
import de.wk.betacore.commands.spigot.commandmanager.CommandManagerOld;
import de.wk.betacore.datamanager.DataManager;
import de.wk.betacore.environment.EnvironmentManager;
import de.wk.betacore.listener.Spigot.RecordListener;
import de.wk.betacore.listener.Spigot.*;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.misc.CommandRemover;
import de.wk.betacore.util.ranksystem.PermissionManager;
import de.wk.betacore.util.travel.ArenaCommand;
import de.wk.betacore.util.travel.BauCommand;
import de.wk.betacore.util.travel.FastTravelSystem;
import de.wk.betacore.util.travel.LobbyCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

public final class BetaCore extends JavaPlugin {


      /*
    <AlphaCore a core plugin for minecraft server.>
    Copyright (C) <2018>  <linksKeineMitte, YoyoNow, Chaoschaot>
    CommandRemover by Exceptionflug.
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
                        "bukkit:tell", "bukkit:list", "version", "bukkit:version", "?", "bukkit:?", "me",
                        "bukkit:help", "minecraft:help", "about", "bukkit:about", "icanhasbukkit", "me", "msg",
                        "bukkit:kill", "bukkit:me", "plugins", "minecraft:me", "eval", "evaluate", "solve", "calc",
                        "calculate", "/eval", "/evaluate", "/solve", "/calc", "/calculate", "w", "minecraft:w", "list", "minecraft:list");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 30);
    }


    public void regCommands() {
        getCommand("money").setExecutor(new Money());
        getCommand("core").setExecutor(new Core());
        getCommand("gm").setExecutor(new Gm());
        getCommand("pc").setExecutor(new PcCommand());
        getCommand("team").setExecutor(new TeamCommandTest());
        getCommand("pi").setExecutor(new PlayerInfoCommand());
        getCommand("cc").setExecutor(new CustomCommand());
        getCommand("addperm").setExecutor(new ManPermissionAdder());
        getCommand("si").setExecutor(new ServerInfoCommand());
        getCommand("pl").setExecutor(new PluginCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("rl").setExecutor(new ReloadCommand());
        getCommand("lag").setExecutor(new LagCommand());
        getCommand("dev").setExecutor(new TeamSzoneServer());
        getCommand("setup").setExecutor(new SetupCommand());
    }

    public void regListeners() {
        Bukkit.getPluginManager().registerEvents(new DataSetter(), this);
        Bukkit.getPluginManager().registerEvents(new MessageSend(), this);
        Bukkit.getPluginManager().registerEvents(new JoinHandler(), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomCommand(), this);
        Bukkit.getPluginManager().registerEvents(new PermissionListener(), this);
        this.getServer().getPluginManager().registerEvents(RecordListener.getInstance(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BUNGEECORD");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BUNGEECORD", new FastTravelSystem());
    }


    @Override
    public void onEnable() {

        log("§6Enabling BetaCore " + Misc.CODENAME + "v." + Misc.VERSION + "...");
        EnvironmentManager.setSpigot(true);

        log("§6Setting up command-framework... ");
        instance = this;
        ConfigManager cm = new ConfigManager();
        CommandManagerOld commandManager = new CommandManagerOld();
        commandManager.setup();
        CommandImplementer.implementCommands();
        log("§aDONE");

        log("§3Registering Commands & Listeners...");
        regCommands();
        regListeners();
        removeCommands();
        log("§aDONE");

        log("§3Setting up Files... ");
        cm.setup();
        cm.setupMySQL();
        log("§aDONE");

        log("§3Establishing MySQL Connection...");
        MySQL mySQL = new MySQL();

        try {
            mySQL.openConnection();
            System.out.println("MySQL Connection erfolgreich.");

        } catch (SQLException x) {
            log("§4FAILED");
            System.out.println("");
            x.printStackTrace();
        }
        log("§aDONE");


        log("§3Setting up Permissions");
        PermissionManager.setupPermissionConfig();
        log("§aDONE");

        log("§3Getting links though servers");
        if (!cm.getConfig().getBoolean("useAsBauServer")) {
            getCommand("bau").setExecutor(new BauCommand());
        } else {
            this.getServer().getPluginManager().registerEvents(new WorldSystemUtil(), this);
            log("§3Using the server as building server");
        }

        if (!cm.getConfig().getBoolean("useAsArena")) {
            getCommand("arena").setExecutor(new ArenaCommand());
        } else {
            log("§3Using the server as arena");
        }

        if (!cm.getConfig().getBoolean("useAsLobby")) {
            getCommand("l").setExecutor(new LobbyCommand());
            getCommand("hub").setExecutor(new LobbyCommand());
        } else {
            Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
            log("Using the server as lobby server");
        }

        log("§aDone");

        log("§6Successfully enabled BetaCore" + Misc.CODENAME + "v." + Misc.VERSION + ".");


        //  Objects.requireNonNull(Environment.getCurrent()).restartDaily();
    }

    @Override
    public void onDisable() {
        log("§3Unloading Files...");
        DataManager.unloadFiles();

        log("§3Successfully disabled " + Misc.CODENAME + "v." + Misc.VERSION + ".");
    }


    public static BetaCore getInstance() {
        return instance;
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Misc.CONSOLEPREFIX + message);
    }

    public static void debug(String message) {
        Bukkit.getConsoleSender().sendMessage(Misc.CONSOLEPREFIX + "[§eDEBUG]" + message);

    }
}
