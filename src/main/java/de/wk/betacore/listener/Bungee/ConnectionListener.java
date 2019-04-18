package de.wk.betacore.listener.Bungee;

import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.objects.WarPlayer;
import de.wk.betacore.util.MaintanceSystem;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import sun.applet.Main;

public class ConnectionListener implements Listener {


    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        try {
            WarPlayer wp = new WarPlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName());
            if (wp.isBanned()) {
                e.getPlayer().disconnect(new TextComponent("Du bist gebannt"));
            }
            if (!(MaintanceSystem.isAdded(e.getPlayer().getUniqueId())) || MaintanceSystem.isBanned(e.getPlayer().getUniqueId())) {
                e.getPlayer().disconnect(new TextComponent("Du bist nicht auf den WartungsModus hinzugefügt."));
                return;
            }else{
                Environment.debug("Spieler ist geadded!!L");
            }


        } catch (Exception x) {
            BetaCoreBungee.debug("Fehler beim Erstellen des WarPlayers " + e.getPlayer().getName() + " aufgetreten.");
            x.printStackTrace();
        }
    }
}


