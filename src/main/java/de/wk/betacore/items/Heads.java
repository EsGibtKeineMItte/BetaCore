package de.wk.betacore.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Create Heads and Manage Heads of all Player.
 */

public class Heads {
    
    private static ItemStack VersionHead()  {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
    }

    /**
     * Create a Head.
     *
     * @param name Name of the Head
     * @param SkullSkin The Skull Skin
     * @param lore The Lore of the Item
     * @return A Skull
     */

    public static ItemStack createHead(String name, Player SkullSkin, String... lore)  {
        ItemStack item = VersionHead();
        SkullMeta im = (SkullMeta) item.getItemMeta();
        im.setOwner(SkullSkin.getDisplayName());
        im.setDisplayName(name);
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    /**
     * Create a Head.
     *
     * @param name The Skull Name
     * @param Type The Skull Type
     * @param lore The Skull Lore
     * @return A Skull
     */

    public static ItemStack createHead(String name, SkullType Type, String... lore)  {
        ItemStack item = VersionHead();
        SkullMeta im = (SkullMeta) item.getItemMeta();
        im.setDisplayName(name);
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    /**
     * Create a Head.
     *
     * @param SkullSkin The Skull Skin
     * @param lore The Skull Lore
     * @return A Skull
     */

    public static ItemStack createHead(Player SkullSkin, String... lore)  {
        ItemStack item = VersionHead();
        SkullMeta im = (SkullMeta) item.getItemMeta();
        im.setOwner(SkullSkin.getDisplayName());
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    /**
     * Create a Head.
     *
     * @param Type The Skull Type
     * @param lore The Skull Lore
     * @return A Skull
     */

    public static ItemStack createHead(SkullType Type, String... lore)  {
        ItemStack item = VersionHead();
        SkullMeta im = (SkullMeta) item.getItemMeta();
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    /**
     * Create a Head.
     *
     * @param name The Skull Name
     * @param SkullSkin The Skull Skin
     * @param lore The Skull Lore
     * @return A Skull
     */

    public static ItemStack createHead(String name, OfflinePlayer SkullSkin, String... lore)  {
        ItemStack item = VersionHead();
        SkullMeta im = (SkullMeta) item.getItemMeta();
        im.setOwner(SkullSkin.getPlayer().getDisplayName());
        im.setDisplayName(name);
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    /**
     * Create a Head.
     *
     * @param SkullSkin The Skull Skin
     * @param lore The Skull Lore
     * @return A Skull
     */

    public static ItemStack createHead(OfflinePlayer SkullSkin, String... lore)  {
        ItemStack item = VersionHead();
        SkullMeta im = (SkullMeta) item.getItemMeta();
        im.setOwner(SkullSkin.getPlayer().getDisplayName());
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    /**
     * Get the Head of a specific Player.
     *
     * @param p The player to get the skull from
     * @param PlayerInfo If you want the Player Info
     * @param Gamemode If you want the Gamemode
     * @return A Skull
     */

    public static ItemStack getHead(Player p, Boolean PlayerInfo, Boolean Gamemode)  {
        ItemStack Head = null;
        if (PlayerInfo == false && Gamemode == false) {
            Head = createHead(p.getDisplayName(), p);
        } else if (PlayerInfo == true && Gamemode == false) {
            String Health = "§7Health §8»§a " + p.getHealth();
            String Food = "§7Food §8»§c " + p.getFoodLevel();
            Head = createHead(p.getDisplayName(), p, " ", Health, Food);
        } else if (PlayerInfo == false && Gamemode == true) {
            String StringGM = "§7GameMode §8»§e " + p.getGameMode();
            Head = createHead(p.getDisplayName(), p,  " ", StringGM);
        } else if (PlayerInfo == true && Gamemode == true) {
            String Health = "§7Health §8»§a " + p.getHealth();
            String Food = "§7Food §8»§c " + p.getFoodLevel();
            String StringGM = "§7GameMode §8»§e " + p.getGameMode();
            Head = createHead(p.getDisplayName(), p, " ", Health, Food, " ", StringGM);
        }
        if (Head == null) {
            throw (new NullPointerException());
        } else {
            return Head;
        }
    }

    /**
     * Get the Heads from every Online or Offline Player.
     *
     * @param PlayerInfo If you want the Player Info
     * @param Gamemode If you want the Gamemode
     * @param Offline If you want every OfflinePlayer as well
     * @return A Skull Array
     */

    public static ItemStack[] getHeads(Boolean PlayerInfo, Boolean Gamemode, Boolean Offline)  {
        ItemStack[] Heads = new ItemStack[Bukkit.getOnlinePlayers().size() + Bukkit.getOfflinePlayers().length];
        Integer headIndex = 0;
        for (Player all: Bukkit.getOnlinePlayers()) {
            if (PlayerInfo == false && Gamemode == false && Offline == false) {
                Heads[headIndex] = createHead(all.getDisplayName(), all);
            } else if (PlayerInfo == true && Gamemode == false && Offline == false) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food);
            } else if (PlayerInfo == false && Gamemode == true && Offline == false) {
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all,  " ", StringGM);
            } else if (PlayerInfo == true && Gamemode == true && Offline == false) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food, " ", StringGM);
            } else if (PlayerInfo == false && Gamemode == false && Offline == true) {
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", "§aOnline");
            } else if (PlayerInfo == true && Gamemode == false && Offline == true) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food, " ", "§aOnline");
            } else if (PlayerInfo == false && Gamemode == true && Offline == true) {
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all,  " ", StringGM, " ", "§aOnline");
            } else if (PlayerInfo == true && Gamemode == true && Offline == true) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food, " ", StringGM, " ", "§aOnline");
            }
            headIndex++;
        }
        for (OfflinePlayer all2: Bukkit.getOfflinePlayers()) {
            if (Offline == false) {
                Heads[headIndex] = createHead(all2.getPlayer().getDisplayName(), all2);
            } else {
                Heads[headIndex] = createHead(all2.getPlayer().getDisplayName(), all2, " ", "§cOffline");
            }

            headIndex++;
        }
        return Heads;
    }

    /**
     * Get the Heads of every Online Player.
     *
     * @param PlayerInfo If you want the Player Info
     * @param Gamemode If you want the Gamemode
     * @return A Skull Array
     */

    public static ItemStack[] getHeads(Boolean PlayerInfo, Boolean Gamemode)  {
        ItemStack[] Heads = new ItemStack[Bukkit.getOnlinePlayers().size()];
        Integer headIndex = 0;
        for (Player all: Bukkit.getOnlinePlayers()) {
            if (PlayerInfo == false && Gamemode == false) {
                Heads[headIndex] = createHead(all.getDisplayName(), all);
            } else if (PlayerInfo == true && Gamemode == false) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food);
            } else if (PlayerInfo == false && Gamemode == true) {
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all,  " ", StringGM);
            } else if (PlayerInfo == true && Gamemode == true) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food, " ", StringGM);
            }
            headIndex++;
        }
        return Heads;
    }

    /**
     * Get the Head of every Online Player.
     *
     * @param PlayerInfo If you want the Player Info
     * @param Gamemode If you want the Gamemode
     * @return A Skull Array
     */

    public static ItemStack[] onlineHeads(Boolean PlayerInfo, Boolean Gamemode)  {
        ItemStack[] Heads = new ItemStack[Bukkit.getOnlinePlayers().size()];
        Integer headIndex = 0;
        for (Player all: Bukkit.getOnlinePlayers()) {
            if (PlayerInfo == false && Gamemode == false) {
                Heads[headIndex] = createHead(all.getDisplayName(), all);
            } else if (PlayerInfo == true && Gamemode == false) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food);
            } else if (PlayerInfo == false && Gamemode == true) {
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all,  " ", StringGM);
            } else if (PlayerInfo == true && Gamemode == true) {
                String Health = "§7Health §8»§a " + all.getHealth();
                String Food = "§7Food §8»§c " + all.getFoodLevel();
                String StringGM = "§7GameMode §8»§e " + all.getGameMode();
                Heads[headIndex] = createHead(all.getDisplayName(), all, " ", Health, Food, " ", StringGM);
            }
            headIndex++;
        }
        return Heads;
    }

}
