package de.felixstaude.testprojectpleaseignore.island;

import de.felixstaude.testprojectpleaseignore.sql.IslandDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockSupport;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class IslandGenerator {

    IslandDatabase islandDatabase = new IslandDatabase();
    private int IslandCount = islandDatabase.getIslandCount();

    public void generateIsland(Player player) {
        if(islandDatabase.hasPlayerIsland(player.getUniqueId())){
            System.out.println("Island could not generate");
            return;
        }

        int islandCount = getIslandCount();
        int spacing = 5000; // spacing of the islands

        // calculate island coordinates on spiral
        SpiralPosition pos = islandCount == 0 ? new SpiralPosition(0, 0) : calculateSpiralPosition(islandCount);

        // calculate spiral coordinate
        int x = pos.x * spacing;
        int z = pos.y * spacing;

        int y = 100; // Y-Coordinate of the island

        pasteIsland(player.getWorld(), x, y, z);
        player.teleport(new Location(player.getWorld(), x + 0.5, y + 2, z + 0.5));

        Location location = new Location(player.getWorld(), x ,y ,z);
        setDatabase(player, location);
    }

    class SpiralPosition {
        int x, y;

        public SpiralPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public ItemStack getLavaItem(){
        ItemStack lava = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta lavaMeta = lava.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§cDieser Eimer kann nur");
        lore.add("§ceinmal genutzt werden");
        lore.add(" ");

        lavaMeta.setLore(lore);
        lavaMeta.setCustomModelData(0001);
        lavaMeta.setDisplayName("§6einmal Lava Eimer");
        lava.setItemMeta(lavaMeta);
        return lava;
    }

    public ItemStack getWaterItem(){
        ItemStack water = new ItemStack(Material.WATER_BUCKET);
        ItemMeta waterMeta = water.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§bDieser Eimer kann nur");
        lore.add("§beinmal genutzt werden");
        lore.add(" ");

        waterMeta.setLore(lore);
        waterMeta.setCustomModelData(0001);
        waterMeta.setDisplayName("§3einmal Wasser Eimer");
        water.setItemMeta(waterMeta);
        return water;
    }

    public void teleportToIsland(Player player){
        if(islandDatabase.hasPlayerIsland(player.getUniqueId())){
            player.teleport(getIslandLocation(player));
        }
    }

    public Location getIslandLocation(Player player) {
        String coordinateString = islandDatabase.getIslandCoordinate(player.getUniqueId());

        String[] parts = coordinateString.split(";");

        if (parts.length >= 4) {
            String worldName = parts[0];
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                return new Location(player.getWorld(), 0, 0, 0);
            }
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);

            return new Location(world, x + 0.5, y + 1, z + 0.5);
        } else {
            return new Location(player.getWorld(), 0, 0, 0);

        }
    }

    private SpiralPosition calculateSpiralPosition(int n) {
        int layer = (int) Math.ceil((Math.sqrt(n + 1) - 1) / 2);
        int legLength = 2 * layer;
        int legStep = (n - (2 * layer - 1) * (2 * layer - 1)) % legLength;
        int x, y;

        int side = (n - (2 * layer - 1) * (2 * layer - 1)) / legLength;
        switch (side) {
            case 0:
                x = layer;
                y = -layer + legStep;
                break;
            case 1:
                x = layer - legStep;
                y = layer;
                break;
            case 2:
                x = -layer;
                y = layer - legStep;
                break;
            default:
                x = -layer + legStep;
                y = -layer;
                break;
        }

        return new SpiralPosition(x, y);
    }

    private void pasteIsland(World world, int centerX, int centerY, int centerZ){
        Location chestLocation = new Location(world, centerX + 2, centerY + 1, centerZ);
        int radius = 5;
        for(int x= -radius; x <= radius; x++) {
            for( int z = -radius; z <= radius; z++){
                Block block = world.getBlockAt(centerX + x, centerY, centerZ + z);
                block.setType(Material.DIRT);
                createLootChest(chestLocation);
            }
        }
    }

    private void createLootChest(Location location){
        Block block = location.getBlock();
        block.setType(Material.CHEST);
        ItemStack sapling = new ItemStack(Material.OAK_SAPLING);

        if(block.getState() instanceof Chest){
            Chest chest = (Chest) block.getState();
            Inventory chestInventory = chest.getBlockInventory();

            chestInventory.setItem(0, getLavaItem());
            chestInventory.setItem(1, getWaterItem());
            chestInventory.setItem(2, sapling);

        }
    }


    private int getIslandCount(){
        return IslandCount;
    }

    private void setDatabase(Player player, Location location){
        String finalLocation;

        finalLocation = location.getWorld().getName();
        finalLocation = finalLocation + ";" + location.getBlockX();
        finalLocation = finalLocation + ";" + location.getBlockY();
        finalLocation = finalLocation + ";" + location.getBlockZ();
        finalLocation = finalLocation + ";" + location.getPitch();
        finalLocation = finalLocation + ";" + location.getYaw();

        islandDatabase.addIsland(player.getUniqueId(), finalLocation);
    }
}
