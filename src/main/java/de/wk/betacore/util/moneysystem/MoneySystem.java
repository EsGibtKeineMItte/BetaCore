package de.wk.betacore.util.moneysystem;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.MySQL;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MoneySystem {

    private static Json data = FileManager.getPlayerData();

    private MoneySystem(){

    }


    public static int getMoney(UUID uuid){

        if(!(Environment.isMysql())){
            return data.getInt(uuid.toString() + ".money");
        }

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

        if(!(Environment.isMysql())){
            data.set(uuid.toString() + ".money", money);
            return;
        }

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


    public static void decreaseMoney(UUID uuid, int money){
        setMoney(uuid, getMoney(uuid) - money);
    }

    public static void increaseMoney(UUID uuid, int money){
        setMoney(uuid, getMoney(uuid) + money);
    }
}
