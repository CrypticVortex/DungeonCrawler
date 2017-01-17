package com.crypticvortex.dc.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomSpawner {
	public int level; // The level of mobs that are being spawned.
	public int spawned; // How many mobs this spawner has created
	public int leftToSpawn;
	private int count;
	public Block block; // The center block to spawn from.
	public MobTable mobType; // The type of mob to spawn.
	public double xOffset, yOffset, zOffset; // Maximum offset value (this value is used for both negative and positive offsets,)
	private List<LivingEntity> alive;
	public boolean hasMobs;
	
	public CustomSpawner(Block block, MobTable type, int count) {
		this.block = block;
		this.mobType = type;
		this.count = count;
		this.leftToSpawn = count;
		alive = new ArrayList<LivingEntity>();
	}
	
	public CustomSpawner(Block block, int count, MobTable type, double xOffset, double yOffset, double zOffset) {
		this(block, type, count);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}
	
	public CustomSpawner(Block block, MobTable type, int count, int level, double xOffset, double yOffset, double zOffset) {
		this(block, count, type, xOffset, yOffset, zOffset);
		this.level = level;
	}
	
	public void reset() {
		kill();
		hasMobs = false;
		leftToSpawn = count;
	}
	
	public void spawn() { // Spawns x amount of mobs.
		Random rand = new Random();
		for(int i = 0; i < leftToSpawn; i++) {
			double xOffset = rand.nextInt((int) this.xOffset), yOffset = this.yOffset, zOffset = rand.nextInt((int) this.zOffset);
			if(rand.nextBoolean()) xOffset = -xOffset;
			if(rand.nextBoolean()) zOffset = -zOffset;
			Location toSpawn = block.getLocation().add(xOffset, yOffset, zOffset);
			// TODO : Add checks to make sure mobs cant spawn inside blocks.
			LivingEntity ent = (LivingEntity) block.getWorld().spawnEntity(toSpawn, mobType.getType());
			ent.setCustomName(mobType.getName());
			ent.setCustomNameVisible(true);
			EntityEquipment equip = ent.getEquipment();
			equip.clear();
			ItemStack[] equipment = mobType.getEquipment();
			equip.setHelmet(equipment[0]);
			equip.setChestplate(equipment[1]);
			equip.setLeggings(equipment[2]);
			equip.setBoots(equipment[3]);
			equip.setItemInMainHand(equipment[4]);
			if(equipment.length == 6)
				equip.setItemInOffHand(equipment[5]);
			switch(mobType.getSpeed()) {
				case FAST: ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)); break;
				case SLOW: ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1)); break;
				default: break;
			}
			alive.add(ent);
		}
		leftToSpawn = 0;
		hasMobs = true;
	}
	
	public void remove(LivingEntity entity) {
		alive.remove(entity);
	}
	
	public void kill() { // Kills all currently active mobs.
		for(LivingEntity e : alive) {
			e.remove();
			leftToSpawn++;
		}
		alive.clear();
		if(leftToSpawn > 0)
			hasMobs = false;
	}
	
	public boolean isFrom(LivingEntity entity) {
		boolean from = false;
		for(LivingEntity ent : alive) {
			if(entity.equals(ent)) {
				from = true;
				break;
			}
		}
		return from;
	}
	
}