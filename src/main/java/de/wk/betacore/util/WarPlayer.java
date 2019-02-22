package de.wk.betacore.util;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WarPlayer extends CraftPlayer {
    TeamSystem teamSystem = new TeamSystem();

    public WarPlayer(Player player) {
        super((CraftServer) Bukkit.getServer(), ((CraftPlayer) player).getHandle());
    }

    public void putÍnTeam(String teamName, WarPlayer player) {


    }

    public void getTeam(String teamName) {

    }

    public void ban(WarPlayer player) {

    }

    public void test(){

    }


    @Override
    public void hidePlayer(Plugin plugin, Player player) {

    }

    @Override
    public void showPlayer(Plugin plugin, Player player) {

    }
}
