package com.crypticvortex.dc.core.spells;

import java.util.HashSet;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;
import com.crypticvortex.dc.core.loot.LootTable;

public class MageSpellLightning extends Spell {

	public MageSpellLightning() {
		super("Lightning Strike", new MButton[] { MButton.RIGHT_CLICK, MButton.LEFT_CLICK, MButton.LEFT_CLICK });
	}

	public boolean cast(DPlayer player) {
		Player pl = player.player;
		Random rand = new Random();
		HashSet<Material> transparent = new HashSet<Material>();
		transparent.add(Material.AIR);
		Block b = pl.getTargetBlock(transparent, 15);
		b.getWorld().strikeLightningEffect(b.getLocation());
		String damage = LootTable.getData(pl.getInventory().getItemInMainHand()).get("Damage");
		int minDamage = 0, maxDamage = 0;
		if(damage.contains("-")) {
			minDamage = Integer.parseInt(damage.split("-")[0]);
			maxDamage = Integer.parseInt(damage.split("-")[1]);
		} else
			maxDamage = Integer.parseInt(damage);
		for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 2, 2, 2))
			if(e instanceof LivingEntity)
				((LivingEntity) e).damage(getDamage() + (maxDamage != minDamage ? (rand.nextInt(maxDamage - minDamage) + minDamage) : maxDamage), pl);
		return true;
	}
	
	@Override
	public double getDamage() {
		return 2.5;
	}
	
	@Override
	public int getCooldown() {
		return 4;
	}
	
	@Override
	public int getCost() {
		return 4; 
	}

}