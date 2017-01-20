package com.crypticvortex.dc.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.BlockIterator;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;
import com.crypticvortex.dc.core.PlayerClass;
import com.crypticvortex.dc.core.loot.LootTable;

public class PlayerListener implements Listener {
	public static Map<Player, AtomicInteger> mageAttack = new HashMap<Player, AtomicInteger>();

	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event) {
		if (event.getItem().getItemStack().getType() == Material.BOW && DungeonCrawler.players.get(event.getPlayer().getUniqueId()).getCurrentClass().getName().equals(PlayerClass.MAGE.getName())) event.getItem().getItemStack().addEnchantment(Enchantment.ARROW_INFINITE, 1);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!DungeonCrawler.players.containsKey(event.getPlayer().getUniqueId())) {
			DungeonCrawler.players.put(event.getPlayer().getUniqueId(), new DPlayer(event.getPlayer()));
			// TODO : Load players character data and give them their previously selected class.
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (DungeonCrawler.players.containsKey(event.getPlayer().getUniqueId())) {
			DPlayer pl = DungeonCrawler.players.get(event.getPlayer().getUniqueId());
			pl.setClass(null);
			// TODO : Save players character data
			DungeonCrawler.players.remove(event.getPlayer().getUniqueId());
		}
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event) {
		((Player) event.getEntity()).setSaturation(20);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (DungeonCrawler.currentLevel == null) return;
		if (event.getHand() == EquipmentSlot.OFF_HAND) return;
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		DPlayer pl = DungeonCrawler.players.get(player.getUniqueId());

		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

			if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if ((block.getType() == Material.DOUBLE_STEP || block.getType() == Material.FLOWER_POT) && DungeonCrawler.currentLevel.isSecret(block.getLocation())) {
					DungeonCrawler.currentLevel.brokenSecrets.add(block.getLocation());
					block.setType(Material.AIR);
					player.getWorld().playSound(block.getLocation(), (block.getType() == Material.DOUBLE_STEP ? Sound.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD : Sound.BLOCK_GLASS_BREAK), SoundCategory.MASTER, 1, (block.getType() == Material.DOUBLE_STEP ? 0.3f : 0.1f));
				}
			}

			if (pl.hasClass()) {
				if (PlayerClass.isClassWeapon(player.getInventory().getItemInMainHand())) {
					if (pl.getCurrentClass().getName().equals(PlayerClass.MAGE.getName()) && !PlayerClass.isClassWeapon(player.getInventory().getItemInMainHand())) return;
					if (PlayerClass.MAGE.getName().equals(PlayerClass.getForWeapon(player.getInventory().getItemInMainHand()).getName()) && pl.getCurrentClass().getName().equals(PlayerClass.MAGE.getName()) && pl.currentCombo.size() <= 0 && !mageAttack.containsKey(event.getPlayer()))
						castSpell(player);
					else {
						if (pl.currentCombo.size() > 0)
							pl.combo(MButton.LEFT_CLICK);
						else if (player.isSneaking()) pl.combo(MButton.LEFT_CLICK);
					}
				}
			}

		}

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (pl.hasClass()) {
				if (!PlayerClass.isClassWeapon(player.getInventory().getItemInMainHand())) return;
				if (player.getInventory().getItemInMainHand().getType() == Material.BOW && pl.currentCombo.size() >= 1)
					pl.combo(MButton.RIGHT_CLICK);
				else {
					if (pl.currentCombo.size() > 0)
						pl.combo(MButton.RIGHT_CLICK);
					else if (player.isSneaking()) pl.combo(MButton.RIGHT_CLICK);
				}
			}

		}
	}

	private void castSpell(Player player) { // Mage basic attack
		Random rand = new Random();
		String damage = LootTable.getData(player.getInventory().getItemInMainHand()).get("Damage");
		int minDamage, maxDamage;
		if (damage.contains("-")) {
			String[] parts = damage.split("-");
			minDamage = Integer.parseInt(parts[0].replaceAll(" ", ""));
			maxDamage = Integer.parseInt(parts[1].replaceAll(" ", ""));
		} else {
			minDamage = Integer.parseInt(damage);
			maxDamage = Integer.parseInt(damage);
		}
		List<Entity> entities = player.getNearbyEntities(17, 3, 17); // Gets entities just outside of effective range to be safe
		Bukkit.getScheduler().runTask(DungeonCrawler.INSTANCE, new Runnable() {
			public void run() {
				int block = 0;
				BlockIterator iterator = new BlockIterator(player.getWorld(), player.getEyeLocation().toVector(), player.getLocation().getDirection(), 0, 15);
				int damaged = 0;
				while (iterator.hasNext()) {
					Block b = iterator.next();
					if (b.getType() != Material.AIR) break;
					for (Entity e : entities) {
						if (e instanceof LivingEntity) {
							if (e.getLocation().distance(b.getLocation().subtract(0, 1, 0)) <= 1.25 || e.getLocation().distance(b.getLocation()) <= 1.25) {
								LivingEntity ent = (LivingEntity) e;
								if (ent.getType() != EntityType.PLAYER) {
									if (minDamage != maxDamage)
										ent.damage(rand.nextInt(maxDamage - minDamage) + minDamage, player);
									else
										ent.damage(minDamage, player);
									damaged += 1;
								}
							}
						}
					}
					b.getLocation().setY(player.getEyeLocation().getY());
					if(block % 2 == 0)
						player.getWorld().spawnParticle(Particle.CRIT_MAGIC, b.getLocation(), 1, 0, 0, 0, 0); // Particle, Location, Count, xOffset, yOffset, zOffset, Extra
					if (damaged > 0) break;
					block++;
				}
			}
		});
		mageAttack.put(player, new AtomicInteger(1));
	}

}