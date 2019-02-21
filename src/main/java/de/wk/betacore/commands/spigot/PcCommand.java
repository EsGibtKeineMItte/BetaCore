package de.wk.betacore.commands.spigot;

;
import de.wk.betacore.util.Player.WarPlayer;
import de.wk.betacore.util.Record;
import de.wk.betacore.util.data.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {


        if (!(cs instanceof Player)) {
            return false;
        } else {
            Player p = (Player) cs;
            if (args.length == 0) {
                p.sendMessage("§7/pc start  - §eStartet eine Aufnahme");
                p.sendMessage("§7/pc stop  - §eStoppt eine Aufnahme");
                p.sendMessage("§7/pc play [ticks] [fackelticks] - §eSpielt eine Aufnahme ab");
                p.sendMessage("§7/pc stopplay  - §eStoppt das Abspielen einer Aufnahme");
                return true;
            } else {
                Record r;
                if (args[0].equalsIgnoreCase("stop")) {
                    r = Record.getRecord(p);
                    r.stopRecord();
                    p.sendMessage(Misc.getPREFIX() + "§cAufnahme gestoppt");
                } else if (args[0].equalsIgnoreCase("stopplay")) {
                    r = Record.getRecord(p);
                    r.stopPlay(true);
                } else if (args[0].equalsIgnoreCase("start")) {
                    r = Record.getRecord(p);
                    r.startRecord();
                    p.sendMessage("§aPlatziere nun das TNT");
                } else if (args[0].equalsIgnoreCase("play")) {
                    r = Record.getRecord(p);
                    r.stopRecord();
                    if (args.length == 1) {
                        r.play(-1L, 8);
                    } else if (args.length == 2) {
                        if (!this.checkInt(args[1])) {
                            p.sendMessage("§e" + args[1] + "§c ist keine Zahl");
                            return true;
                        }

                        r.play((long) Integer.parseInt(args[1]), 8);
                    } else {
                        if (!this.checkInt(args[1])) {
                            p.sendMessage("§e" + args[1] + "§c ist keine Zahl");
                            return true;
                        }

                        if (!this.checkInt(args[2])) {
                            p.sendMessage("§e" + args[2] + "§c ist keine Zahl");
                            return true;
                        }

                        r.play((long) Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    }
                }

                return true;
            }
        }
    }

    private boolean checkInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }
}
