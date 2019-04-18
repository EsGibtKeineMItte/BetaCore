package de.wk.betacore.commands.spigot;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.leonhard.storage.Json;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.util.MaintanceSystem;
import de.wk.betacore.util.data.Misc;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class MaintanceCommand {
    Json maintance = FileManager.getMaintance();


    @Command(name = "maintenance", description = "Maintance Hauptbefehl", permission = "betacore.maintenance", aliases = {"m"})
    public void onMaintance(CommandArgs args) {

        CommandSender sender = args.getSender();

        if (args.length() != 0) {
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/maintance");
            return;
        }
        sender.sendMessage(Misc.PREFIX + "§7=====§eMaintanceSystem by TheWarKing.de§7=====");
        sender.sendMessage(Misc.PREFIX + "§7Adde einen Spieler: §6/maintance add §7<§6Spieler§7>");
        sender.sendMessage(Misc.PREFIX + "§7Entferne einen Spieler");

    }

    @Command(name = "maintenance.start", description = "Startet die Wartung", permission = "betacore.maintenance.start", aliases = {"m.start"})
    public void onStart(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() != 0) {
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/maintance start");
            return;
        }
        if(MaintanceSystem.isMaintance()){
            sender.sendMessage(Misc.PREFIX + "§7Der Wartungsmodus ist bereits aktiviert.");
            return;
        }
        MaintanceSystem.startMaintance();
        sender.sendMessage(Misc.PREFIX + "§aDu hast den WartungsModus erfolgreich aktiviert.");
    }

    @Command(name = "maintenance.stop", description = "Beendet die Wartung", permission = "betacore.maintenance.end", aliases = {"m.stop"})
    public void onEnd(CommandArgs args){
        CommandSender sender = args.getSender();
        if (args.length() != 0) {
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/maintance stop");
            return;
        }
        if(!MaintanceSystem.isMaintance()){
            sender.sendMessage(Misc.PREFIX + "§7Die Wartungsarbeiten sind bereits beendet.");
            return;
        }
        MaintanceSystem.endMaintance();
        sender.sendMessage(Misc.PREFIX + "§aDie Wartungsarbeiten wurden beendet.");
    }

    @Command(name = "maintenance.add", description = "Adde einen Spieler zur Wartung", permission = "betacore.maintenance.add", aliases = {"m.add"})
    public void onAdd(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() != 1) {
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/maintance add §7<§6Spieler§7>");
            return;
        }

        if(Bukkit.getOfflinePlayer(args.getArgs(0)) == null){
            sender.sendMessage(Misc.PREFIX + "§cDieser Spieler existiert nicht");
            return;
        }

        MaintanceSystem.addPlayer(Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId());
        sender.sendMessage(Misc.PREFIX + "§aDu hast den Spieler: §6" + Bukkit.getOfflinePlayer(args.getArgs(0)).getName() +
                " §7 zu der Wartungen hinzugefügt.");

    }

    @Command(name = "maintenance.remove", description = "Entfernt einen Spieler aus der Wartung", permission = "betacore.maintenance.remove", aliases = {"m.remove"})
    public void onRemove(CommandArgs args){
        CommandSender sender = args.getSender();
        if(args.length() != 1){
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/maintance remove §7<§6Spieler§7>");
            return;
        }
        if(Bukkit.getOfflinePlayer(args.getArgs(0)) == null){
            sender.sendMessage(Misc.PREFIX + "§cDieser Spieler existiert nicht.");
            return;
        }
        MaintanceSystem.remove(Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId());

        sender.sendMessage(Misc.PREFIX + "§aDu hast den Spieler: §6" + Bukkit.getOfflinePlayer(args.getArgs(0)).getName() +
                " §7 aus der Wartungs entfernt.");
    }

    @Command(name = "maintance.ban", description = "Bannt einen Spieler, dies ist nur per .json Dateien reversibel", permission = "betacore.maintenance.ban",aliases = {"m.ban"})
    public void onBan(CommandArgs args){
        CommandSender sender = args.getSender();
        if(args.length() != 1){
            sender.sendMessage(Misc.PREFIX + "§7Benutzung: §6/maintance remove §7<§6Spieler§7>");
            return;
        }
        if(Bukkit.getOfflinePlayer(args.getArgs(0)) == null){
            sender.sendMessage(Misc.PREFIX + "§cDieser Spieler existiert nicht.");
            return;
        }
        MaintanceSystem.ban(Bukkit.getOfflinePlayer(args.getArgs(0)).getUniqueId());

        sender.sendMessage(Misc.PREFIX + "§aDu hast den Spieler: §6" + Bukkit.getOfflinePlayer(args.getArgs(0)).getName() +
                " §7 aus der Wartungs entfernt.");
    }
}
