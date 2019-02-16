package de.wk.betacore.util.ranksystem;

import de.wk.betacore.BetaCore;
import de.wk.betacore.util.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PermissionManager {
    public HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    static ConfigManager cm = new ConfigManager();

    public static void addPermission(String perm, Player target) {
        PermissionAttachment attachment = target.addAttachment(BetaCore.getInstance());
        attachment.setPermission(perm, true);
    }

    public static void removePermission(String perm, Player target) {
        PermissionAttachment attachment = target.addAttachment(BetaCore.getInstance());
        attachment.unsetPermission(perm);
    }


    public static void removePermissions(Player player) {
        for (Object perm2 : cm.getPermissions().getList("Admin")) {
            removePermission(perm2.toString(), player);
            System.out.println(perm2.toString());
        }
    }

    public void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(BetaCore.getInstance());
        this.playerPermissions.put(player.getUniqueId(), attachment);

        permissionsSetter(player.getUniqueId());
    }


    private void permissionsSetter(UUID uuid) {
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        RankSystem rankSystem = new RankSystem();
        for (Object permissions : cm.getPermissions().getList(rankSystem.getRank(uuid).toString())) { //Hier wird wohl die Liste nicht gefunden werden.
            System.out.print(permissions.toString());
            attachment.setPermission(permissions.toString(), true);
        }
    }


    public void setPerms() {

        if (cm.getPermissions().getList("ADMIN") != null) {
            return;
        }

        List<String> adminPerms = new ArrayList<String>();
        adminPerms.add("minecraft.*");
        adminPerms.add("alphacore.setpw");

        List<String> managerPerms = new ArrayList<String>();
        managerPerms.add("alphacore.setpw");
        List<String> devPerms = new ArrayList<String>();
        devPerms.add("alphacore.setpw");
        List<String> modPerms = new ArrayList<String>();
        modPerms.add("alphacore.setpw");
        List<String> suppPerms = new ArrayList<String>();
        suppPerms.add("alphacore.setpw");
        List<String> ytPerms = new ArrayList<String>();
        ytPerms.add("alphacore.setpw");
        List<String> premiumPerms = new ArrayList<String>();
        premiumPerms.add("alphacore.setpw");
        List<String> userPerms = new ArrayList<String>();
        userPerms.add("alphacore.setpw");

        cm.getPermissions().setList("ADMIN", adminPerms);
        cm.getPermissions().setList("MANANGER", managerPerms);
        cm.getPermissions().setList("DEV", devPerms);
        cm.getPermissions().setList("MOD", modPerms);
        cm.getPermissions().setList("SUPPORTER", suppPerms);
        cm.getPermissions().setList("You_Tuber", ytPerms);
        cm.getPermissions().setList("PREMIUM", premiumPerms);
        cm.getPermissions().setList("USER", userPerms);
        cm.getPermissions().save();
        cm.getGlobalConfig().setBoolean("permsSetup", true);

    }


    public HashMap<UUID, PermissionAttachment> getPlayerPermissions() {
        return playerPermissions;
    }
}
