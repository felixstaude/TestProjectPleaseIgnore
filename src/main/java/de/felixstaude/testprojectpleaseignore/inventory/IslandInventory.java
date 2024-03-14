package de.felixstaude.testprojectpleaseignore.inventory;

import de.felixstaude.testprojectpleaseignore.sql.IslandDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class IslandInventory {

    IslandDatabase islandDatabase = new IslandDatabase();
    public void openInventory(Player player){
        if(islandDatabase.hasPlayerIsland(player.getUniqueId())){
            hasIslandInventory(player);
            return;
        }
        hasNoIslandInventory(player);
        return;
    }

    private void hasNoIslandInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, "§bIsland Manager");

        inventory.setItem(1, getCreateIslandItem());
        player.openInventory(inventory);
    }

    private void hasIslandInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, "§bIsland Manager");

        inventory.setItem(1, getTeleportIslandItem());
        player.openInventory(inventory);
    }

    private ItemStack getCreateIslandItem(){
        ItemStack item = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§cErstelle eine");
        lore.add("§ceigene Insel");
        lore.add(" ");

        meta.setLore(lore);
        meta.setCustomModelData(0100);
        meta.setDisplayName("§6Insel erstellen");

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack getTeleportIslandItem(){
        ItemStack item = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§cTeleportiere dich");
        lore.add("§c zu deiner eigenen Insel");
        lore.add(" ");

        meta.setLore(lore);
        meta.setCustomModelData(0101);
        meta.setDisplayName("§6Teleportieren");

        item.setItemMeta(meta);

        return item;
    }

}
