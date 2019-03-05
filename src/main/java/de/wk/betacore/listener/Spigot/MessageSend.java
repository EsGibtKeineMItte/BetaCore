package de.wk.betacore.listener.Spigot;

import de.wk.betacore.BetaCore;
import de.wk.betacore.appearance.Chat;
import de.wk.betacore.appearance.Info;
import de.wk.betacore.commands.spigot.CustomCommand;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;



public class MessageSend implements Listener {
    ConfigManager cm = new ConfigManager();

    public static Player[] onlinePlayers() {
        Player[] Online = new Player[Bukkit.getOnlinePlayers().size()];
        Integer PlayerIndex = 0;
        for (Player all : Bukkit.getOnlinePlayers()) {
            Online[PlayerIndex] = all;
            PlayerIndex++;
        }
        return Online;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        if (cm.getPlayerData().getBoolean(e.getPlayer().getUniqueId().toString() + ".muted")) {
            Info.sendInfo(e.getPlayer(), Misc.getMUTED());
            return;
        }
        if (e.getPlayer().equals(CustomCommand.edit)) {
            return;
        }
        if(RankSystem.getRank(e.getPlayer().getUniqueId()) == null){
            e.getPlayer().sendMessage("§4Du hast keinen Rang und kannst somit auch keine Nachrichten in den Chat senden. \nNormalerweise sollte das ein Fehler sein.");
            BetaCore.debug("Fehler 001");
            BetaCore.debug(ChatColor.GRAY + e.getPlayer().getName() + " hat keinen Rang.");
        }

        if (RankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) {
            Chat.sendMessage(e.getPlayer(), onlinePlayers(), e.getMessage(), Rank.USER.getColor() + e.getPlayer().getName() + "§8 »§f");
        } else {
            Chat.sendMessage(e.getPlayer(), onlinePlayers(), e.getMessage(), RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + RankSystem.getRank(e.getPlayer().getUniqueId()).getName() + "§7 | " + RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + " (Name)§8 »" + RankSystem.getRank(e.getPlayer().getUniqueId()).getColor(), true, true);
        }
    }

}
