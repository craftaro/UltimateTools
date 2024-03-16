package com.craftaro.ultimatetools.enchant.enchants;

import com.craftaro.core.compatibility.MethodMapping;
import com.craftaro.core.nms.ReflectionUtils;
import com.craftaro.ultimatetools.enchant.AbstractEnchant;
import com.craftaro.ultimatetools.enchant.EnchantHandler;
import com.craftaro.ultimatetools.enchant.EnchantType;
import com.craftaro.ultimatetools.enchant.ToolType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Blast extends AbstractEnchant {
    public Blast() {
        super(EnchantType.BLAST, "Blast", 1, 3, ToolType.PICKAXE, ToolType.SHOVEL);
    }

    @EnchantHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInHand();

        List<Block> destroyedBlocks = getBlocksInArea(block.getLocation(), event.getPlayer());
        for (Block destroyedBlock : destroyedBlocks)
            destroyedBlock.breakNaturally(tool);


    }

    private List<Block> getBlocksInArea(Location location, Player player) {
        List<Block> blocks = new ArrayList<>();
        int offsetX = 0;
        int offsetY = 0;
        int offsetZ = 0;

        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();

        if (pitch < -60) {
            offsetY = 1; // Player is looking down
        } else if (pitch > 60) {
            offsetY = -1; // Player is looking up
        } else {
            if (yaw >= -45 && yaw < 45) {
                offsetZ = 1; // Player is looking south
            } else if (yaw >= 45 && yaw < 135) {
                offsetX = -1; // Player is looking west
            } else if (yaw >= -135 && yaw < -45) {
                offsetX = 1; // Player is looking east
            } else {
                offsetZ = -1; // Player is looking north
            }
        }

        // Get the center block location
        Location centerLocation = location.clone().add(offsetX, offsetY, offsetZ);

        // Iterate over the 3x3x3 cube
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block block = centerLocation.clone().add(x, y, z).getBlock();
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }
}
