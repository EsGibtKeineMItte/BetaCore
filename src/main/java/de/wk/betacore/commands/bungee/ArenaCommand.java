package de.wk.betacore.commands.bungee;

import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ArenaCommand extends Command {


    public ArenaCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Misc.NOTINCONSOLE);
            return;
        }
        if (strings.length != 1) {
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/arena §7<§61§7|§62§7>");
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        player.connect(ProxyServer.getInstance().getServerInfo("Arena-" + strings[0]));
    }
}

