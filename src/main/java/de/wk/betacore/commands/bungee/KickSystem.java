package de.wk.betacore.commands.bungee;

import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;


public class KickSystem extends Command {

    public KickSystem() {
        super("kick");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("betacore.kick")) {
            sender.sendMessage(new TextComponent(Misc.NOPERM));
            return;
        }

        if (BetaCoreBungee.getInstance().getProxy().getPlayers().size() == 0) {
            sender.sendMessage(new TextComponent("Es sind gerade keine Spieler zum kicken online."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(new TextComponent("§7Benutzung: §6/kick <Spieler> <Reason>" ));
            return;
        }
        ProxiedPlayer player = BetaCoreBungee.getInstance().getProxy().getPlayer(args[0]);

        if (player == null) {
            return;
        }

        if (args.length > 1) {

            ArrayList<String> reason = new ArrayList<>(Arrays.asList(args));

            player.disconnect(new TextComponent(reason.toString()));

        } else {
            sender.sendMessage(new TextComponent(Misc.PREFIX + "§7Benutzung: §6/kick <Spieler> <Grund>"));
        }


    }
}
