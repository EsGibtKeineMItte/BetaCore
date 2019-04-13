package exceptionflug.schemloader.cmd;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.exceptionflug.schemloader.main.Main;
import de.wk.betacore.BetaCore;
import exceptionflug.invlib.*;
import exceptionflug.presets.MultiPageInventory;
import net.thecobix.brew.main.Brew;
import net.thecobix.brew.schematic.SGSchematic;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CommandSchemloader {


    @Command(name = "schemloader.gui", description = "Gui", inGameOnly = true)
    public void onGui(CommandArgs args) {
        final Player p = args.getPlayer();
        if (args.length() < 2) {
            p.sendMessage(Main.prefix + "§bBenutzung: /schemloader gui <Spieler> <Schematic>");
            return;
        }
        final String arg0 = args.getArgs(0);
        String arg1 = args.getArgs(1);
        UUID id = UUIDFetcher.getUUID(arg0);
        int wst = -1;
        if (id == null) {

        }
        if (arg1.contains(";")) {
            p.sendMessage(Main.prefix + "§cDu darfst keine §6; §cverwenden!");
            return;
        }
        if (arg1.contains("/")) {
            p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
            return;
        }
        if (arg1.contains("\\")) {
            p.sendMessage(Main.prefix + "§cDu darfst keine §6\\ §cverwenden!");
            return;
        }
        String name = arg1;
        if (!name.endsWith(".schematic")) {
            name = name + ".schematic";
        }
        final File f = id != null ? new File("/home/netuser/schematics/" + id.toString() + "/" + name) : new File("/home/netuser/schematics/wst/" + wst + "/" + name);
        if (!f.exists()) {
            p.sendMessage(Main.prefix + "§cSchematic existiert nicht");
            return;
        }
        try {
            if (!BetaCore.brew.getSchematicUtil().isSchematic(f)) {
                p.sendMessage(Main.prefix + "§cDas ist keine Schematic.");
                return;
            }
        } catch (IOException e) {
            p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten.");
            e.printStackTrace();
            return;
        }
        Inventory inv = new Inventory(f.getName(), 9);
        final InventoryHandler invh = new InventoryHandler(BetaCore.getInstance(), p);

        final String fname = name;

        LinkItem load = new LinkItem(new ItemStack(Material.WOOL, 1, (short) 5));
        load.setDisplayName("§aSchematic laden");
        load.addClickListener(new ClickActionListener() {
            public void onClick(Inventory inv, InventoryClickEvent event) {
                p.chat("//schem load " + arg0 + " " + fname);
                p.closeInventory();
                invh.die();
            }
        });
        inv.setItem(0, load);

        LinkItem erase = new LinkItem(new ItemStack(Material.WOOL, 1, (short) 14));
        erase.setDisplayName("§cSchematic löschen");
        erase.addClickListener(new ClickActionListener() {
            public void onClick(Inventory inv, InventoryClickEvent event) {
                p.chat("//schem delete " + arg0 + " " + fname);
                p.closeInventory();
                invh.die();
            }
        });
        inv.setItem(1, erase);

        LinkItem icon = new LinkItem(new ItemStack(Material.WOOL, 1, (short) 11));
        icon.setDisplayName("§9Icon ändern");
        icon.addClickListener(new ClickActionListener() {
            public void onClick(Inventory inv, InventoryClickEvent event) {
                inv.build();
                MultiPageInventory mpi = new MultiPageInventory("Icon ändern");
                final InventoryHandler h = new InventoryHandler(BetaCore.getInstance(), p);

                for (final Material m : Material.values()) {
                    if ((m != Material.AIR) && (m != Material.WATER) && (m != Material.STATIONARY_WATER) && (m != Material.LAVA) && (m != Material.STATIONARY_LAVA) &&
                            (m.getId() != 26) &&
                            (m.getId() != 34) && (m.getId() != 36) && (m.getId() != 43) && (m.getId() != 51) &&
                            (m.getId() != 55) && (m.getId() != 59) && (m.getId() != 62) && (m.getId() != 63) &&
                            (m.getId() != 64) && (m.getId() != 68) && (m.getId() != 71) && (m.getId() != 74) &&
                            (m.getId() != 75) && (m.getId() != 83) && (m.getId() != 90) && (m.getId() != 92) &&
                            (m.getId() != 93) && (m.getId() != 94) && (m.getId() != 104) && (m.getId() != 105) &&
                            (m.getId() != 115) && (m.getId() != 117) && (m.getId() != 118) && (m.getId() != 119) &&
                            (m.getId() != 124) && (m.getId() != 125) && (m.getId() != 132) && (m.getId() != 140) &&
                            (m.getId() != 141) && (m.getId() != 149) && (m.getId() != 150) && (m.getId() != 176) &&
                            (m.getId() != 177) && (m.getId() != 178) && (m.getId() != 181) && (m.getId() != 193) &&
                            (m.getId() != 194) && (m.getId() != 195) && (m.getId() != 196) && (m.getId() != 197) &&
                            (m.getId() != 204) && (m.getId() != 207) && (m.getId() != 209) && (m != Material.ICE) && (m != Material.SKULL) && (m != Material.POTATO)) {


                        LinkItem li = new LinkItem(new ItemStack(m));
                        li.addClickListener(new ClickActionListener() {
                            public void onClick(Inventory inv, InventoryClickEvent event) {
                                h.die();
                                event.getWhoClicked().closeInventory();
                                try {
                                    System.out.print("NOCH NIX FERTIG:I -> Command Schematic net.thecobix.schemloader.cmd");
                                    SGSchematic schem = Brew.getBrew().getSchematicUtil().loadGiantSchematic(new File(Main.SCHEM_DIR + ".schematic"));
                                    schem.setIcon(m.name() + ":0");
                                    Brew.getBrew().getSchematicUtil().saveSchematic(
                                            Brew.getBrew().getSchematicUtil().toWeClipboard(schem), new File(Main.SCHEM_DIR + ".schematic"), schem
                                                    .getSchemOwner(), schem.getSchemName(), schem.getType(), schem
                                                    .getIcon(), schem.getCreated());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
//                        mpi.addItem();
                    }
                }
//                mpi.register(h);

                mpi.showUp(p);
            }
        });
        inv.setItem(2, icon);

        LinkItem close = new LinkItem(new ItemStack(Material.IRON_DOOR));
        close.setDisplayName("§7Schließen");
        close.addClickListener(new ClickActionListener() {
            public void onClick(Inventory inv, InventoryClickEvent event) {
                invh.die();
                p.closeInventory();
            }
        });
        inv.setItem(8, close);

        invh.manage(inv);
        p.openInventory(inv.build());
    }
}