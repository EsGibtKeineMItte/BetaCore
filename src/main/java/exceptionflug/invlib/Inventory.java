package exceptionflug.invlib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Inventory {
    
    private String title;
    private int size = 9;
    private HashMap<Integer, InventoryItem> invis = new HashMap<>();
    InventoryHandler handler;
    
    public Inventory(org.bukkit.inventory.Inventory inv)
    {
        this.title = inv.getTitle();
        this.size = inv.getSize();
        for (int i = 0; i < inv.getSize()-1; i++)
        {
            ItemStack curr = inv.getItem(i);
            if(curr != null && !(curr.getType() == Material.AIR)) {
                invis.put(i, new LabelItem(curr));
            }
        }
    }

    public Inventory(String title, int size)
    {
        this.title = title;
        this.size = size;
    }
    
    public InventoryItem getItem(int slot) {
        return invis.get(slot);
    }
    
    public void setItem(int slot, InventoryItem item) {
        invis.put(slot, item);
    }
    
    public int getSize()
    {
        return size;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void updateInventory() {
        if(handler != null)
            handler.onInvUpdate(this);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Inventory) {
            Inventory i = (Inventory) obj;
            if(i.getTitle().equals(title) && i.getSize() == size) {
                return true;
            }
        }
        return false;
    }

    public org.bukkit.inventory.Inventory build()
    {
        org.bukkit.inventory.Inventory i = Bukkit.createInventory(null, size, title);
        for(Integer slot : invis.keySet()) {
            i.setItem(slot, invis.get(slot).getItemStack());
        }
        return i;
    }
    
    
}
