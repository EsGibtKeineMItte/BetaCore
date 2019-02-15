package de.wk.betacore.util.ranksystem;

import de.wk.betacore.util.ConfigManager;

import java.util.UUID;

public class RankSystem {
    ConfigManager cm = new ConfigManager();

    public void setRank(UUID uuid, Rank rank){
        cm.getPlayerData().setString(uuid.toString() + ".rank", rank.toString());
        cm.getPlayerData().save();
    }

    public Rank getRank(UUID uuid){
        if (cm.getPlayerData().getString(uuid.toString() + ".rank") == null) {
            return Rank.USER;
        }
        return Rank.valueOf(cm.getPlayerData().getString(uuid.toString() + ".rank"));
    }
}
