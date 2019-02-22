package de.wk.betacore.util.teamsystem;

import org.bukkit.entity.Player;
public class Team extends TeamSystem {

    private int wsrank;


    public Team(String teamName, String shortName, Player player) {
        createTeam(teamName, shortName, player);
    }
}
