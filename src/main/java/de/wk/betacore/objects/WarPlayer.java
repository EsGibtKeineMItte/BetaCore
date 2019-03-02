package de.wk.betacore.objects;

import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.data.Misc;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.ranksystem.Rank;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WarPlayer {
    static ConfigManager cm = new ConfigManager();


    private int wsrank;
    private int money;
    private Rank rank;
    private String team;
    private String firstjoin;
    private String lastjoin;
    private String uuid;
    private String name;



    public WarPlayer(UUID uuid) {
        if(Bukkit.getOfflinePlayer(uuid) == null){
            BetaCore.debug("Es wurde versucht, einen WarPlayer mit einer UUID zu kreieren, die zu keinen Minecraft - Account gehört.");
            return;
        }
        setupWarPlayer(uuid);
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) {

                cm.getPlayerData().setString(uuid.toString() + ".name", Bukkit.getOfflinePlayer(uuid).getName());
                cm.getPlayerData().setBoolean(uuid.toString() + ".muted", false);
                cm.getPlayerData().setInt(uuid.toString() + ".wsrank", -1);
                //Ist nicht im System.
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();

            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                String rank = rs2.getString("RANK");
                this.money = rs2.getInt("MONEY");
                this.firstjoin = rs2.getTimestamp("JOIN_DATE").toString();
                this.lastjoin = cm.getPlayerData().getString(uuid.toString() + ".lastjoin");
                this.team = cm.getPlayerData().getString(uuid.toString() + ".team");
                this.wsrank = cm.getPlayerData().getInt(uuid.toString() + ".wsrank");
                this.rank = Rank.valueOf(rank);
                this.uuid = uuid.toString();
                this.name = Bukkit.getOfflinePlayer(uuid).getName();
            }

            System.out.println(MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = " + "'" + uuid.toString() + "';").executeQuery());
        } catch (SQLException x) {
            x.printStackTrace();
        }
    }

    public void setupWarPlayer(UUID uuid){
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (!(rs.getInt(1) == 0)) {
               return;
            }
            cm.getPlayerData().setString(uuid.toString() + ".name", Bukkit.getOfflinePlayer(uuid).getName());
            cm.getPlayerData().setBoolean(uuid.toString() + ".muted", false);
            cm.getPlayerData().setInt(uuid.toString() + ".wsrank", -1);
            //Ist nicht im System.
            MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
        }catch (SQLException e){
            System.out.println(Misc.getPREFIX() + "Es ist ein Fehler beim Setzen der MySQL Werte für den Spieler " + Bukkit.getOfflinePlayer(uuid).getName() + " aufgetreten");
            System.out.println("");
            e.printStackTrace();
        }

    }

    public void manuellsetup(UUID uuid){
        if (cm.getPlayerData().getString(uuid.toString() + ".rank") == null) {
            cm.getPlayerData().setString(uuid.toString() + ".rank", "USER");
        }
        if (cm.getPlayerData().getString(uuid.toString() + ".money") == null) {
            cm.getPlayerData().setInt(uuid.toString() + ".money", 0);
        }
        //Name
        if (cm.getPlayerData().getString(uuid.toString() + ".name") == null) {
            cm.getPlayerData().setString(uuid.toString() + ".name", Bukkit.getOfflinePlayer(uuid).getName());
        }
        if (!(cm.getPlayerData().getBoolean(uuid.toString() + ".muted"))) {
            cm.getPlayerData().setBoolean(uuid.toString() + ".muted", false);
        }
        if (cm.getPlayerData().getInt(uuid.toString() + ".wsrank") == 0) {
            cm.getPlayerData().setInt(uuid.toString() + ".wsrank", 900);
        }
        try{
            MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
        }catch (SQLException e){
            System.out.println(Misc.getPREFIX() + "Es ist ein Fehler beim Setzen der SpielerDaten für den Spieler " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            e.printStackTrace();
        }

    }

    public void setWarShipTeam() {

    }

    public String getWarShipTeam() {
        return null;
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
