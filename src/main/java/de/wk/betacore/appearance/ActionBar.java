package de.wk.betacore.appearance;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * ActionBar is to send ActionBars to the Player
 */

public class ActionBar {

    /**
     * Send an ActionBar to the Player.
     *
     * @param p The Player that recieves the Message
     * @param message The Message to send
     */

    public static void sendActionBar(Player p, String message) {
        if (p.isOnline() == false) {
            return;
        }
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    /**
     * Send an ActionBar to the Player List.
     *
     * @param pall Your Players to send the ActionBar to
     * @param message Your Message to send
     * @
     */

    public static void sendActionBar(Player[] pall, String message)  {
        for (Player p : pall) {
            if (p.isOnline() == false) {
                return;
            }
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        }
    }

    /**
     * Send an ActonBar to every Player that is online.
     *
     * @param message Your Message to send
     */

    public static void sendActionBar(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        }
    }

}
