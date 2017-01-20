package com.crypticvortex.dc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.crypticvortex.dc.core.loot.LootTable;
import com.crypticvortex.dc.core.spells.MageSpellBlink;
import com.crypticvortex.dc.core.spells.MageSpellIceQuake;
import com.crypticvortex.dc.core.spells.MageSpellLightning;
import com.crypticvortex.dc.core.spells.Spell;

public class PlayerClass {
	private String name;
	private ItemStack[] equipment;
	private LinkedList<Spell> spells;
	private static List<PlayerClass> classes = new ArrayList<PlayerClass>();
	
	public PlayerClass(String name) {
		this.name = name;
		this.spells = new LinkedList<Spell>();
		classes.add(this);
	}
	
	public void setGear(Player pl) {
		pl.getInventory().clear(); // Fresh slate, will load individual character inventories from database later
		if(equipment[0] != null) pl.getInventory().setHelmet(equipment[0]);
		if(equipment[1] != null) pl.getInventory().setChestplate(equipment[1]);
		if(equipment[2] != null) pl.getInventory().setLeggings(equipment[2]);
		if(equipment[3] != null) pl.getInventory().setBoots(equipment[3]);
		if(equipment[4] != null) pl.getInventory().setItem(0, equipment[4]);
		if(equipment.length == 6)
			if(equipment[5] != null) pl.getInventory().setItemInOffHand(equipment[5]);
	}
	
	public PlayerClass setEquipment(ItemStack[] equipment) {
		this.equipment = equipment;
		return this;
	}
	
	public PlayerClass addSpell(Spell spell) {
		this.spells.add(spell);
		return this;
	}
	
	public PlayerClass addSpells(Spell... spells) {
		this.spells.addAll(Arrays.asList(spells));
		return this;
	}
	
	public Spell getSpell(LinkedList<MButton> combo) {
		Spell spell = null;
		for(Spell s : spells) {
			if(s.getButtons().equals(combo)) {
				spell = s;
				break;
			}
		}
		return spell;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack[] getEquipment() {
		return equipment;
	}
	
	public LinkedList<Spell> getSpells() {
		return spells;
	}
	
	public static List<PlayerClass> getClasses() {
		return classes;
	}
	
	public static PlayerClass getByName(String name) {
		for(PlayerClass p : classes)
			if(p.getName().equalsIgnoreCase(name))
				return p;
		return null;
	}
	
	public static boolean isClassWeapon(ItemStack item) {
		boolean flag = false;
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			if(meta != null) {
				if(meta.hasDisplayName()) {
					String display = meta.getDisplayName();
					if(display != null) { 
						display = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
						if(display.contains("wand") || display.contains("staff") || display.contains("longsword") || display.contains("shortsword") || display.contains("longbow") || display.contains("shortbow"))
							flag = true;
					}
				}
			}
		}
		return flag;
	}
	
	public static PlayerClass getForWeapon(ItemStack item) {
		PlayerClass flag = null;
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			if(meta != null) {
				if(meta.hasDisplayName()) {
					String display = meta.getDisplayName();
					if(display != null) { 
						display = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
						if(display.contains("wand") || display.contains("staff")) flag = MAGE;
						if(display.contains("longsword") || display.contains("shortsword")) flag = WARRIOR;
						if(display.contains("longbow") || display.contains("shortbow")) flag = ARCHER;
					}
				}
			}
		}
		return flag;
	}
	
	public static final PlayerClass MAGE = new PlayerClass("Mage").addSpells(new MageSpellBlink(), new MageSpellLightning(), new MageSpellIceQuake()).setEquipment(new ItemStack[] { LootTable.retrieveItem("Basic Hood"), LootTable.retrieveItem("Basic Robes"), LootTable.retrieveItem("Basic Cloth Leggings"), LootTable.retrieveItem("Basic Shoes"), LootTable.retrieveItem("Basic Wand"), });
	public static final PlayerClass ARCHER = new PlayerClass("Archer");
	public static final PlayerClass WARRIOR = new PlayerClass("Warrior");
}