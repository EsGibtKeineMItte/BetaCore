package de.wk.betacore.appearance;

import de.wk.betacore.util.data.Misc;
import org.bukkit.entity.Player;

public class Info {

    public static void sendInfo(Player p, String info, Boolean replaceGM, Boolean replaceWorld) {
        send(p, info, replaceGM, replaceWorld);
    }

    public static void sendInfo(Player p, String info) {
        send(p, info, false, false);
    }

    private static void send(Player p, String info, Boolean replaceGM, Boolean replaceWorld) {
        if (replaceGM && replaceWorld) {
            info.replaceAll("\\(GM\\)", p.getGameMode().name()).replaceAll("\\(WORLD\\)", p.getWorld().getName());
        } else if (!(replaceGM) && replaceWorld) {
            info.replaceAll("\\(WORLD\\)", p.getWorld().getName());
        } else if (replaceGM && !(replaceWorld)) {
            info.replaceAll("\\(GM\\)", p.getGameMode().name());
        }
        p.sendMessage(Color.ConvertColor(Misc.getPREFIX()) + "§r" + Color.ConvertColor(info));
    }

}
