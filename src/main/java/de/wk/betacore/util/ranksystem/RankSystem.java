package de.wk.betacore.util.ranksystem;

import com.google.common.base.Strings;
import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.MySQL;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RankSystem {
    private static Json data = FileManager.getPlayerData();

    public static void setRank(UUID uuid, String rank) {
        if(!(Environment.isMysql())){
            if(!(EnumUtils.isValidEnum(Rank.class, rank))){
                return;
            }
            data.set(uuid.toString() + ".rank", rank);
            return;
        }



        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
                MySQL.preparedStatement("UPDATE PLAYER_INFO SET RANK = '" + rank + "'" + " WHERE UUID = '" + uuid.toString() + "';").executeUpdate();
                BetaCore.debug("Es wurde ein Rang eines Spielers gesetzt, der noch auf dem Netzwerk war.");

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
        if(!(Environment.isMysql())){
            if(Strings.isNullOrEmpty(data.getString(uuid.toString() + ".rank"))){
                return Rank.USER;
            }
            if(EnumUtils.isValidEnum(Rank.class, data.getString(uuid.toString() + ".rank"))){
                return Rank.valueOf(data.getString(uuid.toString() + ".rank"));
            }else{
                Environment.debug("In den Datenbanken ist ein fehlerhafter Rang für " + uuid + " eingetragen: " + data.getString(uuid.toString() + ".rank"));
            }
        }
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                return Rank.USER;
            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                try{
                    return Rank.valueOf(rs2.getString("RANK"));
                }catch (IllegalArgumentException e){
                    BetaCore.debug("Fehler 000");
                    BetaCore.debug("Für den Spieler mit der UUID " + uuid.toString() + " ist in der MySQL Datenbank ein Rang eingetragen, der nicht existiert.");
                    return Rank.USER;
                }

            }
        } catch (SQLException x) {
            BetaCore.debug("Fehler beim Abfragen des Ranges von " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            x.printStackTrace();
            return Rank.USER;
        }
    }
}
