package de.wk.betacore.objects;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.datamanager.PlayerDataFactory;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.teamsystem.Team;
import de.wk.betacore.util.teamsystem.TeamSystem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WarPlayer implements PlayerDataFactory {


    static Json data = FileManager.getPlayerData();

    public final String UUID;

    private int wsrank, money, fights;
    private Rank rank;
    private String team, firstjoin, lastjoin, uuid, name;
    private boolean banned, muted;
    private boolean inWarShipTeam;
    private Team wsteam;


    public WarPlayer(UUID uuid, String name) {
        setupPlayer(uuid, name);
        this.UUID = uuid.toString();
        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();

            if (!(rs.getInt(1) == 0)) {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                String rank = rs2.getString("RANK");
                this.money = rs2.getInt("MONEY");
                this.firstjoin = data.getString(uuid.toString() + ".firstjoin");
                this.lastjoin = data.getString(uuid.toString() + ".lastjoin");
                this.team = data.getString(uuid.toString() + ".wsteam");
                this.wsrank = data.getInt(uuid.toString() + ".wsrank");
                this.fights = data.getInt(uuid.toString() + ".fights");
                if (isInWarShipTeam()) {
                    this.wsteam = new Team(team);
                }

                this.rank = Rank.valueOf(rank);
                this.uuid = uuid.toString();
                this.name = name;
                this.banned = data.getBoolean(uuid.toString() + ".banned");
                this.muted = data.getBoolean(uuid.toString() + ".muted");

                data.set(uuid.toString() + ".money", this.money);
                data.set(uuid.toString() + ".rank", rank);
            } else {
                throw new NullPointerException("Es wurde versucht einen War-Player zu erstellen, welcher nicht in den Datenbanken existiert.");
            }
            System.out.println(MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = " + "'" + uuid.toString() + "';").executeQuery());
        } catch (SQLException x) {
            BetaCore.debug("[MYSQL] Es ist ein Fehler, beim Erstellen des WarPlayers: " + name + " aufgetreten.");
            x.printStackTrace();
        }
    }

    public void setWarShipTeam(String teamName) {
        data.set(uuid + ".wsteam", teamName);

    }

    public String getTeamName() {
        return data.getString(uuid + ".wsteam");
    }

    public boolean isBanned() {
        return banned;
    }

    public boolean isMuted() {
        return muted;
    }


    public void ban(int banID) {
        this.banned = true;
    }

    public void ban(String reason, String time) {
        this.banned = true;
    }


    public void mute(int muteID) {

    }

    public void mute(String reason, String time) {
        this.muted = true;
    }

    @Override
    public int getWsRank(UUID uuid) {
        return this.wsrank;
    }

    @Override
    public int getMoney(UUID uuid) {
        return this.money;
    }

    @Override
    public int getFights(UUID uuid) {
        return this.fights;
    }

    @Override
    public Rank getRank(UUID uuid) {
        return this.rank;
    }

    @Override
    public String getFirstJoin(UUID uuid) {
        return this.firstjoin;
    }

    @Override
    public String getLastJoin(UUID uuid) {
        return null;
    }

    @Override
    public String getName(UUID uuid) {
        return this.name;
    }

    @Override
    public Team getTeam(UUID uuid) {
        return this.wsteam;
    }

    @Override
    public boolean isBanned(UUID uuid) {
        return this.banned;
    }

    @Override
    public boolean isMuted(UUID uuid) {
        return this.muted;
    }

    @Override
    public void unmute(UUID uuid) {
        this.muted = false;
    }

    @Override
    public void unban(UUID uuid) {
        this.muted = false;
    }


    public boolean isInWarShipTeam() {
        return TeamSystem.isActiveWarShipTeam(team);
    }


}
