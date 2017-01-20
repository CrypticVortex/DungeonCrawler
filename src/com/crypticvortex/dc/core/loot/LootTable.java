package com.crypticvortex.dc.core.loot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.crypticvortex.dc.core.MobTable;

/**
 * This class will be responsible for storing all the various items that can be spawned/dropped/bought.
 * 
 * @author Vortex
 */
public class LootTable {
	public static List<LootItem> itemTable = new ArrayList<LootItem>();
	
	public static void populate() {
		// ------------ Junk
		itemTable.add(new LootItem(45f, LootCategory.JUNK, 6, Material.PAPER, "Scroll", "§7An old scroll that is of no use to you,", "§7but might be worth something to another."));
		itemTable.add(new LootItem(45f, LootCategory.JUNK, 10, new ItemStack(Material.POTION, 1, (short) 0), "Vial of Water", "§7A vial of plain water."));
		itemTable.add(new LootItem(45f, LootCategory.JUNK, 8, Material.FEATHER, "Quill", "§7A quill used for writing on parchment."));
		// ------------ Valuable
		itemTable.add(new LootItem(45f, LootCategory.VALUABLE, 25, Material.RABBIT_FOOT, "Rabbits Foot", "§7Said to bring good luck."));
		// ------------ Weapon
		itemTable.add(new LootItem(35f, LootCategory.WEAPON, 25, Material.STICK, "Basic Wand", "§71 §6Damage"));
		itemTable.add(new LootItem(35f, LootCategory.WEAPON, 25, Material.BOW, "Basic Shortbow", "§72 §6Damage"));
 		itemTable.add(new LootItem(35f, LootCategory.WEAPON, 25, Material.IRON_SWORD, "Basic Shortsword", "§73 §6Damage"));
 		
 		itemTable.add(new LootItem(25f, LootCategory.WEAPON, 25, Material.BLAZE_ROD, "Basic Staff", "§73 §6Damage"));
		itemTable.add(new LootItem(25f, LootCategory.WEAPON, 25, Material.BOW, "Basic Longbow", "§74 §6Damage"));
 		itemTable.add(new LootItem(25f, LootCategory.WEAPON, 25, Material.IRON_SWORD, "Basic Longsword", "§75 §6Damage"));
		// ------------ Armor
		itemTable.add(new LootItem(35f, LootCategory.ARMOR, 25, Material.LEATHER_HELMET, "Basic Hood", "§7? §6Armor Rating"));
		itemTable.add(new LootItem(35f, LootCategory.ARMOR, 25, Material.LEATHER_CHESTPLATE, "Basic Robes", "§7? §6Armor Rating"));
		itemTable.add(new LootItem(35f, LootCategory.ARMOR, 25, Material.LEATHER_LEGGINGS, "Basic Cloth Leggings", "§7? §6Armor Rating"));
		itemTable.add(new LootItem(35f, LootCategory.ARMOR, 25, Material.LEATHER_BOOTS, "Basic Shoes", "§7? §6Armor Rating"));
	}
	
	public static List<ItemStack> possibleDrops(float percent, MobTable.DropRarity rarity) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		for(LootItem entry : LootTable.itemTable) {
			float key = entry.percent;
			switch(rarity) {
				case MINIMUM: key -= 35; break; // Rare drops become extremely rare
				case BELOW_AVERAGE: if(entry.percent == 45f) break; else key -= 20; break; // Rare drops become slightly rarer
				case AVERAGE: break; // Drops are the same
				case ABOVE_AVERAGE: if(entry.percent == 45f) break; else key += 15; break; // Rare drops become more common
				case VERY_HIGH: key += 35; break; // Rare drops become exceptionally more common
			}
			if(key <= 0) key = 1.0f;
			if(percent <= key)
				drops.addAll(Arrays.asList(entry.item));
		}
		return drops;
	}
	
	public static ItemStack retrieveItem(String name) {
		for(LootItem t : LootTable.itemTable) {
			ItemMeta meta = t.item.getItemMeta();
			if(ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase(name)) 
				return t.item;
		}
		return null;
	}
	
	public static ItemStack getRandItem(float percent) {
		Random rand = new Random();
		ItemStack stack = null;
		List<ItemStack> items = LootTable.getByPercent(percent);
		int index = rand.nextInt(items.size());
		stack = items.get(index);
		return stack;
	}
	
	public static List<ItemStack> getByPercent(float percent) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(LootItem t : LootTable.itemTable)
			if(t.percent == percent)
				items.add(t.item);
		return items;
	}
	
	public static Map<String, String> getData(ItemStack item) {
		Map<String, String> data = new HashMap<String, String>();
		ItemMeta meta = item.getItemMeta();
		if(meta != null) {
			if(meta.hasDisplayName()) data.put("Name", ChatColor.stripColor(meta.getDisplayName()));
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					if(ChatColor.stripColor(s).contains("Damage")) data.put("Damage", ChatColor.stripColor(s.substring(0, s.indexOf(" ")).replaceAll(" ", "")));
					if(ChatColor.stripColor(s).matches("\\d+")) data.put("Value", ChatColor.stripColor(s));
				}
			}
		}
		return data;
	}
	
	public enum LootCategory {
		WEAPON,
		ARMOR,
		POTION,
		FOOD,
		JUNK,
		VALUABLE,
	}
	
}