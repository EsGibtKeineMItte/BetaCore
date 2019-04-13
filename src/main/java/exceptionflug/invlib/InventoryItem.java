package exceptionflug.invlib;

import org.bukkit.inventory.ItemStack;

public class InventoryItem {
    
    ItemStack is;
    
    public InventoryItem(ItemStack is)
    {
        this.is = is;
    }
    
    public ItemStack getItemStack()
    {
        return is;
    }

}
