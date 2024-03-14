package de.felixstaude.testprojectpleaseignore.main;

import de.felixstaude.testprojectpleaseignore.island.IslandCommand;
import de.felixstaude.testprojectpleaseignore.inventory.InventoryClick;
import de.felixstaude.testprojectpleaseignore.island.items.SingleUseItems;
import de.felixstaude.testprojectpleaseignore.sql.IslandDatabase;
import de.felixstaude.testprojectpleaseignore.sql.SQLConnection;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestProjectPleaseIgnore extends JavaPlugin {
    IslandDatabase islandDatabase = new IslandDatabase();
    SQLConnection sqlConnection = new SQLConnection();

    @Override
    public void onEnable() {
        sqlConnection.connect();
        islandDatabase.initializeTable();

        this.getCommand("island").setExecutor(new IslandCommand());

        getServer().getPluginManager().registerEvents(new SingleUseItems(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    @Override
    public void onDisable() {
        sqlConnection.disconnect();
    }
}
