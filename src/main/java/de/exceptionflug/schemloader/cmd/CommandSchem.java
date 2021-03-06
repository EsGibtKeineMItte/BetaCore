package de.exceptionflug.schemloader.cmd;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;
import de.exceptionflug.schemloader.main.CheckFile;
import de.exceptionflug.schemloader.main.SchemLoader;
import de.wk.betacore.BetaCore;
import de.wk.betacore.environment.Environment;
import exceptionflug.invlib.*;
import exceptionflug.presets.MultiPageInventory;
import exceptionflug.schemloader.cmd.UUIDFetcher;
import net.thecobix.brew.main.Brew;
import net.thecobix.brew.schematic.SGSchematic;
import net.thecobix.brew.schematic.Schematic;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class CommandSchem implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;


        if (args.length == 0) {
            p.sendMessage(SchemLoader.prefix + "§bBefehle:");
            p.sendMessage("§8//schem load <Schematic> - §6Lädt eine deiner Schematics");
            p.sendMessage("§8//schem save <Schematic> - §6Speichert eine Schematic");
            p.sendMessage("§8//schem changetype <Schematic> <normal,warship,wargear> - §6Ändert den Typ einer Schematic");
            p.sendMessage("§8//schem info <Schematic> - §6Speichert eine Schematic");
            p.sendMessage("§8//schem list - §6Listet alle deine Schematics auf.");
            p.sendMessage("§8//schem addmember <Schematic> <Spieler> - §6Fügt einen Spieler zu einer Schematic hinzu.");
            p.sendMessage("§8//schem delmember <Schematic> <Spieler> - §6Entfernt einen Spieler von einer Schematic.");
            if (p.hasPermission("system.schemloader.help")) {
                p.sendMessage("§8//schem lock <Spieler> <Schematic> - §6Sperrt eine Schematic.");
                p.sendMessage("§8//schem unlock <Spieler> <Schematic> - §6Entsperrt eine Schematic.");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("load")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem load <Name>");
            } else if (args[0].equalsIgnoreCase("save")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem save <Name>");
            } else if (args[0].equalsIgnoreCase("info")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem info <Name>");
            } else if (args[0].equalsIgnoreCase("changetype")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem changetype <Name> <normal,warship,wargear>");
            } else if (args[0].equalsIgnoreCase("addmember")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem addmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("delmember")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem delmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("list")) {
                Bukkit.getScheduler().runTaskAsynchronously(BetaCore.getInstance(), new Runnable() {
                    public void run() {
                        new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/").mkdirs();
                        File[] schems = new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/").listFiles();
                        int count = schems.length;
                        ArrayList<Entry<SGSchematic, String>> schemsList = new ArrayList<>();
                        try {
                            PreparedStatement ps = Environment.getCurrent().getConnectionHolder()
                                    .prepareStatement("SELECT * FROM SchematicMembers WHERE member=?");
                            ps.setString(1, p.getUniqueId().toString());
                            ResultSet rs = Environment.getCurrent().getConnectionHolder().executeQuery(ps);
                            while (rs.next()) {
                                count++;
                                File f = new File(
                                        SchemLoader.SCHEM_DIR + rs.getString("owner") + "/" + rs.getString("name"));
                                if (f.exists()) {
                                    try {
                                        if (!BetaCore.brew.getSchematicUtil().isGiantSchematic(f)) {
                                            continue;
                                        }
                                    } catch (IOException e) {
                                        p.sendMessage(SchemLoader.prefix + "§cFehler: " + e.getMessage());
                                        continue;
                                    }
                                    try {
                                        schemsList.add(new AbstractMap.SimpleEntry(
                                                BetaCore.brew.getBrew().getSchematicUtil().loadGiantSchematic(f),
                                                f.getName()));
                                    } catch (IOException e) {
                                        p.sendMessage(SchemLoader.prefix + "§cFehler: " + e.getMessage());
                                    }
                                }
                            }
                        } catch (SQLException e1) {
                            p.sendMessage(SchemLoader.prefix
                                    + "§cDie Schematics mit Userzugriff konnten nicht kalkuliert werden :(");
                            e1.printStackTrace();
                        }
                        for (File f : schems) {
                            try {
                                if (!BetaCore.brew.getSchematicUtil().isGiantSchematic(f)) {
                                    continue;
                                }
                            } catch (IOException e) {
                                p.sendMessage(SchemLoader.prefix + "§cFehler: " + e.getMessage());
                                continue;
                            }
                            count++;
                            try {
                                schemsList.add(new AbstractMap.SimpleEntry(
                                        BetaCore.brew.getBrew().getSchematicUtil().loadGiantSchematic(f), f.getName()));
                            } catch (IOException e) {
                                p.sendMessage(SchemLoader.prefix + "§cFehler: " + e.getMessage());
                                continue;
                            }
                        }
                        if (count == 0) {
                            p.sendMessage(SchemLoader.prefix + "§cDu hast noch keine Schematics!");
                            return;
                        }
                        InventoryHandler handle = new InventoryHandler(BetaCore.getInstance(), p);
                        MultiPageInventory mpi = new MultiPageInventory("Schematics");
                        for (Entry<SGSchematic, String> gs : schemsList) {
                            String[] datatatatatatata = gs.getKey().getIcon().split(":");
                            try {
                                Material matatatatatatata = Material.valueOf(datatatatatatata[0]);
                                if (matatatatatatata == null)
                                    continue;
                                int datatatatatatatatatat = 0;
                                if (datatatatatatata.length == 2) {
                                    datatatatatatatatatat = Integer.parseInt(datatatatatatata[1]);
                                }
                                ItemStack is = new ItemStack(matatatatatatata);
                                is.setData(new MaterialData(matatatatatatata, (byte) datatatatatatatatatat));
                                LinkItem li = new LinkItem(is);
                                li.setDisplayName("§a" + gs.getValue());
                                li.setLore(new ItemLore()
                                        .addLine("§7Ersteller: §6"
                                                + UUIDFetcher.getName(UUID.fromString(gs.getKey().getSchemOwner())))
                                        .addLine("§7ID: §6" + gs.getKey().getId())
                                        .addLine("§7Typ: §6" + gs.getKey().getType()));
                                li.addClickListener(new ClickActionListener() {

                                    @Override
                                    public void onClick(Inventory inv, InventoryClickEvent event) {
                                        event.getWhoClicked().closeInventory();
//                                        mpi.unregister(han);
                                        handle.die();
                                        ((Player) event.getWhoClicked()).chat("//schematic gui "
                                                + UUIDFetcher.getName(UUID.fromString(gs.getKey().getSchemOwner()))
                                                + " " + gs.getValue());
                                    }
                                });
                                mpi.addItem(li);
                            } catch (Exception e) {
                                e.printStackTrace();
                                continue;
                            }
                        }
                        mpi.register(handle);
                        mpi.showUp(p);
                    }
                });
                p.sendMessage(SchemLoader.prefix + "§aBitte warte einen Moment während die Liste zusammengestellt wird...");
            } else if (args[0].equalsIgnoreCase("scan")) {
                if (p.isOp()) {
                    WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                            .getPlugin("WorldEdit");
                    Selection s = wep.getSelection(p);
                    if (s != null) {
                        try {
                            Region r = s.getRegionSelector().getRegion();
                            p.sendMessage(SchemLoader.prefix + "§eScannen...");
//                            CannonPattern cp = Scanner.scanSingle(BukkitUtil.toLocation(p.getWorld(), r.getMinimumPoint()), BukkitUtil.toLocation(p.getWorld(), r.getMaximumPoint()));
//                            p.sendMessage(Schemloader.prefix + "§aFertig.");
//                            p.sendMessage("§8Zeilen: §6" + cp.getSource().length);
//                            p.sendMessage("§8Segmente Zeile 1: §6" + cp.getSource()[0].length);
//                            p.sendMessage("§8Daten Segment 1: §6" + cp.getSource()[0][0].length);
                        } catch (IncompleteRegionException e) {
                            p.sendMessage(SchemLoader.prefix + "§cDeine Region ist unvollständig.");
                        }
                    } else {
                        p.sendMessage(SchemLoader.prefix + "§cDeine Region ist leer.");
                    }
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("load")) {
                if (args[1].contains(";")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[1].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File schem = new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!schem.exists()) {
                    p.sendMessage(SchemLoader.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    if (!BetaCore.brew.getSchematicUtil().isSchematic(schem)
                            || !BetaCore.brew.getSchematicUtil().isGiantSchematic(schem)) {
                        p.sendMessage(SchemLoader.prefix + "§cKeine gültige SGSchematic.");
                        return false;
                    }
                    SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(schem);
                    if (s.getType().equalsIgnoreCase("gesperrt")) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic ist gesperrt und kann nicht geladen werden!");
                        return false;
                    }
                    WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                            .getPlugin("WorldEdit");
                    WorldEdit we = wep.getWorldEdit();

                    LocalPlayer localPlayer = wep.wrapPlayer(p);
                    LocalSession localSession = we.getSession(localPlayer);
                    EditSession editSession = localSession.createEditSession(localPlayer);
                    WorldData worldData = editSession.getWorld().getWorldData();
                    ClipboardHolder clipboardHolder = new ClipboardHolder(BetaCore.brew.getSchematicUtil().toWeClipboard(s),
                            worldData);
                    localSession.setClipboard(clipboardHolder);
                    p.sendMessage(SchemLoader.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                } catch (IOException e) {
                    p.sendMessage(SchemLoader.prefix + "§cFehler.");
                }
            } else if (args[0].equalsIgnoreCase("save")) {
                if (args[1].contains(";")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[1].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
                WorldEdit we = wep.getWorldEdit();

                LocalPlayer localPlayer = wep.wrapPlayer(p);
                LocalSession localSession = we.getSession(localPlayer);
                try {
                    ClipboardHolder holder = localSession.getClipboard();
                    Clipboard clipboard = holder.getClipboard();
                    Transform transform = holder.getTransform();
                    Clipboard target;

                    // If we have a transform, bake it into the copy
                    if (!transform.isIdentity()) {
                        System.err.println("We cannot use WorldEdits transformer");
                        target = clipboard;
                    } else {
                        target = clipboard;
                    }
                    new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/").mkdirs();
                    BetaCore.brew.getSchematicUtil().saveSchematic(clipboard,
                            new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name),
                            p.getUniqueId().toString(), name, "Normal", "STONE:0", System.currentTimeMillis());
                    p.sendMessage(SchemLoader.prefix + "§aGespeichert: §6" + name);
                } catch (EmptyClipboardException e) {
                    p.sendMessage(SchemLoader.prefix + "§cDein Clipboard ist leer. Kopiere mal was mit §6//copy");
                } catch (IOException e) {
                    p.sendMessage("§cFehler beim speichern.");
                    e.printStackTrace();
                }
                // Bukkit.getScheduler().scheduleSyncDelayedTask(Schemloader.getInstance(),
                // new Runnable() {
                //
                // @Override
                // public void run() {
                // File schem = new
                // File("plugins/WorldEdit/schematics/"+p.getName().toLowerCase()+"/"+name);
                // try {
                // FileUtils.moveFile(schem, new
                // File("/root/Arbeitsfläche/Netzwerk/Global/schems/"+name));
                // } catch (IOException e) {
                // p.sendMessage(Schemloader.prefix+"§cEs ist ein Fehler
                // aufgetreten.");
                // }
                // }
                // }, 20L);
            } else if (args[0].equalsIgnoreCase("info")) {
                if (args[1].contains(";")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[1].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File toLoad = new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!toLoad.exists()) {
                    p.sendMessage(SchemLoader.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    if (!BetaCore.brew.getSchematicUtil().isSchematic(toLoad)) {
                        p.sendMessage(SchemLoader.prefix + "§cDas ist keine Schematic.");
                        return false;
                    }
                } catch (IOException e) {
                    p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten.");
                    e.printStackTrace();
                    return false;
                }
                try {
                    if (!BetaCore.brew.getSchematicUtil().isGiantSchematic(toLoad)) {
                        Schematic s = BetaCore.brew.getSchematicUtil().loadSchematic(toLoad);
                        p.sendMessage("§3--- §6" + name + " §3---");
                        p.sendMessage("§8Typ: §6Schematic");
                        int blocks = s.getWidth() * s.getHeight() * s.getLength();
                        p.sendMessage("§8Blöcke: §6" + blocks);
                        p.sendMessage("§8Höhe: §6" + s.getHeight());
                        p.sendMessage("§8Breite: §6" + s.getWidth());
                        p.sendMessage("§8Länge: §6" + s.getLength());
                        p.sendMessage(
                                "§8WEOrigin: §6" + s.getWEOriginX() + " " + s.getWEOriginY() + " " + s.getWEOriginZ());
                        p.sendMessage(
                                "§8WEOffset: §6" + s.getWEOffsetX() + " " + s.getWEOffsetY() + " " + s.getWEOffsetZ());
                    } else {
                        SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(toLoad);
                        UUID id3 = UUID.fromString(s.getSchemOwner());
                        p.sendMessage("§3--- §6" + name + " §3---");
                        p.sendMessage("§8Typ: §6SGSchematic");
                        p.sendMessage("§8GiantTyp: §6" + s.getType());
                        p.sendMessage("§8Name: §6" + s.getSchemName());
                        p.sendMessage("§8Owner: §6" + UUIDFetcher.getName(id3));
                        PreparedStatement ps = Environment.getCurrent().getConnectionHolder()
                                .prepareStatement("SELECT member FROM SchematicMembers WHERE name=? AND owner=?");
                        ps.setString(1, s.getSchemName());
                        ps.setString(2, id3.toString());
                        ResultSet rs = Environment.getCurrent().getConnectionHolder().executeQuery(ps);
                        StringBuilder sbboss = new StringBuilder();
                        while (rs.next()) {
                            sbboss.append(UUIDFetcher.getName(UUID.fromString(rs.getString("member"))));
                        }
                        p.sendMessage("§8Mitglieder: §6" + sbboss.toString());
                        p.sendMessage("§8ID: §6" + s.getId());
                        int blocks = s.getWidth() * s.getHeight() * s.getLength();
                        p.sendMessage("§8Blöcke: §6" + blocks);
                        p.sendMessage("§8Höhe: §6" + s.getHeight());
                        p.sendMessage("§8Breite: §6" + s.getWidth());
                        p.sendMessage("§8Länge: §6" + s.getLength());
                        p.sendMessage(
                                "§8WEOrigin: §6" + s.getWEOriginX() + " " + s.getWEOriginY() + " " + s.getWEOriginZ());
                        p.sendMessage(
                                "§8WEOffset: §6" + s.getWEOffsetX() + " " + s.getWEOffsetY() + " " + s.getWEOffsetZ());
                    }
                } catch (Exception e) {
                    p.sendMessage("§cEin Fehler ist aufgetreten.");
                    e.printStackTrace();
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!p.hasPermission("system.schem.list.other")) {
                    return false;
                }
                File[] schems = null;
                UUID id = UUIDFetcher.getUUID(args[1]);
                if (id == null) {
                    p.sendMessage(SchemLoader.prefix + "Der Spieler ist unbekannt.");
                    return false;
                }
                try {
                    schems = new File(SchemLoader.SCHEM_DIR + id.toString() + "/").listFiles();
                } catch (Exception e) {
                    p.sendMessage(SchemLoader.prefix + "§cProblem: " + ExceptionUtils.getFullStackTrace(e));
                    return false;
                }
                if (schems.length == 0) {
                    p.sendMessage(SchemLoader.prefix + "§c" + args[1] + " hat noch keine Schematics!");
                    return false;
                }
                p.sendMessage(SchemLoader.prefix + "Schematics:");
                int count = 0;
                for (File f : schems) {
                    try {
                        if (BetaCore.brew.getSchematicUtil().isGiantSchematic(f) == false) {
                            continue;
                        }
                    } catch (IOException e) {
                        p.sendMessage(SchemLoader.prefix + "§cFehler");
                        return false;
                    }
                    count++;
                    try {
                        SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(f);
                        p.sendMessage("§8" + count + " §6" + f.getName() + " §7[§6" + s.getType().toUpperCase() + "§7]");

                    } catch (IOException e) {
                        p.sendMessage("§8" + count + " §6" + f.getName() + "//schem info " + args[1] + " " + f.getName());
                    }
                }
            } else if (args[0].equalsIgnoreCase("addmember")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem addmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("delmember")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem delmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("changetype")) {
                p.sendMessage(SchemLoader.prefix + "§bBenutzung: //schem changetype <Schematic> <normal, warship>");
            } else if (args[0].equalsIgnoreCase("check")) {
                String path = args[1];
                if (!path.endsWith(".schematic")) {
                    path = path + ".schematic";
                }
                if (!p.hasPermission("cobix.check")) {
                    return false;
                }
                File f = new File(SchemLoader.SCHEM_DIR + path);
                if (!f.exists()) {
                    p.sendMessage("§cFehler: Datei nicht existent. §bFormat: uuid/schematic(.schematic)");
                    return false;
                }
                try {
                    if (BetaCore.brew.getSchematicUtil().isSchematic(f)) {
                        if (BetaCore.brew.getSchematicUtil().isGiantSchematic(f)) {
                            SGSchematic cs = BetaCore.brew.getSchematicUtil().loadGiantSchematic(f);
                            int my = cs.getId();
                            Bukkit.getScheduler().runTaskAsynchronously(BetaCore.getInstance(), new Runnable() {

                                @Override
                                public void run() {
                                    CheckFile.check(p, my, new File(SchemLoader.SCHEM_DIR));
                                    p.sendMessage("§aFertig.");
                                }
                            });
                        } else {
                            p.sendMessage("§cFehler: Datei ist keine SGSchematic");
                        }
                    } else {
                        p.sendMessage("§cFehler: Datei ist keine SGSchematic");
                    }
                } catch (IOException e) {
                    p.sendMessage("§4Fehler: §c" + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("changetype")) {
                if (args[1].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                    return false;
                }
                String type = args[2];
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                final String fname = name;
                if (type.equalsIgnoreCase("normal") || type.equalsIgnoreCase("warship") || type.equalsIgnoreCase("WarGear")) {
                    File schem = new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(SchemLoader.prefix + "§cDies ist keine gültige Schematic!");
                            return false;
                        }
                        SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(schem);
                        if (s.getType().equalsIgnoreCase("gesperrt")) {
                            p.sendMessage(
                                    SchemLoader.prefix + "§cDie Schematic ist gesperrt und kann nicht bearbeitet werden!");
                            return false;
                        }
                        s.setType(type);
                        if (s.getType().equalsIgnoreCase("normal")) {
                            BetaCore.brew.getSchematicUtil().saveSchematic(BetaCore.brew.getSchematicUtil().toWeClipboard(s),
                                    new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name),
                                    p.getUniqueId().toString(), s.getSchemName(), s.getType(), s.getIcon(),
                                    System.currentTimeMillis());
                            p.sendMessage(SchemLoader.prefix + "§aTyp erfolgreich geändert.");
                        } else if(s.getType().equalsIgnoreCase("warship")) {//TODO Intregrate WarGears
                            short[] blocks = s.getBlockInfo();
                            boolean allowed = true;
                            int count46 = 0;
                            int count325 = 0;
                            List<Integer> notified = new ArrayList<>();
                            for (int i = 0; i < blocks.length; i++) {
                                if (SchemLoader.isAllowed(blocks[i])) {
                                    if ((int) blocks[i] == 7) {
                                        count325++;
                                    }
                                    if ((int) blocks[i] == 49) {
                                        count325++;
                                    }
                                    if (count325 > 2000) {
                                        if (!notified.contains(49)) {
                                            p.sendMessage(SchemLoader.prefix
                                                    + "§cDu hast zu viel TNT und Schleimblöcke in deinem WarShip! Du darfst maximal §62000 §cvon beiden insgesamt verbauen!");
                                            allowed = false;
                                            notified.add(49);
                                        }
                                    }
                                } else {
                                    if (!notified.contains((int) blocks[i])) {
                                        p.sendMessage(SchemLoader.prefix + "§cEs wurde ein verbotener Block entdeckt: §6"
                                                + Material.getMaterial(blocks[i]));
                                        allowed = false;
                                        notified.add((int) blocks[i]);
                                        if ((int) blocks[i] == 46 || (int) blocks[i] == 325) {
                                            p.sendMessage(
                                                    "§6HINWEIS: §cWenn du TNT oder Schleimblöcke verbauen möchtest, bitten wir darum TNT zu OBSIDIAN und Schleimblöcke zu BEDROCK zu replacen!");
                                        }
                                    }
                                }
                            }
                            if (!allowed) {
                                p.sendMessage(SchemLoader.prefix
                                        + "§cTyp wurde nicht geändert! Bitte überprüfe deine Schematic auf grobe Verstöße.");
                                return false;
                            }

                            File test = new File(SchemLoader.SCHEM_DIR + "tocheck/");

                            if(!test.exists()){
                                test.mkdirs();
                            }

                            BetaCore.brew.getSchematicUtil().saveSchematic(BetaCore.brew.getSchematicUtil().toWeClipboard(s),
                                    new File(SchemLoader.SCHEM_DIR + "tocheck/" + s.getType() + ";"
                                            + p.getUniqueId() + ";" + name),
                                    p.getUniqueId().toString(), s.getSchemName(), s.getType(), s.getIcon(),
                                    s.getCreated());
//                            BetaCore.brew.getSchematicUtil().saveSchematic(BetaCore.brew.getSchematicUtil().toWeClipboard(s), new File(Schemloader.SCHEM_DIR + "tocheck/" + s.getSchemName()),
//                                    p.getUniqueId().toString(), s.getSchemName(), s.getType(), s.getIcon(), s.getCreated());
                            p.sendMessage(SchemLoader.prefix
                                    + "§aTyp erfolgreich geändert. Schematic muss noch auf Regelkonformität geprüft werden.");

                        }else{
                            p.sendMessage(SchemLoader.prefix + "§7WarGears werden erst bald hinzugefügt. Gedulde dich noch etwas.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    p.sendMessage(SchemLoader.prefix
                            + "§cDu hast einen ungültigen Typ angegeben! Gültige Typen sind: §6normal §cund §6warship");
                }
            } else if (args[0].equalsIgnoreCase("load")) {
                if (p.hasPermission("system.schem.load.other")) {
                    if (args[1].contains("/")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isSchematic(schem)
                                || !BetaCore.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(SchemLoader.prefix + "§cKeine gültige Schematic.");
                            return false;
                        }
                        SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(schem);
                        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                .getPlugin("WorldEdit");
                        WorldEdit we = wep.getWorldEdit();

                        LocalPlayer localPlayer = wep.wrapPlayer(p);
                        LocalSession localSession = we.getSession(localPlayer);
                        EditSession editSession = localSession.createEditSession(localPlayer);
                        WorldData worldData = editSession.getWorld().getWorldData();
                        ClipboardHolder clipboardHolder = new ClipboardHolder(
                                BetaCore.brew.getSchematicUtil().toWeClipboard(s), worldData);
                        localSession.setClipboard(clipboardHolder);
                        p.sendMessage(SchemLoader.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                    } catch (IOException e) {
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                } else {
                    if (args[1].contains("/")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        PreparedStatement ps = Environment.getCurrent().getConnectionHolder().prepareStatement(
                                "SELECT id FROM SchematicMembers WHERE name=? AND owner=? AND member=?");
                        ps.setString(1, name);
                        ps.setString(2, id.toString());
                        ps.setString(3, p.getUniqueId().toString());
                        ResultSet rs = Environment.getCurrent().getConnectionHolder().executeQuery(ps);
                        while (rs.next()) {
                            SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(schem);
                            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                    .getPlugin("WorldEdit");
                            WorldEdit we = wep.getWorldEdit();

                            LocalPlayer localPlayer = wep.wrapPlayer(p);
                            LocalSession localSession = we.getSession(localPlayer);
                            EditSession editSession = localSession.createEditSession(localPlayer);
                            WorldData worldData = editSession.getWorld().getWorldData();
                            ClipboardHolder clipboardHolder = new ClipboardHolder(
                                    BetaCore.brew.getSchematicUtil().toWeClipboard(s), worldData);
                            localSession.setClipboard(clipboardHolder);
                            p.sendMessage(SchemLoader.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                            return false;
                        }
                        if (p.getUniqueId().equals(id)) {
                            SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(schem);
                            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                    .getPlugin("WorldEdit");
                            WorldEdit we = wep.getWorldEdit();

                            LocalPlayer localPlayer = wep.wrapPlayer(p);
                            LocalSession localSession = we.getSession(localPlayer);
                            EditSession editSession = localSession.createEditSession(localPlayer);
                            WorldData worldData = editSession.getWorld().getWorldData();
                            ClipboardHolder clipboardHolder = new ClipboardHolder(
                                    BetaCore.brew.getSchematicUtil().toWeClipboard(s), worldData);
                            localSession.setClipboard(clipboardHolder);
                            p.sendMessage(SchemLoader.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                            return false;
                        }
                        p.sendMessage(SchemLoader.prefix + "§cDu hast keine Rechte auf diese Schematic.");
                    } catch (Exception e) {
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("save")) {
                if (p.hasPermission("system.schem.save.other")) {
                    if (args[1].contains("/")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name);
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isSchematic(schem)
                                || !BetaCore.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(SchemLoader.prefix + "§cKeine gültige Schematic.");
                            return false;
                        }
                        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                .getPlugin("WorldEdit");
                        WorldEdit we = wep.getWorldEdit();

                        LocalPlayer localPlayer = wep.wrapPlayer(p);
                        LocalSession localSession = we.getSession(localPlayer);
                        try {
                            ClipboardHolder holder = localSession.getClipboard();
                            Clipboard clipboard = holder.getClipboard();
                            Transform transform = holder.getTransform();
                            Clipboard target;

                            // If we have a transform, bake it into the copy
                            if (!transform.isIdentity()) {
                                System.err.println("We cannot use WorldEdits transformer");
                                target = clipboard;
                            } else {
                                target = clipboard;
                            }
                            new File(SchemLoader.SCHEM_DIR + id.toString() + "/").mkdirs();
                            BetaCore.brew.getSchematicUtil().saveSchematic(clipboard,
                                    new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name), id.toString(), name,
                                    "normal", "STONE:0", System.currentTimeMillis());
                            p.sendMessage(SchemLoader.prefix + "§aGespeichert: §6" + name);
                        } catch (EmptyClipboardException e) {
                            p.sendMessage(SchemLoader.prefix + "§cDein Clipboard ist leer. Kopiere mal was mit §6//copy");
                        } catch (IOException e) {
                            p.sendMessage("§cFehler beim speichern.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                } else {
                    if (args[1].contains("/")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File("plugins/WorldEdit/schematics/" + user.toLowerCase() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isSchematic(schem)
                                || !BetaCore.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(SchemLoader.prefix + "§cKeine gültige Schematic.");
                            return false;
                        }
                        PreparedStatement ps = Environment.getCurrent().getConnectionHolder().prepareStatement(
                                "SELECT id FROM SchematicMembers WHERE name=? AND owner=? AND member=?");
                        ps.setString(1, name);
                        ps.setString(2, id.toString());
                        ps.setString(3, p.getUniqueId().toString());
                        ResultSet rs = Environment.getCurrent().getConnectionHolder().executeQuery(ps);
                        boolean ddos = false;
                        while (rs.next()) {
                            ddos = true;
                        }
                        if (!ddos) {
                            p.sendMessage(SchemLoader.prefix + "§cDu hast kein Recht auf diese Schematic.");
                            return false;
                        }
                        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                .getPlugin("WorldEdit");
                        WorldEdit we = wep.getWorldEdit();

                        LocalPlayer localPlayer = wep.wrapPlayer(p);
                        LocalSession localSession = we.getSession(localPlayer);
                        try {
                            ClipboardHolder holder = localSession.getClipboard();
                            Clipboard clipboard = holder.getClipboard();
                            Transform transform = holder.getTransform();
                            Clipboard target;

                            // If we have a transform, bake it into the copy
                            if (!transform.isIdentity()) {
                                System.err.println("We cannot use WorldEdits transformer");
                                target = clipboard;
                            } else {
                                target = clipboard;
                            }
                            new File(SchemLoader.SCHEM_DIR + id.toString() + "/").mkdirs();
                            BetaCore.brew.getSchematicUtil().saveSchematic(clipboard,
                                    new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name), user, name, "normal",
                                    "STONE:0", System.currentTimeMillis());
                            p.sendMessage(SchemLoader.prefix + "§aGespeichert: §6" + name);
                        } catch (EmptyClipboardException e) {
                            p.sendMessage(SchemLoader.prefix + "§cDein Clipboard ist leer. Kopiere mal was mit §6//copy");
                        } catch (IOException e) {
                            p.sendMessage("§cFehler beim speichern.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("addmember")) {
                if (args[1].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                    return false;
                }
                String user = args[2];
                UUID id = UUIDFetcher.getUUID(user);
                if (id == null) {
                    p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File schem = new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!schem.exists()) {
                    p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                    return false;
                }
                try {
                    PreparedStatement ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("SELECT id FROM SchematicMembers WHERE name=? AND owner=? AND member=?");
                    ps.setString(1, name);
                    ps.setString(2, id.toString());
                    ps.setString(3, p.getUniqueId().toString());
                    ResultSet rs = Environment.getCurrent().getConnectionHolder().executeQuery(ps);
                    boolean ddos = false;
                    while (rs.next()) {
                        ddos = true;
                    }
                    if (ddos) {
                        p.sendMessage(SchemLoader.prefix + "§6" + user + " §cist bereits der Schematic hinzugefügt.");
                        return false;
                    }
                    ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("INSERT INTO SchematicMembers (name, owner, member) VALUES (?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, p.getUniqueId().toString());
                    ps.setString(3, id.toString());
                    Environment.getCurrent().getConnectionHolder().executeUpdate(ps);
                    p.sendMessage(SchemLoader.prefix + "§aDu hast §6" + user + " §azu deiner Schematic hinzugefügt.");
                } catch (SQLException e) {
                    p.sendMessage(SchemLoader.prefix + "§cNetzwerkfehler");
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("delmember")) {
                if (args[1].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String user = args[2];
                UUID id = UUIDFetcher.getUUID(user);
                if (id == null) {
                    p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File schem = new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!schem.exists()) {
                    p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                    return false;
                }
                try {
                    PreparedStatement ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("SELECT id FROM SchematicMembers WHERE name=? AND owner=? AND member=?");
                    ps.setString(1, name);
                    ps.setString(2, id.toString());
                    ps.setString(3, p.getUniqueId().toString());
                    ResultSet rs = Environment.getCurrent().getConnectionHolder().executeQuery(ps);
                    boolean ddos = false;
                    while (rs.next()) {
                        ddos = true;
                    }
                    if (!ddos) {
                        p.sendMessage(SchemLoader.prefix + "§cDer Spieler ist kein Mitglied der Schematic.");
                        return false;
                    }
                    ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("DELETE FROM SchematicMembers WHERE name=?, owner=?, member=?");
                    ps.setString(1, name);
                    ps.setString(2, p.getUniqueId().toString());
                    ps.setString(3, id.toString());
                    Environment.getCurrent().getConnectionHolder().executeUpdate(ps);
                    p.sendMessage(SchemLoader.prefix + "§aDu hast §6" + user + " §caaus deiner Schematic entfernt.");
                } catch (SQLException e) {
                    p.sendMessage(SchemLoader.prefix + "§cNetzwerkfehler");
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("lock")) {
                if (p.hasPermission("system.schem.lock")) {
                    if (args[2].contains("/")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("\\")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    final String fname = name;
                    File schem = new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(SchemLoader.prefix + "§cDies ist keine gültige Schematic!");
                            return false;
                        }
                        SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(schem);
                        if (s.getType().equalsIgnoreCase("gesperrt")) {
                            p.sendMessage(
                                    SchemLoader.prefix + "§cDie Schematic ist gesperrt und kann nicht bearbeitet werden!");
                            return false;
                        }
                        s.setType("gesperrt");
                        BetaCore.brew.getSchematicUtil().saveSchematic(BetaCore.brew.getSchematicUtil().toWeClipboard(s),
                                new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name), p.getUniqueId().toString(),
                                s.getSchemName(), s.getType(), s.getIcon(), s.getCreated());
                        p.sendMessage(SchemLoader.prefix + "§aSchematic gesperrt.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("unlock")) {
                if (p.hasPermission("system.schem.unlock")) {
                    if (args[2].contains("/")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("\\")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    final String fname = name;
                    File schem = new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(SchemLoader.prefix + "§cDies ist keine gültige SGSchematic!");
                            return false;
                        }
                        SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(schem);
                        s.setType("normal");
                        BetaCore.brew.getSchematicUtil().saveSchematic(BetaCore.brew.getSchematicUtil().toWeClipboard(s),
                                new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name), p.getUniqueId().toString(),
                                s.getSchemName(), s.getType(), s.getIcon(), s.getCreated());
                        p.sendMessage(SchemLoader.prefix + "§aSchematic entsperrt.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("info")) {
                if (p.hasPermission("system.schem.info.other")) {
                    if (args[2].contains(";")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6; §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("/")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("\\")) {
                        p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    UUID id = UUIDFetcher.getUUID(args[1]);
                    if (id == null) {
                        p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    File toLoad = new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name);
                    if (!toLoad.exists()) {
                        p.sendMessage(SchemLoader.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                        return false;
                    }
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isSchematic(toLoad)) {
                            p.sendMessage(SchemLoader.prefix + "§cDas ist keine Schematic.");
                            return false;
                        }
                    } catch (IOException e) {
                        p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten.");
                        e.printStackTrace();
                        return false;
                    }
                    try {
                        if (!BetaCore.brew.getSchematicUtil().isGiantSchematic(toLoad)) {
                            Schematic s = BetaCore.brew.getSchematicUtil().loadSchematic(toLoad);
                            p.sendMessage("§3--- §6" + name + " §3---");
                            p.sendMessage("§8Typ: §6Schematic");
                            int blocks = s.getWidth() * s.getHeight() * s.getLength();
                            p.sendMessage("§8Blöcke: §6" + blocks);
                            p.sendMessage("§8Höhe: §6" + s.getHeight());
                            p.sendMessage("§8Breite: §6" + s.getWidth());
                            p.sendMessage("§8Länge: §6" + s.getLength());
                            p.sendMessage("§8WEOrigin: §6" + s.getWEOriginX() + " " + s.getWEOriginY() + " "
                                    + s.getWEOriginZ());
                            p.sendMessage("§8WEOffset: §6" + s.getWEOffsetX() + " " + s.getWEOffsetY() + " "
                                    + s.getWEOffsetZ());
                        } else {
                            SGSchematic s = BetaCore.brew.getSchematicUtil().loadGiantSchematic(toLoad);
                            UUID id3 = UUID.fromString(s.getSchemOwner());
                            p.sendMessage("§3--- §6" + name + " §3---");
                            p.sendMessage("§8Typ: §6WarKing-Schematic");
                            p.sendMessage("§8Typ: §6" + s.getType());
                            p.sendMessage("§8Name: §6" + s.getSchemName());
                            p.sendMessage("§8Owner: §6" + UUIDFetcher.getName(id3));
                            PreparedStatement ps = Environment.getCurrent().getConnectionHolder()
                                    .prepareStatement("SELECT member FROM SchematicMembers WHERE name=? AND owner=?");
                            ps.setString(1, s.getSchemName());
                            ps.setString(2, id3.toString());
                            ResultSet rs = Environment.getCurrent().getConnectionHolder().executeQuery(ps);
                            StringBuilder sbboss = new StringBuilder();
                            while (rs.next()) {
                                sbboss.append(UUIDFetcher.getName(UUID.fromString(rs.getString("member"))));
                            }
                            p.sendMessage("§8Mitglieder: §6" + sbboss.toString());
                            p.sendMessage("§8ID: §6" + s.getId());
                            int blocks = s.getWidth() * s.getHeight() * s.getLength();
                            p.sendMessage("§8Blöcke: §6" + blocks);
                            p.sendMessage("§8Höhe: §6" + s.getHeight());
                            p.sendMessage("§8Breite: §6" + s.getWidth());
                            p.sendMessage("§8Länge: §6" + s.getLength());
                            p.sendMessage("§8WEOrigin: §6" + s.getWEOriginX() + " " + s.getWEOriginY() + " "
                                    + s.getWEOriginZ());
                            p.sendMessage("§8WEOffset: §6" + s.getWEOffsetX() + " " + s.getWEOffsetY() + " "
                                    + s.getWEOffsetZ());
                        }
                    } catch (Exception e) {
                        p.sendMessage("§cEin Fehler ist aufgetreten.");
                        e.printStackTrace();
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("gui")) {
                if (args[2].contains(";")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[2].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[2].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String name = args[2];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                UUID id = UUIDFetcher.getUUID(args[1]);
                if (id == null) {
                    p.sendMessage(SchemLoader.prefix + "§cUnbekannter Spieler.");
                    return false;
                }
                File toLoad = new File(SchemLoader.SCHEM_DIR + id.toString() + "/" + name);
                if (!toLoad.exists()) {
                    p.sendMessage(SchemLoader.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    if (!BetaCore.brew.getSchematicUtil().isSchematic(toLoad)) {
                        p.sendMessage(SchemLoader.prefix + "§cDas ist keine Schematic.");
                        return false;
                    }
                } catch (IOException e) {
                    p.sendMessage(SchemLoader.prefix + "§cEin Fehler ist aufgetreten.");
                    e.printStackTrace();
                    return false;
                }
                Inventory inv = new Inventory(toLoad.getName(), 9);
                InventoryHandler invh = new InventoryHandler(BetaCore.getInstance(), p);

                final String fname = name;

                LinkItem load = new LinkItem(new ItemStack(Material.WOOL, 1, (short) 5));
                load.setDisplayName("§aSchematic laden");
                load.addClickListener(new ClickActionListener() {

                    @Override
                    public void onClick(Inventory inv, InventoryClickEvent event) {
                        p.chat("//schem load " + args[1] + " " + fname);
                        p.closeInventory();
                        invh.die();
                    }
                });
                inv.setItem(0, load);

                LinkItem erase = new LinkItem(new ItemStack(Material.WOOL, 1, (short) 14));
                erase.setDisplayName("§cSchematic löschen");
                erase.addClickListener(new ClickActionListener() {

                    @Override
                    public void onClick(Inventory inv, InventoryClickEvent event) {
                        p.chat("//schem delete " + args[1] + " " + fname);
                        p.closeInventory();
                        invh.die();
                    }
                });
                inv.setItem(1, erase);

                LinkItem icon = new LinkItem(new ItemStack(Material.WOOL, 1, (short) 11));
                icon.setDisplayName("§9Icon ändern");
                icon.addClickListener(new ClickActionListener() {

                    @Override
                    public void onClick(Inventory inv, InventoryClickEvent event) {
                        invh.die();
                        MultiPageInventory mpi = new MultiPageInventory("Icon ändern");
                        InventoryHandler h = new InventoryHandler(BetaCore.getInstance(), p);

                        for (Material m : Material.values()) {
                            if (m == Material.AIR || m == Material.WATER || m == Material.STATIONARY_WATER
                                    || m == Material.LAVA || m == Material.STATIONARY_LAVA || m.getId() == 26
                                    || m.getId() == 34 || m.getId() == 36 || m.getId() == 43 || m.getId() == 51
                                    || m.getId() == 55 || m.getId() == 59 || m.getId() == 62 || m.getId() == 63
                                    || m.getId() == 64 || m.getId() == 68 || m.getId() == 71 || m.getId() == 74
                                    || m.getId() == 75 || m.getId() == 83 || m.getId() == 90 || m.getId() == 92
                                    || m.getId() == 93 || m.getId() == 94 || m.getId() == 104 || m.getId() == 105
                                    || m.getId() == 115 || m.getId() == 117 || m.getId() == 118 || m.getId() == 119
                                    || m.getId() == 124 || m.getId() == 125 || m.getId() == 132 || m.getId() == 140
                                    || m.getId() == 141 || m.getId() == 149 || m.getId() == 150 || m.getId() == 176
                                    || m.getId() == 177 || m.getId() == 178 || m.getId() == 181 || m.getId() == 193
                                    || m.getId() == 194 || m.getId() == 195 || m.getId() == 196 || m.getId() == 197
                                    || m.getId() == 204 || m.getId() == 207 || m.getId() == 209
                                    || m == Material.FROSTED_ICE || m == Material.SKULL || m == Material.POTATO
                                    || m == Material.STRUCTURE_BLOCK) {
                                continue;
                            }
                            LinkItem li = new LinkItem(new ItemStack(m));
                            li.addClickListener(new ClickActionListener() {

                                @Override
                                public void onClick(Inventory inv, InventoryClickEvent event) {
                                    h.die();
                                    p.closeInventory();
                                    try {
                                        SGSchematic schem = Brew.getBrew().getSchematicUtil()
                                                .loadGiantSchematic(toLoad);
                                        schem.setIcon(m.name() + ":0");
                                        Brew.getBrew().getSchematicUtil().saveSchematic(
                                                Brew.getBrew().getSchematicUtil().toWeClipboard(schem), toLoad,
                                                schem.getSchemOwner(), schem.getSchemName(), schem.getType(),
                                                schem.getIcon(), schem.getCreated());
                                        p.sendMessage(
                                                SchemLoader.prefix + "§aDas Icon wurde zu §6" + m.name() + " §ageändert.");
                                    } catch (IOException e) {
                                        p.sendMessage(SchemLoader.prefix + "§cEs ist ein Fehler aufgetreten.");
                                        e.printStackTrace();
                                    }
                                }
                            });
                            mpi.addItem(li);
                        }

                           mpi.register(h);
                        mpi.showUp(p);
                    }
                });
                inv.setItem(2, icon);

                LinkItem close = new LinkItem(new ItemStack(Material.IRON_DOOR));
                close.setDisplayName("§7Schließen");
                close.addClickListener(new ClickActionListener() {

                    @Override
                    public void onClick(Inventory inv, InventoryClickEvent event) {
                        invh.die();
                        p.closeInventory();
                    }
                });
                inv.setItem(8, close);

                invh.manage(inv);
                p.openInventory(inv.build());
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (args[2].contains(";")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[2].contains("/")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[2].contains("\\")) {
                    p.sendMessage(SchemLoader.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String name = args[2];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File toLoad = new File(SchemLoader.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!toLoad.exists()) {
                    p.sendMessage(SchemLoader.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    PreparedStatement ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("DELETE FROM SchematicMembers WHERE name=? AND owner=?");
                    ps.setString(1, toLoad.getName());
                    ps.setString(2, p.getUniqueId().toString());
                    Environment.getCurrent().getConnectionHolder().executeUpdate(ps);
                    toLoad.delete();
                    p.sendMessage(SchemLoader.prefix + "§aSchematic gelöscht");
                } catch (SQLException e) {
                    p.sendMessage(SchemLoader.prefix + "§cEs ist ein Fehla aufgetreten :I");
                    e.printStackTrace();
                }
            }
        } else if (args.length == 4) {
        }
        return false;
    }

    public static URL shortURL(String url, String htdocsPath, String hostname) throws IOException {
        String hashUrl = MD5(url);
        int length = hashUrl.length();
        length -= 26;
        String shortName = hashUrl.substring(0, length);
        File fold = new File(htdocsPath + "/s/" + shortName);
        fold.mkdirs();
        File f = new File(htdocsPath + "/s/" + shortName + "/index.php");
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("<?php header(\"Location: " + url + "\"); ?>");
        pw.flush();
        pw.close();
        return new URL("http://" + hostname + "/s/" + shortName + "/");
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


}
