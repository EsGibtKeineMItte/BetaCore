package de.wk.betacore.util.ranksystem;

import com.google.common.annotations.Beta;
import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.mysql.MySQL;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RankSystem {
    ConfigManager cm = new ConfigManager();


    public static void setRank(UUID uuid, String rank) {
        try {
            MySQL.preparedStatement("UPDATE PLAYER_INFO SET 'RANK' = " + "'" + rank + " WHERE UUID = '" + uuid.toString() + "';").executeQuery();
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
            if (!(rs.getInt(1) == 0)) {
                BetaCore.debug("Der Spieler war noch nicht im System, also wurde USER als Rank geturned");
                return Rank.USER;
            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                BetaCore.debug("Der Spieler war noch nicht im System, also wurde Spieler als Rang returned");
                return Rank.valueOf(rs2.getString("RANK"));
            }
        } catch (SQLException x) {
            BetaCore.debug("Fehler beim Abfragen des Ranges von " + Bukkit.getOfflinePlayer(uuid).getName());
            System.out.println("");
            x.printStackTrace();
            return Rank.USER;
        }
    }
}
