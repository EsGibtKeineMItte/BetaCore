package de.wk.betacore.util.mysql;

import de.butzlabben.world.wrapper.SystemWorld;
import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.Bukkit;

;import java.sql.*;
import java.util.UUID;

public class MySQL {
    private static Connection connection;
    ConfigManager cm = new ConfigManager();


    private int port = cm.getGlobalConfig().getInt("MySQL.port");
    private String host = cm.getGlobalConfig().getString("MySQL.host");
    private String database = cm.getGlobalConfig().getString("MySQL.database");
    private String password = cm.getGlobalConfig().getString("MySQL.password");
    private String username = cm.getGlobalConfig().getString("MySQL.username");


    public void openConnection() throws SQLException {
        if (connection != null && connection.isClosed()) {
            return;
        }
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
    }

    public static PreparedStatement preparedStatement(String query) {
        PreparedStatement ps = null;

        try{
            ps = connection.prepareStatement(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ps;
    }

    public static boolean playerExistis(UUID uuid){
        if(Bukkit.getOfflinePlayer(uuid) == null){
            BetaCore.debug("Es wurde versucht in der SQL-Datenbank nach einem Spieler zu suchen, der nicht existiert.");
            return false;
        }
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
               return false;
            }else{
                return true;
            }
        }catch(SQLException e){
            BetaCore.debug("Es ist ein Fehler, bei dem Versuch zu prüfen, ob sich der Spieler " + Bukkit.getOfflinePlayer(uuid).getName() + " in der Datenbank befindet.");
            System.out.println("");
            e.printStackTrace();
            return false;
        }
    }



}
