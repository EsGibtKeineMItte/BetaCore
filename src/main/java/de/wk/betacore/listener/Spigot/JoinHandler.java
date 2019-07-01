package de.wk.betacore.listener.Spigot;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import de.wk.betacore.BetaCore;
import de.wk.betacore.appearance.Color;
import de.wk.betacore.appearance.Tablist;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.ranksystem.RankSystem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class JoinHandler implements Listener {
    private ConfigManager cm = new ConfigManager();
    private Json data = FileManager.getPlayerData();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        scoreboard(e.getPlayer());

        setPrefix();
        tablist(e.getPlayer());

        if (!cm.getConfig().getBoolean("useAsLobby"))
            e.getPlayer().getInventory().clear();
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().setHealth(20.0);
        e.getPlayer().setFoodLevel(20);

        BetaCore.teleportSpawn(e.getPlayer());

        if (RankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) {
            e.setJoinMessage("");
        } else {
            e.setJoinMessage(Color.ConvertColor(RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + RankSystem.getRank(e.getPlayer().getUniqueId()).getName() + "§7 | " + RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + e.getPlayer().getName() + " &ehat den Server betreten."));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        if (RankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) {
            e.setQuitMessage("");
        } else {
            e.setQuitMessage(Color.ConvertColor(RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + RankSystem.getRank(e.getPlayer().getUniqueId()).getName() + "§7 | " + RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + e.getPlayer().getName() + " &ehat den Server verlassen"));
        }
    }

    public void update(Player e) {
        scoreboard(e.getPlayer());
        tablist(e);
        setPrefix();
    }


    public void tablist(Player e) {
        Tablist.Tablist("&aTheWarKing(n)&7dein WarShip Server", "&7Viel Spaß auf dem Server(n)&e(Name)&7!", e.getPlayer());
    }

    public void playerTablist(Player e) {
        e.getPlayer().setDisplayName(RankSystem.getRank(e.getUniqueId()).getColor() + RankSystem.getRank(e.getUniqueId()).getName());
        if (RankSystem.getRank(e.getPlayer().getUniqueId()).equals(Rank.USER)) {
            e.getPlayer().setPlayerListName(Color.ConvertColor(RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + e.getPlayer().getName()));
        } else {
            e.getPlayer().setPlayerListName(Color.ConvertColor(RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + RankSystem.getRank(e.getPlayer().getUniqueId()).getName() + " §7| " + RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + e.getPlayer().getName()));
        }
    }

    public void scoreboard(Player e) {
//        ConfigManager cm = new ConfigManager();
//
//        ArrayList<String> sscore = new ArrayList<>();
//        sscore.add("&6");
//        sscore.add("&6> &7Money");
//        sscore.add("&6> &e" + MoneySystem.getMoney(e.getUniqueId()));
//        sscore.add("&6> &7Rank");
//        sscore.add("&6> &e" + RankSystem.getRank(e.getPlayer().getUniqueId()).getColor() + RankSystem.getRank(e.getPlayer().getUniqueId()).getName());
//        if (data.getInt(e.getPlayer().getUniqueId().toString() + ".wsrank") < 501) {
//            sscore.add("&7");
//            sscore.add("&6> &7WSRank");
//            sscore.add("&6> &e&l" + data.getInt(e.getPlayer().getUniqueId().toString() + ".wsrank"));
//        }
//        if (TeamSystem.isActiveWarShipTeam(data.getString(e.getUniqueId().toString() + ".wsteam"))) {
//            sscore.add("&6> &7Team: &e&l" + data.getString(e.getUniqueId() + ".wsteam"));
//        }
//        sscore.add("&8");
//        sscore.add("&6> &7Joins &e(Joins)");
//        sscore.add("&6> &7Play Time &e(PlayTime)");
//        sscore.add("&6TheWarking.de");
//
//        String[] st = new String[sscore.size()];
//        int i = 0;
//        for (String string : sscore) {
//            st[i] = string;
//            i++;
//        }
//        ScoreboardUtils.updateScoreboard("&aTheWarKing", st, e.getPlayer());
    }

    public void setPrefix() {
        ConfigManager cm = new ConfigManager();

        if (cm.getConfig().getBoolean("useAsArena")) {
            return;
        }


        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Team Admin = board.registerNewTeam("01Admin");
        Team Dev = board.registerNewTeam("03Dev");
        Team Mod = board.registerNewTeam("04Mod");
        Team Supp = board.registerNewTeam("05Supp");
        Team Archi = board.registerNewTeam("06Archi");
        Team YouTuber = board.registerNewTeam("07YouTuber");
        Team Premium = board.registerNewTeam("08Premium");
        Team User = board.registerNewTeam("09User");

        Admin.setPrefix(Rank.ADMIN.getColor() + Rank.ADMIN.getName() + " §7| " + Rank.ADMIN.getColor());
        Dev.setPrefix(Rank.DEV.getColor() + Rank.DEV.getName() + " §7| " + Rank.DEV.getColor());
        Mod.setPrefix(Rank.MOD.getColor() + Rank.MOD.getName() + " §7| " + Rank.MOD.getColor());
        Supp.setPrefix(Rank.SUPPORTER.getColor() + Rank.SUPPORTER.getName() + " §7| " + Rank.SUPPORTER.getColor());
        Archi.setPrefix(Rank.BUILDER.getColor() + Rank.BUILDER.getName() + " §7| " + Rank.BUILDER.getColor());
        YouTuber.setPrefix(Rank.YOU_TUBER.getColor() + Rank.YOU_TUBER.getName() + " §7| " + Rank.YOU_TUBER.getColor());
        Premium.setPrefix(Rank.PREMIUM.getColor() + Rank.PREMIUM.getName() + " §7| " + Rank.PREMIUM.getColor());
        User.setPrefix(Rank.USER.getColor() + "");

        RankSystem rankSystem = new RankSystem();
        Bukkit.getOnlinePlayers().forEach(p -> {
            switch (RankSystem.getRank(p.getUniqueId())) {

                case ADMIN:
                    Admin.addEntry(p.getName());
                    break;
                case DEV:
                    Dev.addEntry(p.getName());
                    break;
                case MOD:
                    Mod.addEntry(p.getName());
                    break;
                case SUPPORTER:
                    Supp.addEntry(p.getName());
                    break;
                case BUILDER:
                    Archi.addEntry(p.getName());
                    break;
                case YOU_TUBER:
                    YouTuber.addEntry(p.getName());
                    break;
                case PREMIUM:
                    Premium.addEntry(p.getName());
                    break;
                case USER:
                    User.addEntry(p.getName());
                    break;
                default:
                    User.addEntry(p.getName());
                    BetaCore.debug("Hier hat ein Spieler keinen Rang zugewiesen. Das ist nicht gut.");
            }
            p.setScoreboard(board);
        });


    }

}
