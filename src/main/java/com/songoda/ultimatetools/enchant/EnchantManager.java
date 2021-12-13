package com.songoda.ultimatetools.enchant;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.core.nms.NmsManager;
import com.songoda.core.nms.nbt.NBTCore;
import com.songoda.core.nms.nbt.NBTItem;
import com.songoda.ultimatetools.UltimateTools;
import com.songoda.ultimatetools.enchant.enchants.Blast;
import com.songoda.ultimatetools.enchant.enchants.MultiTool;
import com.songoda.ultimatetools.enchant.enchants.RemoteLoot;
import com.songoda.ultimatetools.enchant.enchants.VeinMiner;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnchantManager {

    private final Map<String, AbstractEnchant> registeredEnchants = new LinkedHashMap<>();
    private final List<HandlerWrapper> registeredHandlers = new LinkedList<>();

    private final UltimateTools plugin;

    public EnchantManager(UltimateTools plugin) {
        this.plugin = plugin;
    }

    public EnchantManager load() {
        registerEnchants(new MultiTool(),
                new RemoteLoot(),
                new Blast(),
                new VeinMiner());

        return this;
    }

    public boolean registerEnchant(AbstractEnchant enchant) {
        registeredEnchants.put(enchant.getIdentifyingType(), enchant);
        Set<Method> methods;

        try {
            Method[] publicMethods = enchant.getClass().getMethods();
            methods = new HashSet<>(publicMethods.length, Float.MAX_VALUE);
            methods.addAll(Arrays.asList(publicMethods));
            Collections.addAll(methods, enchant.getClass().getDeclaredMethods());
        } catch (NoClassDefFoundError e) {
            return false;
        }

        for (Method method : methods) {
            final EnchantHandler permissionHandler = method.getAnnotation(EnchantHandler.class);
            if (permissionHandler == null) continue;
            registeredHandlers.add(new HandlerWrapper(enchant, method));
        }

        return true;
    }

    public boolean registerEnchants(AbstractEnchant... enchants) {
        for (AbstractEnchant enchant : enchants) {
            if (!registerEnchant(enchant)) {
                return false;
            }
        }

        return true;
    }

    public boolean isEnchanted(ItemStack item) {
        NBTCore nbt = NmsManager.getNbt();
        NBTItem nbtItem = nbt.of(item);

        return nbtItem.has("UTE");
    }

    public void processEnchant(Event event, ItemStack item) {
        if (CompatibleMaterial.getMaterial(item) == CompatibleMaterial.ENCHANTED_BOOK)
            return;

        NBTCore nbt = NmsManager.getNbt();
        NBTItem nbtItem = nbt.of(item);
        List<String> enchants = Arrays.asList(nbtItem.getNBTObject("UTE").asString().split(";"));

        for (HandlerWrapper wrapper : registeredHandlers) {
            if (!enchants.contains(wrapper.getEnchant().getIdentifyingType()))
                continue;

            Method handler = wrapper.getHandler();
            if (handler.getParameterTypes()[0] != event.getClass())
                continue;

            AbstractEnchant enchant = wrapper.getEnchant();

            try {
                handler.invoke(enchant, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public AbstractEnchant getEnchant(String enchant) {
        return registeredEnchants.get(enchant.toUpperCase().replace(" ", "_"));
    }

    public List<AbstractEnchant> getEnchants() {
        return new ArrayList<>(registeredEnchants.values());
    }

    /**
     * Checks if a plugin has been modified to glow using {@link #setGlowing(ItemStack)}
     *
     * @param item The item to check
     *
     * @return true, if the plugins seems to be glowing
     *
     * @see #setGlowing(ItemStack)
     * @see #unsetGlowing(ItemStack)
     */
    public static boolean isGlowing(ItemStack item) {
        ItemMeta itemMeta = item.hasItemMeta() ? item.getItemMeta() : null;

        return itemMeta != null &&

                item.containsEnchantment(Enchantment.ARROW_INFINITE) &&
                item.getEnchantmentLevel(Enchantment.ARROW_INFINITE) == 99 &&

                itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
    }

    /**
     * Own implementation of {@link com.songoda.core.utils.ItemUtils#addGlow(ItemStack)} because it
     * is too aggressive and cannot guarantee that a player's enchantment is not overwritten, deleted or conflicting
     * <br><br>
     * Enchants the item with {@link Enchantment#ARROW_INFINITE} level 99 and adds {@link ItemFlag#HIDE_ENCHANTS} to it.
     * <br><br>
     *
     * <b>If the item is a {@link Material#BOW} or {@link CompatibleMaterial#CROSSBOW}, {@link Enchantment#LURE} is used instead.</b>
     *
     * <br><br>
     * Does nothing to the item if it is already glowing ({@link #isGlowing(ItemStack)} or {@link ItemStack#getEnchantments()} is empty)
     *
     * @param item The item that should be modified
     *
     * @see #unsetGlowing(ItemStack)
     */
    public static void setGlowing(ItemStack item) {
        if (!isGlowing(item) && item.getEnchantments().isEmpty()) {
            if (item.getType() == Material.BOW || CompatibleMaterial.getMaterial(item.getType()) == CompatibleMaterial.CROSSBOW) {
                item.addUnsafeEnchantment(Enchantment.LURE, 99);
            } else {
                item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 99);
            }

            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;

            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(itemMeta);
        }
    }

    /**
     * Own implementation of {@link com.songoda.core.utils.ItemUtils#addGlow(ItemStack)} because it
     * is too aggressive and cannot guarantee that a player's enchantment is not overwritten, deleted or conflicting
     *
     * @param item The item that should be modified
     *
     * @see #setGlowing(ItemStack)
     */
    public static void unsetGlowing(ItemStack item) {
        if (isGlowing(item)) {
            if (item.getType() == Material.BOW || CompatibleMaterial.getMaterial(item.getType()) == CompatibleMaterial.CROSSBOW) {
                item.removeEnchantment(Enchantment.LURE);
            } else {
                item.removeEnchantment(Enchantment.ARROW_INFINITE);
            }

            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null && itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(itemMeta);
            }
        }
    }
}
