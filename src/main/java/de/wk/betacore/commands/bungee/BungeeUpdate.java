package de.wk.betacore.commands.bungee;

import com.minnymin.command.CommandArgs;
import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

public class BungeeUpdate extends Command {
    public BungeeUpdate(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (sender instanceof ProxiedPlayer) {
            sender.sendMessage(new TextComponent("§cDu kannst diesen Befehl nicht ingame benutzen"));
            return;
        }

        Process p;

        try {
            p = Runtime.getRuntime().exec("cp ../target/Betacore-1.0-SNAPSHOT.jar plugins/Betacore-1.0-SNAPSHOT.jar");
            p.waitFor();
            BetaCoreBungee.log("Update erfolgreich.");
            BetaCoreBungee.log("Stoppe Server...");
            BetaCoreBungee.getInstance().getProxy().stop("Server muss neu starten...");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }



    }
}
