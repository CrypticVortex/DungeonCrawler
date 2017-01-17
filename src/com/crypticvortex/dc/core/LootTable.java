package com.crypticvortex.dc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This class will be responsible for storing all the various items that can be spawned/dropped/bought.
 * 
 * @author Vortex
 */
public class LootTable {
	public static Map<Float, ItemStack[]> itemTable = new HashMap<Float, ItemStack[]>();
	
	public static void populate() {
		itemTable.put(45f, new ItemStack[] {
			rename(Material.PAPER, "Scroll", "§9Junk", "§7An old scroll that is of no use to you,", "§7but might be worth something to another."),
			rename(new ItemStack(Material.POTION), "Vial of Water", "§9Junk", "§7A vial of plain water."),
			rename(Material.FEATHER, "Quill", "§9Junk", "§7A quill used for writing on parchment."),
			rename(Material.RABBIT_FOOT, "Rabbits Foot", "§9Junk", "§7Said to bring good luck."),
		}); // All items that have a 45% chance to spawn.
		itemTable.put(35f, new ItemStack[] {
			rename(Material.STICK, "Basic Wand", "§fCommon", "§9Weapon", "§6Damage:§7 1"),
			rename(Material.BOW, "Basic Shortbow", "§fCommon", "§9Weapon", "§6Damage:§7 2"),
			rename(Material.IRON_SWORD, "Basic Shortsword", "§fCommon", "§9Weapon", "§6Damage:§7 3"),
		}); // All items that have a 35% chance to spawn.
		itemTable.put(25f, new ItemStack[] {
			rename(Material.BLAZE_ROD, "Basic Staff", "§fCommon", "§9Weapon", "§6Damage:§7 3"),
			rename(Material.BOW, "Basic Longbow", "§fCommon", "§9Weapon", "§6Damage:§7 4"),
			rename(Material.IRON_SWORD, "Basic Longsword", "§fCommon", "§9Weapon", "§6Damage:§7 5"),
		}); // All items that have a 25% chance to spawn.
	}
	
	public static boolean containsKey(Object key) {
		return itemTable.containsKey(key);
	}
	
	public static List<ItemStack> possibleDrops(float percent) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		for(Entry<Float, ItemStack[]> entry : itemTable.entrySet())
			if(percent <= entry.getKey()) {
				drops.addAll(Arrays.asList(entry.getValue()));
			}
		return drops;
	}
	
	public static ItemStack getRandValue(Object key) {
		Random rand = new Random();
		ItemStack stack = null;
		int index = rand.nextInt(itemTable.get(key).length);
		stack = itemTable.get(key)[index];
		return stack;
	}
	
	private static ItemStack rename(Material material, String name, String... lore) {
		return rename(new ItemStack(material), name, lore);
	}
	
	private static ItemStack rename(ItemStack stack, String name, String... lore) {
		ItemMeta meta = stack.getItemMeta();
		if(name != null) meta.setDisplayName("§c" + name);
		if(lore != null) meta.setLore(Arrays.asList(lore));
		stack.setItemMeta(meta);
		return stack;
	}
	
}