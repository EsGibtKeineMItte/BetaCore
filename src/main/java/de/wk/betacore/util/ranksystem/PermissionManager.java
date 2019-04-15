package de.wk.betacore.util.ranksystem;

import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.ConfigManager;
import de.wk.betacore.environment.Environment;
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
        try{
            for (Object permissions : cm.getPermissions().getList(RankSystem.getRank(uuid).toString())) {
                attachment.setPermission(permissions.toString(), true);
            }
        }catch(NullPointerException e){
            Environment.debug("Die Permissions wurden noch nicht eingetragen.");
        }

    }


    public static void setupPermissionConfig() {

        if (cm.getPermissions().getBoolean("permsSetup")) {
            return;
        }
        List<String> adminPerms = new ArrayList<String>();
        List<String> devPerms = new ArrayList<String>();
        List<String> modPerms = new ArrayList<String>();
        List<String> suppPerms = new ArrayList<String>();
        List<String> ytPerms = new ArrayList<String>();
        List<String> premiumPerms = new ArrayList<String>();
        List<String> userPerms = new ArrayList<String>();

        cm.getPermissions().setList(Rank.ADMIN.getName(), adminPerms);
        cm.getPermissions().setList(Rank.DEV.getName(), devPerms);
        cm.getPermissions().setList(Rank.MOD.getName(), modPerms);
        cm.getPermissions().setList(Rank.SUPPORTER.getName(), suppPerms);
        cm.getPermissions().setList(Rank.YOU_TUBER.getName(), ytPerms);
        cm.getPermissions().setList(Rank.PREMIUM.getName(), premiumPerms);
        cm.getPermissions().setList(Rank.USER.getName(), userPerms);
        cm.getPermissions().setBoolean("permsSetup", true);
        cm.getPermissions().save();


    }


    public HashMap<UUID, PermissionAttachment> getPlayerPermissions() {
        return playerPermissions;
    }
}
