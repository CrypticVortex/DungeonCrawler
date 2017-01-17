package com.crypticvortex.dc.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.crypticvortex.dc.DungeonCrawler;

import net.md_5.bungee.api.ChatColor;

/**
 * This class will be responsible for the entire algorithm that determines what items go in chests, and what items get dropped by which mobs.
 * 
 * @author Vortex
 */
public class LootGenerator {

	public static List<ItemStack> generateDrops(EntityDeathEvent event) {
		Random rand = new Random();
		float perc = rand.nextFloat() * 100;
		List<ItemStack> drops = new ArrayList<ItemStack>();
		if(event.getEntityType() != EntityType.PLAYER) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) event.getEntity();
				//Player killer = entity.getKiller();
				//DPlayer player = DungeonCrawler.players.get(killer.getUniqueId());
				if(entity.getCustomName() != null) {
					String name = entity.getCustomName();
					if(name.contains(" ")) name = ChatColor.stripColor(name.substring(0, entity.getCustomName().indexOf(" ")));
					MobTable ent = MobTable.getEntityByName(name);
					if(ent != null) {
						List<ItemStack> possible = LootTable.possibleDrops(perc, ent.getDropRarity());
						if(possible.size() <= 3) {
							drops.addAll(possible);
						} else {
							for(int i = 0; i < rand.nextInt(possible.size() - 1); i++)
								drops.add(possible.get(rand.nextInt(possible.size() - 1)));
						}
					}
				}
			}
		}
		return drops;
	}
	
	public static void generateDrops(LivingEntity entity, List<Player> killers) {
		
	}
	
	public static void populateTest(World world) {
		Bukkit.getScheduler().runTaskAsynchronously(DungeonCrawler.INSTANCE, new Runnable() {
			public void run() {
				for(Chunk chunk : world.getLoadedChunks()) {
					System.out.println("[" + chunk.getX() + "," + chunk.getZ() + "] contains (" + chunk.getTileEntities().length + ") TileEntities.");
					for(BlockState state : chunk.getTileEntities()) {
						if(state instanceof Chest) {
							Block b = state.getBlock();
							Chest chest = (Chest) state;
							if(b.getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK) {
								chest.getInventory().setItem(0, LootTable.itemTable.get(35.0f)[0]);
							}
						}
						if(state instanceof DoubleChest) {
							
						}
					}
				}
			}
		});
	}
	
}