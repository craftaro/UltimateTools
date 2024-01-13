package com.craftaro.ultimatetools.enchant;


import com.craftaro.third_party.com.cryptomorin.xseries.XMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ToolType {
    SWORD(XMaterial.NETHERITE_SWORD,
            XMaterial.DIAMOND_SWORD,
            XMaterial.GOLDEN_SWORD,
            XMaterial.IRON_SWORD,
            XMaterial.STONE_SWORD,
            XMaterial.WOODEN_SWORD),

    AXE(XMaterial.NETHERITE_AXE,
            XMaterial.DIAMOND_AXE,
            XMaterial.GOLDEN_AXE,
            XMaterial.IRON_AXE,
            XMaterial.STONE_AXE,
            XMaterial.WOODEN_AXE),

    PICKAXE(XMaterial.NETHERITE_PICKAXE,
            XMaterial.DIAMOND_PICKAXE,
            XMaterial.GOLDEN_PICKAXE,
            XMaterial.IRON_PICKAXE,
            XMaterial.STONE_PICKAXE,
            XMaterial.WOODEN_PICKAXE),

    SHOVEL(XMaterial.NETHERITE_SHOVEL,
            XMaterial.DIAMOND_SHOVEL,
            XMaterial.GOLDEN_SHOVEL,
            XMaterial.IRON_SHOVEL,
            XMaterial.STONE_SHOVEL,
            XMaterial.WOODEN_SHOVEL);

    private final List<XMaterial> materials = new ArrayList<>();

    ToolType(XMaterial... materials) {
        this.materials.addAll(Arrays.asList(materials));
    }

    public List<XMaterial> getMaterials() {
        return Collections.unmodifiableList(materials);
    }
}
