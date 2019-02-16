package de.wk.betacore.commands.spigot.manager;

import de.wk.betacore.appearance.Info;
import de.wk.betacore.util.data.misc;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager {

    static ArrayList<CommandInterface> commands = new ArrayList<>();

    public static void addCommand(CommandInterface ci) {
        commands.add(ci);
    }

    public static void removeCommand(CommandInterface ci) {
        commands.remove(ci);
    }

    public static ArrayList<CommandInterface> getCommands() {
        return commands;
    }

    public static void executeCommand(CommandSender commandSender, String cmd, String[] args) {
        Boolean executable = true;
        CommandInterface command = null;
        for (CommandInterface ci : commands) {
            if (executable && cmd.equals(ci.getName())) {
                executable = false;
                command = ci;
            }
        }
        if (executable) {
            Info.sendInfo((Player) commandSender, "&cUnbekannter Befehl!");
            return;
        }
        if (!(commandSender instanceof Player) && !(command.inConsole())) {
            commandSender.sendMessage(misc.getNOTINCONSOLE());
            return;
        }
        if (args.length == 0) {
            if (command.getInfo().length() > 0) {
                Info.sendInfo((Player) commandSender, "&c" + command.getInfo());
                return;
            }
            if (command.getSubCommands().length == 0) {
                command.run(commandSender, args);
                return;
            }
            command.run(commandSender, args);
        } else {
            Boolean isSubCommand = true;
            for (String st : command.getSubCommands()) {
                if (isSubCommand && st.equals(args[0])) {
                    isSubCommand = false;
                }
            }
            if (isSubCommand) {
                command.run(commandSender, args);
                return;
            }
            String subcmd = command.getName() + " " + args[0];
            Boolean subExecutable = true;
            CommandInterface subCommand = null;
            for (CommandInterface ci : commands) {
                if (subExecutable && subcmd.equals(ci.getName())) {
                    subExecutable = false;
                    subCommand = ci;
                }
            }
            if (!(subExecutable)) {
                Info.sendInfo((Player) commandSender, "&cUnbekannter Befehl!");
                return;
            }
            if (!(commandSender instanceof Player) && !(subCommand.inConsole())) {
                commandSender.sendMessage("Nicht in der Console erlaubt");
                return;
            }
            if (args.length == 0) {
                if (command.getInfo().length() > 0) {
                    Info.sendInfo((Player) commandSender, "&c" + command.getInfo());
                    return;
                }
                command.run(commandSender, args);
            }
        }
    }

}
