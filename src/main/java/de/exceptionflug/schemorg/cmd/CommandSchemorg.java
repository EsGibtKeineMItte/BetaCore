package de.exceptionflug.schemorg.cmd;

import com.google.common.base.Joiner;
import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.exceptionflug.schemorg.main.CheckSchem;
import de.exceptionflug.schemorg.main.SchemOrg;
import de.exceptionflug.schemorg.util.SchematicPaster;
import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class CommandSchemorg {

    @Command(name = "schemorg", description = "Hauptbefehl", permission = "schemorg", aliases = {
            "so"}, inGameOnly = true)
    public void onSchemorg(CommandArgs args) {
        Player p = args.getPlayer();
        p.sendMessage(SchemOrg.S_PREFIX + "SchematicOrganizer v"
                + Misc.VERSION + " by TheWarking.de (C) 2019");
        if (SchemOrg.toCheck.size() == 0) {
            p.sendMessage(SchemOrg.S_PREFIX + "Es gibt §ckeine §7Schematics zu prüfen");
        } else if (SchemOrg.toCheck.size() == 1) {
            p.sendMessage(SchemOrg.S_PREFIX + "Es gibt §ceine §7Schematic zu prüfen");
        } else {
            p.sendMessage(SchemOrg.S_PREFIX + "Es gibt §c" + SchemOrg.toCheck.size() + " §7Schematics zu prüfen");
        }
    }

    @Command(name = "schemorg.list", permission = "schemorg.list", aliases = {"so.list"}, inGameOnly = true)
    public void list(CommandArgs args) {
        Player p = args.getPlayer();
        if (SchemOrg.toCheck.size() == 0) {
            p.sendMessage(SchemOrg.S_PREFIX + "§cEs gibt keine Schematics zu prüfen");
        } else {
            p.sendMessage(SchemOrg.S_PREFIX + "§6Zu prüfen (" + SchemOrg.toCheck.size() + "):");
            for (CheckSchem cs : SchemOrg.toCheck) {

                try {
                    TextComponent tc = new TextComponent("§6" + cs.getName() + " §7von §6" +  Bukkit.getOfflinePlayer(cs.getOwner()).getName() + " §7[" + cs.getType().toUpperCase() + "]");
                    tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND,
                            "/schemorg load " + Bukkit.getOfflinePlayer(cs.getOwner()).getName() + " " + cs.getName()));
                    p.spigot().sendMessage(tc);
                } catch (Exception e) {
                    TextComponent tc = new TextComponent("§6" + cs.getName() + " §7von §6"
                            + "Team " + cs.getOwner() + " §7[" + cs.getType().toUpperCase() + "]");
                    tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND,
                            "/schemorg load " + cs.getOwner() + " " + cs.getName()));
                    p.spigot().sendMessage(tc);
                }
            }
        }
    }

    @Command(name = "schemorg.help", permission = "schemorg.help", aliases = {"so.help"}, inGameOnly = true)
    public void help(CommandArgs args) {
        Player p = args.getPlayer();
        p.sendMessage(SchemOrg.S_PREFIX + "§cHilfe für SchemOrg:");
        p.sendMessage("§8/schemorg - §6Zeigt Infos");
        p.sendMessage("§8/schemorg list - §6Zeigt die zu prüfenden Schematics");
        p.sendMessage("§8/schemorg help - §6Zeigt die Hilfe");
        p.sendMessage("§8/schemorg load <Spieler> <Schematic> - §6Lädt eine Schematic");
        p.sendMessage("§8/schemorg accept <Spieler> <Schematic> - §6Nimmt eine Schematic an");
        p.sendMessage("§8/schemorg decline <Spieler> <Schematic> <Grund> - §6Lehnt Schematic ab");
    }

    @Command(name = "schemorg.load", permission = "schemorg.load", aliases = {"so.load"}, inGameOnly = true)
    public void load(CommandArgs args) {
        Player p = args.getPlayer();
        if (args.length() < 2) {
            p.sendMessage(SchemOrg.S_PREFIX + "§bBenutzung: /schemorg load <Besitzer> <Schematic>");
        } else {
            UUID uuid = Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId();
            String target = null;
            if (uuid == null) {
                target = args.getArgs(0);
            } else {
                target = uuid.toString();
            }
            String name = args.getArgs(1);
            if (name.endsWith(".schematic") == false) {
                name = name.concat(".schematic");
            }
            CheckSchem cs = SchemOrg.getSchem(target, name);
            if (cs == null) {
                p.sendMessage(SchemOrg.S_PREFIX + "§cSchematic nicht gefunden");
                return;
            }
            try {
                if (!p.getWorld().getName().equalsIgnoreCase(SchemOrg.conf.checkSpawn.getWorld().getName())) {
                    p.teleport(SchemOrg.conf.checkSpawn);
                }
                SchematicPaster.pasteWarShip(SchemOrg.conf.autoPasteLoc, p, cs.getFile());
                p.sendMessage(SchemOrg.S_PREFIX + "§aSchematic wurde geladen.");
            } catch (Exception e) {
                p.sendMessage(
                        SchemOrg.S_PREFIX + "§4Es ist ein Fehler bei der Kommunikation mit WorldEdit aufgetreten!");
            }
        }
    }

    @Command(name = "schemorg.gui", permission = "schemorg.list", aliases = {"so.gui"}, inGameOnly = true)
    public void gui(CommandArgs args) {
        Player p = args.getPlayer();
        if (args.length() < 2) {
            p.sendMessage(SchemOrg.S_PREFIX + "§bBenutzung: /schemorg gui <Besitzer> <Schematic>");
        } else {
            UUID uuid = Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId();
            String target = null;
            if (uuid == null) {
                target = args.getArgs(0);
            } else {
                target = uuid.toString();
            }
            String name = args.getArgs(1);
            if (name.endsWith(".schematic") == false) {
                name = name.concat(".schematic");
            }
            CheckSchem cs = SchemOrg.getSchem(target, name);
            if (cs == null) {
                p.sendMessage(SchemOrg.S_PREFIX + "§cSchematic nicht gefunden");
                return;
            }

        }
    }

    @Command(name = "schemorg.accept", permission = "schemorg.accept", aliases = {"so.accept"}, inGameOnly = true)
    public void accept(CommandArgs args) {
        Player p = args.getPlayer();
        if (args.length() < 2) {
            p.sendMessage(SchemOrg.S_PREFIX + "§bBenutzung: /schemorg accept <Besitzer> <Schematic>");
        } else {
            UUID uuid = Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId();
            String target = null;
            if (uuid == null) {
                target = args.getArgs(0);
            } else {
                target = uuid.toString();
            }
            String name = args.getArgs(1);
            if (name.endsWith(".schematic") == false) {
                name = name.concat(".schematic");
            }
            CheckSchem cs = SchemOrg.getSchem(target, name);
            if (cs == null) {
                p.sendMessage(SchemOrg.S_PREFIX + "§cSchematic nicht gefunden");
                return;
            }
            try {
                cs.accept(p.getUniqueId());
                p.sendMessage(SchemOrg.S_PREFIX + "§aSchematic angenommen");
                Player z = Bukkit.getPlayer(uuid);
                if (z != null) {
                    z.sendMessage("§8[§aWarShips§8] §aDeine Schematic §6" + name + " §awurde angenommen.");
                }
            } catch (IOException e) {
                p.sendMessage(SchemOrg.S_PREFIX + "§cEs ist ein Fehler aufgetreten");
                e.printStackTrace();
            }
        }
    }

    @Command(name = "schemorg.decline", permission = "schemorg.decline", aliases = {"so.decline"}, inGameOnly = true)
    public void decline(CommandArgs args) {
        Player p = args.getPlayer();
        if (args.length() < 3) {
            p.sendMessage(SchemOrg.S_PREFIX + "§bBenutzung: /schemorg decline <Besitzer> <Schematic> <Grund>");
        } else {
            UUID uuid = Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId();
            String target = null;
            if (uuid == null) {
                target = args.getArgs(0);
            } else {
                target = uuid.toString();
            }
            String name = args.getArgs(1);
            if (!name.endsWith(".schematic")) {
                name = name.concat(".schematic");
            }
            String msg = Joiner.on(" ").join(args.getArgs())
                    .substring(args.getArgs(1).length() + args.getArgs(0).length() + 2).trim();
            CheckSchem cs = SchemOrg.getSchem(target, name);
            if (cs == null) {
                p.sendMessage(SchemOrg.S_PREFIX + "§cSchematic nicht gefunden");
                return;
            }
            try {
                cs.decline(p.getUniqueId(), msg);
                if(uuid == null) {
                    return;
                }
                p.sendMessage(SchemOrg.S_PREFIX + "§cSchematic abgelehnt");
                Player z = Bukkit.getPlayer(uuid);
                if (z != null) {
                    z.sendMessage(Misc.PREFIX +  " §cDeine Schematic §6" + name + " §cwurde abgelehnt: §6" + msg);
                }
            } catch (IOException e) {
                p.sendMessage(SchemOrg.S_PREFIX + "§cEs ist ein Fehler aufgetreten");
                e.printStackTrace();
            }
        }
    }

}
