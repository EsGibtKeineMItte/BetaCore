package de.wk.betacore.commands.spigot;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;


public class Update {


    @Command(name = "update", aliases = {"up"}, description = "Command, um das FightsSystem zu updaten ", permission = "fight.update")
    public static void onCopy(CommandArgs args) {
        Process p;

        try {
            if (System.getProperties().contains("Windows")) {//FOR NNX:D
                p = Runtime.getRuntime().exec("copy ../target/Betacore-1.0-SNAPSHOT.jar plugins/Betacore-1.0-SNAPSHOT.jar");
                p.waitFor();
                BetaCoreBungee.log("Update erfolgreich.");
                BetaCoreBungee.log("Stoppe Server...");
                BetaCoreBungee.getInstance().getProxy().stop("Server muss neu starten...");
                return;
            }

            p = Runtime.getRuntime().exec("cp ../target/Betacore-1.0-SNAPSHOT.jar plugins/Betacore-1.0-SNAPSHOT.jar");
            p.waitFor();
            BetaCore.log("Kopiervorgang erfolgreich. Reloade nun", true);
            Bukkit.getServer().reload();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
    }

    @Command(name = "update.playerdata", aliases = {"up.playerdata"}, description = "Command, um das FightsSystem zu updaten.", permission = "fight.update")
    public static void onDelete(CommandArgs args) {

        File destination = new File("/home/leonhard/IdeaProjects/Betacore/Data/playerdata.json");

        if (destination.delete()) {
            BetaCore.log("PlayerData erfolgreich gelöscht.");
        }
    }

    @Command(name = "update.safe", aliases = {"up.save"}, description = "Command, um das FightsSystem zu updaten.", permission = "fight.save")
    public static void onUpdate(CommandArgs args) {

        File begin = new File("/home/leonhard/IdeaProjects/Betacore/target/Betacore-1.0-SNAPSHOT.jar");
        File destination = new File("/home/leonhard/IdeaProjects/Betacore/BetaCore-TestServer/plugins/Betacore-1.0-SNAPSHOT.jar");
        if (begin.renameTo(destination)) {
            Bukkit.getServer().broadcastMessage(Misc.PREFIX + "Reloading Server");
            Bukkit.getServer().spigot().restart();
        } else {
            BetaCore.debug("Es ist ein Fehler beim updaten des FighsSystems aufgetreten.");
        }
    }

}
