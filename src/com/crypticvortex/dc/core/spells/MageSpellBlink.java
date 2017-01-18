package com.crypticvortex.dc.core.spells;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;
import com.crypticvortex.dc.core.Spell;

public class MageSpellBlink extends Spell {
	
	public MageSpellBlink() {
		super("Blink", new MButton[] { MButton.RIGHT_CLICK, MButton.RIGHT_CLICK, MButton.RIGHT_CLICK });
	}
	
	public boolean cast(DPlayer player) {
		// --------- Teleport Code ----------
		Player pl = player.player;
		HashSet<Material> transparent = new HashSet<Material>();
		transparent.add(Material.AIR);
		Block b = pl.getTargetBlock(transparent, 7);
		Location tp = b.getLocation();
		if(b.getType() != Material.AIR) tp = b.getRelative(BlockFace.UP).getLocation();
		tp.setDirection(pl.getLocation().getDirection());
		if(tp.getBlock().getType() == Material.AIR && tp.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR && tp.getY() - pl.getLocation().getY() <= 3) {
			pl.teleport(tp);
			// --------- Teleport Effects & Sound ----------
			pl.getWorld().playSound(tp, Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
			return true;
		} else {
			pl.sendMessage("§cUnable to teleport to that location!");
			return false;
		}
	}
	
	@Override
	public int getCooldown() {
		return 6;
	}
	
}