package com.crypticvortex.dc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.crypticvortex.dc.core.spells.MageSpellBlink;
import com.crypticvortex.dc.core.spells.MageSpellLightning;

public class PlayerClass {
	private String name;
	private LinkedList<Spell> spells;
	private static List<PlayerClass> classes = new ArrayList<PlayerClass>();
	
	public PlayerClass(String name) {
		this.name = name;
		this.spells = new LinkedList<Spell>();
		classes.add(this);
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
	
	public LinkedList<Spell> getSpells() {
		return spells;
	}
	
	public static List<PlayerClass> getClasses() {
		return classes;
	}
	
	public static boolean isClassWeapon(ItemStack item) {
		boolean flag = false;
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			String display = meta.getDisplayName();
			if(display != null) display = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
			if(display != null)
				if(display.contains("wand") || display.contains("staff") || display.contains("longsword") || display.contains("shortsword") || display.contains("longbow") || display.contains("shortbow"))
					flag = true;
		}
		return flag;
	}
	
	public static PlayerClass getForWeapon(ItemStack item) {
		PlayerClass flag = null;
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			String display = meta.getDisplayName();
			if(display != null) display = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
			if(display.contains(" ")) display = display.substring(display.lastIndexOf(" "), display.length());
			if(display != null) {
				if(display.equalsIgnoreCase("wand") || display.equalsIgnoreCase("staff")) flag = MAGE;
				if(display.equalsIgnoreCase("longsword") || display.equalsIgnoreCase("shortsword")) flag = WARRIOR;
				if(display.equalsIgnoreCase("longbow") || display.equalsIgnoreCase("shortbow")) flag = ARCHER;
			}
		}
		return flag;
	}
	
	public static final PlayerClass MAGE = new PlayerClass("Mage").addSpells(new MageSpellBlink(), new MageSpellLightning());
	public static final PlayerClass ARCHER = new PlayerClass("Archer");
	public static final PlayerClass WARRIOR = new PlayerClass("Warrior");
}