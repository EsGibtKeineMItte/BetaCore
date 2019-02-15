package de.wk.betacore.appearance;

import org.bukkit.entity.Player;

public class Title {

    /**
     * Send a Title to a Player
     *
     * @param p        The Player you want to Present your Title
     * @param Title    Your Title you want to Send the Player
     * @param Subtitle Your Subtitle you want to Send the Player
     * @param FadeIn   Your FadeIn Time
     * @param Stay     Your Stay Time
     * @param FadeOut  Your FadeOut Time
     */

    public static void sendTitle(Player p, String Title, String Subtitle, Integer FadeIn, Integer Stay, Integer FadeOut) {
        p.sendTitle(Title, Subtitle, FadeIn, Stay, FadeOut);
    }

    /**
     * Send a Title to a Player
     *
     * @param p        The Player you want to Present your Title
     * @param Title    Your Title you want to Send the Player
     * @param Subtitle Your Subtitle you want to Send the Player
     */

    public static void sendTitle(Player p, String Title, String Subtitle) {
        p.sendTitle(Title, Subtitle, 10, 70, 20);
    }

    /**
     * Send a Title to a Player
     *
     * @param Title   Your Title you want to Send the Player
     * @param p       The Player you want to Present your Title
     * @param FadeIn  Your FadeIn Time
     * @param Stay    Your Stay Time
     * @param FadeOut Your FadeOut Time
     */

    public static void sendTitle(String Title, Player p, Integer FadeIn, Integer Stay, Integer FadeOut) {
        p.sendTitle(Title, " ", FadeIn, Stay, FadeOut);
    }

    /**
     * Send a Title to a Player
     *
     * @param Title Your Title you want to Send the Player
     * @param p     The Player you want to Present your Title
     */

    public static void sendTitle(String Title, Player p) {
        p.sendTitle(Title, " ", 10, 70, 20);
    }

    /**
     * Send a Title to a Player
     *
     * @param p        The Player you want to Present your Title
     * @param Subtitle Your Subtitle you want to Send the Player
     * @param FadeIn   Your FadeIn Time
     * @param Stay     Your Stay Time
     * @param FadeOut  Your FadeOut Time
     */

    public static void sendTitle(Player p, String Subtitle, Integer FadeIn, Integer Stay, Integer FadeOut) {
        p.sendTitle(" ", Subtitle, FadeIn, Stay, FadeOut);
    }

    /**
     * Send a Title to a Player
     *
     * @param p        The Player you want to Present your Title
     * @param Subtitle Your Subtitle you want to Send the Player
     */

    public static void sendTitle(Player p, String Subtitle) {
        p.sendTitle(" ", Subtitle, 10, 70, 20);
    }
}
