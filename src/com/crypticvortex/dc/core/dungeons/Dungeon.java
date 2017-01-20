package com.crypticvortex.dc.core.dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Dungeon {
	public String name;
	public Location spawn;
	public List<Level> levels;
	@Deprecated // Deprecated in favor of using Room.spawner
	public List<CustomSpawner> spawners;
	public List<Location> brokenVases = new ArrayList<Location>();
	public List<Location> brokenSecrets = new ArrayList<Location>();
	private static List<Dungeon> dungeons = new ArrayList<Dungeon>(); 
	
	public Dungeon(String name) {
		this.name = name;
		this.levels = new ArrayList<Level>();
		this.brokenVases = new ArrayList<Location>();
		this.brokenSecrets = new ArrayList<Location>();
		this.spawners = new ArrayList<CustomSpawner>();
		dungeons.add(this);
	}
	
	public void restore() {
		for(Level level : levels)
			level.reset();
	}
	
	private List<Location> secrets = new ArrayList<Location>(); // Local cache of all secrets
	public boolean isSecret(Location location) {
		if(!secrets.contains(location) && secrets.size() > 0) return false;
		if(secrets.contains(location)) return true;
		if(secrets.size() == 0) {
			for(Level level : levels)
				for(Secret s : level.secrets)
					secrets.add(s.getLocation());
			if(secrets.contains(location)) return true;
			if(!secrets.contains(location)) return false;
		}
		return false;
	}
	
	public Dungeon addSpawner(CustomSpawner spawner) {
		if(!spawners.contains(spawner))
			spawners.add(spawner);
		return this;
	}
	
	public Dungeon addSpawners(CustomSpawner[] spawners) {
		for(CustomSpawner spawner : spawners)
			if(!this.spawners.contains(spawner))
				this.spawners.add(spawner);
		return this;
	}
	
	public boolean fromSpawner(LivingEntity entity) {
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