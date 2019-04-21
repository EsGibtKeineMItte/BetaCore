package de.wk.betacore.appearance;

import de.wk.betacore.appearance.Color;
import org.bukkit.entity.Player;

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

    public static void sendMessage(Player p, Player[] a, String message, String prefix, Boolean allowColor, Boolean allowSpecial) {
        send(a, p, message, prefix, allowColor, allowSpecial);
    }

    public static void sendMessage(Player p, Player[] a, String message, String prefix) {
        send(a, p, message, prefix, false, false);
    }

    public static void sendMessage(Player p, Player[] a, String message) {
        send(a, p, message, "", false, false);
    }

    private static void send(Player p, String message, String prefix, Boolean allowColor, Boolean allowSpecial) {
        prefix = prefix.replaceAll("\\(World\\)", p.getWorld().getName()).replaceAll("\\(Name\\)", p.getName()).replaceAll("&?", "");
        prefix = Color.ConvertColor(Color.ConvertSpecial(prefix));
        if (allowColor&& allowSpecial) {
            message = Color.ConvertColor(Color.ConvertSpecial(message));
        } else {
            message = getString(message, allowColor, allowSpecial);
        }
        p.sendMessage(prefix + "§r" + message);
    }

    private static String getString(String message, Boolean allowColor, Boolean allowSpecial) {
        if (!allowColor && allowSpecial) {
            message = Color.ConvertSpecial(message);
        } else if (allowColor && !allowSpecial) {
            message = Color.ConvertColor(message);
        } else {

        }
        return message;
    }

    private static void send(Player[] all, Player p, String message, String prefix, Boolean allowColor, Boolean allowSpecial) {
        prefix = prefix.replaceAll("\\(World\\)", p.getWorld().getName()).replaceAll("\\(Name\\)", p.getName()).replaceAll("&?", "");
        System.out.println(Color.ConvertColor(Color.ConvertSpecial(prefix)));
        prefix = Color.ConvertColor(Color.ConvertSpecial(prefix));
        if (allowColor && allowSpecial) {
            message = Color.ConvertColor(Color.ConvertSpecial(message));
        } else message = getString(message, allowColor, allowSpecial);
        p.sendMessage(prefix + "§r" + message);
        for (Player a : all) {
            if (a != p) {
                a.sendMessage(prefix + "§r" + message);
            }
        }
    }

}
