package com.chic3en.proximityarrows.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    // Creates a tag that modpack makers can use: #proximityarrows:harmless_arrows
    public static final TagKey<Item> HARMLESS_ARROWS = TagKey.of(
            RegistryKeys.ITEM,
            Identifier.of("proximityarrows", "harmless_arrows")
    );
}