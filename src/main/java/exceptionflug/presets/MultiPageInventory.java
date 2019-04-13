package exceptionflug.presets;

import exceptionflug.invlib.*;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MultiPageInventory {

    private ArrayList<Inventory> pages = new ArrayList<>();
    private int index = 0;
    private int currentPage = 1;
    private String title;
    
    public MultiPageInventory(String title)
    {
        this.title = title;
        Inventory inv = new Inventory(title.concat(" §7[Seite 1]"), 54);
        pages.add(inv);
    }
    
    public void addItem(InventoryItem item) {
        if(index > 44) {
            LinkItem np = new LinkItem(new ItemStack(Material.PAPER));
            np.setDisplayName("§eNächste Seite");
            np.addClickListener(new ClickActionListener() {
                
                @Override
                public void onClick(Inventory inv, InventoryClickEvent event)
                {
                    currentPage ++;
                    HumanEntity p = event.getWhoClicked();
                    p.closeInventory();
                    p.openInventory(pages.get(currentPage-1).build());
                }
            });
            pages.get(pages.size()-1).setItem(53, np);
            
            pages.get(pages.size()-1).updateInventory();
            Inventory inv = new Inventory(title.concat(" §7[Seite "+(pages.size()+1)+"]"), 54);
            LinkItem pp = new LinkItem(new ItemStack(Material.PAPER));
            pp.setDisplayName("§eVorherige Seite");
            pp.addClickListener(new ClickActionListener() {
                
                @Override
                public void onClick(Inventory inv, InventoryClickEvent event)
                {
                    currentPage --;
                    HumanEntity p = event.getWhoClicked();
                    p.closeInventory();
                    p.openInventory(pages.get(currentPage-1).build());
                }
            });
            inv.setItem(45, pp);
            pages.add(inv);
            index = 0;
        }
        Inventory inv = pages.get(pages.size()-1);
        inv.setItem(index, item);
        index ++;
        inv.updateInventory();
    }
    
    public void register(InventoryHandler manager) {
        for(Inventory page : pages) {
            manager.manage(page);
        }
    }
    
    public void unregister(InventoryHandler manager) {
        for(Inventory page : pages) {
            manager.remove(page);
        }
    }
    
    public void showUp(HumanEntity e) {
        e.openInventory(pages.get(currentPage-1).build());
    }
    
}
