package com.songoda.ultimatetools.listeners;

import com.songoda.ultimatetools.UltimateTools;
import com.songoda.ultimatetools.enchant.EnchantManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListeners implements Listener {

    private final UltimateTools plugin;
    private final EnchantManager enchantManager;

    public BlockListeners(UltimateTools plugin) {
        this.plugin = plugin;
        this.enchantManager = plugin.getEnchantManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if (enchantManager.isEnchanted(event.getPlayer().getItemInHand()))
            plugin.getEnchantManager().processEnchant(event, event.getPlayer().getItemInHand());

    }
}
