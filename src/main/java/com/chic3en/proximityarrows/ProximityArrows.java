package com.chic3en.proximityarrows;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ProximityArrows implements ModInitializer {
	public static final String MOD_ID = "proximity-arrows";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Proximity Arrows initialized! Keep your buff arrows close.");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
