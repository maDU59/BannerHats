package com.madu59.banner_hats;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.plugin.java.JavaPlugin;

public class BannerHats extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BannerHats enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BannerHats disabled!");
    }

	@EventHandler
    public void onPickUp(EntityPickupItemEvent event) {
		makeBannerEquippable(event.getItem().getItemStack());
    }

	@EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
		makeBannerEquippable(event.getItem());
    }

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked().getGameMode() != GameMode.CREATIVE && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)){
			ItemStack stack = makeBannerEquippable(event.getCurrentItem());
			if(stack != null){
				event.getWhoClicked().setItemOnCursor(stack);
				event.setCurrentItem(null);
				event.setCancelled(true);
			}
		}
		if(event.getWhoClicked().getGameMode() != GameMode.CREATIVE && isBanner(event.getCurrentItem().getType())){
			ItemStack stack = makeBannerEquippable(event.getCurrentItem());
			if(stack != null){
				event.setCurrentItem(stack);
			}
		}
	}

	@EventHandler
	public void onCraft(CraftItemEvent event) {
		ItemStack result = makeBannerEquippable(event.getInventory().getResult());
		if (result != null) {
			event.getInventory().setResult(result);
		}
	}

	public ItemStack makeBannerEquippable(ItemStack item) {
		if (item != null && isBanner(item.getType())) {
			ItemMeta meta = item.getItemMeta();
			if (meta != null) {
				EquippableComponent equippable = meta.getEquippable();
				if (equippable != null) {
					equippable.setSlot(EquipmentSlot.HEAD);
					equippable.setEquipSound(Sound.ITEM_ARMOR_EQUIP_GENERIC);
					equippable.setDamageOnHurt(false);
					equippable.setSwappable(true);
					equippable.setDispensable(false);
					meta.setEquippable(equippable);
					item.setItemMeta(meta);
					return item;
				}
			}
		}
		return null;
	}

    private boolean isBanner(Material type) {
        return type.name().endsWith("_BANNER");
    }
}