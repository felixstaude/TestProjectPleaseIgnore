package de.felixstaude.testprojectpleaseignore.island;

import de.felixstaude.testprojectpleaseignore.inventory.IslandInventory;
import de.felixstaude.testprojectpleaseignore.sql.IslandDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandCommand implements CommandExecutor {

    IslandGenerator islandGenerator = new IslandGenerator();
    IslandInventory islandInventory = new IslandInventory();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();

            if(!s.equalsIgnoreCase("island")){return false;}
            if(strings.length == 0){
                islandInventory.openInventory(player);
                return true;
            } else if(strings.length == 1){
                if(strings[0].equalsIgnoreCase("create")){
                    IslandGenerator islandGenerator = new IslandGenerator();
                    islandGenerator.generateIsland(player);
                    return true;
                }
                if(strings[0].equalsIgnoreCase("teleport")){
                    islandGenerator.teleportToIsland(player);
                    return true;
                }
            }
        }
        return false;
    }


}
