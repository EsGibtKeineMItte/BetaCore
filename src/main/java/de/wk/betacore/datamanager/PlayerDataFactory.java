package de.wk.betacore.datamanager;

import com.google.common.base.Strings;
import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.teamsystem.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public interface PlayerDataFactory {
    String PATH = Environment.getPathToDataFolder();
    Json PLAYER_DATA = new Json("playerdata", PATH);
    Json TEAMS = FileManager.getTeams();


    int getWsRank();

    int getMoney();

    int getFights();

    Rank getRank();

    String getFirstJoin();

    String getLastJoin();

    String getName();

    Team getTeam();

    boolean isBanned();

    boolean isMuted();

    void unmute();

    void unban();


    default void setupPlayer(UUID uuid, String name) {
        LocalDate now = LocalDate.now();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(cal.getTime());


        String joinDate = now.toString() + "-" + time;

        if (!(Environment.isMysql())) {
            setupWarPlayer(uuid, name, joinDate);
            return;
        }

        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {

                //Ist nicht im System.
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
                setPlayerData(uuid, name, joinDate);
            } else {

                /*
                PERMS
                 */

                setupWarPlayer(uuid, name, joinDate);
            }
        } catch (SQLException e) {
            BetaCore.debug("[MYSQL]Es ist ein Fehler, beim Erstellen des WarPlayers: " + name + " aufgetreten.");
        }

    }

    default void setupWarPlayer(UUID uuid, String name, String joinDate) {

        if (PLAYER_DATA == null) {

        }

        Environment.debug(PATH);

        if (Strings.isNullOrEmpty(PLAYER_DATA.getString(uuid.toString() + ".name"))) {
            PLAYER_DATA.set(uuid.toString() + ".name", name);
        }

        if (Strings.isNullOrEmpty(PLAYER_DATA.getString(uuid.toString() + ".wsteam"))) {
            PLAYER_DATA.set(uuid.toString() + ".wsteam", " ");
        }


        if (Strings.isNullOrEmpty(PLAYER_DATA.getString(uuid.toString() + ".rank"))) {
            PLAYER_DATA.set(uuid.toString() + ".rank", "USER");
        }

        if (Strings.isNullOrEmpty(PLAYER_DATA.getString(uuid.toString() + ".firstjoin"))) {
            PLAYER_DATA.set(uuid.toString() + ".firstjoin", joinDate);
        }

        if (PLAYER_DATA.getInt(uuid.toString() + ".wsrank") == 0) {
            PLAYER_DATA.set(uuid.toString() + ".wsrank", 900);
        }

        if (!(PLAYER_DATA.getBoolean(uuid.toString() + ".banned"))) {//TODO make usable -> Vllt andere Config.
            PLAYER_DATA.set(uuid.toString() + ".banned", false);
        }
        if (!(PLAYER_DATA.getBoolean(uuid.toString() + ".muted"))) {
            PLAYER_DATA.set(uuid.toString() + ".muted", false);
        }
        if (PLAYER_DATA.getInt(uuid.toString() + ".fights") == 0) {
            PLAYER_DATA.set(uuid.toString() + ".fights", 0);
        }
        PLAYER_DATA.set(uuid.toString() + ".lastjoin", joinDate);
    }


    static void setPlayerData(final UUID uuid, final String name, final String joinDate) {
        PLAYER_DATA.set(uuid.toString() + ".wsrank", 900);
        PLAYER_DATA.set(uuid.toString() + ".wsteam", "");
        PLAYER_DATA.set(uuid.toString() + ".name", name);
        PLAYER_DATA.set(uuid.toString() + ".banned", false);
        PLAYER_DATA.set(uuid.toString() + ".muted", false);
        PLAYER_DATA.set(uuid.toString() + ".fights", 0);
        PLAYER_DATA.set(uuid.toString() + ".firstjoin", joinDate);
        PLAYER_DATA.set(uuid.toString() + ".lastjoin", joinDate);
    }

    static void ban(final UUID uuid) {
        List<String> bannedPlayers = new ArrayList<>(getBannedPlayers());
        bannedPlayers.add(uuid.toString());
        PLAYER_DATA.set("bannedPlayers", bannedPlayers);
    }

    static  void unBan(final UUID uuid){
        List<String> bannedPlayers = new ArrayList<>(getBannedPlayers());
        bannedPlayers.remove(uuid.toString());
        PLAYER_DATA.set("bannedPlayers", bannedPlayers);
    }

    static ArrayList<String> getBannedPlayers() {
        if (PLAYER_DATA.getStringList("bannedPlayers") == null) {
            return new ArrayList<>();
        }

        ArrayList<String> result = new ArrayList<>(PLAYER_DATA.getStringList("bannedPlayers"));
        return result;
    }

    static boolean isBanned(UUID uuid) {
        return (getBannedPlayers().contains(uuid.toString()));
    }
}


