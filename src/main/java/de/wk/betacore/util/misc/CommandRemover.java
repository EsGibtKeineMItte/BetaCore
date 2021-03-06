package de.wk.betacore.util.misc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.Map;


public class CommandRemover {

    private static String packageName = Bukkit.getServer().getClass().getPackage().getName();
    private static String version = packageName.substring(packageName.lastIndexOf(".") + 1);

    public static void removeAll(String... cmds) throws Exception {
        for (int i = 0; i < cmds.length; i++) {
            removeCommand(cmds[i]);
        }
    }

    public static boolean removeCommand(String command) throws Exception {
        Class<?> serverClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");

        Field f1 = serverClass.getDeclaredField("commandMap");
        f1.setAccessible(true);
        SimpleCommandMap commandMap = (SimpleCommandMap) f1.get(Bukkit.getServer());

        Field f2 = SimpleCommandMap.class.getDeclaredField("knownCommands");
        f2.setAccessible(true);
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<String, Command> knownCommands = (Map) f2.get(commandMap);

        return knownCommands.remove(command.toLowerCase()) != null;
    }

    private CommandRemover() {}
}

