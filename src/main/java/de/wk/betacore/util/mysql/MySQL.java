package de.wk.betacore.util.mysql;

import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ConfigManager;

;import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private Connection connection;
    ConfigManager cm = new ConfigManager();


    private int port = cm.getGlobalConfig().getInt("MySQL.port");
    private String host = cm.getGlobalConfig().getString("MySQL.host");
    private String database = cm.getGlobalConfig().getString("MySQL.database");
    private String password = cm.getGlobalConfig().getString("MySQL.password");
    private String username = cm.getGlobalConfig().getString("MySQL.username");

        /*
    if (getGlobalConfig().getString("MySQL.host") == null) {
            getGlobalConfig().setString("MySQL.host", "");
        }
        if (getGlobalConfig().getInt("MySQL.port") == 0) {
            getGlobalConfig().setInt("MySQL.port", 3306);
        }
        if (getGlobalConfig().getString("MySQL.database") == null) {
            getGlobalConfig().setString("MySQL.database", "");
        }

        if (getGlobalConfig().getString("MySQL.password") == null) {
            getGlobalConfig().setString("MySQL.password", "");
        }
     */

    public void openConnection() throws SQLException {
        if (connection != null && connection.isClosed()) {
            return;
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
    }


}
