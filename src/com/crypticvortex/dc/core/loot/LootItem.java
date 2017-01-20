package com.crypticvortex.dc.core.loot;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.crypticvortex.dc.core.loot.LootTable.LootCategory;

public class LootItem {
	public int value;
	public float percent;
	public ItemStack item;
	public LootCategory category;
	
	public LootItem(float percent, LootCategory category, int value, ItemStack item) {
		this.percent = percent;
		this.category = category;
		this.value = value;
		this.item = item;
	}
	
	public LootItem(float percent, LootCategory category, int value, ItemStack item, String name, String... lore) {
		this(percent, category, value, rename(item, name, lore));
	}
		
	public LootItem(float percent, LootCategory category, int value, Material material, String name, String... lore) {
		this(percent, category, value, new ItemStack(material), name, lore);
	}
	
	private static ItemStack rename(ItemStack stack, String name, String... lore) {
		ItemMeta meta = stack.getItemMeta();
		if(name != null) meta.setDisplayName("§c" + name);
		if(lore != null) meta.setLore(Arrays.asList(lore));
		meta.setUnbreakable(true);
		stack.setItemMeta(meta);
		return stack;
	}
	
}