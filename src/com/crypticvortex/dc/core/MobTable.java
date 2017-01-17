package com.crypticvortex.dc.core;

import org.bukkit.entity.EntityType;

public enum MobTable {
	SHAMBLER("Shambler", EntityType.SKELETON, HealthPool.LOW, DropRarity.BELOW_AVERAGE),
	;
	
	private String name;
	private EntityType type;
	private HealthPool health;
	private DropRarity dropRarity;
	private MobTable(String name, EntityType type, HealthPool health, DropRarity dropRarity) {
		this.name = name;
		this.type = type;
		this.health = health;
		this.dropRarity = dropRarity;
	}
	
	public String getName() {
		return name;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public HealthPool getHealth() {
		return health;
	}
	
	public DropRarity getDropRarity() {
		return dropRarity;
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
	
}