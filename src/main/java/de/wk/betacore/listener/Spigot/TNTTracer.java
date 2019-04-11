package de.wk.betacore.listener.Spigot;

import com.google.common.annotations.Beta;
import de.wk.betacore.BetaCore;
import de.wk.betacore.util.data.Misc;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.*;

public class TNTTracer implements Listener {

    @Getter
    private static HashMap<World, ArrayList<Location>> locationHashMap = new HashMap<>();
    @Getter
    private static ArrayList<World> checkedWorlds = new ArrayList<>();

    private static boolean canceled;

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


    public static void showTraces(World w, Player player) {

        if (!(locationHashMap.containsKey(w))) {
            player.sendMessage(Misc.PREFIX + "§cDu hast noch keine Traces aufgenommen.");
            return;
        }
        ArrayList<Location> locations = locationHashMap.get(w);
        List<Location> finalLocations = removeDuplicate(locations);
        BetaCore.debug("Location-Size " + locations.size());
        for (Location loc : locations) {
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean unShowTraces(World w, Player player) {
        if (!(locationHashMap.containsKey(w))) {
            return false;
        }
        ArrayList<Location> locations = locationHashMap.get(w);
        List<Location> finalLocation = removeDuplicate(locations);

        for (Location loc : locations) {
            w.getBlockAt(loc).setType(Material.AIR);
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


    public static ArrayList<Location> testForDuplicates(ArrayList<Location> locs) {

        ArrayList<Location> result = new ArrayList<>();

        int i = 0;
        int dopplungen = 0;
        for (Location loc : locs) {
            if (locs.get(i).equals(locs.get(i + 1))) {
                i++;
                dopplungen++;
                continue;
            }
            /*
            Check whether a RedstoneBlock is under my Block
             */


            Location testLocY = loc;

            if (testLocY.getBlock().getType() == Material.REDSTONE_BLOCK) {
                locs.remove(loc);
                BetaCore.debug("Dopplung entfernt!");
                dopplungen++;
                continue;
            }

            Location newLocation = locs.get(i);

            DecimalFormat f = new DecimalFormat("#0.0");


            newLocation.setX(Math.round(loc.getX()));
            newLocation.setY(Math.round(loc.getY()));
            newLocation.setZ(Math.round(loc.getZ()));

            if (!result.contains(newLocation)) {
                result.add(newLocation);
                i++;
            }else{
                dopplungen++;
                i++;
            }
        }
        Bukkit.getServer().broadcastMessage("Dopplungen: " + dopplungen);
        return result;

    }


    @EventHandler
    public void onTNT(EntityExplodeEvent e) {

        if(canceled){
            return;
        }

        World w = e.getLocation().getWorld();

        if (!(checkedWorlds.contains(w))) {
            BetaCore.debug("Die Welt des Spielers wird nicht getestet.");
            return;
        }

        if (locationHashMap.containsKey(w)) {
            locations = locationHashMap.get(w);
        } else {
            locations = new ArrayList<>();
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(BetaCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Entity entity : w.getEntities()) {
                    if (entity.getType() == EntityType.PRIMED_TNT) {
                        locations.add(entity.getLocation());
                        BetaCore.debug(entity.getLocation().toString());
                        if (locations.size() > 1000) {
                            locations.clear();
                        }
                    }
                }
            }
        }, 3L, 2L);


        Bukkit.getScheduler().runTaskLater(BetaCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                canceled = true;
            }
        }, 10L);

        Bukkit.getScheduler().runTaskLater(BetaCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                canceled = false;
                Bukkit.getScheduler().cancelAllTasks();
                BetaCore.debug("TASKS");
            }
        }, 60L);

        locations = testForDuplicates(locations);
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


}
