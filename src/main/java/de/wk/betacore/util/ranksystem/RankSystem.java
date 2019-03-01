package de.wk.betacore.util.ranksystem;

import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.mysql.MySQL;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RankSystem {
    ConfigManager cm = new ConfigManager();


    public void setRank(UUID uuid, Rank rank) {
        try {
            MySQL.preparedStatement("UPDATE PLAYER_INFO SET RANK = " + "'" + rank + "' WHERE UUID = '" + uuid.toString() + "';").executeUpdate();
//UPDATE `PLAYER_INFO` SET ``RANK`='ADMIN' WHERE UUID = 'f72a9bcc-c01e-454a-bc94-9caa9384504f'
        } catch (SQLException e) {
            System.out.println("Fehler des Setzens des Ranges vom Spieler " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            e.printStackTrace();
        }
    }

    public Rank getRank(UUID uuid) {
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (!(rs.getInt(1) == 0)) {
                return Rank.USER;
            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                return Rank.valueOf(rs2.getString("RANK"));
            }
        } catch (SQLException x) {
            System.out.println("Fehler beim Abfragen des Ranges von " + Bukkit.getOfflinePlayer(uuid).getName() );
            System.out.println("");
            x.printStackTrace();
        }
       return Rank.USER;
    }
}
