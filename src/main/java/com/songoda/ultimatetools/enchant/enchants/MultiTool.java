package com.songoda.ultimatetools.enchant.enchants;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.ultimatetools.enchant.AbstractEnchant;
import com.songoda.ultimatetools.enchant.EnchantHandler;
import com.songoda.ultimatetools.enchant.EnchantType;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MultiTool extends AbstractEnchant {

    public MultiTool() {
        super(EnchantType.MULTI_TOOL, "Multi Tool", 1, 3,
                com.songoda.ultimatetools.enchant.ToolType.PICKAXE, com.songoda.ultimatetools.enchant.ToolType.AXE, com.songoda.ultimatetools.enchant.ToolType.SHOVEL);
    }

    @EnchantHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null)
            updateType(getType(event.getClickedBlock()), event.getPlayer().getItemInHand());
    }

    private void updateType(ToolType type, ItemStack tool) {
        if (type == MultiTool.ToolType.PICKAXE) {
            if (tool.getType().name().contains("NETHERITE"))
                tool.setType(CompatibleMaterial.NETHERITE_PICKAXE.getMaterial());
            else if (tool.getType().name().contains("DIAMOND"))
                tool.setType(CompatibleMaterial.DIAMOND_PICKAXE.getMaterial());
            else if (tool.getType().name().contains("IRON"))
                tool.setType(CompatibleMaterial.IRON_PICKAXE.getMaterial());
            else if (tool.getType().name().contains("STONE"))
                tool.setType(CompatibleMaterial.STONE_PICKAXE.getMaterial());
            else if (tool.getType().name().contains("WOOD"))
                tool.setType(CompatibleMaterial.WOODEN_PICKAXE.getMaterial());
            else if (tool.getType().name().contains("GOLD"))
                tool.setType(CompatibleMaterial.GOLDEN_PICKAXE.getMaterial());
        } else if (type == MultiTool.ToolType.AXE) {
            if (tool.getType().name().contains("NETHERITE"))
                tool.setType(CompatibleMaterial.NETHERITE_AXE.getMaterial());
            else if (tool.getType().name().contains("DIAMOND"))
                tool.setType(CompatibleMaterial.DIAMOND_AXE.getMaterial());
            else if (tool.getType().name().contains("IRON"))
                tool.setType(CompatibleMaterial.IRON_AXE.getMaterial());
            else if (tool.getType().name().contains("STONE"))
                tool.setType(CompatibleMaterial.STONE_AXE.getMaterial());
            else if (tool.getType().name().contains("WOOD"))
                tool.setType(CompatibleMaterial.WOODEN_AXE.getMaterial());
            else if (tool.getType().name().contains("GOLD"))
                tool.setType(CompatibleMaterial.GOLDEN_AXE.getMaterial());
        } else if (type == MultiTool.ToolType.SHOVEL) {
            if (tool.getType().name().contains("NETHERITE"))
                tool.setType(CompatibleMaterial.NETHERITE_SHOVEL.getMaterial());
            else if (tool.getType().name().contains("DIAMOND"))
                tool.setType(CompatibleMaterial.DIAMOND_SHOVEL.getMaterial());
            else if (tool.getType().name().contains("IRON"))
                tool.setType(CompatibleMaterial.IRON_SHOVEL.getMaterial());
            else if (tool.getType().name().contains("STONE"))
                tool.setType(CompatibleMaterial.STONE_SHOVEL.getMaterial());
            else if (tool.getType().name().contains("WOOD"))
                tool.setType(CompatibleMaterial.WOODEN_SHOVEL.getMaterial());
            else if (tool.getType().name().contains("GOLD"))
                tool.setType(CompatibleMaterial.GOLDEN_SHOVEL.getMaterial());
        }
    }

    private enum ToolType {
        PICKAXE, AXE, SHOVEL
    }

    private ToolType getType(Block block) {
        switch (CompatibleMaterial.getMaterial(block).getBlockMaterial()) {
            case ACTIVATOR_RAIL:
            case ANDESITE:
            case ANDESITE_SLAB:
            case ANDESITE_STAIRS:
            case ANDESITE_WALL:
            case ANCIENT_DEBRIS:
            case ANVIL:
            case BASALT:
            case BEACON:
            case BELL:
            case BLACK_CONCRETE:
            case BLACK_GLAZED_TERRACOTTA:
            case BLACK_SHULKER_BOX:
            case BLACK_TERRACOTTA:
            case BLACKSTONE:
            case BLACKSTONE_SLAB:
            case BLACKSTONE_STAIRS:
            case BLACKSTONE_WALL:
            case BLAST_FURNACE:
            case BLUE_CONCRETE:
            case BLUE_GLAZED_TERRACOTTA:
            case BLUE_ICE:
            case BLUE_SHULKER_BOX:
            case BLUE_TERRACOTTA:
            case BONE_BLOCK:
            case BRAIN_CORAL_BLOCK:
            case BREWING_STAND:
            case BRICKS:
            case BRICK_SLAB:
            case BRICK_WALL:
            case BRICK_STAIRS:
            case BROWN_CONCRETE:
            case BROWN_GLAZED_TERRACOTTA:
            case BROWN_SHULKER_BOX:
            case BROWN_TERRACOTTA:
            case BUBBLE_CORAL_BLOCK:
            case CAULDRON:
            case CHIPPED_ANVIL:
            case CHISELED_NETHER_BRICKS:
            case CHISELED_POLISHED_BLACKSTONE:
            case CHISELED_QUARTZ_BLOCK:
            case CHISELED_RED_SANDSTONE:
            case CHISELED_SANDSTONE:
            case CHISELED_STONE_BRICKS:
            case COAL_BLOCK:
            case COAL_ORE:
            case COBBLESTONE:
            case COBBLESTONE_SLAB:
            case COBBLESTONE_STAIRS:
            case COBBLESTONE_WALL:
            case CONDUIT:
            case CRACKED_NETHER_BRICKS:
            case CRACKED_POLISHED_BLACKSTONE_BRICKS:
            case CRACKED_STONE_BRICKS:
            case CRYING_OBSIDIAN:
            case CUT_RED_SANDSTONE:
            case CUT_RED_SANDSTONE_SLAB:
            case CUT_SANDSTONE:
            case CUT_SANDSTONE_SLAB:
            case CYAN_CONCRETE:
            case CYAN_GLAZED_TERRACOTTA:
            case CYAN_SHULKER_BOX:
            case CYAN_TERRACOTTA:
            case DAMAGED_ANVIL:
            case DARK_PRISMARINE:
            case DARK_PRISMARINE_SLAB:
            case DARK_PRISMARINE_STAIRS:
            case DEAD_BRAIN_CORAL_BLOCK:
            case DEAD_BUBBLE_CORAL_BLOCK:
            case DEAD_FIRE_CORAL_BLOCK:
            case DEAD_HORN_CORAL_BLOCK:
            case DEAD_TUBE_CORAL_BLOCK:
            case DETECTOR_RAIL:
            case DIAMOND_BLOCK:
            case DIAMOND_ORE:
            case DIORITE:
            case DIORITE_STAIRS:
            case DIORITE_WALL:
            case DISPENSER:
            case DROPPER:
            case EMERALD_BLOCK:
            case EMERALD_ORE:
            case ENCHANTING_TABLE:
            case ENDER_CHEST:
            case END_STONE:
            case END_STONE_BRICKS:
            case END_STONE_BRICK_SLAB:
            case END_STONE_BRICK_STAIRS:
            case END_STONE_BRICK_WALL:
            case FIRE_CORAL_BLOCK:
            case FURNACE:
            case GILDED_BLACKSTONE:
            case GOLD_BLOCK:
            case GOLD_ORE:
            case GRANITE:
            case GRANITE_STAIRS:
            case GRANITE_WALL:
            case GRAY_CONCRETE:
            case GRAY_GLAZED_TERRACOTTA:
            case GRAY_SHULKER_BOX:
            case GRAY_TERRACOTTA:
            case GREEN_CONCRETE:
            case GREEN_GLAZED_TERRACOTTA:
            case GREEN_SHULKER_BOX:
            case GREEN_TERRACOTTA:
            case GRINDSTONE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case HOPPER:
            case HORN_CORAL_BLOCK:
            case ICE:
            case IRON_BARS:
            case IRON_BLOCK:
            case IRON_DOOR:
            case IRON_ORE:
            case IRON_TRAPDOOR:
            case JIGSAW:
            case LANTERN:
            case LAPIS_BLOCK:
            case LAPIS_ORE:
            case LIGHT_BLUE_CONCRETE:
            case LIGHT_BLUE_GLAZED_TERRACOTTA:
            case LIGHT_BLUE_SHULKER_BOX:
            case LIGHT_BLUE_TERRACOTTA:
            case LIGHT_GRAY_CONCRETE:
            case LIGHT_GRAY_GLAZED_TERRACOTTA:
            case LIGHT_GRAY_SHULKER_BOX:
            case LIGHT_GRAY_TERRACOTTA:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case LIME_CONCRETE:
            case LIME_GLAZED_TERRACOTTA:
            case LIME_SHULKER_BOX:
            case LIME_TERRACOTTA:
            case LODESTONE:
            case MAGENTA_CONCRETE:
            case MAGENTA_GLAZED_TERRACOTTA:
            case MAGENTA_SHULKER_BOX:
            case MAGENTA_TERRACOTTA:
            case MAGMA_BLOCK:
            case MOSSY_COBBLESTONE:
            case MOSSY_COBBLESTONE_SLAB:
            case MOSSY_COBBLESTONE_STAIRS:
            case MOSSY_COBBLESTONE_WALL:
            case MOSSY_STONE_BRICKS:
            case MOSSY_STONE_BRICK_SLAB:
            case MOSSY_STONE_BRICK_STAIRS:
            case MOSSY_STONE_BRICK_WALL:
            case NETHERRACK:
            case NETHER_BRICKS:
            case NETHER_BRICK_FENCE:
            case NETHER_BRICK_SLAB:
            case NETHER_BRICK_STAIRS:
            case NETHER_BRICK_WALL:
            case NETHER_GOLD_ORE:
            case NETHER_QUARTZ_ORE:
            case NETHERITE_BLOCK:
            case OBSERVER:
            case OBSIDIAN:
            case ORANGE_CONCRETE:
            case ORANGE_GLAZED_TERRACOTTA:
            case ORANGE_SHULKER_BOX:
            case ORANGE_TERRACOTTA:
            case PACKED_ICE:
            case PINK_CONCRETE:
            case PINK_GLAZED_TERRACOTTA:
            case PINK_SHULKER_BOX:
            case PINK_TERRACOTTA:
            case POLISHED_ANDESITE:
            case POLISHED_ANDESITE_SLAB:
            case POLISHED_ANDESITE_STAIRS:
            case POLISHED_BASALT:
            case POLISHED_BLACKSTONE:
            case POLISHED_BLACKSTONE_BRICKS:
            case POLISHED_BLACKSTONE_BRICK_SLAB:
            case POLISHED_BLACKSTONE_BRICK_STAIRS:
            case POLISHED_BLACKSTONE_BRICK_WALL:
            case POLISHED_BLACKSTONE_SLAB:
            case POLISHED_BLACKSTONE_STAIRS:
            case POLISHED_BLACKSTONE_WALL:
            case POLISHED_DIORITE:
            case POLISHED_DIORITE_SLAB:
            case POLISHED_DIORITE_STAIRS:
            case POLISHED_GRANITE:
            case POLISHED_GRANITE_SLAB:
            case POLISHED_GRANITE_STAIRS:
            case POWERED_RAIL:
            case PRISMARINE:
            case PRISMARINE_BRICKS:
            case PRISMARINE_BRICK_SLAB:
            case PRISMARINE_BRICK_STAIRS:
            case PRISMARINE_SLAB:
            case PRISMARINE_STAIRS:
            case PRISMARINE_WALL:
            case PURPLE_CONCRETE:
            case PURPLE_GLAZED_TERRACOTTA:
            case PURPLE_SHULKER_BOX:
            case PURPLE_TERRACOTTA:
            case PURPUR_BLOCK:
            case PURPUR_PILLAR:
            case PURPUR_SLAB:
            case PURPUR_STAIRS:
            case QUARTZ_BLOCK:
            case QUARTZ_BRICKS:
            case QUARTZ_PILLAR:
            case QUARTZ_SLAB:
            case QUARTZ_STAIRS:
            case RAIL:
            case REDSTONE_BLOCK:
            case REDSTONE_ORE:
            case RED_CONCRETE:
            case RED_GLAZED_TERRACOTTA:
            case RED_NETHER_BRICKS:
            case RED_NETHER_BRICK_SLAB:
            case RED_NETHER_BRICK_STAIRS:
            case RED_NETHER_BRICK_WALL:
            case RED_SANDSTONE:
            case RED_SANDSTONE_SLAB:
            case RED_SANDSTONE_STAIRS:
            case RED_SANDSTONE_WALL:
            case RED_SHULKER_BOX:
            case RED_TERRACOTTA:
            case RESPAWN_ANCHOR:
            case SANDSTONE:
            case SANDSTONE_SLAB:
            case SANDSTONE_STAIRS:
            case SANDSTONE_WALL:
            case SHULKER_BOX:
            case SMOKER:
            case SMOOTH_QUARTZ:
            case SMOOTH_QUARTZ_SLAB:
            case SMOOTH_QUARTZ_STAIRS:
            case SMOOTH_RED_SANDSTONE:
            case SMOOTH_RED_SANDSTONE_SLAB:
            case SMOOTH_RED_SANDSTONE_STAIRS:
            case SMOOTH_SANDSTONE:
            case SMOOTH_SANDSTONE_SLAB:
            case SMOOTH_SANDSTONE_STAIRS:
            case SMOOTH_STONE:
            case SMOOTH_STONE_SLAB:
            case SOUL_SOIL:
            case SPAWNER:
            case STONE:
            case STONECUTTER:
            case STONE_BRICKS:
            case STONE_BRICK_SLAB:
            case STONE_BRICK_STAIRS:
            case STONE_BRICK_WALL:
            case STONE_BUTTON:
            case STONE_PRESSURE_PLATE:
            case STONE_SLAB:
            case STONE_STAIRS:
            case TARGET:
            case TERRACOTTA:
            case TUBE_CORAL_BLOCK:
            case WHITE_CONCRETE:
            case WHITE_GLAZED_TERRACOTTA:
            case WHITE_SHULKER_BOX:
            case WHITE_TERRACOTTA:
            case YELLOW_CONCRETE:
            case YELLOW_GLAZED_TERRACOTTA:
            case YELLOW_SHULKER_BOX:
            case YELLOW_TERRACOTTA:
                return MultiTool.ToolType.PICKAXE;
            case ACACIA_BUTTON:
            case ACACIA_DOOR:
            case ACACIA_FENCE:
            case ACACIA_FENCE_GATE:
            case ACACIA_LOG:
            case ACACIA_PLANKS:
            case ACACIA_PRESSURE_PLATE:
            case ACACIA_SIGN:
            case ACACIA_SLAB:
            case ACACIA_STAIRS:
            case ACACIA_TRAPDOOR:
            case ACACIA_WALL_SIGN:
            case ACACIA_WOOD:
            case BARREL:
            case BIRCH_BUTTON:
            case BIRCH_DOOR:
            case BIRCH_FENCE:
            case BIRCH_FENCE_GATE:
            case BIRCH_LOG:
            case BIRCH_PLANKS:
            case BIRCH_PRESSURE_PLATE:
            case BIRCH_SIGN:
            case BIRCH_SLAB:
            case BIRCH_STAIRS:
            case BIRCH_TRAPDOOR:
            case BIRCH_WALL_SIGN:
            case BIRCH_WOOD:
            case BLACK_BANNER:
            case BLACK_WALL_BANNER:
            case BLUE_BANNER:
            case BLUE_WALL_BANNER:
            case BOOKSHELF:
            case BROWN_BANNER:
            case BROWN_MUSHROOM_BLOCK:
            case BROWN_WALL_BANNER:
            case CAMPFIRE:
            case CARTOGRAPHY_TABLE:
            case CARVED_PUMPKIN:
            case CHEST:
            case CHORUS_PLANT:
            case COCOA:
            case COMPOSTER:
            case CRAFTING_TABLE:
            case CRIMSON_BUTTON:
            case CRIMSON_DOOR:
            case CRIMSON_FENCE:
            case CRIMSON_FENCE_GATE:
            case CRIMSON_HYPHAE:
            case CRIMSON_NYLIUM:
            case CRIMSON_PLANKS:
            case CRIMSON_PRESSURE_PLATE:
            case CRIMSON_SIGN:
            case CRIMSON_SLAB:
            case CRIMSON_STAIRS:
            case CRIMSON_STEM:
            case CRIMSON_TRAPDOOR:
            case CRIMSON_WALL_SIGN:
            case CYAN_BANNER:
            case CYAN_WALL_BANNER:
            case DARK_OAK_BUTTON:
            case DARK_OAK_DOOR:
            case DARK_OAK_FENCE:
            case DARK_OAK_FENCE_GATE:
            case DARK_OAK_LOG:
            case DARK_OAK_PLANKS:
            case DARK_OAK_PRESSURE_PLATE:
            case DARK_OAK_SIGN:
            case DARK_OAK_SLAB:
            case DARK_OAK_STAIRS:
            case DARK_OAK_TRAPDOOR:
            case DARK_OAK_WALL_SIGN:
            case DARK_OAK_WOOD:
            case DAYLIGHT_DETECTOR:
            case FLETCHING_TABLE:
            case GRAY_BANNER:
            case GRAY_WALL_BANNER:
            case GREEN_BANNER:
            case GREEN_WALL_BANNER:
            case JACK_O_LANTERN:
            case JUKEBOX:
            case JUNGLE_BUTTON:
            case JUNGLE_DOOR:
            case JUNGLE_FENCE:
            case JUNGLE_FENCE_GATE:
            case JUNGLE_LOG:
            case JUNGLE_PLANKS:
            case JUNGLE_PRESSURE_PLATE:
            case JUNGLE_SIGN:
            case JUNGLE_SLAB:
            case JUNGLE_STAIRS:
            case JUNGLE_TRAPDOOR:
            case JUNGLE_WALL_SIGN:
            case JUNGLE_WOOD:
            case LADDER:
            case LECTERN:
            case LOOM:
            case MAGENTA_BANNER:
            case MAGENTA_WALL_BANNER:
            case MELON:
            case NOTE_BLOCK:
            case OAK_BUTTON:
            case OAK_DOOR:
            case OAK_FENCE:
            case OAK_FENCE_GATE:
            case OAK_LOG:
            case OAK_PLANKS:
            case OAK_PRESSURE_PLATE:
            case OAK_SIGN:
            case OAK_SLAB:
            case OAK_STAIRS:
            case OAK_TRAPDOOR:
            case OAK_WALL_SIGN:
            case OAK_WOOD:
            case PINK_BANNER:
            case PINK_WALL_BANNER:
            case POLISHED_BLACKSTONE_BUTTON:
            case POLISHED_BLACKSTONE_PRESSURE_PLATE:
            case PUMPKIN:
            case PURPLE_BANNER:
            case PURPLE_WALL_BANNER:
            case RED_BANNER:
            case RED_MUSHROOM_BLOCK:
            case RED_WALL_BANNER:
            case SMITHING_TABLE:
            case SOUL_CAMPFIRE:
            case SOUL_LANTERN:
            case SPRUCE_BUTTON:
            case SPRUCE_DOOR:
            case SPRUCE_FENCE:
            case SPRUCE_FENCE_GATE:
            case SPRUCE_LOG:
            case SPRUCE_PLANKS:
            case SPRUCE_PRESSURE_PLATE:
            case SPRUCE_SIGN:
            case SPRUCE_SLAB:
            case SPRUCE_STAIRS:
            case SPRUCE_TRAPDOOR:
            case SPRUCE_WALL_SIGN:
            case SPRUCE_WOOD:
            case STRIPPED_ACACIA_LOG:
            case STRIPPED_ACACIA_WOOD:
            case STRIPPED_BIRCH_LOG:
            case STRIPPED_BIRCH_WOOD:
            case STRIPPED_CRIMSON_HYPHAE:
            case STRIPPED_CRIMSON_STEM:
            case STRIPPED_DARK_OAK_LOG:
            case STRIPPED_DARK_OAK_WOOD:
            case STRIPPED_JUNGLE_LOG:
            case STRIPPED_JUNGLE_WOOD:
            case STRIPPED_OAK_LOG:
            case STRIPPED_OAK_WOOD:
            case STRIPPED_SPRUCE_LOG:
            case STRIPPED_SPRUCE_WOOD:
            case STRIPPED_WARPED_HYPHAE:
            case STRIPPED_WARPED_STEM:
            case TRAPPED_CHEST:
            case WARPED_BUTTON:
            case WARPED_DOOR:
            case WARPED_FENCE:
            case WARPED_FENCE_GATE:
            case WARPED_HYPHAE:
            case WARPED_NYLIUM:
            case WARPED_PLANKS:
            case WARPED_PRESSURE_PLATE:
            case WARPED_SIGN:
            case WARPED_SLAB:
            case WARPED_STAIRS:
            case WARPED_STEM:
            case WARPED_TRAPDOOR:
            case WARPED_WALL_SIGN:
            case WARPED_WART_BLOCK:
            case WHITE_BANNER:
            case WHITE_WALL_BANNER:
            case YELLOW_BANNER:
            case YELLOW_WALL_BANNER:
                return MultiTool.ToolType.AXE;
            case BLACK_CONCRETE_POWDER:
            case BLUE_CONCRETE_POWDER:
            case BROWN_CONCRETE_POWDER:
            case CLAY:
            case COARSE_DIRT:
            case CYAN_CONCRETE_POWDER:
            case DIRT:
            case FARMLAND:
            case GRASS_BLOCK:
            case GRASS_PATH:
            case GRAVEL:
            case GRAY_CONCRETE_POWDER:
            case GREEN_CONCRETE_POWDER:
            case LIGHT_BLUE_CONCRETE_POWDER:
            case LIGHT_GRAY_CONCRETE_POWDER:
            case LIME_CONCRETE_POWDER:
            case MAGENTA_CONCRETE_POWDER:
            case MYCELIUM:
            case ORANGE_CONCRETE_POWDER:
            case PINK_CONCRETE_POWDER:
            case PODZOL:
            case PURPLE_CONCRETE_POWDER:
            case RED_CONCRETE_POWDER:
            case RED_SAND:
            case SAND:
            case SNOW:
            case SNOW_BLOCK:
            case SOUL_SAND:
            case WHITE_CONCRETE_POWDER:
            case YELLOW_CONCRETE_POWDER:
                return MultiTool.ToolType.SHOVEL;
        }
        return null;
    }
}
