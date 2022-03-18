package com.songoda.ultimatetools.enchant;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.core.third_party.de.tr7zw.nbtapi.NBTItem;
import com.songoda.core.utils.TextUtils;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractEnchant {

    private final EnchantType type;
    private final String customType;

    private final String name;

    private final int minLevel;

    private final int maxLevel;

    private final List<ToolType> applicableTypes = new ArrayList<>();

    private AbstractEnchant(EnchantType type, String key, String name, int minLevel, int maxLevel, ToolType... toolTypes) {
        this.type = type;
        this.customType = key;
        this.name = name;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        applicableTypes.addAll(Arrays.asList(toolTypes));
    }

    public AbstractEnchant(EnchantType type, String name, int minLevel, int maxLevel, ToolType... toolTypes) {
        this(type, null, name, minLevel, maxLevel, toolTypes);
    }

    public AbstractEnchant(String key, String name, int minLevel, int maxLevel, ToolType... toolTypes) {
        this(EnchantType.CUSTOM, key, name, minLevel, maxLevel, toolTypes);
    }

    public void onInteract(PlayerInteractEvent event) {
    }

    public void onBlockBreak(BlockBreakEvent event) {
    }

    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
    }

    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
    }

    public void onEntityKilledByPlayer(EntityDeathEvent event) {
    }

    public void onEntityExplode(EntityExplodeEvent event) {
    }

    public ItemStack apply(ItemStack item) {
        ItemMeta itemmeta = item.getItemMeta();
        assert itemmeta != null;

        List<String> lore = itemmeta.hasLore() ? itemmeta.getLore() : new ArrayList<>();
        assert lore != null;

        NBTItem nbtItem = new NBTItem(item);

        if (nbtItem.hasKey("UTE")) {
            for (String key : nbtItem.getString("UTE").split(";")) {
                if (key.equals(getIdentifyingType())) {
                    return item;
                }
            }
        }

        lore.add(TextUtils.formatText("&7" + name));

        itemmeta.setLore(lore);
        item.setItemMeta(itemmeta);

        EnchantManager.setGlowing(item);

        nbtItem = new NBTItem(item);

        if (nbtItem.hasKey("UTE"))
            nbtItem.setString("UTE", nbtItem.getString("UTE") + ";" + getIdentifyingType());
        else
            nbtItem.setString("UTE", getIdentifyingType());
        return nbtItem.getItem();
    }

    public ItemStack getBook() {
        return getBook(minLevel);
    }

    public ItemStack getBook(int level) {
        if (level > maxLevel || level < minLevel)
            return null;

        ItemStack book = CompatibleMaterial.ENCHANTED_BOOK.getItem();
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(TextUtils.formatText("&eEnchanted Book"));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(TextUtils.formatText("&7" + name));
        meta.setLore(lore);
        book.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(book);
        nbtItem.setString("UTE", getIdentifyingType()); // UltimateToolsEnchant

        return nbtItem.getItem();
    }

    public EnchantType getType() {
        return type;
    }

    public String getIdentifyingType() {
        return type == EnchantType.CUSTOM ? "CUSTOM_" + customType : type.name();
    }

    public String getName() {
        return name;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public List<ToolType> getApplicableTypes() {
        return Collections.unmodifiableList(applicableTypes);
    }

    public boolean isApplicableType(CompatibleMaterial material) {
        for (ToolType type : applicableTypes) {
            for (CompatibleMaterial m : type.getMaterials()) {
                if (material == m) return true;
            }
        }
        return false;
    }
}
