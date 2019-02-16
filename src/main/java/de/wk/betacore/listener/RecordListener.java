package de.wk.betacore.listener;


import de.wk.betacore.util.Record;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class RecordListener implements Listener {

    private static RecordListener instance = new RecordListener();
    private HashMap<UUID, Record> records = new HashMap();

    public RecordListener() {
    }

    public static RecordListener getInstance() {
        return instance;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (this.records.containsKey(p.getUniqueId())) {
            if (!e.isCancelled() || e.canBuild()) {
                ((Record)this.records.get(p.getUniqueId())).onPlace(e);
            }
        }
    }

    @EventHandler
    public void onPlace(PlayerQuitEvent e) {
        if (this.records.containsKey(e.getPlayer().getUniqueId())) {
            this.unregister(e.getPlayer().getUniqueId());
        }

    }

    public void register(UUID uuid, Record record) {
        this.records.put(uuid, record);
    }

    public void unregister(UUID uuid) {
        this.records.remove(uuid);
    }
}
