package de.wk.betacore.listener.Bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinHandler implements Listener {



    @EventHandler
    public void onJoin(PostLoginEvent e){
        ProxiedPlayer p = e.getPlayer();
        p.setPermission("betacore.servers", true);
    }
}
