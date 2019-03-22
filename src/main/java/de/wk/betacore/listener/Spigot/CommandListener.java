package de.wk.betacore.listener.Spigot;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;


public class CommandListener implements Listener {
    ArrayList<String> commands;

    public CommandListener(String ... commands){
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }





    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e){
        String msg = e.getMessage().toLowerCase();
        for(String cmd : commands){
            if(cmd.contains(msg) && msg.startsWith("/")){
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
       if( e.getMessage().equalsIgnoreCase("/pl")|| e.getMessage().equalsIgnoreCase("/plugins") || e.getMessage().equalsIgnoreCase("/help") ){
           if(!(e.getPlayer().hasPermission("betacore.seeplugins")|| e.getPlayer().hasPermission("betacore.*"))){
              e.setCancelled(true);
           }
       }
    }
}
