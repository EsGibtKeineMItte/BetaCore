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


    public void removePermissions(Player player) {
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


    public static void setupPermissionConfig() {

        if (cm.getPermissions().getBoolean("permsSetup")) {
            return;
        }
        List<String> adminPerms = new ArrayList<String>();
        List<String> managerPerms = new ArrayList<String>();
        List<String> devPerms = new ArrayList<String>();
        List<String> modPerms = new ArrayList<String>();
        List<String> suppPerms = new ArrayList<String>();
        List<String> ytPerms = new ArrayList<String>();
        List<String> premiumPerms = new ArrayList<String>();
        List<String> userPerms = new ArrayList<String>();

        cm.getPermissions().setList("ADMIN", adminPerms);
        cm.getPermissions().setList("MANANGER", managerPerms);
        cm.getPermissions().setList("DEV", devPerms);
        cm.getPermissions().setList("MOD", modPerms);
        cm.getPermissions().setList("SUPPORTER", suppPerms);
        cm.getPermissions().setList("YOU_TUBER", ytPerms);
        cm.getPermissions().setList("PREMIUM", premiumPerms);
        cm.getPermissions().setList("USER", userPerms);
        cm.getPermissions().setBoolean("permsSetup", true);
        cm.getPermissions().save();


    }


    public HashMap<UUID, PermissionAttachment> getPlayerPermissions() {
        return playerPermissions;
    }
}
