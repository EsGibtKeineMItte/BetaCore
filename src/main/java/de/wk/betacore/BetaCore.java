package de.wk.betacore;

import com.minnymin.command.CommandFramework;
import de.exceptionflug.schemloader.cmd.CommandSchem;
import de.exceptionflug.schemorg.cmd.CommandSchemorg;
import de.exceptionflug.schemorg.main.Config;
import de.exceptionflug.schemorg.main.SchemOrg;
import de.leonhard.storage.Json;
import de.wk.betacore.commands.spigot.*;
import de.wk.betacore.commands.spigot.commandmanager.CommandManagerOld;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.listener.Spigot.*;
import de.wk.betacore.listener.Spigot.RecordListener;
import de.wk.betacore.util.ConnectionHolder;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.Synchronizor;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.misc.CommandRemover;
import de.wk.betacore.util.ranksystem.PermissionManager;
import de.wk.betacore.util.ranksystem.PermissionsListener;
import exceptionflug.schemloader.cmd.CommandSchemloader;
import lombok.Getter;
import net.thecobix.brew.main.Brew;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public final class BetaCore extends JavaPlugin {




      /*
    <BetaCore a core plugin for minecraft server.>
    Copyright (C) <2018>  <linksKeineMitte, YoyoNow, Chaoschaot>
    CommandRemover by Exceptionflug.

    Libarys's/API's used:

    MIT: Schemloader & SchemOrg: Copyright (c) 2019 Exceptionflug,EsGibtKeineMitte
    MIT: Lightningstorage: Copyright (c) 2019 Leonhard/EsGibtKeineMitte
    MIT: PluginLib: Copyright (c) 2019 Leonhard/EsGibtKeineMitte
    MIT: MIT-org.json Copyright (c) 2002 JSON.org
    MIT: YAMLBEANS - Copyright (c) 2008 Nathan Sweet, Copyright (c) 2006 Ola Bini


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
    @Getter
    private ConnectionHolder connectionHolder;
    public static Brew brew;


    private void regCommands() {
        getCommand("money").setExecutor(new Money());
        getCommand("core").setExecutor(new Core());
        getCommand("gm").setExecutor(new Gm());
        getCommand("pc").setExecutor(new PcCommand());
        getCommand("pi").setExecutor(new PlayerInfoCommand());
        getCommand("cc").setExecutor(new CustomCommand());
        getCommand("addperm").setExecutor(new ManPermissionAdder());
        getCommand("si").setExecutor(new ServerInfoCommand());
        getCommand("pl").setExecutor(new PluginCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("rl").setExecutor(new ReloadCommand());
        getCommand("lag").setExecutor(new LagCommand());
    }

    private void regListeners() {
        Bukkit.getPluginManager().registerEvents(new WorldSystemUtil(), this);
        Bukkit.getPluginManager().registerEvents(new DataSetter(), this);
        Bukkit.getPluginManager().registerEvents(new MessageSend(), this);
        Bukkit.getPluginManager().registerEvents(new JoinHandler(), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomCommand(), this);
        Bukkit.getPluginManager().registerEvents(new PermissionListener(), this);
        Bukkit.getPluginManager().registerEvents(new PermissionsListener("/help", "/bau", "/arena-1",
                "/arena-2", "/hub", "/l", "/r", "/msg", "/server bau", "/server Arena-1", "/server Arena-2",
                "/server Lobby-1", "/ws", "/wsk", "/fight", "/arena", "/bau get", "/bau home", "bau tp", "ws home", "ws get"), this);
        Bukkit.getPluginManager().registerEvents(new TNTTracer(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
        this.getServer().getPluginManager().registerEvents(RecordListener.getInstance(), this);
    }

    // Remove annoying Native MC Commands
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
    public void onLoad() {
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") == null) {
            Environment.setWorldedit(false);
            log("WorldEdit wurde nicht gefunden.");
        } else {
            log("WorldEdit wurde gefunden.");
            Environment.setWorldedit(true);
        }
        if (Bukkit.getPluginManager().getPermissionSubscriptions("Brew") == null) {
            Environment.setBrew(false);
            log("Brew wurde nicht gefunden.");
        } else {
            Environment.setBrew(true);
            log("Brew wurde gefunden.");
        }
    }

    @Override
    public void onEnable() {

        log(ChatColor.YELLOW + "[§eBetaCore " + ChatColor.GOLD + Misc.CODENAME + ChatColor.YELLOW + "v." + Misc.VERSION + "]");
        Environment.setSpigot(true);


        log("§3Setting up command-framework... ");
        instance = this;

        //TODO Use new Command-Manager
        CommandManagerOld commandManager = new CommandManagerOld();
        commandManager.setup();
        CommandImplementer.implementCommands();


        CommandFramework framework = new CommandFramework(this);
        framework.registerCommands(new Update());
        framework.registerCommands(new TeamCommand());
        framework.registerCommands(new TracerCommand());
        framework.registerCommands(new Update());
        framework.registerCommands(new BuildInformationCommand());
        framework.registerCommands(new BanCommand());
        framework.registerCommands(new MaintanceCommand());
        framework.registerCommands(new BetaCoreCommand());
        log("§aDONE");

        log("§3Registering Commands & Listeners...");
        regCommands();
        regListeners();
        removeCommands();

        log("§aDONE");
        ConfigManager cm = new ConfigManager();

        log("§3Setting up Files... ");
        cm.setup();
        cm.setupMySQL();
        cm.getConfig().setString("build", LocalDate.now().toString());
        cm.getConfig().save();
        SchemOrg.conf = new Config();
        SchemOrg.conf.initConfig();
        log("§aDONE");


        if (cm.getGlobalConfig().getBoolean("UseMySQL")) {
            log("§3Establishing MySQL Connection...");
            Synchronizor.synchronize();

            try {
                connectionHolder = new ConnectionHolder();
                connectionHolder.connect(Environment.getMySqlHost(), Environment.getMySqlDatabase(), Environment.getMySqlPort(), Environment.getMySqlUsername(), Environment.getMySqlPassword());

                MySQL.openConnection();
                System.out.println("MySQL Connection erfolgreich.");

            } catch (SQLException x) {
                log("§4FAILED");
                System.out.println("Verbindung nicht möglich!");
                System.out.print("SQLException: " + x.getMessage());
                System.out.print("SQLState: " + x.getSQLState());
                System.out.print("VendorError: " + x.getErrorCode());
                x.printStackTrace();
            }
            Environment.setMysql(true);
            log("§aDONE");
        } else {
            Environment.setMysql(false);//Neeeded? No:P
        }


        log("§3Setting up Permissions");
        PermissionManager.setupPermissionConfig();
        log("§aDONE");

        log("§3Getting links though servers");

        if(cm.getConfig().getBoolean("useAsLobby")){
            Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
        }

        if (!cm.getConfig().getBoolean("useAsBauServer")) {
            log("Using the server as normal server.");
        } else {

            log("§3Enabling SchemOrg");
            try {
                framework.registerCommands(new CommandSchemorg());
                Bukkit.getScheduler().runTaskTimerAsynchronously(this, new SchemOrg.FileCheckerRunnable(), 60, 60);
                Bukkit.getScheduler().runTaskTimer(this, new SchemOrg.NotifierRunnable(), 20 * 60 * 5, 20 * 60 * 5);
                Bukkit.getPluginManager().registerEvents(new SchemOrg(), this);
            } catch (Exception e) {
                log("§cFAILED");
                e.printStackTrace();
            }
            try {
                log("§3Enabling SchemLoader");
                if (!(Environment.isBrew()) || (!(Environment.isWorldedit()))) {
                    BetaCore.debug("Die benötigten Dependency's sind nicht installiert.");
                }
                brew = Brew.getBrew();
                renameCommand("/schematic", "/schematic_legacy");
                renameCommand("/schem", "/schem_legacy");
                getCommand("schem").setExecutor(new CommandSchem());
                framework.registerCommands(new CommandSchemloader());
            } catch (Exception e) {
                log("§cFAILED");
                e.printStackTrace();
            }
            log("§3Using the server as building server");
        }
        log("§aDone");

        log("§6Successfully enabled BetaCore" + Misc.CODENAME + "v." + Misc.VERSION + ".");
        log("");

        log("§7=====§eBUILDINFORMATIONEN§7======");


        log("");

        log("§eGlobal-Settings:");

        log("Plattform: " + (Environment.isSpigot() ? "§3Spigot" : "§3BungeeCord"));
        log("MySQL: " + (cm.getGlobalConfig().getBoolean("UseMySQL") ? "§atrue" : "§cfalse"));


        log("Chatfilter: " + (cm.getConfig().getBoolean("useDefaultChatFilter") ? "§atrue" : "§cfalse"));
        log("Anti-LaggSystem: " + (cm.getConfig().getBoolean("useAntiLaggSystem") ? "§atrue" : "§cfalse"));

        log("§eServer-Settings:");

        log("Nutzung als Bau-Server: " + (cm.getConfig().getBoolean("useAsBauServer") ? "§atrue" : "§cfalse"));
        log("Nutzung als Arena: " + (cm.getConfig().getBoolean("useAsArena") ? "§atrue" : "§cfalse"));
        log("Nutzung als Lobby: " + (cm.getConfig().getBoolean("useAsLobby") ? "§atrue" : "§cfalse"));

        log("§eDependency's:");
        log("Brew: " + (Environment.isBrew() ? "§atrue" : "§cfalse"));
        log("WorldEdit: " + (Environment.isWorldedit() ? "§atrue" : "§cfalse"));
    }

    @Override
    public void onDisable() {

        log("§3Successfully disabled " + Misc.CODENAME + "v." + Misc.VERSION + ".");
    }


    private void renameCommand(String string, String string2) throws Exception {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf(".") + 1);
        Class serverClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
        Field f1 = serverClass.getDeclaredField("commandMap");
        f1.setAccessible(true);
        SimpleCommandMap commandMap = (SimpleCommandMap) f1.get(Bukkit.getServer());
        Field f2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
        f2.setAccessible(true);
        Map<String, Command> knownCommands = (Map) f2.get(commandMap);
        Command cmd = knownCommands.get(string);
        knownCommands.remove(string);
        knownCommands.put(string2, cmd);
    }

    public static BetaCore getInstance() {
        return instance;
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Misc.CONSOLEPREFIX + message);
    }

    public static void log(String message, boolean chat) {
        Bukkit.getConsoleSender().sendMessage(Misc.CONSOLEPREFIX + message);
        if(chat){
            Bukkit.getServer().broadcastMessage(Misc.PREFIX + message);
        }
    }

    public static void debug(String message) {
        Bukkit.getConsoleSender().sendMessage(Misc.CONSOLEPREFIX + "[§eDEBUG§7]" + message);
    }

    public static void debug(String message, boolean chat) {
        Bukkit.getConsoleSender().sendMessage(Misc.CONSOLEPREFIX + "[§eDEBUG§7]" + message);
        if (chat) {
            Bukkit.getServer().broadcastMessage(Misc.PREFIX + "§8[§eDEBUG§8]§7" + message);
        }
    }



    public static void teleportSpawn(Player p) {
        Json spawn = FileManager.getSpawn();

        try {
            Location loc = new Location(Bukkit.getWorld(spawn.getString("Spawn.world")), spawn.getDouble("Spawn.x"), spawn.getDouble("Spawn.y"),
                    spawn.getDouble("Spawn.z"), (float) (spawn.getDouble("Spawn.yaw")), (float) spawn.getDouble("Spawn.pitch"));

            p.teleport(loc);
        }catch (Exception ignored){
        }
    }

    public static void setSpawn(Location loc) {
        BetaCore.debug("ID: " + Bukkit.getServerId() + " Name: " + Bukkit.getServer().getName());
        Json spawn = FileManager.getSpawn();

        spawn.set("Spawn.world", loc.getWorld().getName());

        spawn.set("Spawn.x", loc.getBlockX() + 0.01);
        spawn.set("Spawn.y", loc.getBlockY() + 0.01);
        spawn.set("Spawn.z", loc.getZ() + 0.01);
        spawn.set("Spawn.yaw", Math.round(loc.getYaw()) + 0.1);
        spawn.set("Spawn.pitch", Math.round(loc.getPitch()) + 0.1);
    }
}
