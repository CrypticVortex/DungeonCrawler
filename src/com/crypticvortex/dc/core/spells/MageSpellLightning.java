package com.crypticvortex.dc.core.spells;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;
import com.crypticvortex.dc.core.Spell;

public class MageSpellLightning extends Spell {

	public MageSpellLightning() {
		super("Lightning Strike", new MButton[] { MButton.RIGHT_CLICK, MButton.LEFT_CLICK, MButton.LEFT_CLICK });
	}

	public boolean cast(DPlayer player) {
		// --------- Lightning Strike Code ----------
		Player pl = player.player;
		HashSet<Material> transparent = new HashSet<Material>();
		transparent.add(Material.AIR);
		Block b = pl.getTargetBlock(transparent, 15);
		b.getWorld().strikeLightningEffect(b.getLocation());
		for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 2, 2, 2))
			if(e instanceof LivingEntity)
				((LivingEntity) e).damage(2.5, pl);
		return true;
	}
	
	@Override
	public int getCooldown() {
		return 4;
	}

}