package de.wk.betacore.datamanager;

import de.wk.betacore.BetaCore;
import de.wk.betacore.environment.EnvironmentManager;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.teamsystem.Team;
import io.bluecube.thunderbolt.io.ThunderFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface PlayerDataFactory {
    String PATH = EnvironmentManager.getPathToDataFolder();
    ThunderFile PLAYER_DATA = FileManager.getPlayerData();
    ThunderFile TEAMS = FileManager.getTeams();


    int getWsRank(UUID uuid);

    int getMoney(UUID uuid);

    int getFights(UUID uuid);

    Rank getRank(UUID uuid);

    String getFirstJoin(UUID uuid);

    String getLastJoin(UUID uuid);

    String getName(UUID uuid);

    Team getTeam(UUID uuid);

    boolean isBanned(UUID uuid);

    boolean isMuted(UUID uuid);
    
    void unmute(UUID uuid);
    
    void unban(UUID uuid);


    default void setupPlayer(UUID uuid, String name) {
        LocalDate now = LocalDate.now();
        LocalTime time = LocalTime.now();

        String joinDate = now.toString() + "-" + time.toString();
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {

                //Ist nicht im System.
                MySQL.preparedStatement("INSERT INTO PLAYER_INFO(UUID, RANK, MONEY, JOIN_DATE) VALUES ('" + uuid.toString() + "'," + "DEFAULT, DEFAULT, DEFAULT);").executeUpdate();
                PLAYER_DATA.set(uuid.toString() + ".wsrank", 900);
                PLAYER_DATA.set(uuid.toString() + ".wsteam", "");
                PLAYER_DATA.set(uuid.toString() + ".name", name);
                PLAYER_DATA.set(uuid.toString() + ".banned", false);
                PLAYER_DATA.set(uuid.toString() + ".muted", false);
                PLAYER_DATA.set(uuid.toString() + ".fights", 0);
                PLAYER_DATA.set(uuid.toString() + ".firstjoin", joinDate);
                PLAYER_DATA.set(uuid.toString() + ".lastjoin", joinDate);
                PLAYER_DATA.save();
            }else{
                if(PLAYER_DATA.getInt(uuid.toString() + ".wsrank") == 0){
                    PLAYER_DATA.set(uuid.toString() + ".wsrank", 900);
                }
                if(PLAYER_DATA.getString(uuid.toString() + ".wsteam").equalsIgnoreCase("")){
                    PLAYER_DATA.set(uuid.toString() + ".wsteam", " ");

                }
                if(PLAYER_DATA.getString(uuid.toString() + ".name") == null){
                    PLAYER_DATA.set(uuid.toString() + ".name", name);
                }
                if(!PLAYER_DATA.getBoolean(uuid.toString() + ".banned")){
                    PLAYER_DATA.set(uuid.toString() + ".banned", false);
                }
                if(!PLAYER_DATA.getBoolean(uuid.toString() + ".muted")){
                    PLAYER_DATA.set(uuid.toString() + ".muted", false);
                }
                if(PLAYER_DATA.getInt(uuid.toString() + ".fights") == 0){
                    PLAYER_DATA.set(uuid.toString() + ".fights", 0);
                }
                PLAYER_DATA.set(uuid.toString() + ".lastjoin", joinDate);

                PLAYER_DATA.save();
            }
        }catch(SQLException e){
            BetaCore.debug("[MYSQL]Es ist ein Fehler, beim Erstellen des WarPlayers: " + name + " aufgetreten.");
        } catch (IOException e) {
            BetaCore.debug("[JSON-FILES]Es ist ein Fehler, beim Erstellen des WarPlayers: " + name + " aufgetreten.");
            e.printStackTrace();
        }

    }
}
