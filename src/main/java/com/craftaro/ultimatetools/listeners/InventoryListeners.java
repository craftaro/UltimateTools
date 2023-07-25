package com.craftaro.ultimatetools.listeners;


import com.craftaro.core.third_party.com.cryptomorin.xseries.XMaterial;
import com.craftaro.core.third_party.de.tr7zw.nbtapi.NBTItem;
import com.craftaro.ultimatetools.UltimateTools;
import com.craftaro.ultimatetools.enchant.AbstractEnchant;
import com.craftaro.ultimatetools.enchant.EnchantManager;
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
            if (XMaterial.matchXMaterial(book) != XMaterial.ENCHANTED_BOOK)
                return;

            ItemStack tool = event.getCurrentItem();
            if (!enchantManager.isEnchanted(book)) return;

            NBTItem nbtItem = new NBTItem(book);
            String[] enchants = nbtItem.getString("UTE").split(";");

            for (String enchantStr : enchants) {
                AbstractEnchant enchant = enchantManager.getEnchant(enchantStr);
                if (enchant == null) continue;

                if (book.hasItemMeta() && enchant.isApplicableType(XMaterial.matchXMaterial(tool))) {
                    event.setCurrentItem(enchant.apply(tool));
                    event.setCancelled(true);
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    player.updateInventory();
                }
            }
        }
    }
}
