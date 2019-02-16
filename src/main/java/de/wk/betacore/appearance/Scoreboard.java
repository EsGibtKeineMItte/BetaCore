package de.wk.betacore.appearance;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Scoreboard {

    public static Integer PlayTime(Player p) {
        Integer PlayTime = p.getStatistic(Statistic.PLAY_ONE_TICK) / 20 / 60 / 60;
        return PlayTime;
    }

    /**
     * Update the Scoreboard in the Sidebar
     *
     * @param DisplayName Your Display Name for the Scoreboard
     *                    (Name) gets Replaced to the Name of the Player
     *                    (World) gets Replaced to the World of the Player
     *                    (Server) gets Replaced to the Server Name of the Player
     * @param Entries Your Entries and an Array
     *                (World) gets Replaced to the World of the Player
     *                (Name) gets Replaced to the Name of the Player
     *                (Joins) gets Replaced to the Join Count of the Player
     *                (Server) gets Replaced to the Server of the Player
     *                (PlayTime) gets Replaced to the PlayTime in Hours of the Time
     *
     * @param p Your Player to show the Scoreboard to
     */

    public static void updateScoreboard(String DisplayName, String[] Entries, Player p) {
        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        if (p.getScoreboard() != board) {
            p.setScoreboard(board);
        }

        Objective obj = board.registerNewObjective("a", "b");
        DisplayName = DisplayName.replaceAll("\\(Name\\)", p.getName());
        DisplayName = DisplayName.replaceAll("\\(World\\)", p.getWorld().toString());
        DisplayName = DisplayName.replaceAll("\\(Server\\)", p.getServer().getName());

        if (DisplayName.isEmpty()) {
            DisplayName = " ";
        }

        DisplayName = Color.ConvertColor(DisplayName);
        DisplayName = Color.ConvertSpecial(DisplayName);

        obj.setDisplayName(DisplayName);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Integer j = 0;
        for (int i = 0; i < Entries.length; i++) {
            if (Entries[i] != null) {
                if (Entries[i].toCharArray().length < 17) {
                    j += 1;
                }
            }
        }

        for (String string : board.getEntries()) {
            board.resetScores(string);
        }

        for (int i = 0; i < j + 2; i++) {

            if (Entries[i] != null) {
                if (Entries[i].toCharArray().length < 17) {

                    String text = Entries[i];
                    text = Color.ConvertColor(text);
                    text = Color.ConvertSpecial(text);
                    text = text.replaceAll("\\(World\\)", p.getWorld().toString());
                    text = text.replaceAll("\\(Name\\)", p.getName());
                    Integer joins = p.getStatistic(Statistic.LEAVE_GAME) + 1;
                    String Joins2 = joins + "";
                    text = text.replaceAll("\\(Joins\\)", Joins2);
                    text = text.replaceAll("\\(Server\\)", p.getServer().getServerName());
                    Integer PlayTime = PlayTime(p);
                    String PlayTime2 = PlayTime + "H";
                    text = text.replaceAll("\\(PlayTime\\)", PlayTime2);

                    Score score = obj.getScore(text);
                    score.setScore(j + 1 - i);

                    p.setScoreboard(board);
                }
            } else {
                System.out.println("No Scoreboard able to create!");
            }
        }
    }

    /**
     * Update the Scoreboard in the Sidebar
     *
     * @param DisplayName Your Display Name for the Scoreboard
     *                    (Name) gets Replaced to the Name of the Player
     *                    (World) gets Replaced to the World of the Player
     *                    (Server) gets Replaced to the Server Name of the Player
     * @param Entries Your Entries and an Array
     *                (World) gets Replaced to the World of the Player
     *                (Name) gets Replaced to the Name of the Player
     *                (Joins) gets Replaced to the Join Count of the Player
     *                (Server) gets Replaced to the Server of the Player
     *                (PlayTime) gets Replaced to the PlayTime in Hours of the Time
     *
     * @param all Your Players (Player Array) to show the Scoreboard to
     */

    public static void updateScoreboard(String DisplayName, String[] Entries, Player[] all) {
        for (Player p: all) {
            org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            if (p.getScoreboard() != board) {
                p.setScoreboard(board);
            }

            Objective obj = board.registerNewObjective("a", "b");
            DisplayName = DisplayName.replaceAll("\\(Name\\)", p.getName());
            DisplayName = DisplayName.replaceAll("\\(World\\)", p.getWorld().toString());
            DisplayName = DisplayName.replaceAll("\\(Server\\)", p.getServer().getServerName());

            if (DisplayName.isEmpty()) {
                DisplayName = " ";
            }

            DisplayName = Color.ConvertColor(DisplayName);
            DisplayName = Color.ConvertSpecial(DisplayName);

            obj.setDisplayName(DisplayName);
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Integer j = 0;
            for (int i = 0; i < Entries.length; i++) {
                if (Entries[i] != null) {
                    if (Entries[i].toCharArray().length < 17) {
                        j += 1;
                    }
                }
            }

            for (String string : board.getEntries()) {
                board.resetScores(string);
            }

            for (int i = 0; i < j + 2; i++) {

                if (Entries[i] != null) {
                    if (Entries[i].toCharArray().length < 17) {

                        String text = Entries[i];
                        text = Color.ConvertColor(text);
                        text = Color.ConvertSpecial(text);
                        text = text.replaceAll("\\(World\\)", p.getWorld().toString());
                        text = text.replaceAll("\\(Name\\)", p.getName());
                        Integer joins = p.getStatistic(Statistic.LEAVE_GAME) + 1;
                        String Joins2 = joins + "";
                        text = text.replaceAll("\\(Joins\\)", Joins2);
                        text = text.replaceAll("\\(Server\\)", p.getServer().getName());
                        Integer PlayTime = PlayTime(p);
                        String PlayTime2 = PlayTime + "H";
                        text = text.replaceAll("\\(PlayTime\\)", PlayTime2);

                        Score score = obj.getScore(text);
                        score.setScore(j + 1 - i);

                        p.setScoreboard(board);
                    }
                } else {
                    System.out.println("No Scoreboard able to create!");
                }
            }
        }
    }

}
