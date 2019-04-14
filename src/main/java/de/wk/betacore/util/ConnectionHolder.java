package de.wk.betacore.util;


import de.wk.betacore.BetaCore;
import de.wk.betacore.BetaCoreBungee;
import de.wk.betacore.environment.Environment;
import lombok.Getter;
//import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class ConnectionHolder {

    private Connection connection;
    @Getter
    private Object lock = new Object();
    private boolean taskRunning;
    @Getter
    private int statements;

    private String database;

    //Eine Datenbank (Schrank) ist wie eine Frau, in dem man keinen
    //Platz verschwenden möchte

    public void connect(String host, String database, int port, String user, String password) {
        this.database = database;
        synchronized (lock) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Environment.debug("Die Treiber funktionieren nicht.");
                return;
            }
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password);
                // Schrank tot gefickt
                if (Environment.isBungeecord()) {
                    System.out.print("DAFUQ?");
//                    BetaCoreBungee.log("Zum angegebenen MySQL Server verbunden!");
//                    if (!taskRunning) {
//                        System.out.print("HALLO ICH BIN EIN SCHEDULER DER SINNLOS GEFICKT WURDE");
//                        ProxyServer.getInstance().getScheduler().schedule(BetaCoreBungee.getInstance(), () -> {
//                            taskRunning = true;
//                            statements = 0;
//                            if (!testConnection()) {
//                                close();
//                                connect(host, database, port, user, password);
//
//                            }
//                        }, 1, 1, TimeUnit.MINUTES);
//                    }
                } else {
                    //Schrank penetriert
                    BetaCore.log("Zum angegebenen MySQL Server verbunden!");
                    if (!taskRunning) {
                        Bukkit.getScheduler().runTaskTimer(BetaCore.getInstance(), () -> {
                            taskRunning = true;
                            statements = 0;
                            if (!testConnection()) {
                                close();
                                connect(host, database, port, user, password);

                            }
                        }, 20 * 60, 20 * 60);
                    }
                }
            } catch (SQLException e) {
                // Falscher Schlüssel für Schranktür ODER GAR FALSCHER
                // SCHRANK?!?!?!?
                Environment.debug("Verbindung zum Server fehlgeschlagen");
                e.printStackTrace();
            }
        }
    }

    public void close() {
        synchronized (lock) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Threadsafe implementation of connection.prepareStatement
     *
     * @param sql - SQL command
     * @return
     * @throws SQLException if an error occurs
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        synchronized (lock) {
            statements++;
            return connection.prepareStatement(sql);
        }
    }

    /**
     * Threadsafe implementation of preperedstatement.executeQuery
     *
     * @param ps - PreparedStatement
     * @return
     * @throws SQLException if an error occurs
     */
    public ResultSet executeQuery(PreparedStatement ps) throws SQLException {
        synchronized (lock) {
            return ps.executeQuery();
        }
    }

    /**
     * Threadsafe implementation of preperedstatement.executeUpdate
     *
     * @param ps - PreparedStatement
     * @return
     * @throws SQLException if an error occurs
     */
    public int executeUpdate(PreparedStatement ps) throws SQLException {
        synchronized (lock) {
            return ps.executeUpdate();
        }
    }

    public boolean testConnection() {
        try {
            PreparedStatement ps = prepareStatement("USE " + database);
            executeUpdate(ps);
            return true;
        } catch (Exception e) {
            Environment.debug("§cConnection test failed:");
            e.printStackTrace();
        }
        return false;
    }
}
