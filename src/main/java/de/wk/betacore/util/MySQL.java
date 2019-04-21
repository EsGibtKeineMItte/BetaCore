package de.wk.betacore.util;

import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.Environment;
import lombok.Getter;

import java.sql.*;
import java.util.UUID;

public class MySQL {
    @Getter
    private static Connection connection;
    private static ConfigManager cm = new ConfigManager();
    private static Json mysql = FileManager.getMysql();
    private static boolean isSetup;

    @Getter
    private static int port;
    @Getter
    private static String host;
    @Getter
    private static String database;
    @Getter
    private static String password;
    @Getter
    private static String username;



    /*
    Für später: Mit setup im Konstruktor, bzw getter per .getInstance neu schreiben.
     */

    public static void setup() {
        if (Environment.isSpigot()) {
            port = cm.getGlobalConfig().getInt("MySQL.port");
            host = cm.getGlobalConfig().getString("MySQL.host");
            database = cm.getGlobalConfig().getString("MySQL.database");
            password = cm.getGlobalConfig().getString("MySQL.password");
            username = cm.getGlobalConfig().getString("MySQL.username");
        } else {
            port = mysql.getInt("MySQL.port");
            host = mysql.getString("MySQL.host");
            database = mysql.getString("MySQL.database");
            password = mysql.getString("MySQL.password");
            username = mysql.getString("MySQL.username");
        }
        try {
            if (connection != null && connection.isClosed()) {
                return;
            }

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        isSetup = true;
    }


    public MySQL() {
        setup();
    }


    public static void openConnection() throws SQLException {
        if (connection != null && connection.isClosed()) {
            return;
        }

        if (!(isSetup)) {
            setup();
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
    }


    public static PreparedStatement preparedStatement(String query) {
        if (!(isSetup)) {
            setup();
        }

        PreparedStatement ps = null;

        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            }
            ps = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static boolean playerExistis(UUID uuid) {
        if (!(isSetup)) {
            setup();
        }
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            return rs.getInt(1) != 0;
        } catch (SQLException e) {
            Environment.debug("Es ist ein Fehler, beim Versuch zu prüfen, ob sich der Spieler " + uuid + " in der Datenbank befindet aufgetreten.");
            System.out.println("");
            e.printStackTrace();
            return false;
        }
    }
}
