package com.craftaro.ultimatetools.listeners;

import com.craftaro.ultimatetools.UltimateTools;
import com.craftaro.ultimatetools.enchant.EnchantManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityListeners implements Listener {

    private final UltimateTools plugin;
    private final EnchantManager enchantManager;

    public EntityListeners(UltimateTools plugin) {
        this.plugin = plugin;
        this.enchantManager = plugin.getEnchantManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) && !(event.getDamager() instanceof Player)) return;
        Player player = (Player) (event.getDamager() instanceof Player ? event.getDamager() : event.getEntity());
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed)) return;

        Entity entity = ((TNTPrimed) event.getEntity()).getSource();
        if (!(entity instanceof Player)) return;

        Player player = (Player) entity;
        if (enchantManager.isEnchanted(player.getItemInHand()))
            plugin.getEnchantManager().processEnchant(event, player.getItemInHand());
    }
}
