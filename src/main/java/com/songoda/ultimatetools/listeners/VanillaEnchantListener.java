package com.songoda.ultimatetools.listeners;

import com.songoda.ultimatetools.UltimateTools;
import com.songoda.ultimatetools.enchant.EnchantManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class VanillaEnchantListener implements Listener {
    private final UltimateTools plugin;

    public VanillaEnchantListener(UltimateTools plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEnchantItem(EnchantItemEvent e) {
        if (this.plugin.getEnchantManager().isEnchanted(e.getItem())) {
            EnchantManager.unsetGlowing(e.getItem());
        }
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent e) {
        if (e.getResult() != null &&
                this.plugin.getEnchantManager().isEnchanted(e.getResult()) &&
                EnchantManager.isGlowing(e.getResult()) &&
                e.getResult().getEnchantments().size() > 1) {
            EnchantManager.unsetGlowing(e.getResult());
        }
    }
}
