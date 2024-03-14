package de.felixstaude.testprojectpleaseignore.inventory;

import de.felixstaude.testprojectpleaseignore.island.IslandGenerator;
import de.felixstaude.testprojectpleaseignore.sql.IslandDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    IslandDatabase islandDatabase = new IslandDatabase();
    IslandGenerator islandGenerator = new IslandGenerator();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getAction() != InventoryAction.NOTHING){
            // Island create Item
            if(event.getCurrentItem().getItemMeta().getCustomModelData() == 0100){
                if(!islandDatabase.hasPlayerIsland(event.getWhoClicked().getUniqueId())){
                    islandGenerator.generateIsland((Player) event.getWhoClicked());
                }
                event.setCancelled(true);
            }
            if(event.getCurrentItem().getItemMeta().getCustomModelData() == 0101){
                if(!islandDatabase.hasPlayerIsland(event.getWhoClicked().getUniqueId())){
                    islandGenerator.teleportToIsland((Player) event.getWhoClicked());
                }
                event.setCancelled(true);
            }
        }
    }
}
