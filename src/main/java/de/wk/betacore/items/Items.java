package de.wk.betacore.items;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Items {

    public static ItemStack createItem(String name, ItemStack item, String... lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack createItem(String name, ItemStack item, List<String> lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        if (lore != null) {
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack createItem(ItemStack item, String... lore) {
        ItemMeta im = item.getItemMeta();
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack createItem(ItemStack item, List<String> lore) {
        ItemMeta im = item.getItemMeta();
        if (lore != null) {
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack createItem(String name, ItemStack item, short Durability, String... lore) {
        ItemStack it = createItem(item.getItemMeta().getDisplayName(), new ItemStack(item.getType(), item.getAmount(), (short) Durability), item.getItemMeta().getLore());
        ItemMeta im = it.getItemMeta();
        im.setDisplayName(name);
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        it.setItemMeta(im);
        return it;
    }

    public static ItemStack createItem(ItemStack item, short Durability, String... lore) {
        ItemStack it = createItem(new ItemStack(item.getType(), item.getAmount(), (short) Durability), item.getItemMeta().getLore());
        ItemMeta im = it.getItemMeta();
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        it.setItemMeta(im);
        return it;
    }

    public static ItemStack renameItem(ItemStack item, String Name) {
        item.getItemMeta().setDisplayName(Name);
        return item;
    }

    public static ItemStack[] renameItem(ItemStack[] item, String Name) {
        Integer i = 0;
        for (ItemStack it : item) {
            it.getItemMeta().setDisplayName(Name);
            item[i] = it;
            i++;
        }
        return item;
    }

    public static ItemStack reloreItem(ItemStack item, String... lore) {
        ItemMeta im = item.getItemMeta();
        if (lore != null) {
            List<String> lorelist = Arrays.asList(lore);
            im.setLore(lorelist);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack[] reloreItem(ItemStack[] item, String... lore) {
        Integer i = 0;
        for (ItemStack it : item) {
            ItemMeta im = it.getItemMeta();
            if (lore != null) {
                List<String> lorelist = Arrays.asList(lore);
                im.setLore(lorelist);
            }
            it.setItemMeta(im);
            item[i] = it;
            i++;
        }
        return item;
    }

    public static ItemStack enchant(ItemStack item, Boolean HideFlags, Enchantment[] enchantments, Integer[] levels) {
        ItemMeta im = item.getItemMeta();
        if (HideFlags == true) {
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        if (enchantments != null) {
            if (levels != null) {
                for (int i = 0; i < enchantments.length; i++) {
                    im.addEnchant(enchantments[i], levels[i], true);
                }
            } else {
                for (int i = 0; i < enchantments.length; i++) {
                    im.addEnchant(enchantments[i], 1, true);
                }
            }
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack enchant(ItemStack item, Boolean HideFlags, Enchantment[] enchantments) {
        ItemMeta im = item.getItemMeta();
        if (HideFlags == true) {
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        if (enchantments != null) {
            for (int i = 0; i < enchantments.length; i++) {
                im.addEnchant(enchantments[i], 1, true);
            }
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack enchant(ItemStack item, Boolean HideFlags, Enchantment enchantments, Integer levels) {
        ItemMeta im = item.getItemMeta();
        if (HideFlags == true) {
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        if (enchantments != null) {
            if (levels != null) {
                im.addEnchant(enchantments, levels, true);
            } else {
                im.addEnchant(enchantments, 1, true);
            }
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack enchant(ItemStack item, Boolean HideFlags, Enchantment enchantments) {
        ItemMeta im = item.getItemMeta();
        if (HideFlags == true) {
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        if (enchantments != null) {
            im.addEnchant(enchantments, 1, true);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack unenchantItem(ItemStack item, Enchantment Enchantment) {
        ItemMeta im = item.getItemMeta();
        im.removeEnchant(Enchantment);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack unenchantItem(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        im.removeEnchant(Enchantment.DURABILITY);
        im.removeEnchant(Enchantment.ARROW_DAMAGE);
        im.removeEnchant(Enchantment.ARROW_FIRE);
        im.removeEnchant(Enchantment.ARROW_INFINITE);
        im.removeEnchant(Enchantment.ARROW_KNOCKBACK);
        im.removeEnchant(Enchantment.BINDING_CURSE);
        im.removeEnchant(Enchantment.DAMAGE_ALL);
        im.removeEnchant(Enchantment.DAMAGE_ARTHROPODS);
        im.removeEnchant(Enchantment.DAMAGE_UNDEAD);
        im.removeEnchant(Enchantment.DEPTH_STRIDER);
        im.removeEnchant(Enchantment.DIG_SPEED);
        im.removeEnchant(Enchantment.FIRE_ASPECT);
        im.removeEnchant(Enchantment.FROST_WALKER);
        im.removeEnchant(Enchantment.KNOCKBACK);
        im.removeEnchant(Enchantment.LOOT_BONUS_BLOCKS);
        im.removeEnchant(Enchantment.LOOT_BONUS_MOBS);
        im.removeEnchant(Enchantment.LUCK);
        im.removeEnchant(Enchantment.LURE);
        im.removeEnchant(Enchantment.MENDING);
        im.removeEnchant(Enchantment.OXYGEN);
        im.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
        im.removeEnchant(Enchantment.PROTECTION_EXPLOSIONS);
        im.removeEnchant(Enchantment.PROTECTION_FALL);
        im.removeEnchant(Enchantment.PROTECTION_FIRE);
        im.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
        im.removeEnchant(Enchantment.SILK_TOUCH);
        im.removeEnchant(Enchantment.SWEEPING_EDGE);
        im.removeEnchant(Enchantment.THORNS);
        im.removeEnchant(Enchantment.VANISHING_CURSE);
        im.removeEnchant(Enchantment.WATER_WORKER);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack hideFlags(ItemStack item, ItemFlag HideFlags) {
        ItemMeta im = item.getItemMeta();
        if (im.getItemFlags().contains(HideFlags)) {
            im.addItemFlags(HideFlags);
        } else {
            im.removeItemFlags(HideFlags);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack hideFlags(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        if (im.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) {
            im.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            im.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            im.removeItemFlags(ItemFlag.HIDE_DESTROYS);
            im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            im.removeItemFlags(ItemFlag.HIDE_PLACED_ON);
            im.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        } else {
            im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            im.addItemFlags(ItemFlag.HIDE_DESTROYS);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack unbreakable(ItemStack item, boolean unbreakable) {
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(unbreakable);
        if (unbreakable == true) {
            im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        } else {
            im.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack unbreakable(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        if (im.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {
            im.setUnbreakable(false);
            im.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        } else {
            im.setUnbreakable(true);
            im.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }
        item.setItemMeta(im);
        return item;
    }

}
