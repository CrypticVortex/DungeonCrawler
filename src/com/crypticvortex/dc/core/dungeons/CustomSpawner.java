package com.crypticvortex.dc.core.dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.crypticvortex.dc.core.MobTable;

public class CustomSpawner {
	public int level;
	private int count;
	public Block block;
	public int spawned;
	public int leftToSpawn;
	public boolean hasMobs;
	public MobTable[] mobTypes;
	private List<LivingEntity> alive;
	private List<MobTable> spawnedTypes;
	public double xOffset, yOffset, zOffset;
	
	public CustomSpawner(Block block, int count, MobTable... types) {
		this.block = block;
		this.mobTypes = types;
		this.count = count;
		this.leftToSpawn = count;
		alive = new ArrayList<LivingEntity>();
		spawnedTypes = new ArrayList<MobTable>();
	}
	
	public CustomSpawner(Block block, int count, double xOffset, double yOffset, double zOffset, MobTable... types) {
		this(block, count, types);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}
	
	public CustomSpawner(Block block, int count, int level, double xOffset, double yOffset, double zOffset, MobTable... types) {
		this(block, count, xOffset, yOffset, zOffset, types);
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
			boolean usedList = false;
			double xOffset = rand.nextInt((int) this.xOffset), yOffset = this.yOffset, zOffset = rand.nextInt((int) this.zOffset);
			if(rand.nextBoolean()) xOffset = -xOffset;
			if(rand.nextBoolean()) zOffset = -zOffset;
			Location toSpawn = block.getLocation().add(xOffset, yOffset, zOffset);
			// TODO : Add checks to make sure mobs cant spawn inside blocks.
			MobTable type = null;
			if(spawnedTypes.size() == leftToSpawn) {
				type = spawnedTypes.get(0);
				usedList = true;
			} else
				type = mobTypes[rand.nextInt(mobTypes.length)];
			LivingEntity ent = (LivingEntity) block.getWorld().spawnEntity(toSpawn, type.getType());
			if(ent.getType() == EntityType.ZOMBIE)
				((Zombie) ent).setBaby(false);
			ent.setCustomName(type.getName());
			ent.setCustomNameVisible(true);
			EntityEquipment equip = ent.getEquipment();
			equip.clear();
			ItemStack[] equipment = type.getEquipment();
			equip.setHelmet(equipment[0]);
			equip.setChestplate(equipment[1]);
			equip.setLeggings(equipment[2]);
			equip.setBoots(equipment[3]);
			equip.setItemInMainHand(equipment[4]);
			if(equipment.length == 6)
				equip.setItemInOffHand(equipment[5]);
			switch(type.getSpeed()) {
				case FAST: ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)); break;
				case SLOW: ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1)); break;
				default: break;
			}
			alive.add(ent);
			if(usedList) {
				type = null;
				spawnedTypes.remove(0);
			} else
				spawnedTypes.add(type);
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