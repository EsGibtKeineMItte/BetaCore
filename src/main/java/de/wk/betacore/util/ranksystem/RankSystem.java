package de.wk.betacore.util.ranksystem;

import de.wk.betacore.util.ConfigManager;

import java.util.UUID;

public class RankSystem {


    public void setRank(UUID uuid, Rank rank){
        ConfigManager cm = new ConfigManager();
        cm.getPlayerData().setString(uuid.toString() + ".rank", rank.name().toUpperCase());
        cm.getPlayerData().save();
    }

    public Rank getRank(UUID uuid){
        ConfigManager cm = new ConfigManager();
        cm.getPlayerData().reload();
        if (cm.getPlayerData().getString(uuid.toString() + ".rank") == null) {
            return Rank.USER;
        }
        return Rank.valueOf(cm.getPlayerData().getString(uuid.toString() + ".rank").toUpperCase());

    }
}
