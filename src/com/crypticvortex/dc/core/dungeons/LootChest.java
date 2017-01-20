package com.crypticvortex.dc.core.dungeons;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;

public class LootChest {
	public Location location;
	private boolean isTrapped;
	public boolean doubleChest;
	public Optional<Location> secondHalf;
	
	public LootChest(Location location, Optional<Location> secondHalf) {
		this.location = location;
		if(location.getBlock().getType() == Material.TRAPPED_CHEST) 
			isTrapped = true;
		this.secondHalf = secondHalf;
		if(secondHalf.isPresent())
			doubleChest = true;
	}
	
	public void restore() {
		location.getBlock().setType((isTrapped ? Material.TRAPPED_CHEST : Material.CHEST));
		if(doubleChest)
			secondHalf.get().getBlock().setType((isTrapped ? Material.TRAPPED_CHEST : Material.CHEST));
	}
	
}