package de.wk.betacore.listener.Bungee;

import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.objects.WarPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectionListener implements Listener {


   @EventHandler
    public void onLogin(PostLoginEvent e){
        try {
            WarPlayer wp = new WarPlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName());
            if(wp.isBanned()){
                e.getPlayer().disconnect(new TextComponent("Du bist heselig fick dich amk" ));
            }else{
                BetaCoreBungee.debug("Banned: " + FileManager.getPlayerData().getBoolean(wp.uuid.toString() + ".banned") + "UUID: " + wp.uuid);
            }

        }catch (Exception x){
            BetaCoreBungee.debug("Es ist ein Fehler, beim erstellen, des WarPlayers " + e.getPlayer().getName() + " aufgetreten.");
            x.printStackTrace();
        }
    }
}
