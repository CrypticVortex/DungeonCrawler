package com.crypticvortex.dc.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Dungeon {
	public String name;
	public Location spawn;
	public List<CustomSpawner> spawners;
	
	public Dungeon(String name) {
		this.name = name;
		this.spawners = new ArrayList<CustomSpawner>();
	}
	
	public Dungeon addSpawner(CustomSpawner spawner) {
		if(!spawners.contains(spawner))
			spawners.add(spawner);
		return this;
	}
	
	public boolean isFromSpawner(LivingEntity entity) {
		boolean from = false;
		for(CustomSpawner s : spawners) {
			if(s.isFrom(entity)) {
				from = true;
				break;
			}
		}
		return from;
	}
	
	public CustomSpawner getSpawner(LivingEntity entity) {
		CustomSpawner spawner = null;
		for(CustomSpawner s : spawners) {
			if(s.isFrom(entity)) {
				spawner = s;
				break;
			}
		}
		return spawner;
	}
	
	public List<CustomSpawner> getSpawnersNear() {
		List<CustomSpawner> near = new ArrayList<CustomSpawner>();
		for(CustomSpawner s : spawners) {
			for(Player pl : Bukkit.getOnlinePlayers()) {
				if(s.block.getLocation().distance(pl.getLocation()) <= 15.0) {
					if(!near.contains(s))
						near.add(s);
				}
			}
		}
		return near;
	}
	
	public List<CustomSpawner> getSpawnersNear(Player pl) {
		List<CustomSpawner> near = new ArrayList<CustomSpawner>();
		for(CustomSpawner s : spawners)
			if(s.block.getLocation().distanceSquared(pl.getLocation()) <= 30)
				near.add(s);
		return near;
	}
	
}