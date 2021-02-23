package com.songoda.ultimatetools.enchant.enchants;

import com.songoda.ultimatetools.enchant.AbstractEnchant;
import com.songoda.ultimatetools.enchant.EnchantHandler;
import com.songoda.ultimatetools.enchant.EnchantType;
import com.songoda.ultimatetools.enchant.ToolType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Blast extends AbstractEnchant {

    private final Map<UUID, TNTBlock> primed = new HashMap<>();

    public Blast() {
        super(EnchantType.BLAST, "Blast", 1, 3, ToolType.PICKAXE, ToolType.SHOVEL);
    }

    @EnchantHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        block.setType(Material.AIR);
        for (int i = 0; i != 5; i++) {
            TNTPrimed tnt = block.getWorld().spawn(block.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(i * 5);
            setSource(tnt, event.getPlayer());
            primed.put(tnt.getUniqueId(), new TNTBlock(tnt, event.getBlock().getLocation(), event.getPlayer().getLocation().getDirection()));
        }
    }

    @EnchantHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player)
            event.setCancelled(true);
    }

    @EnchantHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!primed.containsKey(event.getEntity().getUniqueId()))
            return;

        TNTBlock tntBlock = primed.get(event.getEntity().getUniqueId());
        List<Block> destroyed = event.blockList();
        List<Block> acceptable = getInRadius(tntBlock, 1);

        destroyed.removeIf(b -> !acceptable.contains(b));
    }

    private Class<?> clazzEntityLiving, clazzCraftLivingEntity ,clazzCraftTNTPrimed;
    private Method getHandle, getHandle2;
    private Field source;

    private void setSource(TNTPrimed tntPrimed, Player player) {
        if (clazzEntityLiving == null) {
            try {
                String ver = Bukkit.getServer().getClass().getPackage().getName().substring(23);
                clazzEntityLiving = Class.forName("net.minecraft.server." + ver + ".EntityLiving");
                Class<?> clazzEntityTNTPrimed = Class.forName("net.minecraft.server." + ver + ".EntityTNTPrimed");
                clazzCraftLivingEntity = Class.forName("org.bukkit.craftbukkit." + ver + ".entity.CraftLivingEntity");
                clazzCraftTNTPrimed = Class.forName("org.bukkit.craftbukkit." + ver + ".entity.CraftTNTPrimed");

                getHandle = clazzCraftLivingEntity.getDeclaredMethod("getHandle");
                getHandle2 = clazzCraftTNTPrimed.getDeclaredMethod("getHandle");

                source = clazzEntityTNTPrimed.getDeclaredField("source");
                source.setAccessible(true);
            } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        // Change via NMS the source of the TNT by the player
        try {
            Object nmsEntityLiving = getHandle.invoke(clazzCraftLivingEntity.cast(player));
            Object nmsTNT = getHandle2.invoke(clazzCraftTNTPrimed.cast(tntPrimed));
            source.set(nmsTNT, nmsEntityLiving);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public List<Block> getInRadius(TNTBlock tntBlock, int radius) {
        Location location = tntBlock.location.add(tntBlock.direction.multiply(2));
        Block clickedBlock = location.getBlock();
        List<Block> blocks = new ArrayList<>();
        blocks.add(clickedBlock);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = clickedBlock.getWorld().getBlockAt(clickedBlock.getX() + x,
                            clickedBlock.getY() + y, clickedBlock.getZ() + z);
                    if (!blocks.contains(block))
                        blocks.add(block);
                }
            }
        }
        return blocks;
    }

    private class TNTBlock {

        private final TNTPrimed tntPrimed;
        private final Location location;
        private final Vector direction;

        public TNTBlock(TNTPrimed tntPrimed, Location location, Vector direction) {
            this.tntPrimed = tntPrimed;
            this.location = location;
            this.direction = direction;
        }

        public TNTPrimed getTntPrimed() {
            return tntPrimed;
        }

        public Location getLocation() {
            return location.clone();
        }

        public Vector getDirection() {
            return direction;
        }
    }

}


