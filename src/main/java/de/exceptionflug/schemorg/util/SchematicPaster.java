package de.exceptionflug.schemorg.util;

import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.world.registry.WorldData;
import de.wk.betacore.environment.Environment;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SchematicPaster {

    public static final String SCHEM_DIR = Environment.getPathToDataFolder() + "/schematics";

    public static void pasteWarShip(final Location loc, final Player p, final File schem) {
        Location pastepoint = loc;
        pastepoint.setWorld(p.getWorld());

        try {
            // LOAD
            FileInputStream in = new FileInputStream(schem);
            ClipboardReader cr = ClipboardFormat.SCHEMATIC.getReader(in);
            BukkitWorld bw = new BukkitWorld(loc.getWorld());
            WorldData data = bw.getWorldData();
            BlockArrayClipboard c = (BlockArrayClipboard) cr.read(data);

            // CALC PASTELOCATION
            int y = 0;
            Vector lastVec = null;
            for (int i = 0; i < c.getRegion().getHeight(); i++) {
                if (lastVec == null) {
                    lastVec = c.getMinimumPoint();
                } else {
                    lastVec = lastVec.add(0, 1, 0);
                }
                BaseBlock curr = c.getBlock(lastVec);
                if (curr.isAir() == false) {
                    y++;
                }
                if (curr.isAir()) {
                    break;
                }
            }
            pastepoint = new Location(pastepoint.getWorld(), pastepoint.getX(), loc.getY() - y, pastepoint.getZ());

            final Schematic schematic = ClipboardFormat.SCHEMATIC.load(schem);
            schematic.getClipboard().setOrigin(c.getMinimumPoint());
            schematic.paste(BukkitUtil.getLocalWorld(p.getWorld()), BukkitUtil.toVector(pastepoint), false, true, null);
        } catch (IOException e1) {
        }
    }
}