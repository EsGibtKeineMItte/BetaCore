package de.wk.betacore.util;

import de.wk.betacore.BetaCore;
import de.wk.betacore.util.mysql.MySQL;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MoneySystem {

    public static int getMoney(UUID uuid){
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                return 0;
            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                return rs2.getInt("MONEY");
            }
        } catch (SQLException x) {
            BetaCore.debug("Fehler beim Abfragen des Ranges von " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            x.printStackTrace();
            return 0;
        }
    }

    public static void setMoney(UUID uuid, int money){
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
                MySQL.preparedStatement("UPDATE PLAYER_INFO SET Money = " +  money + " WHERE UUID = '" + uuid.toString() + "';").executeUpdate();

            } else {
                MySQL.preparedStatement("UPDATE PLAYER_INFO SET Money = " + money  + " WHERE UUID = '" + uuid.toString() + "';").executeUpdate();
            }
        } catch (SQLException e) {
            BetaCore.debug("Fehler des Setzens des Ranges vom Spieler " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            e.printStackTrace();
        }
    }


    /*
     public static void setRank(UUID uuid, String rank) {

        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
                MySQL.preparedStatement("UPDATE PLAYER_INFO SET RANK = " + "'" + rank + "' WHERE UUID = '" + uuid.toString() + "';").executeUpdate();

            } else {
                MySQL.preparedStatement("UPDATE PLAYER_INFO SET RANK = '" + rank + "'" + " WHERE UUID = '" + uuid.toString() + "';").executeUpdate();
            }


            //UPDATE `PLAYER_INFO` SET `RANK`='ADMIN' WHERE UUID = 'f72a9bcc-c01e-454a-bc94-9caa9384504f'
        } catch (SQLException e) {
            BetaCore.debug("Fehler des Setzens des Ranges vom Spieler " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            e.printStackTrace();
        }
    }

    public static Rank getRank(UUID uuid) {
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                return Rank.USER;
            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                return Rank.valueOf(rs2.getString("RANK"));
            }
        } catch (SQLException x) {
            BetaCore.debug("Fehler beim Abfragen des Ranges von " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            x.printStackTrace();
            return Rank.USER;
        }
    }
     */
}
