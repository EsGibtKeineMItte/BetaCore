package de.wk.betacore.listener.Bungee;

import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.environment.Environment;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {



    @EventHandler
    public void onCMD(ChatEvent e){
        if(e.getMessage().toLowerCase().contains("/bau")){
            BetaCoreBungee.debug("JAAJAJAA");
            if(!(e.getSender() instanceof ProxiedPlayer)){
                return;
            }
            ProxiedPlayer player = (ProxiedPlayer) e.getSender();

            if(e.getMessage().equalsIgnoreCase("/bau") && (!player.getServer().getInfo()
                    .equals(ProxyServer.getInstance().getServerInfo("Bau")))){
                e.setCancelled(true);
                player.connect(ProxyServer.getInstance().getServerInfo("Bau"));
                Environment.debug(player.getServer().getInfo().toString()   );
            }
        }
    }
}
