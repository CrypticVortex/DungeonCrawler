package com.crypticvortex.dc.core.dungeons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class Secret {
	private SecretType type;
	public Location location;
	private boolean isTrigger;
	private boolean triggered;
	private Material material;
	private MaterialData blockData;
	private TriggerType triggerType;
	
	public Secret(Location location, SecretType type) {
		this.location = location;
		this.material = location.getBlock().getType();
		if(location.getBlock().getType() == Material.DOUBLE_STEP && type == SecretType.DESTRUCTIBLE)
			blockData = location.getBlock().getState().getData();
		this.type = type;
	}
	
	public void checkTrigger() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getLocation().equals(location) && triggerType != TriggerType.NONE) {
				switch(triggerType) { // TODO : Add secret trigger code
					default: break;
				}
			}
		}
	}
	
	public void restore() {
		switch(type) {
			case DESTRUCTIBLE: 
				location.getBlock().setType(material);
				if(blockData != null)
					location.getBlock().getState().setData(blockData);
				break;
			default: break;
		}
	}
	
	public Secret setTrigger(boolean isTrigger) {
		this.isTrigger = isTrigger;
		return this;
	}
	
	public boolean isTrigger() {
		return isTrigger;
	}
	
	public boolean isTriggered() {
		return triggered;
	}
	
	public SecretType getType() {
		return type;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public TriggerType getTriggerType() {
		return triggerType;
	}
	
	public MaterialData getBlockData() {
		return blockData;
	}
	
	public enum TriggerType {
		NONE,
		MESSAGE,
		OPEN_ROOM,
		SPAWN_ITEM
	}
	
	public enum SecretType {
		DESTRUCTIBLE, // Breakable wall / vase
		TRIGGER; // Standing near/hitting this block will activate something.
	}
}