package com.craftaro.ultimatetools.enchant.enchants;

import com.craftaro.core.compatibility.CompatibleHand;
import com.craftaro.core.third_party.com.cryptomorin.xseries.XMaterial;
import com.craftaro.ultimatetools.enchant.AbstractEnchant;
import com.craftaro.ultimatetools.enchant.EnchantHandler;
import com.craftaro.ultimatetools.enchant.EnchantType;
import com.craftaro.ultimatetools.enchant.ToolType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VeinMiner extends AbstractEnchant {

    private final List<XMaterial> ore = new ArrayList<>();

    public VeinMiner() {
        super(EnchantType.VEIN_MINER, "Vein Miner", 1, 3, ToolType.PICKAXE);

        ore.addAll(Arrays.asList(XMaterial.IRON_ORE,
                XMaterial.DIAMOND_ORE,
                XMaterial.COAL_ORE,
                XMaterial.LAPIS_ORE,
                XMaterial.REDSTONE_ORE,
                XMaterial.EMERALD_ORE,
                XMaterial.GOLD_ORE,
                XMaterial.NETHER_GOLD_ORE,
                XMaterial.NETHER_QUARTZ_ORE));
    }

    @EnchantHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        XMaterial material = XMaterial.matchXMaterial(block.getType());

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
                    if (XMaterial.matchXMaterial(foundBlock.getType()) == material)
                        foundBlock.breakNaturally(pick);
                }
            }
        }
    }
}


