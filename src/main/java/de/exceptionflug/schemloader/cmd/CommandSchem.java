package de.exceptionflug.schemloader.cmd;

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

import de.exceptionflug.schemloader.main.CheckFile;
import de.exceptionflug.schemloader.main.Main;
import de.wk.betacore.BetaCore;
import de.wk.betacore.environment.Environment;
import exceptionflug.invlib.*;
import exceptionflug.presets.MultiPageInventory;
import exceptionflug.schemloader.cmd.UUIDFetcher;
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

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;

import net.thecobix.brew.main.Brew;
import net.thecobix.brew.schematic.SGSchematic;
import net.thecobix.brew.schematic.Schematic;

public class CommandSchem implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;


        if (args.length == 0) {
            p.sendMessage(Main.prefix + "§bBefehle:");
            p.sendMessage("§8//schem load <Schematic> - §6Lädt eine deiner Schematics");
            p.sendMessage("§8//schem save <Schematic> - §6Speichert eine Schematic");
            p.sendMessage("§8//schem changetype <Schematic> <normal,warship> - §6Ändert den Typ einer Schematic");
            p.sendMessage("§8//schem info <Schematic> - §6Speichert eine Schematic");
            p.sendMessage("§8//schem list - §6Listet alle deine Schematics auf.");
            p.sendMessage("§8//schem addmember <Schematic> <Spieler> - §6Fügt einen Spieler zu einer Schematic hinzu.");
            p.sendMessage("§8//schem delmember <Schematic> <Spieler> - §6Entfernt einen Spieler von einer Schematic.");
            p.sendMessage("§8//schem download <Schematic> - §6Gibt einen Downloadlink für deine Schematic");
            p.sendMessage("§8//schem market - §6Öffnet den Schematic-Shop");
            if (p.hasPermission("system.schemloader.help")) {
                p.sendMessage("§8//schem lock <Spieler> <Schematic> - §6Sperrt eine Schematic.");
                p.sendMessage("§8//schem unlock <Spieler> <Schematic> - §6Entsperrt eine Schematic.");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("load")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem load <Name>");
            } else if (args[0].equalsIgnoreCase("save")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem save <Name>");
            } else if (args[0].equalsIgnoreCase("info")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem info <Name>");
            } else if (args[0].equalsIgnoreCase("changetype")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem changetype <Name> <normal,warship>");
            } else if (args[0].equalsIgnoreCase("addmember")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem addmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("delmember")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem delmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("list")) {
                Bukkit.getScheduler().runTaskAsynchronously(BetaCore.getInstance(), new Runnable() {
                    public void run() {
                        new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/").mkdirs();
                        File[] schems = new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/").listFiles();
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
                                        Main.SCHEM_DIR + rs.getString("owner") + "/" + rs.getString("name"));
                                if (f.exists()) {
                                    try {
                                        if (Main.brew.getSchematicUtil().isGiantSchematic(f) == false) {
                                            continue;
                                        }
                                    } catch (IOException e) {
                                        p.sendMessage(Main.prefix + "§cFehler: " + e.getMessage());
                                        continue;
                                    }
                                    try {
                                        schemsList.add(new AbstractMap.SimpleEntry(
                                                Main.brew.getBrew().getSchematicUtil().loadGiantSchematic(f),
                                                f.getName()));
                                    } catch (IOException e) {
                                        p.sendMessage(Main.prefix + "§cFehler: " + e.getMessage());
                                    }
                                }
                            }
                        } catch (SQLException e1) {
                            p.sendMessage(Main.prefix
                                    + "§cDie Schematics mit Userzugriff konnten nicht kalkuliert werden :(");
                            e1.printStackTrace();
                        }
                        for (File f : schems) {
                            try {
                                if (Main.brew.getSchematicUtil().isGiantSchematic(f) == false) {
                                    continue;
                                }
                            } catch (IOException e) {
                                p.sendMessage(Main.prefix + "§cFehler: " + e.getMessage());
                                continue;
                            }
                            count++;
                            try {
                                schemsList.add(new AbstractMap.SimpleEntry(
                                        Main.brew.getBrew().getSchematicUtil().loadGiantSchematic(f), f.getName()));
                            } catch (IOException e) {
                                p.sendMessage(Main.prefix + "§cFehler: " + e.getMessage());
                                continue;
                            }
                        }
                        if (count == 0) {
                            p.sendMessage(Main.prefix + "§cDu hast noch keine Schematics!");
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
                                        BetaCore.debug("Inventory Handler zu Inventory: Command Schem: 171");
                                        handle.die();
                                        ((Player) event.getWhoClicked()).chat("//schematic gui "
                                                + UUIDFetcher.getName(UUID.fromString(gs.getKey().getSchemOwner()))
                                                + " " + gs.getValue());
                                    }
                                });
//                                mpi.addItem(li);
                                BetaCore.debug("Noch nicht vervollständigter Code: Inventory Item -> Linkitem CommandSchem:178 (Gibts 2x, die obere Stelle)");
                            } catch (Exception e) {
                                e.printStackTrace();
                                continue;
                            }
                        }
//                        mpi.register(handle);
                        mpi.showUp(p);
                    }
                });
                p.sendMessage(Main.prefix + "§aBitte warte einen Moment während die Liste zusammengestellt wird...");
            } else if (args[0].equalsIgnoreCase("scan")) {
                if (p.isOp()) {
                    WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                            .getPlugin("WorldEdit");
                    Selection s = wep.getSelection(p);
                    if (s != null) {
                        try {
                            Region r = s.getRegionSelector().getRegion();
                            p.sendMessage(Main.prefix + "§eScannen...");
//                            CannonPattern cp = Scanner.scanSingle(BukkitUtil.toLocation(p.getWorld(), r.getMinimumPoint()), BukkitUtil.toLocation(p.getWorld(), r.getMaximumPoint()));
//                            p.sendMessage(Main.prefix + "§aFertig.");
//                            p.sendMessage("§8Zeilen: §6" + cp.getSource().length);
//                            p.sendMessage("§8Segmente Zeile 1: §6" + cp.getSource()[0].length);
//                            p.sendMessage("§8Daten Segment 1: §6" + cp.getSource()[0][0].length);
                        } catch (IncompleteRegionException e) {
                            p.sendMessage(Main.prefix + "§cDeine Region ist unvollständig.");
                        }
                    } else {
                        p.sendMessage(Main.prefix + "§cDeine Region ist leer.");
                    }
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("load")) {
                if (args[1].contains(";")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[1].contains("/")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File schem = new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!schem.exists()) {
                    p.sendMessage(Main.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    if (!Main.brew.getSchematicUtil().isSchematic(schem)
                            || !Main.brew.getSchematicUtil().isGiantSchematic(schem)) {
                        p.sendMessage(Main.prefix + "§cKeine gültige SGSchematic.");
                        return false;
                    }
                    SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(schem);
                    if (s.getType().equalsIgnoreCase("gesperrt")) {
                        p.sendMessage(Main.prefix + "§cDie Schematic ist gesperrt und kann nicht geladen werden!");
                        return false;
                    }
                    WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                            .getPlugin("WorldEdit");
                    WorldEdit we = wep.getWorldEdit();

                    LocalPlayer localPlayer = wep.wrapPlayer(p);
                    LocalSession localSession = we.getSession(localPlayer);
                    EditSession editSession = localSession.createEditSession(localPlayer);
                    WorldData worldData = editSession.getWorld().getWorldData();
                    ClipboardHolder clipboardHolder = new ClipboardHolder(Main.brew.getSchematicUtil().toWeClipboard(s),
                            worldData);
                    localSession.setClipboard(clipboardHolder);
                    p.sendMessage(Main.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                } catch (IOException e) {
                    p.sendMessage(Main.prefix + "§cFehler.");
                }
            } else if (args[0].equalsIgnoreCase("save")) {
                if (args[1].contains(";")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[1].contains("/")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
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
                    new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/").mkdirs();
                    BetaCore.brew.getSchematicUtil().saveSchematic(clipboard,
                            new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name),
                            p.getUniqueId().toString(), name, "Normal", "STONE:0", System.currentTimeMillis());
                    p.sendMessage(Main.prefix + "§aGespeichert: §6" + name);
                } catch (EmptyClipboardException e) {
                    p.sendMessage(Main.prefix + "§cDein Clipboard ist leer. Kopiere mal was mit §6//copy");
                } catch (IOException e) {
                    p.sendMessage("§cFehler beim speichern.");
                    e.printStackTrace();
                }
                // Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
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
                // p.sendMessage(Main.prefix+"§cEs ist ein Fehler
                // aufgetreten.");
                // }
                // }
                // }, 20L);
            } else if (args[0].equalsIgnoreCase("info")) {
                if (args[1].contains(";")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[1].contains("/")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File toLoad = new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!toLoad.exists()) {
                    p.sendMessage(Main.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    if (!Main.brew.getSchematicUtil().isSchematic(toLoad)) {
                        p.sendMessage(Main.prefix + "§cDas ist keine Schematic.");
                        return false;
                    }
                } catch (IOException e) {
                    p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten.");
                    e.printStackTrace();
                    return false;
                }
                try {
                    if (!Main.brew.getSchematicUtil().isGiantSchematic(toLoad)) {
                        Schematic s = Main.brew.getSchematicUtil().loadSchematic(toLoad);
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
                        SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(toLoad);
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
                    p.sendMessage(Main.prefix + "Der Spieler ist unbekannt.");
                    return false;
                }
                try {
                    schems = new File(Main.SCHEM_DIR + id.toString() + "/").listFiles();
                } catch (Exception e) {
                    p.sendMessage(Main.prefix + "§cProblem: " + ExceptionUtils.getFullStackTrace(e));
                    return false;
                }
                if (schems.length == 0) {
                    p.sendMessage(Main.prefix + "§c" + args[1] + " hat noch keine Schematics!");
                    return false;
                }
                p.sendMessage(Main.prefix + "Schematics:");
                int count = 0;
                for (File f : schems) {
                    try {
                        if (Main.brew.getSchematicUtil().isGiantSchematic(f) == false) {
                            continue;
                        }
                    } catch (IOException e) {
                        p.sendMessage(Main.prefix + "§cFehler");
                        return false;
                    }
                    count++;
                    try {
                        SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(f);
                        p.sendMessage("§8" + count + " §6" + f.getName() + " §7[§6" + s.getType().toUpperCase() + "§7]");

                    } catch (IOException e) {
                        p.sendMessage("§8" + count + " §6" + f.getName() + "//schem info " + args[1] + " " + f.getName());
                    }
                }
            } else if (args[0].equalsIgnoreCase("addmember")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem addmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("delmember")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem delmember <Schematic> <Spieler>");
            } else if (args[0].equalsIgnoreCase("changetype")) {
                p.sendMessage(Main.prefix + "§bBenutzung: //schem changetype <Schematic> <normal, warship>");
            } else if (args[0].equalsIgnoreCase("check")) {
                String path = args[1];
                if (!path.endsWith(".schematic")) {
                    path = path + ".schematic";
                }
                if (!p.hasPermission("cobix.check")) {
                    return false;
                }
                File f = new File(Main.SCHEM_DIR + path);
                if (!f.exists()) {
                    p.sendMessage("§cFehler: Datei nicht existent. §bFormat: uuid/schematic(.schematic)");
                    return false;
                }
                try {
                    if (Main.brew.getSchematicUtil().isSchematic(f)) {
                        if (Main.brew.getSchematicUtil().isGiantSchematic(f)) {
                            SGSchematic cs = Main.brew.getSchematicUtil().loadGiantSchematic(f);
                            int my = cs.getId();
                            Bukkit.getScheduler().runTaskAsynchronously(BetaCore.getInstance(), new Runnable() {

                                @Override
                                public void run() {
                                    CheckFile.check(p, my, new File(Main.SCHEM_DIR));
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
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                    return false;
                }
                String type = args[2];
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                final String fname = name;
                if (type.equalsIgnoreCase("normal") || type.equalsIgnoreCase("warship")) {
                    File schem = new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!Main.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(Main.prefix + "§cDies ist keine gültige SGSchematic!");
                            return false;
                        }
                        SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(schem);
                        if (s.getType().equalsIgnoreCase("gesperrt")) {
                            p.sendMessage(
                                    Main.prefix + "§cDie Schematic ist gesperrt und kann nicht bearbeitet werden!");
                            return false;
                        }
                        s.setType(type);
                        if (s.getType().equalsIgnoreCase("normal")) {
                            Main.brew.getSchematicUtil().saveSchematic(Main.brew.getSchematicUtil().toWeClipboard(s),
                                    new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name),
                                    p.getUniqueId().toString(), s.getSchemName(), s.getType(), s.getIcon(),
                                    System.currentTimeMillis());
                            p.sendMessage(Main.prefix + "§aTyp erfolgreich geändert.");
                        } else {
                            short[] blocks = s.getBlockInfo();
                            boolean allowed = true;
                            int count46 = 0;
                            int count325 = 0;
                            List<Integer> notified = new ArrayList<>();
                            for (int i = 0; i < blocks.length; i++) {
                                if (Main.isAllowed(blocks[i])) {
                                    if ((int) blocks[i] == 7) {
                                        count325++;
                                    }
                                    if ((int) blocks[i] == 49) {
                                        count325++;
                                    }
                                    if (count325 > 1000) {
                                        if (!notified.contains(49)) {
                                            p.sendMessage(Main.prefix
                                                    + "§cDu hast zu viel TNT und Schleimblöcke in deinem WarShip! Du darfst maximal §61000 §cvon beiden insgesamt verbauen!");
                                            allowed = false;
                                            notified.add(49);
                                        }
                                    }
                                } else {
                                    if (!notified.contains((int) blocks[i])) {
                                        p.sendMessage(Main.prefix + "§cEs wurde ein verbotener Block entdeckt: §6"
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
                            if (allowed == false) {
                                p.sendMessage(Main.prefix
                                        + "§cTyp wurde nicht geändert! Bitte überprüfe deine Schematic auf grobe Verstöße.");
                                return false;
                            }

                            Main.brew.getSchematicUtil().saveSchematic(Main.brew.getSchematicUtil().toWeClipboard(s),
                                    new File("/home/netuser/tocheck/" + s.getType() + ";"
                                            + p.getUniqueId().toString() + ";" + name),
                                    p.getUniqueId().toString(), s.getSchemName(), s.getType(), s.getIcon(),
                                    s.getCreated());
                            p.sendMessage(Main.prefix
                                    + "§aTyp erfolgreich geändert. Schematic muss noch auf Regelkonformität geprüft werden.");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    p.sendMessage(Main.prefix
                            + "§cDu hast einen ungültigen Typ angegeben! Gültige Typen sind: §6normal §cund §6warship");
                }
            } else if (args[0].equalsIgnoreCase("load")) {
                if (p.hasPermission("system.schem.load.other")) {
                    if (args[1].contains("/")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File(Main.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!Main.brew.getSchematicUtil().isSchematic(schem)
                                || !Main.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(Main.prefix + "§cKeine gültige SGSchematic.");
                            return false;
                        }
                        SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(schem);
                        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                .getPlugin("WorldEdit");
                        WorldEdit we = wep.getWorldEdit();

                        LocalPlayer localPlayer = wep.wrapPlayer(p);
                        LocalSession localSession = we.getSession(localPlayer);
                        EditSession editSession = localSession.createEditSession(localPlayer);
                        WorldData worldData = editSession.getWorld().getWorldData();
                        ClipboardHolder clipboardHolder = new ClipboardHolder(
                                Main.brew.getSchematicUtil().toWeClipboard(s), worldData);
                        localSession.setClipboard(clipboardHolder);
                        p.sendMessage(Main.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                    } catch (IOException e) {
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                } else {
                    if (args[1].contains("/")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File(Main.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
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
                            SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(schem);
                            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                    .getPlugin("WorldEdit");
                            WorldEdit we = wep.getWorldEdit();

                            LocalPlayer localPlayer = wep.wrapPlayer(p);
                            LocalSession localSession = we.getSession(localPlayer);
                            EditSession editSession = localSession.createEditSession(localPlayer);
                            WorldData worldData = editSession.getWorld().getWorldData();
                            ClipboardHolder clipboardHolder = new ClipboardHolder(
                                    Main.brew.getSchematicUtil().toWeClipboard(s), worldData);
                            localSession.setClipboard(clipboardHolder);
                            p.sendMessage(Main.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                            return false;
                        }
                        if (p.getUniqueId().equals(id)) {
                            SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(schem);
                            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                                    .getPlugin("WorldEdit");
                            WorldEdit we = wep.getWorldEdit();

                            LocalPlayer localPlayer = wep.wrapPlayer(p);
                            LocalSession localSession = we.getSession(localPlayer);
                            EditSession editSession = localSession.createEditSession(localPlayer);
                            WorldData worldData = editSession.getWorld().getWorldData();
                            ClipboardHolder clipboardHolder = new ClipboardHolder(
                                    Main.brew.getSchematicUtil().toWeClipboard(s), worldData);
                            localSession.setClipboard(clipboardHolder);
                            p.sendMessage(Main.prefix + "§aSchematic §6" + s.getSchemName() + " §ageladen! ");
                            return false;
                        }
                        p.sendMessage(Main.prefix + "§cDu hast keine Rechte auf diese Schematic.");
                    } catch (Exception e) {
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("save")) {
                if (p.hasPermission("system.schem.save.other")) {
                    if (args[1].contains("/")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File(Main.SCHEM_DIR + id.toString() + "/" + name);
                    try {
                        if (!Main.brew.getSchematicUtil().isSchematic(schem)
                                || !Main.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(Main.prefix + "§cKeine gültige SGSchematic.");
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
                            new File(Main.SCHEM_DIR + id.toString() + "/").mkdirs();
                            Main.brew.getSchematicUtil().saveSchematic(clipboard,
                                    new File(Main.SCHEM_DIR + id.toString() + "/" + name), id.toString(), name,
                                    "normal", "STONE:0", System.currentTimeMillis());
                            p.sendMessage(Main.prefix + "§aGespeichert: §6" + name);
                        } catch (EmptyClipboardException e) {
                            p.sendMessage(Main.prefix + "§cDein Clipboard ist leer. Kopiere mal was mit §6//copy");
                        } catch (IOException e) {
                            p.sendMessage("§cFehler beim speichern.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                } else {
                    if (args[1].contains("/")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[1].contains("\\")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    File schem = new File("plugins/WorldEdit/schematics/" + user.toLowerCase() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!Main.brew.getSchematicUtil().isSchematic(schem)
                                || !Main.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(Main.prefix + "§cKeine gültige SGSchematic.");
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
                            p.sendMessage(Main.prefix + "§cDu hast kein Recht auf diese Schematic.");
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
                            new File(Main.SCHEM_DIR + id.toString() + "/").mkdirs();
                            Main.brew.getSchematicUtil().saveSchematic(clipboard,
                                    new File(Main.SCHEM_DIR + id.toString() + "/" + name), user, name, "normal",
                                    "STONE:0", System.currentTimeMillis());
                            p.sendMessage(Main.prefix + "§aGespeichert: §6" + name);
                        } catch (EmptyClipboardException e) {
                            p.sendMessage(Main.prefix + "§cDein Clipboard ist leer. Kopiere mal was mit §6//copy");
                        } catch (IOException e) {
                            p.sendMessage("§cFehler beim speichern.");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("addmember")) {
                if (args[1].contains("/")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                    return false;
                }
                String user = args[2];
                UUID id = UUIDFetcher.getUUID(user);
                if (id == null) {
                    p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File schem = new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!schem.exists()) {
                    p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
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
                        p.sendMessage(Main.prefix + "§6" + user + " §cist bereits der Schematic hinzugefügt.");
                        return false;
                    }
                    ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("INSERT INTO SchematicMembers (name, owner, member) VALUES (?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, p.getUniqueId().toString());
                    ps.setString(3, id.toString());
                    Environment.getCurrent().getConnectionHolder().executeUpdate(ps);
                    p.sendMessage(Main.prefix + "§aDu hast §6" + user + " §azu deiner Schematic hinzugefügt.");
                } catch (SQLException e) {
                    p.sendMessage(Main.prefix + "§cNetzwerkfehler");
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("delmember")) {
                if (args[1].contains("/")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[1].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String user = args[2];
                UUID id = UUIDFetcher.getUUID(user);
                if (id == null) {
                    p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                    return false;
                }
                String name = args[1];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File schem = new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!schem.exists()) {
                    p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
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
                        p.sendMessage(Main.prefix + "§cDer Spieler ist kein Mitglied der Schematic.");
                        return false;
                    }
                    ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("DELETE FROM SchematicMembers WHERE name=?, owner=?, member=?");
                    ps.setString(1, name);
                    ps.setString(2, p.getUniqueId().toString());
                    ps.setString(3, id.toString());
                    Environment.getCurrent().getConnectionHolder().executeUpdate(ps);
                    p.sendMessage(Main.prefix + "§aDu hast §6" + user + " §caaus deiner Schematic entfernt.");
                } catch (SQLException e) {
                    p.sendMessage(Main.prefix + "§cNetzwerkfehler");
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("lock")) {
                if (p.hasPermission("system.schem.lock")) {
                    if (args[2].contains("/")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("\\")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    final String fname = name;
                    File schem = new File(Main.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!Main.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(Main.prefix + "§cDies ist keine gültige SGSchematic!");
                            return false;
                        }
                        SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(schem);
                        if (s.getType().equalsIgnoreCase("gesperrt")) {
                            p.sendMessage(
                                    Main.prefix + "§cDie Schematic ist gesperrt und kann nicht bearbeitet werden!");
                            return false;
                        }
                        s.setType("gesperrt");
                        Main.brew.getSchematicUtil().saveSchematic(Main.brew.getSchematicUtil().toWeClipboard(s),
                                new File(Main.SCHEM_DIR + id.toString() + "/" + name), p.getUniqueId().toString(),
                                s.getSchemName(), s.getType(), s.getIcon(), s.getCreated());
                        p.sendMessage(Main.prefix + "§aSchematic gesperrt.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("unlock")) {
                if (p.hasPermission("system.schem.unlock")) {
                    if (args[2].contains("/")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("\\")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §\\ §cverwenden!");
                        return false;
                    }
                    String user = args[1];
                    UUID id = UUIDFetcher.getUUID(user);
                    if (id == null) {
                        p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    final String fname = name;
                    File schem = new File(Main.SCHEM_DIR + id.toString() + "/" + name);
                    if (!schem.exists()) {
                        p.sendMessage(Main.prefix + "§cDie Schematic existiert nicht!");
                        return false;
                    }
                    try {
                        if (!Main.brew.getSchematicUtil().isGiantSchematic(schem)) {
                            p.sendMessage(Main.prefix + "§cDies ist keine gültige SGSchematic!");
                            return false;
                        }
                        SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(schem);
                        s.setType("normal");
                        Main.brew.getSchematicUtil().saveSchematic(Main.brew.getSchematicUtil().toWeClipboard(s),
                                new File(Main.SCHEM_DIR + id.toString() + "/" + name), p.getUniqueId().toString(),
                                s.getSchemName(), s.getType(), s.getIcon(), s.getCreated());
                        p.sendMessage(Main.prefix + "§aSchematic entsperrt.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten!");
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("info")) {
                if (p.hasPermission("system.schem.info.other")) {
                    if (args[2].contains(";")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6; §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("/")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                        return false;
                    }
                    if (args[2].contains("\\")) {
                        p.sendMessage(Main.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                        return false;
                    }
                    String name = args[2];
                    if (!name.endsWith(".schematic")) {
                        name = name + ".schematic";
                    }
                    UUID id = UUIDFetcher.getUUID(args[1]);
                    if (id == null) {
                        p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                        return false;
                    }
                    File toLoad = new File(Main.SCHEM_DIR + id.toString() + "/" + name);
                    if (!toLoad.exists()) {
                        p.sendMessage(Main.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                        return false;
                    }
                    try {
                        if (!Main.brew.getSchematicUtil().isSchematic(toLoad)) {
                            p.sendMessage(Main.prefix + "§cDas ist keine Schematic.");
                            return false;
                        }
                    } catch (IOException e) {
                        p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten.");
                        e.printStackTrace();
                        return false;
                    }
                    try {
                        if (!Main.brew.getSchematicUtil().isGiantSchematic(toLoad)) {
                            Schematic s = Main.brew.getSchematicUtil().loadSchematic(toLoad);
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
                            SGSchematic s = Main.brew.getSchematicUtil().loadGiantSchematic(toLoad);
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
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[2].contains("/")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[2].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String name = args[2];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                UUID id = UUIDFetcher.getUUID(args[1]);
                if (id == null) {
                    p.sendMessage(Main.prefix + "§cUnbekannter Spieler.");
                    return false;
                }
                File toLoad = new File(Main.SCHEM_DIR + id.toString() + "/" + name);
                if (!toLoad.exists()) {
                    p.sendMessage(Main.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    if (!Main.brew.getSchematicUtil().isSchematic(toLoad)) {
                        p.sendMessage(Main.prefix + "§cDas ist keine Schematic.");
                        return false;
                    }
                } catch (IOException e) {
                    p.sendMessage(Main.prefix + "§cEin Fehler ist aufgetreten.");
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
                                                Main.prefix + "§aDas Icon wurde zu §6" + m.name() + " §ageändert.");
                                    } catch (IOException e) {
                                        p.sendMessage(Main.prefix + "§cEs ist ein Fehler aufgetreten.");
                                        e.printStackTrace();
                                    }
                                }
                            });
                            BetaCore.debug("FEHLER: Noch nicht gefixter Code: Inventory Item zu Link-Item -> CommandSchem 1240");
                            //mpi.addItem(li);
                        }

                        //   mpi.register(h);
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
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6; §cverwenden!");
                    return false;
                }
                if (args[2].contains("/")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6/ §cverwenden!");
                    return false;
                }
                if (args[2].contains("\\")) {
                    p.sendMessage(Main.prefix + "§cDu darfst keine §6\\ §cverwenden!");
                    return false;
                }
                String name = args[2];
                if (!name.endsWith(".schematic")) {
                    name = name + ".schematic";
                }
                File toLoad = new File(Main.SCHEM_DIR + p.getUniqueId().toString() + "/" + name);
                if (!toLoad.exists()) {
                    p.sendMessage(Main.prefix + "§cDie Schematic konnte nicht gefunden werden.");
                    return false;
                }
                try {
                    PreparedStatement ps = Environment.getCurrent().getConnectionHolder()
                            .prepareStatement("DELETE FROM SchematicMembers WHERE name=? AND owner=?");
                    ps.setString(1, toLoad.getName());
                    ps.setString(2, p.getUniqueId().toString());
                    Environment.getCurrent().getConnectionHolder().executeUpdate(ps);
                    toLoad.delete();
                    p.sendMessage(Main.prefix + "§aSchematic gelöscht");
                } catch (SQLException e) {
                    p.sendMessage(Main.prefix + "§cEs ist ein Fehla aufgetreten :I");
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
