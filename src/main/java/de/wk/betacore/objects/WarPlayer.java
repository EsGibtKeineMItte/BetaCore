package de.wk.betacore.objects;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.DataManager;
import de.wk.betacore.util.*;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.ranksystem.Rank;
import io.bluecube.thunderbolt.io.ThunderFile;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class WarPlayer {
    static ConfigManager cm = new ConfigManager();
    static ThunderFile data = DataManager.getPlayerData();


    private int wsrank;
    private int money;
    private Rank rank;
    private String team;
    private String firstjoin;
    private String lastjoin;
    private String uuid;
    private String name;
    private boolean banned;
    private boolean muted;


    public WarPlayer(UUID uuid) {

        if (NameFetcher.getName(uuid) == null) {
            BetaCore.debug("Es wurde versucht, einen WarPlayer mit einer UUID zu kreieren, die zu keinen Minecraft - Account gehört.");
            return;
        }
        setupWarPlayer(uuid);
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) {
                LocalDate now = LocalDate.now();
                data.set(uuid.toString() + ".name", NameFetcher.getName(uuid));
                data.set(uuid.toString() + ".rank", "USER");
                data.set(uuid.toString() + ".money", 0);
                data.set(uuid.toString() + ".wsrank", -1);
                data.set(uuid.toString() + ".muted", false);
                data.set(uuid.toString() + ".banned", false);
                data.set(uuid.toString() + "firstjoin", now.toString());
                data.set(uuid.toString() + "lastjoin", now.toString());


                data.save();
                //Ist nicht im System.
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();

            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                String rank = rs2.getString("RANK");
                this.money = rs2.getInt("MONEY");
                this.firstjoin = data.getString(uuid.toString() + ".firstjoin");
                this.lastjoin = data.getString(uuid.toString() + ".lastjoin");
                this.team = data.getString(uuid.toString() + ".wsteam");
                this.wsrank = data.getInt(uuid.toString() + ".wsrank");
                this.rank = Rank.valueOf(rank);
                this.uuid = uuid.toString();
                this.name = NameFetcher.getName(uuid);
            }

            System.out.println(MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = " + "'" + uuid.toString() + "';").executeQuery());
        } catch (SQLException | IOException x) {
            BetaCore.debug("Es ist ein Fehler, beim Erstellen des WarPlayers: " + NameFetcher.getName(uuid) + " aufgetreten.");
            x.printStackTrace();
        }
    }

    public void setupWarPlayer(UUID uuid) {
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (!(rs.getInt(1) == 0)) {
                return;
            }
            LocalDate now = LocalDate.now();
            data.set(uuid.toString() + ".name", NameFetcher.getName(uuid));
            data.set(uuid.toString() + ".rank", "USER");
            data.set(uuid.toString() + ".money", 0);
            data.set(uuid.toString() + ".wsrank", -1);
            data.set(uuid.toString() + ".muted", false);
            data.set(uuid.toString() + ".banned", false);
            data.set(uuid + "firstjoin", now.toString());
            data.set(uuid + "lastjoin", now.toString());

            data.save();
            //Ist nicht im System.
            MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(Misc.getPREFIX() + "Es ist ein Fehler beim Setzen der MySQL Werte für den Spieler " + NameFetcher.getName(uuid) + " aufgetreten");
            System.out.println("");
            e.printStackTrace();
        }

    }

    public static void manuellsetup(UUID uuid) {
        LocalDate now = LocalDate.now();

        data.set(uuid + ".name", NameFetcher.getName(uuid));
        data.set(uuid + ".money", 0);
        data.set(uuid + ".wsrank", -1);
        data.set(uuid + ".wsteam", "");
        data.set(uuid + "firstjoin", now.toString());
        data.set(uuid + "lastjoin", now.toString());
        data.set(uuid + ".muted", false);
        data.set(uuid.toString() + ".banned", false);

        try {
            data.save();
            MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(Misc.getPREFIX() + "Es ist ein Fehler beim Setzen der SpielerDaten für den Spieler " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            e.printStackTrace();
        }

    }

    public void setWarShipTeam(String teamName) {
        data.set(uuid + ".wsteam", teamName);
        try {
            data.save();
        } catch (IOException e) {
            BetaCore.debug("Es ist ein Fehler beim setzen des WarShip-Teams von " + name + " aufgetreten.");
            e.printStackTrace();
        }
    }


    public String getWarShipTeam() {
        return data.getString(uuid + ".wsteam");
    }

    public boolean isBanned(){
        return banned;
    }

    public boolean isMuted(){
        return muted;
    }


    public void ban(int banID) {
        //TODO BannSystem einfügen
    }

    public void ban(String reason, String time) {

    }


    public void mute(int muteID) {

    }

    public void mute(String reason, String time) {

    }

    public void unban() {

    }

    public void unmute() {

    }


    public int getWsrank() {
        return wsrank;
    }

    public int getMoney() {
        return money;
    }

    public Rank getRank() {
        return rank;
    }

    public String getTeam() {
        return team;
    }

    public String getFirstjoin() {
        return firstjoin;
    }

    public String getLastjoin() {
        return lastjoin;
    }


}
