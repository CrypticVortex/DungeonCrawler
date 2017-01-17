package com.crypticvortex.dc.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.BlockIterator;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;
import com.crypticvortex.dc.core.PlayerClass;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(!DungeonCrawler.players.containsKey(event.getPlayer().getUniqueId()))
			DungeonCrawler.players.put(event.getPlayer().getUniqueId(), new DPlayer(event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		// TODO : Unbind your currently selected class and inform you to set it once you rejoin.
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		
		Player player = event.getPlayer();
		DPlayer pl = DungeonCrawler.players.get(player.getUniqueId());	
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {	
			if(PlayerClass.MAGE.getName().equals(PlayerClass.getForWeapon(player.getInventory().getItemInMainHand()).getName()))
				castSpell(player);
			pl.combo(MButton.LEFT_CLICK);
		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(pl.getCurrentClass().getName().equals(PlayerClass.MAGE.getName()) && !PlayerClass.isClassWeapon(player.getInventory().getItemInMainHand())) return;
			if(player.getInventory().getItemInMainHand().getType() == Material.BOW && pl.currentCombo.size() >= 1)
				pl.combo(MButton.RIGHT_CLICK);
			else
				pl.combo(MButton.RIGHT_CLICK);
		}
	}
	
	private void castSpell(Player player) {
		List<Entity> entities = player.getNearbyEntities(17, 3, 17);
		Bukkit.getScheduler().runTask(DungeonCrawler.INSTANCE, new Runnable() {
			public void run() {
				BlockIterator iterator = new BlockIterator(player.getWorld(), player.getEyeLocation().toVector(), player.getLocation().getDirection(), 0, 15);
				int damaged = 0;
				while(iterator.hasNext()) {
					Block b = iterator.next();
					for(Entity e : entities) {
						if(e instanceof LivingEntity) {
							if(e.getLocation().distance(b.getLocation().subtract(0, 1, 0)) <= 1.25) {
								((LivingEntity) e).damage(2, player);
								damaged += 1;
							}
						}
					}
					player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, b.getLocation(), 1, 0, 0, 0, 0); // Particle, Location, Count, xOffset, yOffset, zOffset, Extra
					if(damaged > 0) break;
				}
			}
		});
	}
	
}