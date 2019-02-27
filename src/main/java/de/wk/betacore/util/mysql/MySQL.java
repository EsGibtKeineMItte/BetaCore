package de.wk.betacore.util.mysql;

import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ConfigManager;

;import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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



}
