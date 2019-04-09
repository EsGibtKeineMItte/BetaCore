package de.wk.betacore.util;

import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.EnvironmentManager;
import lombok.Getter;
import java.sql.*;
import java.util.UUID;

public class MySQL {
    private static Connection connection;
    ConfigManager cm = new ConfigManager();

    @Getter
    private int port;
    @Getter
    private String host;
    @Getter
    private String database;
    @Getter
    private String password;
    @Getter
    private String username;

    Json mysql = FileManager.getMysql();

    public MySQL() {

        if (EnvironmentManager.isSpigot()) {
            this.port = cm.getGlobalConfig().getInt("MySQL.port");
            this.host = cm.getGlobalConfig().getString("MySQL.host");
            this.database = cm.getGlobalConfig().getString("MySQL.database");
            this.password = cm.getGlobalConfig().getString("MySQL.password");
            this.username = cm.getGlobalConfig().getString("MySQL.username");
        } else {
            this.port = mysql.getInt("MySQL.port");
            this.host = mysql.getString("MySQL.host");
            this.database = mysql.getString("MySQL.database");
            this.password = mysql.getString("MySQL.password");
            this.username = mysql.getString("MySQL.username");
        }
    }


    public void openConnection() throws SQLException {
        if (connection != null && connection.isClosed()) {
            return;
        }
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
    }

    public static PreparedStatement preparedStatement(String query) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static boolean playerExistis(UUID uuid) {
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            EnvironmentManager.debug("Es ist ein Fehler, beim Versuch zu prüfen, ob sich der Spieler " + uuid + " in der Datenbank befindet aufgetreten.");
            System.out.println("");
            e.printStackTrace();
            return false;
        }
    }


}
