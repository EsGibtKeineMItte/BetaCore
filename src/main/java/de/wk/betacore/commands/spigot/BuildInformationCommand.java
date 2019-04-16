package de.wk.betacore.commands.spigot;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.data.Misc;

public class BuildInformationCommand {


    @Command(name = "build", description = "Command, um sich die Informationen, zum aktuellen BetaCore-build ausgaben zu lassen", permission = "betacore.build")
    public void build(CommandArgs args){
        if(args.length() != 0){
            args.getSender().sendMessage(Misc.PREFIX + "§7Benutzung: §6/build");
            return;
        }
        ConfigManager cm = new ConfigManager();
        args.getSender().sendMessage("§7=====§eBUILDINFORMATIONEN§7======");


        args.getSender().sendMessage("");

        args.getSender().sendMessage("§eGlobal-Settings:");

        args.getSender().sendMessage("§7Plattform: " + (Environment.isSpigot() ? "§3Spigot" : "§3BungeeCord"));
        args.getSender().sendMessage("§7MySQL: " + (cm.getGlobalConfig().getBoolean("UseMySQL") ? "§atrue" : "§cfalse"));


        args.getSender().sendMessage("§7Chatfilter: " + (cm.getConfig().getBoolean("useDefaultChatFilter") ? "§atrue" : "§cfalse"));
        args.getSender().sendMessage("§7Anti-LaggSystem: " + (cm.getConfig().getBoolean("useAntiLaggSystem") ? "§atrue" : "§cfalse"));

        args.getSender().sendMessage("§eServer-Settings:");

        args.getSender().sendMessage("§7Nutzung als Bau-Server: " + (cm.getConfig().getBoolean("useAsBauServer") ? "§atrue" : "§cfalse"));
        args.getSender().sendMessage("§7Nutzung als Arena: " + (cm.getConfig().getBoolean("useAsArena") ? "§atrue" : "§cfalse"));
        args.getSender().sendMessage("§7Nutzung als Lobby: " + (cm.getConfig().getBoolean("useAsLobby") ? "§atrue" : "§cfalse"));

        args.getSender().sendMessage("§eDependency's:");
        args.getSender().sendMessage("§7Brew: " + (Environment.isBrew() ? "§atrue" : "§cfalse"));
        args.getSender().sendMessage("§7WorldEdit: " + (Environment.isWorldedit() ? "§atrue" : "§cfalse"));

    }

  

}
