package de.wk.betacore.commands.bungee;

import com.google.common.collect.Lists;
import de.wk.betacore.BetaCore;
import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.logging.Handler;

public class BungeePluginsReloadCommand extends Command {


    public BungeePluginsReloadCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        for (Plugin plugin : Lists.reverse(new ArrayList<>(ProxyServer.getInstance().getPluginManager().getPlugins()))) {
            try {
                plugin.onDisable();
                ProxyServer.getInstance().getPluginManager().unregisterCommands(plugin);
                ProxyServer.getInstance().getPluginManager().unregisterListeners(plugin);
                for (Handler handler : plugin.getLogger().getHandlers()) {
                    handler.close();
                }
            } catch (Throwable t) {
                BetaCore.log("§4Exception beim deaktivieren von " + plugin.getDescription().getName() + ":");
                t.printStackTrace();
            }
            ProxyServer.getInstance().getScheduler().cancel(plugin);
            plugin.getExecutorService().shutdownNow();
        }

        ProxyServer.getInstance().getPluginManager().detectPlugins(new java.io.File("./plugins"));
        ProxyServer.getInstance().getPluginManager().loadPlugins();
        ProxyServer.getInstance().getPluginManager().enablePlugins();
        sender.sendMessage(new TextComponent(Misc.PREFIX + "§aPlugins neu geladen"));
    }
}

