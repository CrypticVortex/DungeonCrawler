package com.crypticvortex.dc.core;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public enum MobTable { // equipment : Hellmet, Chestplate, Leggings, Boots, MainHand, OffHand
	SHAMBLER("Shambler", EntityType.SKELETON, new ItemStack[] { null, null, null, null, new ItemStack(Material.BONE) }, HealthPool.LOW, DropRarity.BELOW_AVERAGE, MovementSpeed.SLOW),
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