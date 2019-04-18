package de.wk.betacore.util;

import de.leonhard.storage.Json;
import de.wk.betacore.environment.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MaintanceSystem {

    private static Json maintance = new Json("maintance", Environment.getPathToDataFolder());

    public static void addPlayer(final UUID uuid) {
        List<String> addedPlayers = getAddedPlayers();
        addedPlayers.add(uuid.toString());
        maintance.set("maintance.added", addedPlayers);
    }

    public static List<String> getAddedPlayers() {
        return (maintance.getStringList("maintance.added") == null ? new ArrayList<>() : maintance.getStringList("maintance.added") );
    }

    public static void remove(final UUID uuid) {
        List<String> addedPlayers = new ArrayList<>(getAddedPlayers());
        addedPlayers.remove(uuid.toString());
        maintance.set("maintance.added", addedPlayers);
    }

    public static void ban(final UUID uuid) {
        List<String> banned = new ArrayList<>(getBannedPlayers());
        banned.add(uuid.toString());
        maintance.set("maintance.added", banned);
    }

    public static List<String> getBannedPlayers() {
        return (maintance.getStringList("maintance.banned") == null ? new ArrayList<>() : maintance.getStringList("maintance.banned"));
    }

    public static boolean isAdded(UUID uuid) {
        return getAddedPlayers().contains(uuid.toString());
    }

    public static boolean isBanned(UUID uuid) {
        return getBannedPlayers().contains(uuid.toString());
    }

    public static void startMaintance(){
        maintance.set("isMaintance", true);
    }

    public static void endMaintance(){
        maintance.set("isMaintance", false);
    }

    public static boolean isMaintance(){
        return maintance.getBoolean("isMaintance");
    }

}
