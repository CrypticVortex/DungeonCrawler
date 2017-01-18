package com.crypticvortex.dc.core;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public enum MobTable { // equipment : Hellmet, Chestplate, Leggings, Boots, MainHand, OffHand
	SHAMBLER("Shambler", EntityType.SKELETON, new ItemStack[] { null, null, null, null, new ItemStack(Material.BONE) }, HealthPool.LOW, DropRarity.BELOW_AVERAGE, MovementSpeed.SLOW),
	UNDEAD("Undead", EntityType.ZOMBIE, new ItemStack[] { null, null, null, null, null }, HealthPool.LOW, DropRarity.BELOW_AVERAGE, MovementSpeed.SLOW),
	SKELETAL_WARRIOR("Skeletal Warrior", EntityType.SKELETON, new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.SHIELD) }, HealthPool.AVERAGE, DropRarity.BELOW_AVERAGE, MovementSpeed.AVERAGE),
	SKELETAL_ARCHER("Skeletal Archer", EntityType.SKELETON, new ItemStack[] { null, null, null, null, new ItemStack(Material.BOW) }, HealthPool.LOW, DropRarity.BELOW_AVERAGE, MovementSpeed.SLOW),
	UNDEAD_WARRIOR("Undead Warrior", EntityType.HUSK, new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.SHIELD) }, HealthPool.LOW, DropRarity.BELOW_AVERAGE, MovementSpeed.AVERAGE),
	UNDEAD_ARCHER("Undead Archer", EntityType.ZOMBIE, new ItemStack[] { null, null, null, null, new ItemStack(Material.BOW) }, HealthPool.LOW, DropRarity.BELOW_AVERAGE, MovementSpeed.SLOW),
	;
	
	private String name;
	private EntityType type;
	private HealthPool health;
	private MovementSpeed speed;
	private ItemStack[] equipment;
	private DropRarity dropRarity;
	private MobTable(String name, EntityType type, ItemStack[] equipment, HealthPool health, DropRarity dropRarity, MovementSpeed speed) {
		this.name = name;
		this.type = type;
		this.health = health;
		this.dropRarity = dropRarity;
		this.speed = speed;
		this.equipment = equipment;
	}
	
	public String getName() {
		return name;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public ItemStack[] getEquipment() {
		return equipment;
	}
	
	public HealthPool getHealth() {
		return health;
	}
	
	public MovementSpeed getSpeed() {
		return speed;
	}
	
	public DropRarity getDropRarity() {
		return dropRarity;
	}
	
	public static MobTable getEntityByName(String name) {
		MobTable ent = null;
		for(MobTable entity : MobTable.values())
			if(entity.name.equalsIgnoreCase(name)) {
				ent = entity;
				break;
			}
		return ent;
	}
	
	public enum HealthPool {
		VERY_LOW,
		LOW,
		AVERAGE,
		HIGH,
		VERY_HIGH;
	}
	
	public enum DropRarity {
		MINIMUM,
		BELOW_AVERAGE,
		AVERAGE,
		ABOVE_AVERAGE,
		VERY_HIGH
	}
	
	public enum MovementSpeed {
		SLOW,
		AVERAGE,
		FAST;
	}
	
}