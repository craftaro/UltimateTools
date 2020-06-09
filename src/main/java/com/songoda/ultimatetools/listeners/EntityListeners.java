package com.songoda.ultimatetools.listeners;

import com.songoda.ultimatetools.UltimateTools;
import com.songoda.ultimatetools.enchant.EnchantManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListeners implements Listener {

    private final UltimateTools plugin;
    private final EnchantManager enchantManager;

    public EntityListeners(UltimateTools plugin) {
        this.plugin = plugin;
        this.enchantManager = plugin.getEnchantManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if (enchantManager.isEnchanted(player.getItemInHand()))
            plugin.getEnchantManager().processEnchant(event, player.getItemInHand());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;
        if (enchantManager.isEnchanted(player.getItemInHand()))
            plugin.getEnchantManager().processEnchant(event, player.getItemInHand());

    }
}
