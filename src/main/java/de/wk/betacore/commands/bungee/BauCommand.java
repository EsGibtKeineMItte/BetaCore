package de.wk.betacore.commands.bungee;

import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BauCommand extends Command {


    public BauCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Misc.NOTINCONSOLE);
            return;
        }
        if (strings.length != 0) {
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/bau");
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        player.connect(ProxyServer.getInstance().getServerInfo("Bau"));
    }
}

