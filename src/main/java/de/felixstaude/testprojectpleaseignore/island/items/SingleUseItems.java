package de.felixstaude.testprojectpleaseignore.island.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SingleUseItems implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            ItemStack item = event.getItem();
            if(item != null && item.getItemMeta().hasCustomModelData()){
                int customModelData = item.getItemMeta().getCustomModelData();
                Block clickedBlock = event.getClickedBlock();
                BlockFace face = event.getBlockFace();

                if(clickedBlock != null && (customModelData == 0001)){
                    Block placeBlock = clickedBlock.getRelative(face);

                    switch (item.getType()) {
                        case LAVA_BUCKET:
                            placeBlock.setType(Material.LAVA);
                            break;
                        case WATER_BUCKET:
                            placeBlock.setType(Material.WATER);
                            break;
                        default:
                            return;
                    }

                    if(item.getAmount() == 1) {
                        event.getPlayer().getInventory().setItem(event.getHand(), new ItemStack(Material.AIR));
                    } else {
                        item.setAmount(item.getAmount() - 1);
                    }

                    event.setCancelled(true);
                }
            }
        }
    }
}
