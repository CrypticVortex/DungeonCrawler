package com.crypticvortex.dc.core.spells;

import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;
import com.crypticvortex.dc.core.loot.LootTable;

public class MageSpellIceQuake extends Spell {
	
	public MageSpellIceQuake() { // Lightning - R-L-L, Blink R-R-R
		super("Ice Quake", new MButton[] { MButton.RIGHT_CLICK, MButton.RIGHT_CLICK, MButton.LEFT_CLICK });
	}
	
	public boolean cast(DPlayer player) {
		Player pl = player.player;
		Random rand = new Random();
		String damage = LootTable.getData(pl.getInventory().getItemInMainHand()).get("Damage");
		int minDamage = 0, maxDamage = 0;
		if(damage.contains("-")) {		
			minDamage = Integer.parseInt(damage.split("-")[0]);
			maxDamage = Integer.parseInt(damage.split("-")[1]);
		} else
			maxDamage = Integer.parseInt(damage);
		
		for(Entity e : pl.getNearbyEntities(10, 1, 10))
			if(e instanceof LivingEntity)
				((LivingEntity) e).damage(getDamage() + (maxDamage != minDamage ? (rand.nextInt(maxDamage - minDamage) + minDamage) : maxDamage), pl);
		
		for(int i = 0; i < 20; i++) { // 20 iterations spawning 3 particles at the random location
			pl.getWorld().spawnParticle(Particle.SPELL_MOB, pl.getLocation(), 5, rand.nextInt(11), 0, rand.nextInt(11), 0);
		}
		
		return true;
	}
	
	@Override
	public double getDamage() {
		return 7.5;
	}
	
	@Override
	public int getCost() {
		return 6;
	}
	
	public int getCooldown() {
		return 7;
	}

}
