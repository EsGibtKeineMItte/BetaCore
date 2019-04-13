package de.exceptionflug.schemloader.main;

import net.thecobix.brew.schematic.SGSchematic;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CheckFile {
    public static void check(Player p, int id, File folder) {
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                check(p, id, f);
            } else {
                try {
                    if (Main.brew.getSchematicUtil().isSchematic(f)) {
                        SGSchematic s = Main.brew.getSchematicUtil().toGiantSchematic(Main.brew.getSchematicUtil().loadSchematic(f), "N/A", "N/A", "CheckSchematic", "STONE:0", System.currentTimeMillis());
                        int min;
                        int max;
                        if (id > s.getId()) {
                            max = id;
                            min = s.getId();
                        } else {
                            max = s.getId();
                            min = id;
                        }
                        int dif = max - min;
                        if (dif == 0) {
                            p.sendMessage("§6" + f.getAbsolutePath() + "§8: §4EXAKT IDENTISCH");
                        } else if (dif < 100) {
                            p.sendMessage("§6" + f.getAbsolutePath() + "§8: §4NAHEZU IDENTISCH");
                        } else if (dif < 200) {
                            p.sendMessage("§6" + f.getAbsolutePath() + "§8: §cSEHR ÄHNLICH");
                        } else if (dif < 300) {
                            p.sendMessage("§6" + f.getAbsolutePath() + "§8: §cÄHNLICH");
                        } else if (dif < 500) {
                            p.sendMessage("§6" + f.getAbsolutePath() + "§8: §eBEDINGTE ÄHNLICHKEIT");
                        } else if (dif < 1000) {
                            p.sendMessage("§6" + f.getAbsolutePath() + "§8: §eGERINGFÜGIG ÄHNLICH");
                        }
                    }
                } catch (IOException localIOException) {
                }
            }
        }
    }
}
