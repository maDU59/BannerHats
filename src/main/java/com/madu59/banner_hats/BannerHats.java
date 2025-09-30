package com.madu59.banner_hats;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BannerHats implements ModInitializer {
	public static final String MOD_ID = "banner_hats";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Item[] banners = new Item[] {
			Items.WHITE_BANNER, Items.ORANGE_BANNER, Items.MAGENTA_BANNER, Items.LIGHT_BLUE_BANNER,
			Items.YELLOW_BANNER, Items.LIME_BANNER, Items.PINK_BANNER, Items.GRAY_BANNER,
			Items.LIGHT_GRAY_BANNER, Items.CYAN_BANNER, Items.PURPLE_BANNER, Items.BLUE_BANNER,
			Items.BROWN_BANNER, Items.GREEN_BANNER, Items.RED_BANNER, Items.BLACK_BANNER
		};

		for (Item banner : banners) {
			DefaultItemComponentEvents.MODIFY.register(context -> {
					context.modify(banner, builder -> {
						builder.add(DataComponentTypes.EQUIPPABLE,
							EquippableComponent.builder(EquipmentSlot.HEAD)
								.equipSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC)
								.dispensable(true)
								.swappable(true)
								.damageOnHurt(true)
								.equipOnInteract(false)
								.canBeSheared(false)
								.build()
						);
					});
			});
		}

		LOGGER.info("Hello Fabric world!");
	}
}