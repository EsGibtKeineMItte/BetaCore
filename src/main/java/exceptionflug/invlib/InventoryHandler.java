package exceptionflug.invlib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class InventoryHandler implements Listener {

    private ArrayList<Inventory> inventories = new ArrayList<>();
    private Player p;
    private boolean alive = true;
    
    public InventoryHandler(JavaPlugin plugin, Player p)
    {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.p = p;
    }
    
    public Inventory getInventory(String title, int size) {
        for(Inventory inv : this.inventories) {
            if(inv.getSize() == size && inv.getTitle().equals(title)) {
                return inv;
            }
        }
        return null;
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onClick(InventoryClickEvent e) {
        if(alive == false)
            return;
        if(!e.getWhoClicked().getUniqueId().equals(p.getUniqueId()))
            return;
        Inventory inv = getInventory(e.getInventory().getTitle(), e.getInventory().getSize());
        if(inv != null) {
            e.setCancelled(true);
            InventoryItem item = inv.getItem(e.getSlot());
            if(item != null) {
                if(item instanceof LinkItem) {
                    LinkItem i = (LinkItem) item;
                    i.click(inv, e);
                }
            }
        }
    }
    
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(alive == false)
            return;
        if(e.getPlayer().getUniqueId().equals(p.getUniqueId())) {
            Inventory inv = getInventory(e.getInventory().getTitle(), e.getInventory().getSize());
            if(inv != null && !inv.getTitle().contains("Seite")) {
                die();
            }
        }
    }
    
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        if(alive == false)
            return;
        if(e.getPlayer().getUniqueId().equals(p.getUniqueId()))
            die();
    }
    
    void onInvUpdate(Inventory inv) {
        p.openInventory(inv.build());
    }
    
    public void manage(Inventory inv) {
        inv.handler = this;
        alive = true;
        this.inventories.add(inv);
    }
    
    public void remove(Inventory inv) {
        inv.handler = null;
        this.inventories.remove(inv);
    }
    
    public void die() {
        this.alive = false;
        PlayerQuitEvent.getHandlerList().unregister(this);
        InventoryCloseEvent.getHandlerList().unregister(this);
        InventoryClickEvent.getHandlerList().unregister(this);
    }
    
}
