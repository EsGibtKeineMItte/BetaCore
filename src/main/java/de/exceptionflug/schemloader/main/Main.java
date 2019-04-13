package de.exceptionflug.schemloader.main;

import com.minnymin.command.CommandFramework;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.ConnectionHolder;
import lombok.Getter;
import net.thecobix.brew.main.Brew;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Map;


public class Main  {

    @Getter
    private static ConnectionHolder connectionHolder;


    public static final String SCHEM_DIR = Environment.getPathToDataFolder() + "/schematics/";
    public static String prefix = "§8[§3Schematic§8] §7";

    public static Brew brew;

    private int runnerTask;

    private CommandFramework framework;

//    @Override
//    public void onEnable() {
//
//        System.out.println("Plugin activated!");
//        framework = new CommandFramework(this);
//        framework.registerCommands(new CommandSchematic());
//        framework.registerCommands(new CommandSchemloader());
//
//        System.out.print(prefix + "JZ MYSQL MEIN FREUND");
//
//        connectionHolder = new ConnectionHolder();
//        //GEHEIM NICHT KLAUEN
//
////	this.framework.registerHelp();
//
//        brew = Brew.getBrew();
//    }

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


   public static synchronized Main getInstance(){
        return new Main();
   }

    public static boolean isAllowed(int id) {
        switch (id) {
            case 166:
                return false;

            case 137:
                return false;

            case 120:
                return false;

            case 145:
                return false;

            case 116:
                return false;

            case 130:
                return false;

            case 10:
                return false;

            case 11:
                return false;

            case 122:
                return false;

            case 121:
                return false;

            case 138:
                return false;

            case 52:
                return false;

            case 79:
                return false;

            case 46:
                return false;

            case 325:
                return false;

            default:
                return true;
        }
    }

}
