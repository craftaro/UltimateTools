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
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
        for (AbstractEnchant enchant : enchants)
            if (!registerEnchant(enchant))
                return false;
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
}
