package de.wk.betacore.listener.Spigot;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;



public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
       if( e.getMessage().equalsIgnoreCase("/pl")|| e.getMessage().equalsIgnoreCase("/plugins")){
           if(!(e.getPlayer().hasPermission("betacore.seeplugins")|| e.getPlayer().hasPermission("betacore.*"))){
              e.setCancelled(true);
           }
       }
    }
}
