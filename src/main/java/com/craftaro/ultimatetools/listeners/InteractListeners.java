package com.craftaro.ultimatetools.listeners;

import com.craftaro.core.compatibility.CompatibleHand;
import com.craftaro.ultimatetools.UltimateTools;
import com.craftaro.ultimatetools.enchant.EnchantManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListeners implements Listener {

    private final UltimateTools plugin;
    private final EnchantManager enchantManager;

    public InteractListeners(UltimateTools plugin) {
        this.plugin = plugin;
        this.enchantManager = plugin.getEnchantManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (CompatibleHand.getHand(event) == CompatibleHand.OFF_HAND) return;
        if (enchantManager.isEnchanted(event.getPlayer().getItemInHand()))
            plugin.getEnchantManager().processEnchant(event, event.getPlayer().getItemInHand());
    }
}
