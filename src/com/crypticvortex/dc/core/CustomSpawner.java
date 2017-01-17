package com.crypticvortex.dc.core;

import org.bukkit.block.Block;

public class CustomSpawner {
	public int level; // The level of mobs that are being spawned.
	public int spawned; // How many mobs this spawner has created
	public Block block; // The center block to spawn from.
	public MobTable mobType; // The type of mob to spawn.
	public double xOffset, yOffset, zOffset; // Maximum offset value (this value is used for both negative and positive offsets,)
	
	
}