package de.wk.betacore.appearance;

import org.bukkit.entity.Player;
import de.wk.betacore.appearance.Color;

public class Chat {

    public static void sendMessage(Player p, String message, String prefix, Boolean allowColor, Boolean allowSpecial) {
        send(p, message, prefix, allowColor, allowSpecial);
    }

    public static void sendMessage(Player p, String message, String prefix) {
        send(p, message, prefix, false, false);
    }

    public static void sendMessage(Player p, String message) {
        send(p, message, "", false, false);
    }

    private static void send(Player p, String message, String prefix, Boolean allowColor, Boolean allowSpecial) {
        prefix = prefix.replaceAll("\\(World\\)", p.getWorld().getName()).replaceAll("\\(Name\\)", p.getName()).replaceAll("&?", "");
        prefix = Color.ConvertColor(Color.ConvertSpecial(prefix));
        if (allowColor  == true && allowSpecial == true) {
            message = Color.ConvertColor(Color.ConvertSpecial(message));
        } else if (allowColor  == false && allowSpecial == true) {
            message = Color.ConvertSpecial(message);
        } else if (allowColor  == true && allowSpecial == false) {
            message = Color.ConvertColor(message);
        } else {

        }
        p.sendMessage(prefix + " §r" + message);
    }

}
