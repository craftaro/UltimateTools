package com.songoda.ultimatetools.listeners;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.core.nms.NmsManager;
import com.songoda.core.nms.nbt.NBTCore;
import com.songoda.core.nms.nbt.NBTItem;
import com.songoda.ultimatetools.UltimateTools;
import com.songoda.ultimatetools.enchant.AbstractEnchant;
import com.songoda.ultimatetools.enchant.EnchantManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by songoda on 3/14/2017.
 */
public class InventoryListeners implements Listener {
    private final EnchantManager enchantManager;

    public InventoryListeners(UltimateTools plugin) {
        this.enchantManager = plugin.getEnchantManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;

        if (event.getCursor() != null && event.getCurrentItem() != null) {
            ItemStack book = event.getCursor();
            if (CompatibleMaterial.getMaterial(book) != CompatibleMaterial.ENCHANTED_BOOK)
                return;

            ItemStack tool = event.getCurrentItem();
            if (!enchantManager.isEnchanted(book)) return;

            NBTCore nbt = NmsManager.getNbt();
            NBTItem nbtItem = nbt.of(book);
            String[] enchants = nbtItem.getNBTObject("UTE").asString().split(";");

            for (String enchantStr : enchants) {
                AbstractEnchant enchant = enchantManager.getEnchant(enchantStr);
                if (enchant == null) continue;

                if (book.hasItemMeta() && enchant.isApplicableType(CompatibleMaterial.getMaterial(tool))) {
                    event.setCurrentItem(enchant.apply(tool));
                    event.setCancelled(true);
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    player.updateInventory();
                }
            }
        }
    }
}
