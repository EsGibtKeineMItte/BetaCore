package de.wk.betacore.util;

import de.wk.betacore.BetaCore;
import de.wk.betacore.listener.Spigot.RecordListener;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Record implements Listener {
    private UUID uuid;
    private ArrayList<Location> recordedBlocks = new ArrayList();
    private HashMap<Location, Byte> torches = new HashMap();
    private static HashMap<UUID, Record> recs = new HashMap();
    private Iterator<Location> i;
    private Iterator<Location> i1;
    private Location stopLocation;
    private long period;
    private long lastStart;
    private BukkitRunnable br;


    private Record(Player player) {
        this.i = this.recordedBlocks.iterator();
        this.i1 = this.torches.keySet().iterator();
        this.stopLocation = null;
        this.period = 0L;
        this.lastStart = 0L;
        this.br = null;
        this.uuid = player.getUniqueId();
    }


    public void startRecord() {
        this.recordedBlocks.clear();
        this.torches.clear();
        this.stopRecord();
        this.stopPlay(false);
        RecordListener.getInstance().register(this.uuid, this);
    }

    public void stopRecord() {
        RecordListener.getInstance().unregister(this.uuid);
    }


    public void stopPlay(boolean tell) {
        if (this.br != null) {
            this.br.cancel();
        }

        if (tell && this.period != 0L) {
            Bukkit.getPlayer(this.uuid).sendMessage("§eDurchschnittliche Kadenz: " + this.period + "ms");
        }

        this.period = 0L;
        this.lastStart = 0L;
    }

    public void play(long waitTicks, int waitTorchTicks) {
        this.stopPlay(false);
        this.playOnce(waitTicks, waitTorchTicks);
    }

    private void playOnce(long waitTicks, final int waitTorchTicks) {
        if (this.recordedBlocks.size() == 0) {
            Bukkit.getPlayer(this.uuid).sendMessage("§cDu hast nocht nichts erfassen lassen");
        } else if (!Bukkit.getPlayer(this.uuid).getLocation().getWorld().equals(this.recordedBlocks.get(0).getWorld())) {
            this.recordedBlocks.clear();
            this.torches.clear();
            Bukkit.getPlayer(this.uuid).sendMessage("§cDu kannst nichts in einer anderen Welt abspielen lassen");
            this.stopPlay(false);
        } else {
            if (waitTicks == -1L) {
                waitTicks = (long) (49 / this.recordedBlocks.size());
            }

            Bukkit.getPlayer(this.uuid).sendMessage(Misc.getPREFIX() + "§eAktionen werden im Abstand von §a" + waitTicks + "§e Ticks ausgeführt. Fackeln werden nach §a" + waitTorchTicks + "§e Ticks entfernt");
            this.i = this.recordedBlocks.iterator();
            this.i1 = this.torches.keySet().iterator();
            this.br = new BukkitRunnable() {
                public void run() {
                    Location loc;
                    if (Record.this.i.hasNext()) {
                        loc = Record.this.stopLocation;
                        if (loc == null) {
                            loc = Record.this.i.next();
                        }

                        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 1.0D, 1.0D, 1.0D);
                        Iterator var4 = entities.iterator();

                        while (var4.hasNext()) {
                            Entity e = (Entity) var4.next();
                            if (e instanceof TNTPrimed) {
                                Record.this.stopLocation = loc;
                                return;
                            }
                        }

                        Block bx = loc.getBlock();
                        if (bx.getType() != Material.AIR && bx.getType() != Material.WATER && bx.getType() != Material.STATIONARY_WATER && bx.getType() != Material.TNT) {
                            Record.this.stopLocation = loc;
                            return;
                        }

                        Record.this.stopLocation = null;
                        bx.setType(Material.TNT);
                    } else if (!Record.this.i1.hasNext()) {
                        Record.this.i = Record.this.recordedBlocks.iterator();
                        Record.this.i1 = Record.this.torches.keySet().iterator();
                        if (Record.this.lastStart != 0L) {
                            long duration = System.currentTimeMillis() - Record.this.lastStart;
                            if (Record.this.period != 0L) {
                                Record.this.period = (Record.this.period + duration) / 2L;
                            } else {
                                Record.this.period = duration;
                            }
                        }

                        Record.this.lastStart = System.currentTimeMillis();
                    } else {
                        while (Record.this.i1.hasNext()) {
                            loc = Record.this.i1.next();
                            Block b = loc.getBlock();
                            Material before = b.getType();
                            byte dataBefore = b.getData();
                             b.setTypeIdAndData(76, (Byte)Record.this.torches.get(loc), true);
                            b.setType(Material.TNT);

                            //   b.setType(76,(Byte)Record.this.torches.get(loc), true);
                            b.setType(Material.REDSTONE_TORCH_ON);


//                                Bukkit.getScheduler().runTaskLater(this.run()){
//                                b.setType(before);
//                                b.setData(dataBefore);
//                                b.setTypeIdAndData(76, dataBefore, true);
//                                System.out.println("Sehr gut");
//                           }, (long) waitTorchTicks)
                        }
                    }

                }
            };
            this.br.runTaskTimer(BetaCore.getInstance(), 0L, waitTicks);
        }
    }

    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (e.getBlockPlaced().getType() == Material.TNT) {
            if (this.recordedBlocks.size() >= 50) {
                p.sendMessage(Misc.getPREFIX() + "§cDu kannst nicht mehr als 50 Blöcke aufnehmen lassen");
                return;
            }

            if (!this.recordedBlocks.isEmpty() && !b.getLocation().getWorld().equals(this.recordedBlocks.get(0).getWorld())) {
                this.stopRecord();
                p.sendMessage(Misc.getPREFIX() + "§cDu kannst nicht in unterschiedlichen Welten aufnehmen lassen");
                return;
            }

            this.recordedBlocks.add(b.getLocation());
            p.sendMessage("§aTNT aufgenommen (" + this.recordedBlocks.size() + ")");
        } else if (e.getBlockPlaced().getType() == Material.REDSTONE_TORCH_ON) {
            if (this.torches.size() >= 50) {
                p.sendMessage(Misc.getPREFIX() + "§cDu kannst nicht mehr als 50 Blöcke aufnehmen lassen");
                return;
            }

            if (!this.recordedBlocks.isEmpty() && !b.getLocation().getWorld().equals(this.recordedBlocks.get(0).getWorld())) {
                this.stopRecord();
                p.sendMessage(Misc.getPREFIX() + "§cDu kannst nicht in unterschiedlichen Welten aufnehmen lassen");
                return;
            }

            this.torches.put(b.getLocation(), b.getData());
            p.sendMessage("§aFackel aufgenommen  (" + this.torches.size() + ")");
            e.setCancelled(true);
        }
    }

    public static Record getRecord(Player player) {
        Record r;
        if (recs.containsKey(player.getUniqueId())) {
            r = recs.get(player.getUniqueId());
            return r;
        } else {
            r = new Record(player);
            recs.put(player.getUniqueId(), r);
            return r;
        }
    }
}
