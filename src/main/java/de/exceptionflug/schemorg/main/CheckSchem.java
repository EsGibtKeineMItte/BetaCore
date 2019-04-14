package de.exceptionflug.schemorg.main;


import de.exceptionflug.schemorg.util.DBUtil;
import de.exceptionflug.schemorg.util.SchematicPaster;
import lombok.Getter;
import net.thecobix.brew.main.Brew;
import net.thecobix.brew.schematic.SGSchematic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class CheckSchem {

    @Getter
    private File file;

    @Getter
    private String type, name;

    @Getter
    private String owner;

    public CheckSchem(File f, String t, String sname, String ownaz) {
        file = f;
        type = t;
        name = sname;
        owner = ownaz;
    }

    public void accept(UUID rejector) throws IOException {
        if (Brew.schematicUtil.isSchematic(file)) {
            File tar = new File(SchematicPaster.SCHEM_DIR + "/" + owner + "/" + name);
            try {
                DBUtil.addRejection(owner, rejector, name, "Accepted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new File(tar.getParent()).mkdirs();
            if (!tar.exists()) {
                tar.createNewFile();
            }
            if (!Brew.schematicUtil.isGiantSchematic(file)) {
                Brew.schematicUtil.saveSchematic(
                        Brew.schematicUtil.toWeClipboard(Brew.schematicUtil.loadSchematic(file)), tar, owner,
                        name, type, "TNT:0", System.currentTimeMillis());
                if (!file.delete()) {
                    FileUtils.forceDelete(file);
                }
            } else {
                SGSchematic schem = Brew.schematicUtil.loadGiantSchematic(file);
                Brew.schematicUtil.saveSchematic(Brew.schematicUtil.toWeClipboard(schem), tar, owner, name,
                        type, schem.getIcon(), schem.getCreated());
                if (!file.delete()) {
                    FileUtils.forceDelete(file);
                }
            }
        }
    }

    public void decline(UUID rejector, String reason) throws IOException {
        try {
            DBUtil.addRejection(owner, rejector, name, reason);
            if (!file.delete()) {
                FileUtils.forceDelete(file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
