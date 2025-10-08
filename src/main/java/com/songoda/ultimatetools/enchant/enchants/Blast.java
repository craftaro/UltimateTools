package com.songoda.ultimatetools.enchant.enchants;

import com.songoda.third_party.com.cryptomorin.xseries.XMaterial;
import com.songoda.ultimatetools.enchant.AbstractEnchant;
import com.songoda.ultimatetools.enchant.EnchantHandler;
import com.songoda.ultimatetools.enchant.EnchantType;
import com.songoda.ultimatetools.enchant.ToolType;
import com.songoda.ultimatetools.settings.Settings;
import com.songoda.ultimatetools.utils.LocationUtils;
import com.songoda.core.third_party.de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Blast extends AbstractEnchant {
    public Blast() {
        super(EnchantType.BLAST, "Blast", 1, 3, ToolType.PICKAXE, ToolType.SHOVEL);
    }

    @EnchantHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInHand();

        // Check for Remote Loot tool and get the linked chest location
        Location remoteChestLocation = null;
        boolean hasRemoteLoot = false;
        if (tool != null && tool.hasItemMeta()) {
            NBTItem nbtItem = new NBTItem(tool);
            if (nbtItem.hasKey("RLL")) {
                remoteChestLocation = LocationUtils.unserializeLocation(nbtItem.getString("RLL"));
                // Verify the chest still exists
                if (remoteChestLocation != null && remoteChestLocation.getBlock().getType() == Material.CHEST) {
                    hasRemoteLoot = true;
                }
            }
        }

        List<Block> destroyedBlocks = getBlocksInArea(block.getLocation(), player);
        // Remove the main block from the list to prevent processing it twice
        destroyedBlocks.remove(block);

        // Process each block in the blast radius (excluding the main block)
        for (Block destroyedBlock : destroyedBlocks) {
            if (hasRemoteLoot && remoteChestLocation != null) {
                // Cancel the event to prevent natural drops
                event.setDropItems(false);
                
                // Get and process the drops
                Collection<ItemStack> drops = destroyedBlock.getDrops(tool);
                if (!drops.isEmpty()) {
                    InventoryHolder chest = (InventoryHolder) remoteChestLocation.getBlock().getState();
                    for (ItemStack drop : drops) {
                        if (drop != null) {
                            // Add to chest or drop if full
                            Map<Integer, ItemStack> remaining = chest.getInventory().addItem(drop);
                            if (!remaining.isEmpty()) {
                                for (ItemStack item : remaining.values()) {
                                    destroyedBlock.getWorld().dropItemNaturally(destroyedBlock.getLocation(), item);
                                }
                            }
                        }
                    }
                }
                // Set to air to remove the block
                destroyedBlock.setType(Material.AIR);
            } else {
                // If not the main block, break it naturally
                if (!destroyedBlock.equals(block)) {
                    destroyedBlock.breakNaturally(tool);
                }
            }
        }

        // Apply tool damage for all blocks broken
        if (tool != null && tool.hasItemMeta() && !tool.getItemMeta().isUnbreakable()) {
            applyDamage(tool, destroyedBlocks.size());
        }
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
                    Material blockType = block.getType();

                    // Check if the block is blacklisted
                    if (blockType != Material.BEDROCK
                            && blockType != XMaterial.BARRIER.get()
                            && !Settings.BLAST_TOOLS_BLACKLIST.getStringList().contains(blockType.name())) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }
}
