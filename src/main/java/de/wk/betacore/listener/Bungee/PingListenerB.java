package de.wk.betacore.listener.Bungee;


import de.wk.betacore.BetaCoreBungee;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Random;

public class PingListenerB implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        Random rand = new Random();
        int random = rand.nextInt(10);


        if(random < 4){
            ping.setDescriptionComponent(new TextComponent("§e§lTheWarKing.de \n§3Dein offizieller WarShip§7-§3Server              §7[§51.12-1.13§7]"));
        }else if(random < 7){
            ping.setDescriptionComponent(new TextComponent("§e§lTheWarKing.de §7- §3Der WarShip§7-§3Server \n§7[§a!§7] §a§lNeuer Bau-Spawn!                 §7[§51.12-1.13§7]"));
        }else if(random <= 10){
            ping.setDescriptionComponent(new TextComponent("§e§lTheWarKing.de §7- §3Dein §lWarShip§7-§3Server \n§cClosed-Alpha                                    §7[§51.12-1.13§7]"));
        }
        ServerPing.PlayerInfo[] pi = new ServerPing.PlayerInfo[]{new ServerPing.PlayerInfo("§6Dein offizieller WarShip Server", "0"), new ServerPing.PlayerInfo("§eServerversion: §a1.12-1.13", "0"),new ServerPing.PlayerInfo("§e", "0"),new ServerPing.PlayerInfo("", "0")};
        ping.setPlayers(new ServerPing.Players(250, BetaCoreBungee.getInstance().getProxy().getOnlineCount(), pi));
      //  ping.setVersion(new Protocol("BungeeCord 1.12-1.13", 404));
        e.setResponse(ping);
    }
}
