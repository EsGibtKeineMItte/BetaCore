package de.wk.betacore.listener.Spigot;

import de.wk.betacore.BetaCore;
import de.wk.betacore.util.data.Misc;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

@Getter
public class TNTTracer implements Listener {

    @Getter
    private static HashMap<World, ArrayList<Location>> locationHashMap = new HashMap<>();
    @Getter
    private static List<World> checkedWorlds = new ArrayList<>();
    @Getter
    private static ArrayList<Location> locations = new ArrayList<>();


    public static void addPlayerToTracer(Player player) {
        World w = player.getWorld();
        if (checkedWorlds.contains(w)) {
            return;
        }

        checkedWorlds.add(w);
    }

    public static void removePlayerFromTracer(Player player) {
        World w = player.getWorld();

        if (!(checkedWorlds.contains(w))) {
            return;
        }
        checkedWorlds.remove(w);
        locations.clear();
        locationHashMap.remove(w);
    }



    public static boolean showTraces(World w, Player player) {

        if (!(locationHashMap.containsKey(w))) {
            return false;
        }
        ArrayList<Location> locations = locationHashMap.get(w);
        BetaCore.debug("Location-Size " + locations.size());
        for (Location loc : locations) {

            for(Player p : w.getPlayers()){
                player.sendBlockChange(loc, Material.STAINED_GLASS, (byte) 3);
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean showTraces(World w, Player player, byte type) {

        if (!(locationHashMap.containsKey(w))) {
            return false;
        }
        ArrayList<Location> locations = locationHashMap.get(w);
        BetaCore.debug("Location-Size " + locations.size());
        for (Location loc : locations) {

            for(Player p : w.getPlayers()){
                player.sendBlockChange(loc, Material.STAINED_GLASS, type);
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean unShowTraces(World w, Player player) {
        if (!(locationHashMap.containsKey(w))) {
            return false;
        }
        ArrayList<Location> locations = locationHashMap.get(w);
        List<Location> finalLocation = removeDuplicate(locations);

        for (Location loc : locations) {
            for(Player p : w.getPlayers()){
                p.sendBlockChange(loc, Material.AIR, (byte) 0);
            }
        }
        return true;
    }

    private static void unShowTraces(World w) {
        if (!(locationHashMap.containsKey(w))) {
            return;
        }
        ArrayList<Location> locations = locationHashMap.get(w);

        for (Location loc : locations) {
            w.getBlockAt(loc).setType(Material.AIR);
        }
    }


    @EventHandler
    public void onTNT(EntityExplodeEvent e) {

        World w = e.getLocation().getWorld();

        if (!(checkedWorlds.contains(w))) {
            return;
        }

        checkedWorlds.remove(w);

        if (locationHashMap.containsKey(w)) {
            locations = locationHashMap.get(w);
        } else {
            locations = new ArrayList<>();
        }

        for(Player player : e.getLocation().getWorld().getPlayers()){
            player.sendMessage(Misc.PREFIX + "§eAufnahme pausiert. Warte mit dem beladen von TNT etwas.");
        }


        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(BetaCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Entity entity : w.getEntities()) {
                    if (entity.getType() == EntityType.PRIMED_TNT) {

                        Location loc = new Location(e.getLocation().getWorld(), round1(entity.getLocation().getX() + 0.1),
                                round1(entity.getLocation().getY()), round1(entity.getLocation().getZ()) + 0.1);

                        if (!(locations.contains(loc)) && (loc.getBlock().getType() != Material.WATER)) {
                            locations.add(loc);
                            BetaCore.debug(loc.toString());
                        }
                        if (locations.size() > 1000) {
                            locations.clear();
                        }
                    }
                }
            }
        }, 2L, 2L);



        Bukkit.getScheduler().runTaskLater(BetaCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                checkedWorlds.add(w);
                for(Player player : e.getLocation().getWorld().getPlayers()){
                    player.sendMessage(Misc.PREFIX + "§aDu kannst nun wieder schießen.");
                }
                Bukkit.getScheduler().cancelAllTasks();
            }
        }, 60L);

        locationHashMap.put(w, locations);
    }


    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        locationHashMap.remove(e.getFrom());
        unShowTraces(e.getFrom());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

    }


    public static List<Location> removeDuplicate(List<Location> list) {
        Set<Location> set = transformListIntoSet(list);
        List<Location> listWithoutDuplicate = transformSetIntoList(set);
        return listWithoutDuplicate;
    }

    public static Set<Location> transformListIntoSet(List<Location> list) {
        Set<Location> set = new LinkedHashSet<>();
        set.addAll(list);
        return set;
    }

    public static List<Location> transformSetIntoList(Set<Location> set) {
        List<Location> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    public static double round1(double value){
        return Math.round(10.0 * value)/10;
    }

}
