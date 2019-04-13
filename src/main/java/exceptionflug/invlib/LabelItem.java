package exceptionflug.invlib;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LabelItem extends InventoryItem {

    public LabelItem(ItemStack is)
    {
        super(is);
    }
    
    public void setLore(ItemLore lore) {
        ItemMeta m = is.getItemMeta();
        m.setLore(lore.getLines());
        is.setItemMeta(m);
    }
    
    public void setDisplayName(String name) {
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
    }
    
    public void setEnchanted(boolean ench) {
        if(ench) {
            ItemMeta m = is.getItemMeta();
            m.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            is.setItemMeta(m);
        } else {
            ItemMeta m = is.getItemMeta();
            m.removeEnchant(Enchantment.DAMAGE_ALL);
            m.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            is.setItemMeta(m);
        }
    }

}
