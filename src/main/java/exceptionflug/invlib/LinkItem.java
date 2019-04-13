package exceptionflug.invlib;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class LinkItem extends InventoryItem {
    
    private ArrayList<ClickActionListener> listener = new ArrayList<>();

    public LinkItem(ItemStack is)
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
    
    public void addClickListener(ClickActionListener listener) {
        this.listener.add(listener);
    }
    
    public void removeClickListener(ClickActionListener listener) {
        this.listener.remove(listener);
    }
    
    void click(Inventory inv, InventoryClickEvent ev) {
        for(ClickActionListener l : listener) {
            l.onClick(inv, ev);
        }
    }
    
}
