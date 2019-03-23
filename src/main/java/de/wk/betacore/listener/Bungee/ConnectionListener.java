package de.wk.betacore.listener.Bungee;

import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.objects.WarPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectionListener implements Listener {


    @EventHandler
    public void onLogin(PreLoginEvent e){
        try {
            WarPlayer wp = new WarPlayer(e.getConnection().getUniqueId(), e.getConnection().getName());
        }catch (Exception x){
            BetaCoreBungee.debug("Es ist ein Fehler, beim erstellen, des Spielers " + e.getConnection().getName() + " aufgetreten.");
        }

    }


}
