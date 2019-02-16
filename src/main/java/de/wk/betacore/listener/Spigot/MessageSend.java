package de.wk.betacore.listener.Spigot;

import de.wk.betacore.appearance.Chat;
import de.wk.betacore.appearance.Info;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.misc;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageSend implements Listener {
    ConfigManager cm = new ConfigManager();

    public static Player[] onlinePlayers() {
        Player[] Online = new Player[Bukkit.getOnlinePlayers().size()];
        Integer PlayerIndex = 0;
        for (Player all: Bukkit.getOnlinePlayers()) {
            Online[PlayerIndex] = all;
            PlayerIndex++;
        }
        return Online;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        if (cm.getPlayerData().getBoolean(e.getPlayer().getUniqueId().toString() + ".muted")) {
            Info.sendInfo(e.getPlayer(), misc.getMUTED());
            return;
        }
        RankSystem rankSystem = new RankSystem();
        if (rankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) {
            Chat.sendMessage(e.getPlayer(), onlinePlayers(), e.getMessage(),Rank.USER.getColor() + "(Name)§8 »§f");
        } else {
            Chat.sendMessage(e.getPlayer(), onlinePlayers(), e.getMessage(),rankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + rankSystem.getRank(e.getPlayer().getUniqueId()).getName() + " (Name)§8 »", true, true);
        }
    }

}
