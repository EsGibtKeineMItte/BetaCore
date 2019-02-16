package de.wk.betacore.commands.spigot.commandmanager;

import de.wk.betacore.BetaCore;
import de.wk.betacore.commands.spigot.SetRankCommand;
import de.wk.betacore.commands.spigot.SetupCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CommandManagerOld implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();


    public CommandManagerOld() {
    }

    //Sub Commands
    public String main = "core";
    public String info = "info";

    public void setup() {
        BetaCore.getInstance().getCommand(main).setExecutor(this);
        this.commands.add(new SetRankCommand());
        this.commands.add(new SetupCommand());

//        this.commands.add(new InfoCommand());
//        this.commands.add(new HelpCommand());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Du kannst diesen Befehl nicht in der Konsole verwenden");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase(main)) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Nutze /help für Hilfe");
                return true;
            }

            SubCommand target = this.get(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Invalid subcommand");
                return true;
            }

            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));
            arrayList.remove(0);

            try {
                target.onCommand(player, args);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "An error has occurred.");

                e.printStackTrace();
            }
        }

        return true;
    }

    private SubCommand get(String name) {
        Iterator<SubCommand> subcommands = this.commands.iterator();

        while (subcommands.hasNext()) {
            SubCommand sc = (SubCommand) subcommands.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }

            }
        }
        return null;
    }
}
