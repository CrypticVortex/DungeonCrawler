package com.crypticvortex.dc.core.spells;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;
import com.crypticvortex.dc.core.Spell;

public class MageSpellBlink extends Spell {
	
	public MageSpellBlink() {
		super("Blink", new MButton[] { MButton.RIGHT_CLICK, MButton.RIGHT_CLICK, MButton.RIGHT_CLICK });
	}
	
	public void cast(DPlayer player) {
		// --------- Teleport Code ----------
		Player pl = player.player;
		HashSet<Material> transparent = new HashSet<Material>();
		transparent.add(Material.AIR);
		Block b = pl.getTargetBlock(transparent, 7);
		Location tp = b.getLocation();
		//int yOff = pl.getWorld().getHighestBlockYAt(tp);
		//tp.setY(yOff);
		// TODO : Check if both blocks of the teleport location are free, if not cancel the teleport and inform the player.
		tp.setDirection(pl.getLocation().getDirection());
		pl.teleport(tp);
		// --------- Teleport Effects & Sound ----------
		pl.getWorld().playSound(tp, Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
	}
	
}