package com.songoda.ultimatetools.enchant;

import com.songoda.core.compatibility.CompatibleMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ToolType {
    SWORD(CompatibleMaterial.NETHERITE_SWORD,
            CompatibleMaterial.DIAMOND_SWORD,
            CompatibleMaterial.GOLDEN_SWORD,
            CompatibleMaterial.IRON_SWORD,
            CompatibleMaterial.STONE_SWORD,
            CompatibleMaterial.WOODEN_SWORD),

    AXE(CompatibleMaterial.NETHERITE_AXE,
            CompatibleMaterial.DIAMOND_AXE,
            CompatibleMaterial.GOLDEN_AXE,
            CompatibleMaterial.IRON_AXE,
            CompatibleMaterial.STONE_AXE,
            CompatibleMaterial.WOODEN_AXE),

    PICKAXE(CompatibleMaterial.NETHERITE_PICKAXE,
            CompatibleMaterial.DIAMOND_PICKAXE,
            CompatibleMaterial.GOLDEN_PICKAXE,
            CompatibleMaterial.IRON_PICKAXE,
            CompatibleMaterial.STONE_PICKAXE,
            CompatibleMaterial.WOODEN_PICKAXE),

    SHOVEL(CompatibleMaterial.NETHERITE_SHOVEL,
            CompatibleMaterial.DIAMOND_SHOVEL,
            CompatibleMaterial.GOLDEN_SHOVEL,
            CompatibleMaterial.IRON_SHOVEL,
            CompatibleMaterial.STONE_SHOVEL,
            CompatibleMaterial.WOODEN_SHOVEL);

    private final List<CompatibleMaterial> materials = new ArrayList<>();

    ToolType(CompatibleMaterial... materials) {
        this.materials.addAll(Arrays.asList(materials));
    }

    public List<CompatibleMaterial> getMaterials() {
        return Collections.unmodifiableList(materials);
    }
}
