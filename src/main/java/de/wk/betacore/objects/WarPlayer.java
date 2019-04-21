package de.wk.betacore.objects;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.PlayerDataFactory;
import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.MySQL;
import de.wk.betacore.util.ranksystem.Rank;
import de.wk.betacore.util.teamsystem.Team;
import de.wk.betacore.util.teamsystem.TeamSystem;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WarPlayer implements PlayerDataFactory {


    private static final Json DATA = new Json("playerdata", PATH);

    @Getter
    public final UUID uuid;

    private int wsrank, money, fights;
    private Rank rank;
    public final String UUID;
    private String team, firstjoin, lastjoin, name;
    private boolean banned, muted;
    private boolean inWarShipTeam;
    private Team wsteam;


    public WarPlayer(UUID uuid, String name) {
        setupPlayer(uuid, name);
        this.UUID = uuid.toString();
        this.uuid = uuid;

        if (!(Environment.isMysql())) {

            //Data from Json
            this.firstjoin = DATA.getString(uuid.toString() + ".firstjoin");
            this.rank = Rank.valueOf(DATA.getString(uuid.toString() + ".rank"));
            this.money = DATA.getInt(uuid.toString() + ".money");
            this.firstjoin = DATA.getString(uuid.toString() + ".firstjoin");
            this.lastjoin = DATA.getString(uuid.toString() + ".lastjoin");
            this.team = DATA.getString(uuid.toString() + ".wsteam");
            this.wsrank = DATA.getInt(uuid.toString() + ".wsrank");
            this.fights = DATA.getInt(uuid.toString() + ".fights");
            this.banned = DATA.getBoolean(uuid.toString() + ".banned");
            this.muted = DATA.getBoolean(uuid.toString() + ".muted");



            //Assigments
            this.name = name;

            if (isInWarShipTeam()) {
                this.wsteam = new Team(team);
            }

            return;
        }

        try {
            ResultSet rs = MySQL.preparedStatement("SELECT COUNT(UUID) FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
            rs.next();

            if (!(rs.getInt(1) == 0)) {
                ResultSet rs2 = MySQL.preparedStatement("SELECT * FROM PLAYER_INFO WHERE UUID = '" + uuid.toString() + "';").executeQuery();
                rs2.next();
                String rank = rs2.getString("RANK");
                this.money = rs2.getInt("MONEY");
                this.firstjoin = DATA.getString(uuid.toString() + ".firstjoin");
                this.lastjoin = DATA.getString(uuid.toString() + ".lastjoin");
                this.team = DATA.getString(uuid.toString() + ".wsteam");
                this.wsrank = DATA.getInt(uuid.toString() + ".wsrank");
                this.fights = DATA.getInt(uuid.toString() + ".fights");
                if (isInWarShipTeam()) {
                    this.wsteam = new Team(team);
                }

                this.rank = Rank.valueOf(rank);
                this.name = name;
                this.banned = DATA.getBoolean(uuid.toString() + ".banned");
                this.muted = DATA.getBoolean(uuid.toString() + ".muted");

                DATA.set(uuid.toString() + ".money", this.money);
                DATA.set(uuid.toString() + ".rank", rank);
                DATA.set(uuid.toString() + ".name", name);
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
        DATA.set(uuid + ".wsteam", teamName);

    }

    public String getTeamName() {
        Environment.debug("UUID: " + uuid);
        return DATA.getString(uuid + ".wsteam");
    }

    public boolean isBanned(){
        return PlayerDataFactory.isBanned(uuid);
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
    public int getWsRank() {
        return this.wsrank;
    }

    @Override
    public int getMoney() {
        return this.money;
    }

    @Override
    public int getFights() {
        return this.fights;
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }

    @Override
    public String getFirstJoin() {
        return this.firstjoin;
    }

    @Override
    public String getLastJoin() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Team getTeam() {
        return this.wsteam;
    }

    @Override
    public void unmute() {
        this.muted = false;
    }

    @Override
    public void unban() {
        this.muted = false;
    }


    public boolean isInWarShipTeam() {
        return TeamSystem.isActiveWarShipTeam(team);
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(uuid) == null;
    }


    public void sendMessage(String msg) {
        if(isOnline()){
            Bukkit.getPlayer(uuid).sendMessage(msg);
        }
    }

}
