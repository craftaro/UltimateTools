package com.songoda.ultimatetools.enchant.enchants;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.core.compatibility.ServerVersion;
import com.songoda.core.nms.NmsManager;
import com.songoda.core.nms.nbt.NBTCore;
import com.songoda.core.nms.nbt.NBTItem;
import com.songoda.ultimatetools.UltimateTools;
import com.songoda.ultimatetools.enchant.AbstractEnchant;
import com.songoda.ultimatetools.enchant.EnchantHandler;
import com.songoda.ultimatetools.enchant.EnchantType;
import com.songoda.ultimatetools.enchant.ToolType;
import com.songoda.ultimatetools.settings.Settings;
import com.songoda.ultimatetools.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RemoteLoot extends AbstractEnchant {

    private final Map<UUID, Player> entities = new HashMap<>();
    private final Random random;

    public RemoteLoot() {
        super(EnchantType.REMOTE_LOOT, "Remote Loot", 1, 3,
                ToolType.SWORD, ToolType.AXE, ToolType.PICKAXE, ToolType.SHOVEL);
        this.random = new Random();
    }

    @EnchantHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        double d = ((LivingEntity) event.getEntity()).getHealth() - event.getDamage();
        if (d < 1) {
            entities.put(event.getEntity().getUniqueId(), player);
        }
    }

    @EnchantHandler
    public void onEntityKilledByPlayer(EntityDeathEvent event) {
        if (!entities.containsKey(event.getEntity().getUniqueId())) return;
        Player player = entities.get(event.getEntity().getUniqueId());

        ItemStack tool = player.getItemInHand();
        NBTCore nbt = NmsManager.getNbt();
        NBTItem nbtItem = nbt.of(tool);

        if (!nbtItem.has("RLL") || nbtItem.getNBTObject("RLL") == null)
            return;

        Location location = LocationUtils.unserializeLocation(nbtItem.getNBTObject("RLL").asString());

        if (location.getBlock().getType() != Material.CHEST) return;

        InventoryHolder ih = (InventoryHolder) location.getBlock().getState();
        for (ItemStack is : event.getDrops()) {
            Map<Integer, ItemStack> notDropped = ih.getInventory().addItem(is);
            if (!notDropped.isEmpty())
                location.getWorld().dropItemNaturally(event.getEntity().getLocation(), new ArrayList<>(notDropped.values()).get(0));
        }
        event.getDrops().clear();
    }

    @EnchantHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_BLOCK
                || event.getClickedBlock() == null
                || player.isSneaking()
                || !(event.getClickedBlock().getState() instanceof InventoryHolder || event.getClickedBlock().getType().equals(Material.ENDER_CHEST))) {
            return;
        }

        if (event.getClickedBlock().getType() == Material.CHEST) {
            ItemStack tool = event.getPlayer().getInventory().getItemInHand();
            NBTCore nbt = NmsManager.getNbt();
            NBTItem nbtItem = nbt.of(tool);
            boolean isLinked = nbtItem.has("RLL");

            if (isLinked) {
                UltimateTools.getInstance().getLocale()
                        .getMessage("event.remoteloot.unlink").sendPrefixedMessage(player);
                nbtItem.remove("RLL");
            } else {
                UltimateTools.getInstance().getLocale()
                        .getMessage("event.remoteloot.link").sendPrefixedMessage(player);
                nbtItem.set("RLL", LocationUtils.serializeLocation(event.getClickedBlock().getLocation()));
            }
            player.setItemInHand(nbtItem.finish());
            event.setCancelled(true);
        }
    }

    @EnchantHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack tool = event.getPlayer().getInventory().getItemInHand();

        ItemMeta meta = tool.getItemMeta();
        Location location = null;

        NBTCore nbt = NmsManager.getNbt();
        NBTItem nbtItem = nbt.of(tool);
        if (nbtItem.has("RLL")) //Remote Loot Location
            location = LocationUtils.unserializeLocation(nbtItem.getNBTObject("RLL").asString());

        CompatibleMaterial material = CompatibleMaterial.getMaterial(event.getBlock());

        if (location == null
                || material == CompatibleMaterial.CHEST
                || Settings.REMOTE_TOOLS_BLACKLIST.getStringList().contains(event.getBlock().getType().name())

                || material.name().contains("SHULKER")
                || material == CompatibleMaterial.SPAWNER) {
            return;
        }

        InventoryHolder ih = (InventoryHolder) location.getBlock().getState();
        Player player = event.getPlayer();
        Collection<ItemStack> drops = event.getBlock().getDrops();
        if (drops.isEmpty()) {
            drops = new ArrayList<>();
            ItemStack itemStack = getOreDrop(material, random);
            if (itemStack != null)
                drops.add(getOreDrop(material, random));
        }
        if (meta.hasEnchant(Enchantment.SILK_TOUCH)) {
            ih.getInventory().addItem(CompatibleMaterial.getMaterial(event.getBlock()).getItem());
        } else {
            if (meta.hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
                int level = meta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
                int dropAmount = calculateFortuneDrops(material, level, random);
                for (int i = 0; i < dropAmount; i++) {
                    for (ItemStack is : drops) ih.getInventory().addItem(is);
                }
            } else {
                for (ItemStack is : drops) ih.getInventory().addItem(is);
            }
        }

        if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_12)) {
            event.setDropItems(false);
            return;
        }

        event.isCancelled();
        player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
        if (player.getItemInHand().getDurability() >= player.getItemInHand().getType().getMaxDurability()) {
            player.getItemInHand().setType(null);
        }
        if (event.getExpToDrop() > 0)
            player.getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(event.getExpToDrop());
        event.getBlock().setType(Material.AIR);
    }

    private int calculateFortuneDrops(CompatibleMaterial material, int level, Random random) {
        if (material != CompatibleMaterial.COAL_ORE
                && material != CompatibleMaterial.DIAMOND_ORE
                && material != CompatibleMaterial.EMERALD_ORE
                && material != CompatibleMaterial.NETHER_QUARTZ_ORE
                && material != CompatibleMaterial.LAPIS_ORE) return 1;
        if (level <= 0) return 1;
        int drops = random.nextInt(level + 2) - 1;
        if (drops < 0) drops = 0;
        return applyLapisDrops(material, random) * (drops + 1);
    }

    private int applyLapisDrops(CompatibleMaterial material, Random random) {
        return material == CompatibleMaterial.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
    }

    private ItemStack getOreDrop(CompatibleMaterial material, Random random) {
        ItemStack item = null;
        switch (material) {
            case COAL_ORE:
                item = CompatibleMaterial.COAL.getItem();
                break;
            case DIAMOND_ORE:
                item = CompatibleMaterial.DIAMOND.getItem();
                break;
            case EMERALD_ORE:
                item = CompatibleMaterial.EMERALD.getItem();
                break;
            case GOLD_ORE:
                item = CompatibleMaterial.GOLD_ORE.getItem();
                break;
            case IRON_ORE:
                item = CompatibleMaterial.IRON_ORE.getItem();
                break;
            case LAPIS_ORE:
                item = CompatibleMaterial.LAPIS_LAZULI.getItem();
                break;
            case NETHER_QUARTZ_ORE:
                item = CompatibleMaterial.QUARTZ.getItem();
                break;
            case REDSTONE_ORE:
                item = CompatibleMaterial.REDSTONE.getItem();
                break;
        }

        switch (material) {
            case LAPIS_ORE:
                item.setAmount(random.nextInt((9 - 4) + 1) + 4);
                break;
            case REDSTONE_ORE:
                item.setAmount(random.nextInt((5 - 4) + 1) + 4);
                break;
        }
        return item;
    }
}
