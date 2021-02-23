package com.songoda.ultimatetools.enchant.enchants;

import com.songoda.core.compatibility.CompatibleHand;
import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.ultimatetools.enchant.AbstractEnchant;
import com.songoda.ultimatetools.enchant.EnchantHandler;
import com.songoda.ultimatetools.enchant.EnchantType;
import com.songoda.ultimatetools.enchant.ToolType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VeinMiner extends AbstractEnchant {

    private final List<CompatibleMaterial> ore = new ArrayList<>();

    public VeinMiner() {
        super(EnchantType.VEIN_MINER, "Vein Miner", 1, 3, ToolType.PICKAXE);

        ore.addAll(Arrays.asList(CompatibleMaterial.IRON_ORE,
                CompatibleMaterial.DIAMOND_ORE,
                CompatibleMaterial.COAL_ORE,
                CompatibleMaterial.LAPIS_ORE,
                CompatibleMaterial.REDSTONE_ORE,
                CompatibleMaterial.EMERALD_ORE,
                CompatibleMaterial.GOLD_ORE,
                CompatibleMaterial.NETHER_GOLD_ORE,
                CompatibleMaterial.NETHER_QUARTZ_ORE));
    }

    @EnchantHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        CompatibleMaterial material = CompatibleMaterial.getMaterial(block);

        if (!ore.contains(material)) return;

        int radius = 3;

        ItemStack pick = CompatibleHand.getHand(event).getItem(event.getPlayer());

        Location location = block.getLocation();
        Block clickedBlock = location.getBlock();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block foundBlock = clickedBlock.getWorld().getBlockAt(clickedBlock.getX() + x,
                            clickedBlock.getY() + y, clickedBlock.getZ() + z);
                    if (CompatibleMaterial.getMaterial(foundBlock) == material)
                        foundBlock.breakNaturally(pick);
                }
            }
        }
    }
}


