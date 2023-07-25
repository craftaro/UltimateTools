package com.craftaro.ultimatetools.listeners;

import com.craftaro.ultimatetools.UltimateTools;
import com.craftaro.ultimatetools.enchant.EnchantManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListeners implements Listener {

    private final UltimateTools plugin;
    private final EnchantManager enchantManager;

    public BlockListeners(UltimateTools plugin) {
        this.plugin = plugin;
        this.enchantManager = plugin.getEnchantManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getItemInHand();
        if (enchantManager.isEnchanted(item))
            plugin.getEnchantManager().processEnchant(event, item);
    }
}
