package de.wk.betacore.listener.Spigot;


import de.wk.betacore.util.ConfigManager;
import de.wk.betacore.util.WarPlayer;
import de.wk.betacore.util.mysql.MySQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DataSetter implements Listener {
    ConfigManager cm = new ConfigManager();

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {

        Player player = e.getPlayer();


        WarPlayer.getWarPlayer(e.getPlayer());
        WarPlayer wp = WarPlayer.getWarPlayer(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PreparedStatement ps;

        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) {
                //Ist nicht im System.
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + player.getUniqueId() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();

            } else {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + player.getUniqueId() + "';").executeQuery();
                rs2.next();

                String rank = rs2.getString("RANK");
                int money = rs2.getInt("MONEY");
                String timespamp = rs2.getTimestamp("JOIN_DATE").toString();
                System.out.println(rank);
                System.out.println(money);
                System.out.println(timespamp);

            }

            System.out.println(MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = " + "'" + player.getUniqueId() + "';").executeQuery());
        } catch (SQLException x) {
            x.printStackTrace();
        }

        LocalDate today = LocalDate.now();
        if (cm.getPlayerData().getString(e.getPlayer().getUniqueId().toString() + ".firstjoin") == null) {
            cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".firstjoin", today.toString());
            return;
        }
        cm.getPlayerData().setString(e.getPlayer().getUniqueId().toString() + ".lastjoin", today.toString());
    }
}
